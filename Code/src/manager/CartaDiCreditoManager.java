package manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.naming.NoPermissionException;
import org.apache.tomcat.jdbc.pool.DataSource;
import bean.AccountBean;
import bean.CartaDiCreditoBean;
import bean.CartaDiCreditoBean.CartaEnum;
import bean.AccountBean.Ruolo;
import exception.AlreadyExistingException;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class CartaDiCreditoManager {

	DataSource dataSource;
	AccountManager accountManager;
	
	public  CartaDiCreditoManager() {
		dataSource= new DataSource();
	}
	
	/**
	 * Ottiene una carta dal db con l'account associato
	 * @param code
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException La carta non esiste
	 * @throws NoPermissionException 
	 */
	public CartaDiCreditoBean doRetrieveByKey(String code) throws SQLException,NotFoundException, NoPermissionException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		CartaDiCreditoBean temp=new CartaDiCreditoBean();
		accountManager= new AccountManager();
		
		String sql="SELECT* FROM CartaDiCredito WHERE numeroCarta=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, code);
			System.out.println("Query: " + preparedStatement.toString());
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("La carta non è stato trovata"); //controllo che esista la carta
	
			temp.setNomeIntestatario(rs.getString("NomeIntestatario"));
			temp.setNumeroCarta(rs.getString("NumeroCarta"));
			temp.setMeseScadenza(rs.getString("MeseScadenza"));
			temp.setAnnoScadenza(rs.getString("AnnoScadenza"));
			temp.setTipo(CartaEnum.valueOf(rs.getString("tipo")));
			temp.setAccount(accountManager.doRetrieveByKey(rs.getString("accountMail")));
			
		}finally {
			try{
				if(preparedStatement!=null)
					preparedStatement.close();
			}finally {
				connection.close();
			}
				
		}
		
		return temp;
	}


	/**
	 * Inserisce una nuova carta nel db
	 * @param carta
	 * @throws SQLException
	 * @throws NotWellFormattedException La carta non è formattata bene
	 * @throws AlreadyExistingException La carta esiste già
	 * @throws NoPermissionException 
	 */
	public void registerCard(CartaDiCreditoBean carta) throws SQLException, NotWellFormattedException, AlreadyExistingException, NoPermissionException {
		if(!isWellFormatted(carta)) throw new NotWellFormattedException("La carta non è formattata bene");
		if(checkCarta(carta.getNumeroCarta())) throw new AlreadyExistingException("La carta esiste già");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO Carta VALUES(?,?,?,?,?)";
		try {
			connection=dataSource.getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, carta.getNomeIntestatario());
			preparedStatement.setString(2, carta.getNumeroCarta());
			preparedStatement.setString(3, carta.getMeseScadenza());
			preparedStatement.setString(4, carta.getAnnoScadenza());
			preparedStatement.setString(5, carta.getTipo().toString());
			System.out.println("doSave: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
		}
		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				connection.close();
			}
		}
	}
	
	/**
	 * Modifica una carta esistente
	 * @param newCarta la nuova carta da inserire
	 * @param numeroCarta la carta da modificare
	 * @throws SQLException
	 * @throws NotFoundException 
	 * @throws AlreadyExistingException 
	 * @throws NoPermissionException 
	 * @throws NotWellFormattedException 
	 * @throws Exception
	 */
	public void modifyCard(CartaDiCreditoBean newCarta,String numeroCarta) throws SQLException, NotFoundException, AlreadyExistingException, NoPermissionException, NotWellFormattedException {
		if(!isWellFormatted(newCarta)) throw new NotWellFormattedException("La carta non è ben formattata");
		if(!checkCarta(numeroCarta)) throw new NotFoundException("La carta da modificare non esiste");
		if(checkCarta(newCarta.getNumeroCarta())) throw new AlreadyExistingException("La carta inserita esiste già");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="Update Cartadicredito set numerocarta=?, annooscadenza=?, mesescadenza=?, tipo=?, nomeIntestatario=?, accountMail=?"
				+ " where numeroCarta=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			System.out.println("Update: " + preparedStatement.toString());
			preparedStatement.setString(1, newCarta.getNumeroCarta());
			preparedStatement.setString(5, newCarta.getNomeIntestatario());
			preparedStatement.setString(3, newCarta.getMeseScadenza());
			preparedStatement.setString(2, newCarta.getAnnoScadenza());
			preparedStatement.setString(4, newCarta.getTipo().toString());
			preparedStatement.setString(6, newCarta.getAccount().getMail());
			preparedStatement.setString(7, newCarta.getNumeroCarta());
			preparedStatement.executeUpdate();
			
		}finally {
			if(preparedStatement!=null)
				preparedStatement.close();
		}	
	}
	
	/**
	 * Controlla se una certa carta esiste
	 * @param numeroCarta La PK di carta
	 * @return true le la carta esiste, false in caso contrario
	 * @throws SQLException 
	 * @throws NoPermissionException 
	 */
	private boolean checkCarta(String numeroCarta) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="SELECT numeroCarta FROM CartaDiCredito WHERE numeroCarta=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, numeroCarta);
			System.out.println("Query: " + preparedStatement.toString());
			ResultSet rs= preparedStatement.executeQuery();
			
			return rs.next();
		}finally {
			try{
				if(preparedStatement!=null)
					preparedStatement.close();
			}finally {
				connection.close();
			}
		}
	}
	
	
	/**
	 * Recupera una carta di credito in base ad una mail
	 * @param email Account a cui è associata alla mail
	 * @return La carta associata all'account
	 * @throws SQLException 
	 * @throws NotFoundException Non esiste l'account o la carta associata
	 * @throws NoPermissionException L'account è un supervisore
	 */
	public CartaDiCreditoBean retrieveByAccount(AccountBean account) throws SQLException, NotFoundException, NoPermissionException {
		accountManager=new AccountManager();
		if(accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non può avere corsi creati");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		CartaDiCreditoBean temp=new CartaDiCreditoBean();
		
		String sql="SELECT* FROM CartaDiCredito WHERE accountMail=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			System.out.println("Query: " + preparedStatement.toString());
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("La carta non è stato trovata"); //controllo che esista la carta
	
			temp.setNomeIntestatario(rs.getString("NomeIntestatario"));
			temp.setNumeroCarta(rs.getString("NumeroCarta"));
			temp.setMeseScadenza(rs.getString("MeseScadenza"));
			temp.setAnnoScadenza(rs.getString("AnnoScadenza"));
			temp.setTipo(CartaEnum.valueOf(rs.getString("tipo")));
			temp.setAccount(account);
		}finally {
			if(preparedStatement!=null)
				preparedStatement.close();
		}
		
		return temp;
	}
	
	/**
	 * Controlla se una carta è well formed
	 * @param carta La carta da controllare
	 * @return true se è ben formattata, false altrimenti
	 */
	public boolean isWellFormatted(CartaDiCreditoBean carta) {
		String nome=carta.getNomeIntestatario();
		String numero=carta.getNumeroCarta();
		Integer mese=Integer.parseInt(carta.getMeseScadenza());
		Integer anno=Integer.parseInt(carta.getAnnoScadenza());
		
		return nome.matches("[a-zA-Z]{2,20}") &&
				numero.matches("^[0-9]{16}") &&
				mese!=null && mese>0 && mese<13 && anno!=null && anno>Calendar.YEAR;
	}
	
}
