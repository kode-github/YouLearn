package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import bean.AccountBean;
import bean.AccountBean.Ruolo;
import connection.DriverManagerConnectionPool;
import exception.*;

public class AccountManager {
	
	/**
	 * Converte un oggetto Ruolo in un 0 o 1 per il salvataggio nel DB
	 * @param r Oggetto Ruolo
	 * @return 0=Utente 1=Supervisore
	 */
	private int ruoloParser(Ruolo r) {
		return r.equals(Ruolo.Utente)? 0:1;
	}
	
	/**
	 * Recupera un Account dal DB
	 * @param code La mail dell'Account
	 * @return Account
	 * @throws SQLException Errore nell'accesso al db
	 */
	public AccountBean doRetrieveByKey(String code) throws SQLException,NotFoundException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		AccountBean temp=new AccountBean();
		
		String sql="SELECT* FROM Account WHERE email=?";		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, code);
			System.out.println("Query: " + preparedStatement.toString());
			
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("L'account non è stato trovato"); //controllo che esista l'account
	
			temp.setNome(rs.getString("Nome"));
			temp.setCognome(rs.getString("Cognome"));
			temp.setPassword(rs.getString("Password"));
			int tipo=rs.getInt("Tipo");
			if(tipo==0)
				temp.setTipo(Ruolo.Utente);
			else 
				temp.setTipo(Ruolo.Supervisore);
			temp.setMail(rs.getString("Email"));
			temp.isVerificato(rs.getBoolean("Verificato"));
			temp.setNumeroCarta(rs.getString("numeroCarta"));
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return temp;
	}

	/**
	 * Aggiunge un nuovo Account al DB
	 * @param product Il nuovo Account da inserire
	 * @throws SQLException Errore di connessione al DB
	 */
	public void doSave(AccountBean product) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO Account VALUES(?,?,?,?,?)";
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, product.getNome());
			preparedStatement.setString(2, product.getCognome());
			preparedStatement.setString(3, product.getPassword());
			preparedStatement.setString(4, product.getMail());
			preparedStatement.setInt(5, ruoloParser(product.getTipo()));
			preparedStatement.setBoolean(6, product.getVerificato());
			preparedStatement.setString(7, product.getNumeroCarta());
			System.out.println("doSave: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
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
	 * Aggiorna un Account nel DB
	 * @param product Il nuovo Account
	 * @throws SQLException Errore di connessione al DB
	 * @throws NotFoundException L'Account non era stato precedentemente inserito nel DB
	 */
	public void doUpdate(AccountBean product) throws SQLException, NotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE Account SET Nome = ?, Cognome = ?, Password= ?, Email=?, Tipo=?, Verificato=? "
				+ " WHERE Email = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, product.getNome());
			preparedStatement.setString(2, product.getCognome());
			preparedStatement.setString(3, product.getPassword());
			preparedStatement.setString(4, product.getMail());
			preparedStatement.setInt(5, ruoloParser(product.getTipo()));
			preparedStatement.setBoolean(6, product.getVerificato());
			preparedStatement.setString(7, product.getNumeroCarta());
			preparedStatement.setString(8, product.getMail());
			System.out.println("doUpdate: "+ preparedStatement.toString());
			if((preparedStatement.executeUpdate())==0) throw new NotFoundException("Account non trovato");
			connection.commit();
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
	 * Modifica la password di un Account
	 * @param cf
	 * @param pass
	 * @return
	 * @throws SQLException 
	 * @throws NotFoundException 
	 */
	public void modificaPassword(String email, String pass) throws SQLException, NotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE Account SET Password= ? "
				+ " WHERE Email = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, pass);
			preparedStatement.setString(2, email);
			System.out.println("doUpdate: "+ preparedStatement.toString());
			if((preparedStatement.executeUpdate())==0) throw new NotFoundException("Account non trovato");
			connection.commit();
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
	 * Modifica la mail di un Account
	 * @param cf
	 * @param mail
	 * @return
	 * @throws SQLException 
	 * @throws NotFoundException 
	 */
	public void modificaMail(String email, String newMail) throws SQLException, NotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE Account SET Email= ? "
				+ " WHERE Email = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, newMail);
			preparedStatement.setString(2, email);
			System.out.println("doUpdate: "+ preparedStatement.toString());
			if((preparedStatement.executeUpdate())==0) throw new NotFoundException("Account non trovato");
			connection.commit();
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
	 * Effettua il login di un Account
	 * @param email email dell'account
	 * @param password password dell'account
	 * @return L'Account 
	 * @throws SQLException Errore di connessione ad DB
	 * @throws NotFoundException 
	 */
	public AccountBean authenticateUser(String email, String password) throws SQLException, NotFoundException {
			AccountBean account=doRetrieveByKey(email);
			if(!account.getPassword().equals(password))
				return null;
			else {
				account.setPassword(""); //Elimino la password prima di metterlo in sessione (?)
				return account;
			}	
	}
	
	/**
	 * Registra un nuovo utente
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	public boolean setRegistration(AccountBean user) {
		try {
			doSave(user);
			return true;
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	/**
	 * Verifica se un certo utente esiste già nel database
	 * @param email la mail da verificare
	 * @return
	 * @throws SQLException 
	 * @throws NotFoundException 
	 */
	public boolean checkMail(String email) throws SQLException {
		try {
			AccountBean account=doRetrieveByKey(email);
			return true;
		} catch (NotFoundException e) {
			return false;
		}
		
	}

	/**
	 * Controlla se un account è Utente o Supervisore
	 * @param email
	 * @return 0=Utente  1=Supervisore
	 * @throws SQLException Errore di connessione al DB
	 * @throws NotFoundException 
	 */
	public int checkTipoUser(String email) throws SQLException, NotFoundException {
		AccountBean account=doRetrieveByKey(email);
		if(account.getTipo().equals(Ruolo.Utente))
			return 0;
		else
			return 1;
	}
	
	 /**
	  * Sta roba non ha senso
	  * Verifica se la password per un certo user è corretta
	  * @param password
	  * @param cf
	  * @return
	  */
	public boolean checkPassword(String password, String cf) {
		return false;
		
	}
	
}
