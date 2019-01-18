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
import bean.AccountBean.Ruolo;
import exception.AlreadyExistingException;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import utility.CartaEnumUtility;

public class CartaDiCreditoManager {

	DataSource dataSource;
	AccountManager accountManager;
	
	public  CartaDiCreditoManager() {
		dataSource= new DataSource();
	}
	
	/**
	 * Ottiene una carta dal db
	 * @param code
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException La carta non esiste
	 */
	public CartaDiCreditoBean doRetrieveByKey(String code) throws SQLException,NotFoundException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		CartaDiCreditoBean temp=new CartaDiCreditoBean();
		
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
			temp.setTipo(CartaEnumUtility.parserTipoCarta(rs.getInt("tipo")));
		}finally {
			if(preparedStatement!=null)
				preparedStatement.close();
		}
		
		return temp;
	}


	/**
	 * Inserisce una nuova carta nel db
	 * @param carta
	 * @throws SQLException
	 * @throws NotWellFormattedException La carta non è formattata bene
	 * @throws AlreadyExistingException La carta esiste già
	 */
	public void registerCard(CartaDiCreditoBean carta) throws SQLException, NotWellFormattedException, AlreadyExistingException {
		if(!isWellFormatted(carta)) throw new NotWellFormattedException("La carta non è formattata bene");
		if(!checkCarta(carta.getNumeroCarta())) throw new AlreadyExistingException("La carta esiste già");
		
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
			preparedStatement.setInt(5, CartaEnumUtility.parserTipoCarta(carta.getTipo()));
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
				dataSource.close();
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
	 * @throws Exception
	 */
	public void modifyCard(CartaDiCreditoBean newCarta,String numeroCarta) throws SQLException, NotFoundException, AlreadyExistingException {
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
			preparedStatement.setInt(4, CartaEnumUtility.parserTipoCarta(newCarta.getTipo()));
			preparedStatement.setString(6, newCarta.getAccount().getMail());
			preparedStatement.setString(7, newCarta.getNumeroCarta());
			preparedStatement.executeUpdate();
			
		}finally {
			if(preparedStatement!=null)
				preparedStatement.close();
		}	
	}
	
	private boolean checkCarta(String numeroCarta) throws SQLException {
		try {
			doRetrieveByKey(numeroCarta);
			return true;
		}catch(NotFoundException e) {
			return false;
		}
		
	}

	/**
	 * Dovrebbe cercare se una carta esiste
	 * Non so se necessario
	 * @param cartaDiCreditoBean
	 * @return
	 * @throws SQLException
	 */
	public boolean checkCarta(CartaDiCreditoBean cartaDiCreditoBean) throws SQLException {
			//controllare tutti i campo di CartaDiCredito e dare vero o falso su result set
			return true;
		
	}
	
	

	/**
	 * Recupera una carta di credito in base ad una mail
	 * @param email
	 * @return una carta senza l'account collegato
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
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
			temp.setTipo(CartaEnumUtility.parserTipoCarta(rs.getInt("tipo")));
			temp.setAccount(account);
		}finally {
			if(preparedStatement!=null)
				preparedStatement.close();
		}
		
		return temp;
	}
	
	/**
	 * Controlla se una carta è well formed
	 * @param carta
	 * @return
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
