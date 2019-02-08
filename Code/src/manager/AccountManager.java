package manager;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.naming.NoPermissionException;
import bean.AccountBean;
import bean.AccountBean.Ruolo;
import connection.DriverManagerConnectionPool;

import exception.*;

public class AccountManager {
	
	private static AccountManager istanza;
	private CartaDiCreditoManager managerCarta;
	private CorsoManager managerCorso;
	private IscrizioneManager managerIscrizione;
	
	
	private AccountManager() {}
	
	public static AccountManager getIstanza() {
		if(istanza==null)
			istanza=new AccountManager();
		return istanza;
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
		
		String sql="SELECT* FROM account WHERE email=?";		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, code);
			System.out.println("Query: " + preparedStatement.toString());
			
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("L'account non � stato trovato"); //controllo che esista l'account
	
			temp.setNome(rs.getString("Nome"));
			temp.setCognome(rs.getString("Cognome"));
			temp.setPassword(rs.getString("Password"));
			temp.setTipo(Ruolo.valueOf(rs.getString("tipo")));
			temp.setMail(rs.getString("Email"));
			temp.setVerificato(rs.getBoolean("Verificato"));
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				if(connection != null)
				connection.close();
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
	public void modificaPassword(String email, String pass) throws SQLException, NotFoundException, NoPermissionException,NotWellFormattedException {
		if(!checkMail(email)) throw new NotFoundException("L'account non esiste");
		//TODO Controllo sul formato della password
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE account SET password= ? "
				+ " WHERE email = ?";

		try {
			connection =DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, pass);
			preparedStatement.setString(2, email);
			System.out.println("doUpdate: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
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
	 * Modifica la mail di un Account
	 * @param cf
	 * @param mail
	 * @return
	 * @throws SQLException 
	 * @throws NotFoundException 
	 * @throws NoPermissionException 
	 * @throws AlreadyExistingException 
	 */
	public void modificaMail(String email, String newMail) throws SQLException, NotFoundException, NoPermissionException, AlreadyExistingException {
		//Controlli sui formati delle mail
		if(!checkMail(email)) throw new NotFoundException("L'account non esiste");
		if(checkMail(newMail)) throw new AlreadyExistingException("La mail esiste gia");
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE account SET email= ? "
				+ " WHERE Email = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, newMail);
			preparedStatement.setString(2, email);
			preparedStatement.executeUpdate();
			System.out.println("Modfica mail: "+preparedStatement.toString());
			connection.commit();
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
	 * Effettua il login di un Account
	 * @param email email dell'account
	 * @param password password dell'account
	 * @return L'Account 
	 * @throws SQLException Errore di connessione ad DB
	 * @throws NotFoundException 
	 * @throws DatiErratiException 
	 * @throws NoPermissionException 
	 * @throws NotWellFormattedException 
	 */
	public AccountBean login(String email, String password) throws SQLException, NotFoundException, DatiErratiException, NoPermissionException, NotWellFormattedException {
		AccountBean temp=doRetrieveByKey(email); //NotFoundException se non esiste
		if(!temp.getPassword().equals(password)) throw new DatiErratiException("Le password non corrispondono"); 
		
		managerCarta= CartaDiCreditoManager.getIstanza();
		managerCorso= CorsoManager.getIstanza();
		managerIscrizione= IscrizioneManager.getIstanza();

		
		if(temp.getTipo().equals(Ruolo.Utente)) {
			managerCarta.retrieveByAccount(temp); //recupero la carta
//			managerCorso.retrieveByCreatore(temp); //recupero gli account da lui creati
//			managerIscrizione.getIscrizioniUtente(temp); //recupero gli account a cui � iscritto
		}
//		else {
//			managerCorso.doRetrieveBySupervisore(temp); //recupero i corsi supervisionati
//		}
		temp.setPassword(""); //elimino la password in quanto va inserito in sessione
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
		managerCarta= CartaDiCreditoManager.getIstanza();
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO account VALUES(?,?,?,?,?,?)";
		try {
			connection=DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getNome());
			preparedStatement.setString(2, user.getCognome());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getMail());
			preparedStatement.setString(5, user.getTipo().toString());
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
					connection.close();
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
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="SELECT email FROM account WHERE email=?";		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			System.out.println("CheckMail: " + preparedStatement.toString());
			
			ResultSet rs= preparedStatement.executeQuery();
			
			return rs.next();
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				if(connection != null)
				connection.close();
			}
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
		
		String sql="SELECT email FROM account WHERE email=? AND nome=? AND cognome=? AND tipo=? AND verificato=? ";		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			preparedStatement.setString(2, account.getNome());
			preparedStatement.setString(3, account.getCognome());
			preparedStatement.setString(4, account.getTipo().toString());
			preparedStatement.setBoolean(5, account.getVerificato());
			System.out.println("Query di select: "+preparedStatement.toString());
			rs= preparedStatement.executeQuery();
			return rs.next();
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				connection.close();
			}
		}
		
	}
	
	
	/**
	 * Controlla che un account sia ben formattato
	 * TODO Da controllare
	 * @param account
	 */
	public boolean isWellFormatted(AccountBean account) {
//		if(account.getTipo()!=null) {
//			if(account.getTipo().equals(Ruolo.Utente)) 
//				if(account.getCorsiDaSupervisionare()!=null || account.getCarta()==null)
//					return false;
//			else
//				if(account.getIscrizioni()!=null  || 
//						account.getCorsiTenuti()!=null || account.getCarta()==null)
//					return false;
			String nome=account.getNome();
			String cognome=account.getCognome();
			String password=account.getPassword();
			return nome!=null && nome.matches("^[a-zA-Z]{2,20}") && cognome!=null &&
			cognome.matches("^[a-zA-Z]{2,20}") && /**password!=null && password.matches("^[a-zA-Z0-9]{5,30}") &&*/ account.getTipo()!=null;
	
//			}
//		else 
//			return false;
	}

	/**
	 * Recupero il supervisore con il minor numero di corsi da supervisionare
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException 
	 */
	public AccountBean retrieveLessUsedSupervisor() throws SQLException, NotFoundException {
		Connection connection=null;
		PreparedStatement statement=null;
		AccountBean temp= new AccountBean();
		
		String sql="select * from(\r\n" + 
				"	select email,account.nome,cognome,tipo,verificato,count(idCorso) as numero\r\n" + 
				"	from account left join corso on email=accountSupervisore	\r\n" + 
				"	where tipo='Supervisore'\r\n" + 
				"	group by email) as t\r\n" + 
				"	where numero=(select min(numero2) \r\n" + 
				"			  from (\r\n" + 
				"					select email,count(idCorso) as numero2\r\n" + 
				"					from account left join corso on email=accountSupervisore\r\n" + 
				"					where tipo='Supervisore' \r\n" + 
				"                    group by email) as v) ";
		try {
			connection=DriverManagerConnectionPool.getConnection();
			statement=connection.prepareStatement(sql);
			System.out.println("retrieveLessUsedSupervisor: "+statement.toString());
			ResultSet rs=statement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("Non esistono supervisori");
			
			temp.setNome(rs.getString("Nome"));
			temp.setCognome(rs.getString("Cognome"));
			temp.setTipo(Ruolo.valueOf(rs.getString("tipo")));
			temp.setMail(rs.getString("Email"));
			temp.setVerificato(rs.getBoolean("Verificato"));
		}finally {
			try{
				if(statement!=null)
					statement.close();
			}finally {
				connection.close();
			}
		}
		return temp;
	}
	
}
