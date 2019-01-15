package manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import bean.CartaDiCreditoBean;
import connection.DriverManagerConnectionPool;
import exception.NotFoundException;
import utility.CartaEnumUtility;

public class CartaDiCreditoManager {

	DataSource dataSource;
	
	public  CartaDiCreditoManager() {
		dataSource= new DataSource();
	}
	
	private CartaDiCreditoBean doRetrieveByKey(String code) throws SQLException,NotFoundException {
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
			temp.setMeseScadenza(rs.getDate("MeseScadenza"));
			temp.setAnnoScadenza(rs.getDate("AnnoScadenza"));
			temp.setTipo(CartaEnumUtility.parserTipoCarta(rs.getInt("tipo")));
		}finally {
			if(preparedStatement!=null)
				preparedStatement.close();
		}
		
		return temp;
	}
	
	private void doSave(CartaDiCreditoBean product) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO Ca VALUES(?,?,?,?,?)";
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, product.getNomeIntestatario());
			preparedStatement.setString(2, product.getNumeroCarta());
			preparedStatement.setDate(3, product.getMeseScadenza());
			preparedStatement.setDate(4, product.getAnnoScadenza());
			preparedStatement.setInt(5, CartaEnumUtility.parserTipoCarta(product.getTipo()));
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
	


	//O boolean?
	public void registerCard(CartaDiCreditoBean carta) throws SQLException {
		doSave(carta);
	}
	
	/**
	 * Modifica una carta esistente
	 * @param newCarta la nuova carta da inserire
	 * @param numeroCarta la carta da modificare
	 * @throws SQLException
	 * @throws Exception
	 */
	public void modifyCard(CartaDiCreditoBean newCarta,String numeroCarta) throws SQLException, Exception {
		if(!checkCarta(numeroCarta)) throw new NotFoundException("La carta da modificare non esiste");
		if(!checkCarta(newCarta.getNumeroCarta())) throw new Exception("La carta inserita esiste già");
		
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
			preparedStatement.setDate(3, newCarta.getMeseScadenza());
			preparedStatement.setDate(2, newCarta.getAnnoScadenza());
			preparedStatement.setInt(4, CartaEnumUtility.parserTipoCarta(newCarta.getTipo()));
			preparedStatement.setString(6, newCarta.getAccount().getMail());
			preparedStatement.setString(7, newCarta.getNumeroCarta());
			preparedStatement.executeUpdate();
			
		}finally {
		
			if(preparedStatement!=null)
				preparedStatement.close();
		}	
	}
	
	public boolean checkCarta(String numeroCarta) throws SQLException {
		try {
			CartaDiCreditoBean carta=doRetrieveByKey(numeroCarta);
			return true;
		} catch (NotFoundException e) {
			return false;
		}
		
		
	}
}
