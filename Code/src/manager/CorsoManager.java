package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.naming.NoPermissionException;

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
	ConfiguredDataSource dataSource;
	
	public CorsoManager() {
		dataSource=new ConfiguredDataSource();
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
			lezioneManager.retrieveLezioniByCorso(corso); //recuperlo le lezioni
			iscrizioneManager.getIscrittiCorso(corso); //recupero il corso
			corso.setDocente(accountManager.doRetrieveByKey(rs.getString("accountCreatore"))); //recupero il docente
			corso.setSupervisore(accountManager.doRetrieveByKey(rs.getString("accountSupervisore"))); //recupero il supervisore
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
	 * @throws SQLException
	 * @throws NotWellFormattedException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 */
	public void creaCorso(CorsoBean corso) throws SQLException, NotWellFormattedException, NotFoundException, NoPermissionException {
		accountManager= new AccountManager();
		if(corso==null) throw new NotWellFormattedException("Il corso non può essere null");
		corso.setStato(Stato.Completamento); //necessario per usare il WellFormatted
		if(corso.getIdCorso()!=null || !isWellFormatted(corso)) 
			throw new NotWellFormattedException("Il corso non è ben formattato");
		if(!accountManager.checkMail(corso.getDocente().getMail())) throw new NotFoundException("Il creatore non esiste");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO Corso VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
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
			preparedStatement.setString(8, corso.getCopertina());
			preparedStatement.setInt(9, corso.getPrezzo());
			preparedStatement.setString(10, corso.getStato().toString());
			preparedStatement.setString(11, corso.getDescrizione());
			System.out.println("Registrazione corso: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
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
			//Controllo se c'è da aggiornare il supervisore
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
			preparedStatement.setString(11, corso.getDescrizione());
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
	 */
	public void modificaCorso(CorsoBean corso) throws NotFoundException, NotWellFormattedException, SQLException, NoPermissionException {
		if(!checkCorso(corso.getIdCorso())) throw new NotFoundException("Il corso non esiste");
		if(!isWellFormatted(corso) || corso.getIdCorso()==null) throw new NotWellFormattedException("Il corso non è ben formattato");
		if(!corso.getStato().equals(Stato.Completamento)) throw new NoPermissionException("Non si può modificare un corso non in "
																								+ "completamento");
		
		doUpdate(corso);
	}
	

	/**
	 * Convalida un corso in attesa di supervisione
	 * @param accetta se true allora accetta, altrimenti rifiuta
	 * @param corso corso da convalidare
	 * @throws NotFoundException IL CORSO non esiste
	 * @throws NotWellFormattedException Il corso non è ben formattato
	 * @throws NoPermissionException Il corso non è in stato di attesa
	 * @throws SQLException
	 */
	public void convalidaCorso(boolean accetta,CorsoBean corso) throws NotFoundException, NotWellFormattedException, NoPermissionException, SQLException {
		if(!isWellFormatted(corso) || corso.getIdCorso()==null) throw new NotWellFormattedException("Il corso non è ben formattato");
		if(!checkCorso(corso)) throw new NotFoundException("Il corso non esiste");
		if(!corso.getStato().equals(Stato.Attesa)) throw new NoPermissionException("Non si può confermare un corso non in attesa");
		
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
		if(!isWellFormatted(corso) || corso.getIdCorso()==null) throw new NotWellFormattedException("Il corso non è ben formattato");
		if(!checkCorso(corso)) throw new NotFoundException("Il corso non esiste");
		if(!corso.getStato().equals(Stato.Completamento)) throw new NoPermissionException("Non si può confermare un corso non in completamento");
		
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
		if(!accountManager.isWellFormatted(account)) throw new NotWellFormattedException("L'account non è ben formattato");
		if(!accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non può avere corsi creati");
		
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
				 corso.setIdCorso(rs.getInt("idcorso"));
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
		if(!accountManager.isWellFormatted(account)) throw new NotWellFormattedException("L'account non è ben formattato");
		if(!accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non può avere corsi creati");
		
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
				 corso.setIdCorso(rs.getInt("idcorso"));
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
				 //non carichiamo il docente
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
	 * Controlla se un certo corso è corretto ed esiste
	 * @param corso
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkCorso(CorsoBean corso) throws SQLException {
		Connection connection=null;
		PreparedStatement statement=null;
		
		String sql="Select idcorso from corso where idCorso=?, nome=?, descrizione=?,dataCreazione=?,dataFine=?,"
				+ " copertina=?, prezzo=?, stato=?, categoria=?, nLezioni=?, nIscritti=?, accountCreatore=?, accountSupervisore=? ";
		
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
			statement.setString(13, corso.getSupervisore().getMail());
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
