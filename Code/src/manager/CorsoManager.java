package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.naming.NoPermissionException;

import org.apache.tomcat.jdbc.pool.DataSource;

import bean.AccountBean;
import bean.AccountBean.Ruolo;
import bean.CorsoBean;
import bean.CorsoBean.Categoria;
import bean.CorsoBean.Stato;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import utility.StatoUtility;

public class CorsoManager {
	
	AccountManager accountManager;
	LezioneManager lezioneManager;
	DataSource dataSource;
	
	public CorsoManager() {
		dataSource=new DataSource();
	}
	
	
	public CorsoBean doRetrieveByKey(int id) {
		// TODO Auto-generated method stub
		return null;
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
		
		if(!isWellFormatted(corso) || corso.getIdCorso()!=null) 
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
					dataSource.close();
				}		
		}
	}
	
	/**
	 * Aggiorna un corso
	 * @param corso
	 * @throws SQLException
	 */
	public void doUpdate(CorsoBean corso) throws SQLException {
		//Dovrei controllare se è null l'id di corso o il corso?
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
					dataSource.close();
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
		if(!isWellFormatted(corso)) throw new NotWellFormattedException("Il corso non è ben formattato");
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
		if(!isWellFormatted(corso)) throw new NotWellFormattedException("Il corso non è ben formattato");
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
		if(!isWellFormatted(corso)) throw new NotWellFormattedException("Il corso non è ben formattato");
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
	 */
	public Collection<CorsoBean> retrieveByCreatore(AccountBean account) throws NoPermissionException, SQLException, NotFoundException {
		accountManager= new AccountManager();
		lezioneManager= new LezioneManager();
		if(accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
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
				 corso.setStato(StatoUtility.parserTipoCarta(rs.getInt("stato")));
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
				dataSource.close();
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
	 */
	public Collection<CorsoBean> doRetrieveBySupervisore(AccountBean account) throws SQLException, NotFoundException, NoPermissionException {
		accountManager= new AccountManager();
		lezioneManager= new LezioneManager();
		if(!accountManager.checkMail(account.getMail())) throw new NotFoundException("Questo account non esiste");
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
				 corso.setStato(StatoUtility.parserTipoCarta(rs.getInt("stato")));
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
				dataSource.close();
			}
		}
		return collection;
		
	}


	/**
	 * Controlla se un certo corso è corretto ed esiste
	 * @param corso
	 * @return
	 */
	public boolean checkCorso(CorsoBean corso) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Controlla se un certo corso esiste
	 * @param idCorso id del corso
	 * @return
	 */
	private boolean checkCorso(Integer idCorso) {
		try {
			doRetrieveByKey(idCorso);
			return true;
		}catch(NotFoundException e) {
				return false;
		}
	}
	
	/**
	 * Controlla se gli elementi principali di corso hanno una formattazione corretta
	 * @param corso
	 * @return
	 */
	public boolean isWellFormatted(CorsoBean corso) {
		Date dataOdierna = new Date();
		
		return corso.getNome().matches("^[a-zA-Z]{2,20}") && corso.getDescrizione().matches("^[a-zA-Z0-9]{0,2048}") &&
				corso.getDataCreazione()!=null && corso.getDataFine()!=null && corso.getDataCreazione().before(corso.getDataFine())
				&& !corso.getDataCreazione().after(dataOdierna) && corso.getnLezioni()==corso.getLezioni().size() &&
				corso.getnIscritti()==corso.getIscrizioni().size() && corso.getCopertina()!=null && 
				corso.getCopertina().matches("^[a-zA-Z\\.]{1,255}") && corso.getDocente()!=null && corso.getCategoria()!=null
				&& corso.getStato()!=null;
	}

}
