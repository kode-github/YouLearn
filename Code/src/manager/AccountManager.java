package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
	
	DataSource dataSource;
	
	public AccountManager() {
		dataSource= new DataSource();
	}
	
	/**
	 * Recupera un Account dal DB
	 * @param code La mail dell'Account
	 * @return Account
	 * @throws SQLException Errore nell'accesso al db
	 * @throws NotFoundException L'account non esiste
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
	 * Modifica la password di un Account
	 * @param cf
	 * @param pass
	 * @return
	 * @throws SQLException 
	 * @throws NotFoundException 
	 */
	public void modificaPassword(String email, String pass) throws SQLException, NotFoundException {
		if(!checkMail(email)) throw new NotFoundException("L'account non esiste");
		
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
			if((preparedStatement.executeUpdate())==0) throw new NotFoundException("Account non trovato");
			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
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
		if(!checkMail(email)) throw new NotFoundException("L'account non esiste");
		
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
	 * Effettua il login di un Utente
	 * Sto metodo si deve ottimizzare, fa schifo
	 * @param email email dell'account
	 * @param password password dell'account
	 * @return L'Account 
	 * @throws SQLException Errore di connessione ad DB
	 * @throws NotFoundException 
	 * @throws DatiErratiException 
	 */
	public AccountBean loginUtente(String email, String password) throws SQLException, NotFoundException, DatiErratiException {
		if(!checkMail(email) || checkTipoUser(email)==0) throw new NotFoundException("Questo utente non esiste: " + email); //Precondizione
		if(!checkPassword(email,password)) throw new DatiErratiException();
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		AccountBean temp=new AccountBean();
		
		//Query per le diverse informazioni da ottenere
		String sqlCarta="numeroCarta,ca.annoScadenza,ca.meseScadenza,ca.nomeIntestatario,ca.tipo "+ 
				"cartadicredito as ca \r\n" + 
				"ca.accountMail=?";		
		String sqlCreati="select idCorso,nome,descrizione,copertina,stato \r\n" + 
				"from corso\r\n" +
				"where accountCreatore=?";
		String sqlSeguiti="SELECT idCorso,nome,descrizione,copertina,stato "
				+ " FROM corso, Iscrizione"
				+ " where idCorso=corsoIdCorso && accountMail=?";
		try {
			//Informazioni su Account
			temp=doRetrieveByKey(email);
			//FINE INFORMAZIONI ACCOUNT
			connection=dataSource.getConnection();
			//Informazioni di carta
			preparedStatement= connection.prepareStatement(sqlCarta);
			preparedStatement.setString(1, email);
			System.out.println("Query: " + preparedStatement.toString());
			ResultSet rs= preparedStatement.executeQuery();
			rs.next();
			CartaDiCreditoBean carta= new CartaDiCreditoBean();
			carta.setNumeroCarta(rs.getString("numeroCarta"));
			carta.setAnnoScadenza(rs.getDate("annoScadenza"));
			carta.setMeseScadenza(rs.getDate("meseScadenza"));
			carta.setNomeIntestatario(rs.getString("NomeIntestatario"));
			carta.setTipo(CartaEnumUtility.parserTipoCarta(rs.getInt("tipo")));
			temp.setCarta(carta);
			preparedStatement.close();
			//Informazioni corsi tenuti
			preparedStatement= connection.prepareStatement(sqlCreati);
			preparedStatement.setString(1, email);
			System.out.println("Query: " + preparedStatement.toString());
			rs= preparedStatement.executeQuery();
			while(rs.next()) {
				// idCorso,nome,descrizione,copertina,stato
				 CorsoBean corso= new CorsoBean();
				 corso.setIdCorso(rs.getInt("idcorso"));
				 corso.setNome(rs.getString("nome"));
				 corso.setDescrizione(rs.getString("Descrizione"));
				 corso.setCopertina(rs.getString("copertina"));
				 corso.setStato(StatoUtility.parserTipoCarta(rs.getInt("stato")));
				 temp.AddCorsoTenuto(corso);
			}
			preparedStatement.close();
			//Fine informazioni corso
			//Informazioni corsi seguiti
			preparedStatement= connection.prepareStatement(sqlSeguiti);
			preparedStatement.setString(1, email);
			System.out.println("Query: " + preparedStatement.toString());
			rs= preparedStatement.executeQuery();
			while(rs.next()) {
				// idCorso,nome,descrizione,copertina,stato
				 CorsoBean corso= new CorsoBean();
				 IscrizioneBean i=new IscrizioneBean();
				 corso.setIdCorso(rs.getInt("idcorso"));
				 corso.setNome(rs.getString("nome"));
				 corso.setDescrizione(rs.getString("Descrizione"));
				 corso.setCopertina(rs.getString("copertina"));
				 corso.setStato(StatoUtility.parserTipoCarta(rs.getInt("stato")));
				 i.setCorso(corso);
				 temp.addIscrizione(i);
			}
			
		}finally {
			if(preparedStatement!=null)
				preparedStatement.close();
		}
		return temp;
	}
	
	/**
	 * Registra un nuovo utente
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public void setRegistration(AccountBean user) throws Exception {
		if(checkMail(user.getMail())) throw new Exception("Questo account esiste già");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO Account VALUES(?,?,?,?,?,?)";
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, user.getNome());
			preparedStatement.setString(2, user.getCognome());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getMail());
			preparedStatement.setInt(5, RuoloUtility.ruoloParser(user.getTipo()));
			preparedStatement.setBoolean(6, user.getVerificato());
			System.out.println("Registrazione Utente: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
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
	  * Verifica se la password per un certo user è corretta
	  * @param password
	  * @param cf
	  * @return
	  */
	public boolean checkPassword(String password, String email) {
		return false;
		
	}
	

	
}
