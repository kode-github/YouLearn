package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import bean.CommentoBean;
import connection.DriverManagerConnectionPool;
import exception.NotFoundException;

public class CommentoManager {

	
	public boolean delCommento(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM commento WHERE id = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, code);

			System.out.println("doDelete: "+ preparedStatement.toString());
			result = preparedStatement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return (result != 0);
	}

	public void insCommento(CommentoBean product) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO commento VALUES(?,?,?,?)";
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, product.getNumeroLezione());
			preparedStatement.setInt(2, product.getIdCommento());
			preparedStatement.setString(3, product.getTesto());
			preparedStatement.setString(4, product.getAccountMail());
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
	
	public Collection<CommentoBean> retrieveCommentiByCorso(int nLezione,int idCorso) throws SQLException,NotFoundException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		CommentoBean temp= new CommentoBean();
		Collection<CommentoBean> list= new LinkedList<>();
		
		String sql="SELECT* FROM commento WHERE numeroLezione=? AND corsoIdCorso=?";		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, nLezione);
			preparedStatement.setInt(2, idCorso);
			System.out.println("Query: " + preparedStatement.toString());
			
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("Non esiste questa lezione o questo corso"); //controllo che esista la lezione
			do {
				temp.setNumeroLezione(rs.getInt("numeroLezione"));
				temp.setIdCommento(rs.getInt("idCommento"));
				temp.setTesto(rs.getString("testo"));
				temp.setAccountMail(rs.getString("accountMail"));
				list.add(temp);
			}while(rs.next());
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return list;
	}
	
	
	
}
	
	
	

