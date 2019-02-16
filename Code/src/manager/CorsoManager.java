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
import java.util.Comparator;
import java.sql.Date;
import java.util.LinkedList;
import java.util.UUID;

import javax.naming.NoPermissionException;
import javax.servlet.http.Part;


import bean.AccountBean;
import bean.AccountBean.Ruolo;
import bean.CorsoBean;
import bean.CorsoBean.Categoria;
import bean.CorsoBean.Stato;
import connection.DriverManagerConnectionPool;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class CorsoManager {
	
	private static CorsoManager istanza;
	private LezioneManager lezioneManager;
	private AccountManager accountManager;
	private IscrizioneManager iscrizioneManager;
	private static String PATH;
	
	private CorsoManager() {}
	
	public static CorsoManager getIstanza(String path) {
		if(istanza==null)
			istanza=new CorsoManager();
		PATH=path;
		return istanza;
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
	public synchronized CorsoBean doRetrieveByKey(int id) throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		Connection connection=null;
		PreparedStatement statement=null;
		CorsoBean corso=new CorsoBean();
		lezioneManager=LezioneManager.getIstanza("");
		iscrizioneManager=IscrizioneManager.getIstanza();
		accountManager=AccountManager.getIstanza();
		
		String sql="Select * from corso where idCorso=?";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
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
			if(corso.getStato()!=Stato.Completamento)
				corso.setSupervisore(accountManager.doRetrieveByKey(rs.getString("accountSupervisore"))); //recupero il supervisore
			lezioneManager.retrieveLezioniByCorso(corso); //recuperlo le lezioni
			iscrizioneManager.getIscrittiCorso(corso); //recupero il corso
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				if(statement!=null)
					statement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
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
	public synchronized Collection<CorsoBean> searchCorso(String search) throws SQLException, NotFoundException{
		if(search==null || search.equals("")) return null; //controllo che la stringa non sia vuota
		Connection connection=null;
		PreparedStatement statement=null;
		Collection<CorsoBean> collection=new LinkedList<>();
		accountManager=AccountManager.getIstanza();
		
		String sql="Select * from corso where (nome like ? OR categoria=?) AND stato='Attivo' ";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			statement=connection.prepareStatement(sql);
			statement.setString(1,"%" + search + "%");
			statement.setString(2, search);
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
				DriverManagerConnectionPool.releaseConnection(connection);
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
		accountManager= AccountManager.getIstanza();
		if(corso==null || corso.getIdCorso()!=null || !isWellFormatted(corso) || copertina==null) 
			throw new NotWellFormattedException("Il corso non ï¿½ ben formattato");
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
			
			connection=DriverManagerConnectionPool.getConnection();
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
			Path test=Paths.get(PATH+File.separator+"Resources"+File.separator+corso.getIdCorso());
			if(!Files.isDirectory(test, LinkOption.NOFOLLOW_LINKS))
				Files.createDirectories(test);
			test=Paths.get(PATH+File.separator+"Resources"+File.separator+corso.getIdCorso()+File.separator+filename);
			
			copertina.write(test.toString());
			if(!connection.getAutoCommit())
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
					DriverManagerConnectionPool.releaseConnection(connection);
				}		
		}
	}
	
	/**
	 * Aggiorna un corso
	 * @param corso
	 * @throws SQLException
	 * @throws NotWellFormattedException 
	 */
	public synchronized void doUpdate(CorsoBean corso) throws SQLException, NotWellFormattedException {
		if(corso==null || !isWellFormatted(corso)) throw new NotWellFormattedException("Il corso non è ben formattato");
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE Corso SET idCorso=?, accountCreatore=?, accountSupervisore=?, nome=?, descrizione=?, "
				+ " dataCreazione=?, dataFine=?, copertina=?, prezzo=?, stato=?, categoria=? "
				+ " WHERE idCorso = ?";

		try {
			connection=DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, corso.getIdCorso());
			preparedStatement.setString(2, corso.getDocente().getMail());
			//Controllo se c'Ã¨ da aggiornare il supervisore
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
			if(!connection.getAutoCommit())
				connection.commit();
		} finally {
				try{
					if (preparedStatement != null)
						preparedStatement.close();
				}finally {
					DriverManagerConnectionPool.releaseConnection(connection);
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
	public synchronized void modificaCorso(CorsoBean corso,Part file) throws NotFoundException, NotWellFormattedException, SQLException, NoPermissionException, IOException {
		if(!checkCorso(corso.getIdCorso())) throw new NotFoundException("Il corso non esiste");
		if(!isWellFormatted(corso) || corso.getIdCorso()==null) throw new NotWellFormattedException("Il corso non ï¿½ ben formattato");
		if(!corso.getStato().equals(Stato.Completamento)) throw new NoPermissionException("Non si puï¿½ modificare un corso non in "
																								+ "completamento");
		/* Salvo la copertina */
		if(file!=null) {
			Path path=Paths.get(PATH+File.separator+"Resources"+File.separator+corso.getIdCorso());
			if(!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
				Files.createDirectories(path);
			//Riuso il vecchio nome
			String filename=corso.getCopertina().substring(corso.getCopertina().indexOf(corso.getIdCorso()+File.separator)+2, corso.getCopertina().indexOf('.'));
			String type=file.getSubmittedFileName().substring(file.getSubmittedFileName().indexOf('.'));
			
			if(!type.equals(".jpg") && !type.equals(".png") && !type.equals(".jpeg")) 
				throw new NotWellFormattedException("La copertina non ha un formato adeguato");
			
			path=Paths.get(PATH+File.separator+"Resources"+File.separator+corso.getIdCorso()+File.separator+filename);
			file.write(path.toString());
			corso.setCopertina(filename+type); //Assegno al corso la copertina appena salvata
		}
		/* Aggiorno il corso */
		doUpdate(corso);
	}
	
	/**
	 * Elimina un corso
	 * @param idCorso
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws IOException 
	 */
	public synchronized void removeCorso(int idCorso) throws SQLException, NotFoundException, IOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "delete from corso where idCorso = ? and stato=?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, idCorso);
			preparedStatement.setString(2, Stato.Completamento.toString());
			System.out.println("doDelete: "+ preparedStatement.toString());
			int result=preparedStatement.executeUpdate();
			
			if(result==0) throw new NotFoundException("Il corso non esiste oppure non Ã¨ in completamento");
			if(!connection.getAutoCommit())
				connection.commit();
			
			Path toDelete=Paths.get(PATH+File.separator+"Resources"+File.separator+idCorso);
			/*
			 * walk: esamina ogni file presente nella directory 
			 * sorted: ordina le cartelle nell'ordine inverso rispetto a come vengono esaminate da walk, così da avere i file all'inizio
			 * map: per ogni path, ne restituisce un file
			 * forEach: per ogni file, cancellalo
			 */
			if(Files.isDirectory(toDelete, LinkOption.NOFOLLOW_LINKS))
				Files.walk(toDelete).sorted(Comparator.reverseOrder()).map(s->s.toFile()).peek(System.out::println).forEach(s->s.delete());
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	

	/**
	 * Convalida un corso in attesa di supervisione
	 * @param accetta se true allora accetta, altrimenti rifiuta
	 * @param corso corso da convalidare
	 * @throws NotFoundException IL CORSO non esiste
	 * @throws NotWellFormattedException Il corso non ï¿½ ben formattato
	 * @throws NoPermissionException Il corso non ï¿½ in stato di attesa
	 * @throws SQLException
	 */
	public synchronized void convalidaCorso(boolean accetta,CorsoBean corso) throws NotFoundException, NotWellFormattedException, NoPermissionException, SQLException {
		if(corso.getIdCorso()==null || !isWellFormatted(corso) ) throw new NotWellFormattedException("Il corso non ï¿½ ben formattato");
		if(!checkCorso(corso.getIdCorso())) throw new NotFoundException("Il corso non esiste");
		if(!corso.getStato().equals(Stato.Attesa)) throw new NoPermissionException("Non si puï¿½ confermare un corso non in attesa");
		
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
	public synchronized void confermaCorso(CorsoBean corso) throws NotWellFormattedException, NotFoundException, NoPermissionException, SQLException {
		if(corso==null || corso.getIdCorso()==null || !isWellFormatted(corso)) throw new NotWellFormattedException("Il corso non ï¿½ ben formattato");
		if(!checkCorso(corso.getIdCorso())) throw new NotFoundException("Il corso non esiste");
		if(!corso.getStato().equals(Stato.Completamento)) throw new NoPermissionException("Non si puï¿½ confermare un corso non in completamento");
		
		accountManager=AccountManager.getIstanza();
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
	public synchronized Collection<CorsoBean> retrieveByCreatore(AccountBean account) throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		accountManager= AccountManager.getIstanza();
		lezioneManager= LezioneManager.getIstanza("");
		if(account==null || !accountManager.isWellFormatted(account)) throw new NotWellFormattedException("L'account non ï¿½ ben formattato");
		if(!accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non puï¿½ avere corsi creati");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<CorsoBean> collection= new LinkedList<CorsoBean>();
		
		String sql="SELECT* FROM Corso WHERE accountCreatore=?";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			System.out.println("RetriveByCreatore: "+preparedStatement.toString());
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
				 //Non recupera le iscrizioni
				 collection.add(corso);
			}
			account.setCorsiTenuti(collection);
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
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
	public synchronized Collection<CorsoBean> doRetrieveBySupervisore(AccountBean account) throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		accountManager= AccountManager.getIstanza();
		lezioneManager= LezioneManager.getIstanza("");
		if(account==null) throw new NotWellFormattedException("l'account è null");
		if(!accountManager.isWellFormatted(account)) throw new NotWellFormattedException("L'account non ï¿½ ben formattato");
		if(!accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Supervisore)) throw new NoPermissionException("Questo utente non puï¿½ avere corsi da supervisionare");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<CorsoBean> collection= new LinkedList<CorsoBean>();
		
		String sql="SELECT* FROM Corso WHERE accountSupervisore=? AND stato='Attesa'";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			System.out.println("RetriveBySupervisore: "+preparedStatement.toString());
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
				 System.out.println("\n\nStato di corso in retrieve by sup: "+ corso.getStato());
				 corso.setSupervisore(account);
				 corso.setDocente(accountManager.doRetrieveByKey(rs.getString("accountCreatore")));

				 lezioneManager.retrieveLezioniByCorso(corso);
				 collection.add(corso);
			}
			account.setCorsiSupervisionati(collection);
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return collection;
		
	}


	/**
	 * Controlla se un certo corso ï¿½ corretto ed esiste
	 * @param corso
	 * @return
	 * @throws SQLException 
	 */
	public synchronized boolean checkCorso(CorsoBean corso) throws SQLException {
		if(corso==null) return false;
		Connection connection=null;
		PreparedStatement statement=null;
		
		String sql="Select idCorso from corso where idCorso=? AND nome=? AND descrizione=? AND dataCreazione=? AND dataFine=? AND"
				+ " copertina=? AND prezzo=? AND stato=? AND categoria=? AND nLezioni=? AND nIscritti=? AND accountCreatore=?";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
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
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	/**
	 * Controlla se un certo corso esiste
	 * @param idCorso id del corso
	 * @return true se esiste il corso, false altrimenti
	 * @throws SQLException 
	 */
	public synchronized boolean checkCorso(int idCorso) throws SQLException {
		Connection connection=null;
		PreparedStatement statement=null;
		
		String sql="Select idCorso from corso where idCorso=?";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			statement=connection.prepareStatement(sql);
			statement.setInt(1,idCorso);
			return (statement.executeQuery()).next();
		}finally {
			try {
				if(statement!=null)
					statement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
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
	public synchronized boolean isWellFormatted(CorsoBean corso) {
		if(corso==null) return false;
		return  corso.getNome()!=null && corso.getNome().matches("^[a-zA-Zèòàùì\\s\\!\\-\\d]{5,50}$")  &&
				corso.getDataCreazione()!=null && corso.getDescrizione().length()<1048 && corso.getDescrizione().length()>10 
				&& corso.getDataFine()!=null && corso.getDataCreazione().before(corso.getDataFine()) && 
				corso.getDescrizione()!=null && corso.getDescrizione().length()<400  && corso.getCopertina()!=null
				&& corso.getDocente()!=null && corso.getCategoria()!=null
				&& corso.getStato()!=null;
		

	}

}
