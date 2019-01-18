package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NoPermissionException;

import org.apache.tomcat.jdbc.pool.DataSource;

import bean.AccountBean;
import bean.AccountBean.Ruolo;
import bean.CartaDiCreditoBean;
import bean.CorsoBean;
import bean.IscrizioneBean;
import connection.DriverManagerConnectionPool;
import exception.*;
import utility.CartaEnumUtility;
import utility.RuoloUtility;
import utility.StatoUtility;

public class AccountManager {
	
	private DataSource dataSource;
	private CartaDiCreditoManager managerCarta;
	private CorsoManager managerCorso;
	private IscrizioneManager managerIscrizione;
	
	public AccountManager() {
		dataSource= new DataSource();
	}
	
	/**
	 * Recupera un Account dal DB
	 * @param code La mail dell'Account
	 * @return Account
	 * @throws SQLException Errore nell'accesso al db
	 * @throws NotFoundException L'account non esiste
	 * @throws NoPermissionException 
	 */
	public AccountBean doRetrieveByKey(String code) throws SQLException,NotFoundException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		AccountBean temp=new AccountBean();
		managerCarta= new CartaDiCreditoManager();
		
		String sql="SELECT* FROM Account WHERE email=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, code);
			System.out.println("Query: " + preparedStatement.toString());
			
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("L'account non � stato trovato"); //controllo che esista l'account
	
			temp.setNome(rs.getString("Nome"));
			temp.setCognome(rs.getString("Cognome"));
			temp.setPassword(rs.getString("Password"));
			temp.setTipo(RuoloUtility.ruoloParser(rs.getInt("tipo")));
			temp.setMail(rs.getString("Email"));
			temp.setVerificato(rs.getBoolean("Verificato"));
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				dataSource.close();
			}
		}
		return temp;
	}

	/**
	 * Modifica la password di un Account
	 * @param cf
	 * @param pass
	 * @return
	 * @throws SQLException 
	 * @throws NotFoundException 
	 * @throws NoPermissionException 
	 */
	public void modificaPassword(String email, String pass) throws SQLException, NotFoundException, NoPermissionException {
		if(!checkMail(email)) throw new NotFoundException("L'account non esiste");//necessario?
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE Account SET Password= ? "
				+ " WHERE Email = ?";

		try {
			connection =dataSource.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, pass);
			preparedStatement.setString(2, email);
			System.out.println("doUpdate: "+ preparedStatement.toString());
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
	 * Modifica la mail di un Account
	 * @param cf
	 * @param mail
	 * @return
	 * @throws SQLException 
	 * @throws NotFoundException 
	 * @throws NoPermissionException 
	 */
	public void modificaMail(String email, String newMail) throws SQLException, NotFoundException, NoPermissionException {
		if(!checkMail(email)) throw new NotFoundException("L'account non esiste"); //necessario?
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE Account SET Email= ? "
				+ " WHERE Email = ?";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, newMail);
			preparedStatement.setString(2, email);
			preparedStatement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				dataSource.close();
			}
		}
		
	}
	
	/**
	 * Effettua il login di un Account
	 * @param email email dell'account
	 * @param password password dell'account
	 * @return L'Account 
	 * @throws SQLException Errore di connessione ad DB
	 * @throws NotFoundException 
	 * @throws DatiErratiException 
	 * @throws NoPermissionException 
	 */
	public AccountBean login(String email, String password) throws SQLException, NotFoundException, DatiErratiException, NoPermissionException {
		AccountBean temp=doRetrieveByKey(email); //NotFoundException se non esiste
		if(temp.getPassword().equals(password)) throw new DatiErratiException(); 
		
		managerCarta= new CartaDiCreditoManager();
		managerCorso= new CorsoManager();
		managerIscrizione= new IscrizioneManager();

		temp.setPassword(""); //elimino la password
		if(temp.getTipo().equals(Ruolo.Utente)) {
			managerCarta.retrieveByAccount(temp); //recupero la carta
			managerCorso.retrieveByCreatore(temp); //recupero gli account da lui creati
			managerIscrizione.getIscrizioniUtente(temp); //recupero gli account a cui � iscritto
		}
		else {
			managerCorso.doRetrieveBySupervisore(temp); //recupero i corsi supervisionati
		}
			
		return temp;
	}
	/**
	 * Registra un nuovo utente con la propria carta
	 * @param user
	 * @return
	 * @throws NotWellFormattedException 
	 * @throws AlreadyExistingException 
	 * @throws SQLException 
	 * @throws NoPermissionException 
	 * @throws Exception 
	 */
	public void setRegistration(AccountBean user) throws NotWellFormattedException, AlreadyExistingException, SQLException, NoPermissionException {
		if(!isWellFormatted(user)) throw new NotWellFormattedException("Dati non corretti");
		if(checkMail(user.getMail())) throw new AlreadyExistingException("Questo account esiste gi�");
		managerCarta= new CartaDiCreditoManager();
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO Account VALUES(?,?,?,?,?,?)";
		try {
			connection=dataSource.getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getNome());
			preparedStatement.setString(2, user.getCognome());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getMail());
			preparedStatement.setInt(5, RuoloUtility.ruoloParser(user.getTipo()));
			preparedStatement.setBoolean(6, user.getVerificato());
			System.out.println("Registrazione Utente: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			managerCarta.registerCard(user.getCarta()); //inserisco la carta
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
	 * Verifica se un certo utente esiste gi� nel database
	 * @param email la mail da verificare
	 * @return
	 * @throws SQLException 
	 * @throws NoPermissionException 
	 * @throws NotFoundException 
	 */
	public boolean checkMail(String email) throws SQLException, NoPermissionException {
		try {
			doRetrieveByKey(email);
			return true;
		} catch (NotFoundException e) {
			return false;
		}
		
	}
	
	/**
	 * Controlla se un account ha dati corretti
	 * Non testa la password dato che si tratta di utenti in sessione
	 * Ma non so se serva realmente
	 * @param account
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkAccount(AccountBean account) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs;
		
		String sql="SELECT email FROM Account WHERE email=?, nome=?, cognome=?, tipo=?, verificato=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			preparedStatement.setString(2, account.getNome());
			preparedStatement.setString(3, account.getCognome());
			preparedStatement.setInt(4, RuoloUtility.ruoloParser(account.getTipo()));
			preparedStatement.setBoolean(5, account.getVerificato());
			rs= preparedStatement.executeQuery();
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				dataSource.close();
			}
		}
		return rs.next();
	}
//
//	/**
//	 * Controlla se un account � Utente o Supervisore
//	 * @param email
//	 * @return 0=Utente  1=Supervisore
//	 * @throws SQLException Errore di connessione al DB
//	 * @throws NotFoundException 
//	 * @throws NoPermissionException 
//	 */
//	public int checkTipoUser(String email) throws SQLException, NotFoundException, NoPermissionException {
//		AccountBean account=doRetrieveByKey(email);
//		if(account.getTipo().equals(Ruolo.Utente))
//			return 0;
//		else
//			return 1;
//	}
	
	
	/**
	 * Controlla che un account sia ben formattato
	 * @param account
	 */
	public boolean isWellFormatted(AccountBean account) {
//		if(account.getTipo()!=null) {
//			if(account.getTipo().equals(Ruolo.Utente)) 
//				if(account.getCorsiDaSupervisionare()!=null || account.getCarta()==null)
//					return false;
//			else
//				if(account.getIscrizioni()!=null || account.getCommentiScritti()!=null || 
//						account.getCorsiTenuti()!=null || account.getCarta()==null)
//					return false;
			String nome=account.getNome();
			String cognome=account.getCognome();
			String password=account.getPassword();
			return nome.matches("^[a-zA-Z]{2,20}") && 
				   cognome.matches("^[a-zA-Z]{2,20}") &&
				   password.matches("^[a-zA-Z0-9]{5,30}") && account.getTipo()!=null;
//			}
//		else 
//			return false;
	}



	
}
