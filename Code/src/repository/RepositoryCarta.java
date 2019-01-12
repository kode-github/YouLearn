package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import bean.AccountBean;
import bean.CartaDiCreditoBean;
import bean.CartaDiCreditoBean.CartaEnum;
import connection.DriverManagerConnectionPool;
import exception.NotFoundException;
import utility.*;

public class RepositoryCarta implements Repository<CartaDiCreditoBean> {

	Connection connection;
	
	@Override
	public void add(CartaDiCreditoBean item) throws SQLException {
		add(Collections.singletonList(item));
		
	}

	@Override
	public void add(Iterable<CartaDiCreditoBean> items) throws SQLException {
		PreparedStatement preparedStatement=null;
		String sql="INSERT INTO CartaDiCredito VALUES(?,?,?,?,?)";
		try {
			connection=DriverManagerConnectionPool.getConnection();
			for(CartaDiCreditoBean item: items) {
				preparedStatement= connection.prepareStatement(sql);
				preparedStatement.setString(1, item.getNomeIntestatario());
				preparedStatement.setString(2, item.getNumeroCarta());
				preparedStatement.setDate(3,item.getMeseScadenza());
				preparedStatement.setDate(4, item.getAnnoScadenza());
				preparedStatement.setString(5, CartaEnumUtility.parserTipoCarta(item.getTipo()));
				System.out.println("doSave: "+ preparedStatement.toString());
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}	
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

	@Override
	public void update(CartaDiCreditoBean item) throws SQLException {
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE CartaDiCredito SET NomeIntestatario = ?, NumeroCarta = ?, MeseScadenza= ?, "
				+ "AnnoScadenza=?, Tipo=? "
				+ " WHERE NumeroCarta = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, item.getNomeIntestatario());
			preparedStatement.setString(2, item.getNumeroCarta());
			preparedStatement.setDate(3, item.getMeseScadenza());
			preparedStatement.setDate(4, item.getAnnoScadenza());
			preparedStatement.setString(5, CartaEnumUtility.parserTipoCarta(item.getTipo()));
			preparedStatement.setString(6, item.getNumeroCarta());
			System.out.println("doUpdate: "+ preparedStatement.toString());
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

	@Override
	public void remove(CartaDiCreditoBean item) throws SQLException {
		// TODO non necessario
		
	}

	@Override
	public void remove(Specification specification) throws SQLException {
		// TODO non necessario
		
	}

	@Override
	public Collection<CartaDiCreditoBean> query(Specification specification) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<CartaDiCreditoBean> collection= new LinkedList<>();
		SQLSpecification sqlSpecification=(SQLSpecification) specification;
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sqlSpecification.toSQLQuery());
			System.out.println("Query: " + preparedStatement.toString());
			ResultSet rs= preparedStatement.executeQuery();
			
			while(rs.next()) {
				CartaDiCreditoBean temp= new CartaDiCreditoBean();
				temp.setNomeIntestatario(rs.getString("NomeIntestatario"));
				temp.setNumeroCarta(rs.getString("NumeroCarta"));
				temp.setMeseScadenza(rs.getDate("MeseScadenza"));
				temp.setAnnoScadenza(rs.getDate("AnnoScadenza"));
				temp.setTipo(CartaEnumUtility.parserTipoCarta(rs.getString("tipo")));
				
				AccountBean temp2= new AccountBean();
				temp2.setNome(rs.getString("Nome"));
				temp2.setCognome(rs.getString("Cognome"));
				temp2.setPassword(rs.getString("Password"));
				temp2.setTipo(RuoloUtility.ruoloParser(rs.getInt("Tipo")));
				temp2.setMail(rs.getString("Email"));
				temp2.isVerificato(rs.getBoolean("Verificato"));
				temp.setAccount(temp2);
				
				collection.add(temp);
			}
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return collection;
	}
	
	

}
