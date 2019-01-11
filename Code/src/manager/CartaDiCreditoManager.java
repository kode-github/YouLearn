package manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import bean.CartaDiCreditoBean;
import bean.CartaDiCreditoBean.CartaEnum;
import connection.DriverManagerConnectionPool;
import exception.NotFoundException;

public class CartaDiCreditoManager {

	private CartaDiCreditoBean doRetrieveByKey(String code) throws SQLException,NotFoundException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		CartaDiCreditoBean temp=new CartaDiCreditoBean();
		
		String sql="SELECT* FROM CartaDiCredito WHERE numeroCarta=?";		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, code);
			System.out.println("Query: " + preparedStatement.toString());
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("La carta non è stato trovata"); //controllo che esista la carta
	
			temp.setNomeIntestatario(rs.getString("NomeIntestatario"));
			temp.setNumeroCarta(rs.getString("NumeroCarta"));
			temp.setMeseScadenza(rs.getString("MeseScadenza"));
			temp.setAnnoScadenza(rs.getString("AnnoScadenza"));
			temp.setTipo(parserTipoCarta(rs.getString("tipo")));
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
			preparedStatement.setString(5, parserTipoCarta(product.getTipo()));
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
	 * Aggiorna una carta nel DB
	 * @param product La nuova carta
	 * @throws SQLException Errore di connessione al DB
	 * @throws NotFoundException La carta non era stata precedentemente inserita nel DB
	 */
	private void doUpdate(CartaDiCreditoBean product) throws SQLException, NotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE CartaDiCredito SET NomeIntestatario = ?, NumeroCarta = ?, MeseScadenza= ?, "
				+ "AnnoScadenza=?, Tipo=? "
				+ " WHERE NumeroCarta = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, product.getNomeIntestatario());
			preparedStatement.setString(2, product.getNumeroCarta());
			preparedStatement.setString(3, product.getMeseScadenza());
			preparedStatement.setString(4, product.getAnnoScadenza());
			preparedStatement.setString(5, parserTipoCarta(product.getTipo()));
			preparedStatement.setString(6, product.getNumeroCarta());
			System.out.println("doUpdate: "+ preparedStatement.toString());
			if((preparedStatement.executeUpdate())==0) throw new NotFoundException("Carta non trovata");
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
	 * Restituisce un oggetto CartaEnum in base ai valori 0,1,2 usati nel DB per salvarla
	 * @param string
	 * @return
	 */
	private CartaEnum parserTipoCarta(String string) {
		CartaEnum tmp = null;
		switch(string) {
		case "0":  tmp=CartaEnum.POSTEPAY; break;
		case "1":  tmp=CartaEnum.AMERICANEXPRESS; break;
		case "2":  tmp=CartaEnum.PAYPAL; break;
		}
		return tmp;
	}
	
	/** Restituisce un oggetto CartaEnum in base ai valori 0,1,2 usati nel DB per salvarla
	 * @param string
	 * @return
	 */
	private String parserTipoCarta(CartaEnum enumCarta) {
		String tmp = null;
		switch(enumCarta) {
		case POSTEPAY:  tmp="0"; break;
		case AMERICANEXPRESS:  tmp="1"; break;
		case PAYPAL:  tmp="2"; break;
		}
		return tmp;
	}

	//O boolean?
	public void registerCard(CartaDiCreditoBean carta) throws SQLException {
		doSave(carta);
	}
	
	public void modifyCard(CartaDiCreditoBean newCarta,String numeroCarta) {
		//Vedi Appunti 10 gennaio
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
