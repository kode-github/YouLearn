package manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import connection.ConfiguredDataSource;

import bean.AccountBean;
import bean.AccountBean.Ruolo;
import bean.CorsoBean;
import bean.CorsoBean.Categoria;
import bean.CorsoBean.Stato;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class CorsoManager {
	
	AccountManager accountManager;
	LezioneManager lezioneManager;
	IscrizioneManager iscrizioneManager;
	DataSource dataSource;
	
	public CorsoManager() {
		Context ctx;
		try {
			ctx = new InitialContext();
			dataSource= (DataSource) ctx.lookup("java:/comp/env/jdbc/MyLocalDB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Recuper le informazioni su un corso
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 * @throws NotWellFormattedException 
	 */
	public CorsoBean doRetrieveByKey(int id) throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		Connection connection=null;
		PreparedStatement statement=null;
		CorsoBean corso=new CorsoBean();
		lezioneManager=new LezioneManager();
		iscrizioneManager=new IscrizioneManager();
		accountManager=new AccountManager();
		
		String sql="Select * from corso where idCorso=?";
		
		try {
			connection=dataSource.getConnection();
			statement=connection.prepareStatement(sql);
			statement.setInt(1,id);
			ResultSet rs=statement.executeQuery();
			if(!rs.next()) throw new NotFoundException("Il corso non esiste");
			
			corso.setIdCorso(id);
			corso.setNome(rs.getString("nome"));
			corso.setDescrizione(rs.getString("Descrizione"));
			corso.setDataCreazione(rs.getDate("DataCreazione"));
			corso.setDataFine(rs.getDate("dataFine"));
			corso.setCopertina(rs.getString("Copertina"));
			corso.setPrezzo(rs.getInt("Prezzo"));
			corso.setStato(Stato.valueOf(rs.getString("stato")));
			corso.setCategoria(Categoria.valueOf(rs.getString("categoria")));
			corso.setnLezioni(rs.getInt("nLezioni"));
			corso.setnIscritti(rs.getInt("nIscritti"));
			corso.setDocente(accountManager.doRetrieveByKey(rs.getString("accountCreatore"))); //recupero il docente
			corso.setSupervisore(accountManager.doRetrieveByKey(rs.getString("accountSupervisore"))); //recupero il supervisore
			lezioneManager.retrieveLezioniByCorso(corso); //recuperlo le lezioni
			iscrizioneManager.getIscrittiCorso(corso); //recupero il corso
			
		}finally {
			try {
				if(statement!=null)
					statement.close();
			}finally {
				connection.close();
			}
		}
		return corso;
	}
	
	/**
	 * Recupera le informazioni base sui corsi che hanno un nome corrispondente alla stringa di ricerca
	 * Non recupera info su supervisori e lezioni per evitare overhead (nel caso di 1000 corsi da 10 lezioni l'uno
	 * caricherei 10000 informazioni di cui solo, orientativamente, 60 saranno utili)--promemoria
	 * @param search la stringa da ricercare 
	 * @return i corsi corrispondenti alla ricerca
	 * @throws SQLException
	 * @throws NotFoundException 
	 */
	public Collection<CorsoBean> searchCorso(String search) throws SQLException, NotFoundException{
		if(search==null || search.equals("")) return null; //controllo che la stringa non sia vuota
		Connection connection=null;
		PreparedStatement statement=null;
		Collection<CorsoBean> collection=new LinkedList<>();
		accountManager=new AccountManager();
		
		String sql="Select * from corso where nome like %?%";
		
		try {
			connection=dataSource.getConnection();
			statement=connection.prepareStatement(sql);
			statement.setString(1,search);
			ResultSet rs=statement.executeQuery();
			while(rs.next()) {
				CorsoBean corso=new CorsoBean();
				corso.setIdCorso(rs.getInt("idCorso"));
				corso.setNome(rs.getString("nome"));
				corso.setDescrizione(rs.getString("Descrizione"));
				corso.setDataCreazione(rs.getDate("DataCreazione"));
				corso.setDataFine(rs.getDate("dataFine"));
				corso.setCopertina(rs.getString("Copertina"));
				corso.setPrezzo(rs.getInt("Prezzo"));
				corso.setStato(Stato.valueOf(rs.getString("stato")));
				corso.setCategoria(Categoria.valueOf(rs.getString("categoria")));
				corso.setnLezioni(rs.getInt("nLezioni"));
				corso.setnIscritti(rs.getInt("nIscritti"));
//				lezioneManager.retrieveLezioniByCorso(corso); //recuperlo le lezioni
//				iscrizioneManager.getIscrittiCorso(corso); //recupero il corso
				corso.setDocente(accountManager.doRetrieveByKey(rs.getString("accountCreatore"))); //recupero il docente
//				corso.setSupervisore(accountManager.doRetrieveByKey(rs.getString("accountSupervisore"))); //recupero il supervisore
				collection.add(corso);
			}
		}finally {
			try {
				if(statement!=null)
					statement.close();
			}finally {
				connection.close();
			}
		}
		return collection;
	}
 	
	/**
	 * Registra un nuovo corso 
	 * @param corso
	 * @return 
	 * @throws SQLException
	 * @throws NotWellFormattedException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 */
	public synchronized void creaCorso(CorsoBean corso,Part copertina) throws SQLException, NotWellFormattedException, NotFoundException, NoPermissionException {
		accountManager= new AccountManager();
		if(corso==null || corso.getIdCorso()!=null || !isWellFormatted(corso) || copertina==null) 
			throw new NotWellFormattedException("Il corso non � ben formattato");
		if(!accountManager.checkMail(corso.getDocente().getMail())) throw new NotFoundException("Il creatore non esiste");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO Corso VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			String filename=UUID.randomUUID().toString();
			String type=copertina.getSubmittedFileName().substring(copertina.getSubmittedFileName().indexOf('.'));
			
			if(!type.equals(".jpg") && !type.equals(".png") && !type.equals(".jpeg")) 
				throw new NotWellFormattedException("La copertina non ha un formato adeguato");
			filename=filename+type;
			
			connection=dataSource.getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, null);
			preparedStatement.setString(2, corso.getDocente().getMail());
			preparedStatement.setString(3, null);
			preparedStatement.setString(4, corso.getNome());
			preparedStatement.setString(5, corso.getDescrizione());
			preparedStatement.setDate(6, corso.getDataCreazione());
			preparedStatement.setDate(7, corso.getDataFine());
			preparedStatement.setString(8, filename);
			preparedStatement.setInt(9, corso.getPrezzo());
			preparedStatement.setString(10, corso.getStato().toString());
			preparedStatement.setString(11, corso.getCategoria().toString());
			preparedStatement.setInt(12, 0);
			preparedStatement.setInt(13, 0);
			System.out.println("Registrazione corso: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			//Recupero il corso appena inserito con la sua nuova chiave primaria
			preparedStatement=connection.prepareStatement("select idCorso\r\n" + 
					"from corso\r\n" + 
					"where idCorso=(select max(idCorso)\r\n" + 
					"				from corso)");
			ResultSet rs=preparedStatement.executeQuery();
			rs.next();
			corso.setIdCorso((rs.getInt("idCorso")));
			
			/* Salvo la copertina */
			Path path=Paths.get("C:\\Users\\Antonio\\Documents\\Universita\\IS\\Progetto\\"
					+ "YouLearn\\Code\\WebContent\\Resources\\"+corso.getIdCorso());
			if(!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
					Files.createDirectories(path); 
			path=Paths.get("C:\\Users\\Antonio\\Documents\\Universita\\IS\\Progetto\\"
					+ "YouLearn\\Code\\WebContent\\Resources\\"+corso.getIdCorso()+File.separator+
																				filename);
			copertina.write(path.toString());
			connection.commit();
		}catch(SQLException |IOException e) {
			e.printStackTrace();
			connection.rollback();
			throw new SQLException();
		}finally {
				try{
					if (preparedStatement != null)
						preparedStatement.close();
				}finally {
					connection.close();
				}		
		}
	}
	
	/**
	 * Aggiorna un corso
	 * @param corso
	 * @throws SQLException
	 */
	public void doUpdate(CorsoBean corso) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE Corso SET idCorso=?, accountCreatore=?, accountSupervisore=?, nome=?, descrizione=?, "
				+ " dataCreazione=?, dataFine=?, copertina=?, prezzo=?, stato=?, categoria=? "
				+ " WHERE idCorso = ?";

		try {
			connection=dataSource.getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, corso.getIdCorso());
			preparedStatement.setString(2, corso.getDocente().getMail());
			//Controllo se c'� da aggiornare il supervisore
			if(corso.getSupervisore()!=null)
				preparedStatement.setString(3, corso.getSupervisore().getMail());
			else
				preparedStatement.setString(3, null);
			preparedStatement.setString(4, corso.getNome());
			preparedStatement.setString(5, corso.getDescrizione());
			preparedStatement.setDate(6, corso.getDataCreazione());
			preparedStatement.setDate(7, corso.getDataFine());
			preparedStatement.setString(8, corso.getCopertina());
			preparedStatement.setInt(9, corso.getPrezzo());
			preparedStatement.setString(10, corso.getStato().toString());
			preparedStatement.setString(11, corso.getCategoria().toString());
			preparedStatement.setInt(12, corso.getIdCorso());
			preparedStatement.executeUpdate();
			connection.commit();
		} finally {
				try{
					if (preparedStatement != null)
						preparedStatement.close();
				}finally {
					connection.close();
				}		
		}
		
	}
	
	/**
	 * Modifica un corso
	 * @param corso
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 * @throws SQLException
	 * @throws NoPermissionException 
	 * @throws IOException 
	 */
	public void modificaCorso(CorsoBean corso,Part file) throws NotFoundException, NotWellFormattedException, SQLException, NoPermissionException, IOException {
		if(!checkCorso(corso.getIdCorso())) throw new NotFoundException("Il corso non esiste");
		if(!isWellFormatted(corso) || corso.getIdCorso()==null) throw new NotWellFormattedException("Il corso non � ben formattato");
		if(!corso.getStato().equals(Stato.Completamento)) throw new NoPermissionException("Non si pu� modificare un corso non in "
																								+ "completamento");
		/* Salvo la copertina */
		if(file!=null) {
			Path path=Paths.get("C:\\Users\\Antonio\\Documents\\Universita\\IS\\Progetto\\"
					+ "YouLearn\\Code\\WebContent\\Resources\\"+corso.getIdCorso());
			if(!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
					Files.createDirectories(path); 
			
//			String filename=UUID.randomUUID().toString();
			//Riuso il vecchio nome
			String filename=corso.getCopertina().substring(corso.getCopertina().indexOf(corso.getIdCorso()+"\\")+2, corso.getCopertina().indexOf('.'));
			String type=file.getSubmittedFileName().substring(file.getSubmittedFileName().indexOf('.'));
			
			if(!type.equals(".jpg") && !type.equals(".png") && !type.equals(".jpeg")) 
				throw new NotWellFormattedException("La copertina non ha un formato adeguato");
			
			path=Paths.get("C:\\Users\\Antonio\\Documents\\Universita\\IS\\Progetto\\"
					+ "YouLearn\\Code\\WebContent\\Resources\\"+corso.getIdCorso()+File.separator+
																				filename+type);
			System.out.println(path.toString());
			file.write(path.toString());
			corso.setCopertina(filename+type); //Assegno al corso la copertina appena salvata
		}
		/* Aggiorno il corso */
		doUpdate(corso);
	}
	
	public void removeCorso(int idCorso) throws SQLException, NotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM Corso WHERE idCorso=? AND stato=?";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, idCorso);
			preparedStatement.setString(2, Stato.Completamento.toString());
			System.out.println("doDelete: "+ preparedStatement.toString());
			int result=preparedStatement.executeUpdate();
			
			if(result==0) throw new NotFoundException("Il corso non esiste oppure non � in completamento");
			
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				connection.close();
			}
		}
	}
	

	/**
	 * Convalida un corso in attesa di supervisione
	 * @param accetta se true allora accetta, altrimenti rifiuta
	 * @param corso corso da convalidare
	 * @throws NotFoundException IL CORSO non esiste
	 * @throws NotWellFormattedException Il corso non � ben formattato
	 * @throws NoPermissionException Il corso non � in stato di attesa
	 * @throws SQLException
	 */
	public void convalidaCorso(boolean accetta,CorsoBean corso) throws NotFoundException, NotWellFormattedException, NoPermissionException, SQLException {
		if(!isWellFormatted(corso) || corso.getIdCorso()==null) throw new NotWellFormattedException("Il corso non � ben formattato");
		if(!checkCorso(corso)) throw new NotFoundException("Il corso non esiste");
		if(!corso.getStato().equals(Stato.Attesa)) throw new NoPermissionException("Non si pu� confermare un corso non in attesa");
		
		if(accetta)
			corso.setStato(Stato.Attivo);
		else
			corso.setStato(Stato.Completamento);
		doUpdate(corso);
	}
	
	/**
	 * Conferma un corso in fase di completamento
	 * @param corso il corso da confemare
	 * @throws NotWellFormattedException 
	 * @throws NotFoundException
	 * @throws NoPermissionException
	 * @throws SQLException
	 */
	public void confermaCorso(CorsoBean corso) throws NotWellFormattedException, NotFoundException, NoPermissionException, SQLException {
		if(corso==null || !isWellFormatted(corso) || corso.getIdCorso()==null) throw new NotWellFormattedException("Il corso non � ben formattato");
		if(!checkCorso(corso)) throw new NotFoundException("Il corso non esiste");
		if(!corso.getStato().equals(Stato.Completamento)) throw new NoPermissionException("Non si pu� confermare un corso non in completamento");
		
		accountManager=new AccountManager();
		corso.setStato(Stato.Attesa);
		AccountBean sup=accountManager.retrieveLessUsedSupervisor();
		corso.setSupervisore(sup);
		
		doUpdate(corso);
	}


	/**
	 * Recupera gli account tenuti da un docente
	 * @param account 
	 * @return
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException 
	 */
	public Collection<CorsoBean> retrieveByCreatore(AccountBean account) throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		accountManager= new AccountManager();
		lezioneManager= new LezioneManager();
		if(!accountManager.isWellFormatted(account)) throw new NotWellFormattedException("L'account non � ben formattato");
		if(!accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non pu� avere corsi creati");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<CorsoBean> collection= new LinkedList<CorsoBean>();
		
		String sql="SELECT* FROM Corso WHERE accountCreatore=?";
		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			ResultSet rs= preparedStatement.executeQuery();
			while(rs.next()) {
			CorsoBean corso= new CorsoBean();
				 corso.setIdCorso(rs.getInt("idCorso"));
				 corso.setNome(rs.getString("nome"));
				 corso.setDescrizione(rs.getString("Descrizione"));
				 corso.setCopertina(rs.getString("copertina"));
				 corso.setPrezzo(rs.getInt("prezzo"));
				 corso.setStato(Stato.valueOf(rs.getString("stato")));
				 corso.setDataCreazione(rs.getDate("dataCreazione"));
				 corso.setDataFine(rs.getDate("DataFine"));
				 corso.setnLezioni(rs.getInt("nLezioni"));
				 corso.setnIscritti(rs.getInt("nIscritti"));
				 corso.setCategoria(Categoria.valueOf(rs.getString("Categoria")));
				 corso.setStato(Stato.valueOf(rs.getString("stato")));
				 corso.setDocente(account);
				 lezioneManager.retrieveLezioniByCorso(corso);
				 //non carichiamo il supervisore
				 collection.add(corso);
			}
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				connection.close();
			}
		}
		return collection;
	}

	/**
	 * Recupera i corsi supervisionati da un certo supervisore
	 * @param account
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException 
	 */
	public Collection<CorsoBean> doRetrieveBySupervisore(AccountBean account) throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		accountManager= new AccountManager();
		lezioneManager= new LezioneManager();
		if(!accountManager.isWellFormatted(account)) throw new NotWellFormattedException("L'account non � ben formattato");
		if(!accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Supervisore)) throw new NoPermissionException("Questo utente non pu� avere corsi da supervisionare");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<CorsoBean> collection= new LinkedList<CorsoBean>();
		
		String sql="SELECT* FROM Corso WHERE accountSupervisore=?";
		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			ResultSet rs= preparedStatement.executeQuery();
			while(rs.next()) {
				CorsoBean corso= new CorsoBean();
				 corso.setIdCorso(rs.getInt("idCorso"));
				 corso.setNome(rs.getString("nome"));
				 corso.setDescrizione(rs.getString("Descrizione"));
				 corso.setCopertina(rs.getString("copertina"));
				 corso.setStato(Stato.valueOf(rs.getString("stato")));
				 corso.setDataCreazione(rs.getDate("dataCreazione"));
				 corso.setDataFine(rs.getDate("DataFine"));
				 corso.setnLezioni(rs.getInt("nLezioni"));
				 corso.setnIscritti(rs.getInt("nIscritti"));
				 corso.setCategoria(Categoria.valueOf(rs.getString("Categoria")));
				 corso.setStato(Stato.valueOf(rs.getString("stato")));
				 corso.setSupervisore(account);
				 lezioneManager.retrieveLezioniByCorso(corso);
				 corso.setDocente(accountManager.doRetrieveByKey(rs.getString("accountCreatore")));
				 collection.add(corso);
			}
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				connection.close();
			}
		}
		return collection;
		
	}


	/**
	 * Controlla se un certo corso � corretto ed esiste
	 * @param corso
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkCorso(CorsoBean corso) throws SQLException {
		Connection connection=null;
		PreparedStatement statement=null;
		
		String sql="Select idCorso from corso where idCorso=? AND nome=? AND descrizione=? AND dataCreazione=? AND dataFine=? AND"
				+ " copertina=? AND prezzo=? AND stato=? AND categoria=? AND nLezioni=? AND nIscritti=? AND accountCreatore=?";
		
		try {
			connection=dataSource.getConnection();
			statement=connection.prepareStatement(sql);
			statement.setInt(1,corso.getIdCorso());
			statement.setString(2, corso.getNome());
			statement.setString(3, corso.getDescrizione());
			statement.setDate(4, corso.getDataCreazione());
			statement.setDate(5, corso.getDataFine());
			statement.setString(6, corso.getCopertina());
			statement.setInt(7, corso.getPrezzo());
			statement.setString(8, corso.getStato().toString());
			statement.setString(9, corso.getCategoria().toString());
			statement.setInt(10, corso.getnLezioni());
			statement.setInt(11, corso.getnIscritti());
			statement.setString(12, corso.getDocente().getMail());
			System.out.println("CheckCorso: "+statement.toString());
			return (statement.executeQuery()).next();
			
		}finally {
			try {
				if(statement!=null)
					statement.close();
			}finally {
				connection.close();
			}
		}
	}
	
	/**
	 * Controlla se un certo corso esiste
	 * @param idCorso id del corso
	 * @return true se esiste il corso, false altrimenti
	 * @throws SQLException 
	 */
	public boolean checkCorso(int idCorso) throws SQLException {
		Connection connection=null;
		PreparedStatement statement=null;
		
		String sql="Select idCorso from corso where idCorso=?";
		
		try {
			connection=dataSource.getConnection();
			statement=connection.prepareStatement(sql);
			statement.setInt(1,idCorso);
			return (statement.executeQuery()).next();
		}finally {
			try {
				if(statement!=null)
					statement.close();
			}finally {
				connection.close();
			}
		}
	
	}
	
	/**
	 * Controlla se gli elementi principali di corso hanno una formattazione corretta
	 * NON CONTROLLA L'ID DEL CORSO IN QUANTO DEVE ESSERE NULL IN FASE DI REGISTRAZIONE
	 * TODO Da testare BENE che non so se funzioni benissimo
	 * @param corso
	 * @return
	 */
	public boolean isWellFormatted(CorsoBean corso) {
		Date dataOdierna = new Date();
//		
//		return  corso.getNome()!=null && corso.getNome().matches("^[a-zA-Z]{2,20}") && 
//				corso.getDescrizione()!=null && corso.getDescrizione().matches("^[a-zA-Z0-9]{0,2048}") &&
//				corso.getDataCreazione()!=null && corso.getDataFine()!=null && corso.getDataCreazione().before(corso.getDataFine())
//				&& !corso.getDataCreazione().after(dataOdierna) && ( corso.getLezioni()==null || corso.getnLezioni()==corso.getLezioni().size())
//				&& (corso.getIscrizioni()==null || corso.getnIscritti()==corso.getIscrizioni().size()) && corso.getCopertina()!=null && 
//				corso.getCopertina().matches("^[a-zA-Z\\.]{1,255}") && corso.getDocente()!=null && corso.getCategoria()!=null
//				&& corso.getStato()!=null;
		return true;
	}

}
