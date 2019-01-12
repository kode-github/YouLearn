package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import utility.*;
import bean.AccountBean;
import bean.AccountBean.Ruolo;
import bean.CartaDiCreditoBean.CartaEnum;
import bean.CartaDiCreditoBean;
import connection.DriverManagerConnectionPool;
import exception.NotFoundException;

public class RepositoryAccount implements Repository<AccountBean> {

	Connection c;
	
	@Override
	public void add(AccountBean item) throws SQLException {
		add(Collections.singletonList(item));
	}

	@Override
	public void add(Iterable<AccountBean> items) throws SQLException {
		PreparedStatement statement=null;
		String sql;
		try {
			c=DriverManagerConnectionPool.getConnection();
			for(AccountBean a: items) {
				sql="Insert into Account values(?,?,,?,?,?,?)";
				statement=c.prepareStatement(sql);
				statement.setString(1, a.getNome());
				statement.setString(2, a.getCognome());
				statement.setString(3, a.getPassword());
				statement.setString(4, a.getMail());
				statement.setInt(5, RuoloUtility.ruoloParser(a.getTipo()));
				statement.setBoolean(6, a.getVerificato());
				
				statement.executeUpdate();
				statement.close();
			}
			c.commit();
		}finally {
			try {
				if(statement!=null)
					statement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(c);
			}
		}
		
	}

	@Override
	public void update(AccountBean item) throws SQLException {
		PreparedStatement statement=null;
		String sql="Update Account Set nome=?, cognome=?, password=?, mail=?, tipo=?, verificato=?"
				+ "	where mail = ?";
		try {
			c=DriverManagerConnectionPool.getConnection();
			statement=c.prepareStatement(sql);
			
			statement.setString(1,item.getNome());
			statement.setString(2,item.getCognome());
			statement.setString(3,item.getPassword());
			statement.setString(4,item.getMail());
			statement.setInt(5, RuoloUtility.ruoloParser(item.getTipo()));
			statement.setBoolean(6, item.getVerificato());
			statement.setString(7,item.getMail());
			statement.executeUpdate();
			
		}finally {
			try{
				if(statement!= null)
					statement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(c);
			}

		}
		
	}

	@Override
	public void remove(AccountBean item) {
		// TODO Non necessario al momento
		
	}

	@Override
	public void remove(Specification specification) {
		// TODO Non necessario al momento
		
	}

	@Override
	public Collection<AccountBean> query(Specification specification) {
		PreparedStatement preparedStatement=null;
		SQLSpecification sqlSpecification=(SQLSpecification) specification;
		Collection<AccountBean> collection= new LinkedList<>();
		try {
			c=DriverManagerConnectionPool.getConnection();
			preparedStatement= c.prepareStatement(sqlSpecification.toSQLQuery());
			System.out.println("Query: " + preparedStatement.toString());
			
			ResultSet rs= preparedStatement.executeQuery();
			
			while(rs.next()) {
				AccountBean temp= new AccountBean();
				temp.setNome(rs.getString("Nome"));
				temp.setCognome(rs.getString("Cognome"));
				temp.setPassword(rs.getString("Password"));
				temp.setTipo(RuoloUtility.ruoloParser(rs.getInt("Tipo")));
				temp.setMail(rs.getString("Email"));
				temp.isVerificato(rs.getBoolean("Verificato"));
				if(temp.getTipo().equals(Ruolo.Utente)) {
					CartaDiCreditoBean carta= new CartaDiCreditoBean();
					carta.setMeseScadenza(rs.getDate("meseScadenza"));
					carta.setAnnoScadenza(rs.getDate("AnnoScadenza"));
					carta.setNomeIntestatario(rs.getString("NomeIntestatario"));
					carta.setNumeroCarta(rs.getString("NumeroCarta"));
					carta.setTipo(CartaEnumUtility.parserTipoCarta(rs.getString("Tipo")));
					temp.setCarta(carta);
				}
				else
					temp.setCarta(null);
				collection.add(temp);
			}
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(c);
			}
		}
		return collection;
	}


	
	
}
