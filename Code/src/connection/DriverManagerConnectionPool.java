package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class DriverManagerConnectionPool {

	private static List<Connection> freeDbConnections;

	static {
		freeDbConnections = new LinkedList<Connection>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("DB driver not found:"+ e.getMessage());
		} 
	}
	
	private static synchronized Connection createDBConnection() throws SQLException {
		Connection newConnection = null;
		String ip = "localhost";
		String port = "3306";
		String db = "youlearndb";
		String username = "root";
		String password = "PentiumD";

		newConnection = DriverManager.getConnection("jdbc:mysql://"+ ip+":"+ 
				port+"/"+db+"?zeroDateTimeBehavior=convertToNull&useSSL=false", username, password);		

		System.out.println("Create a new DB connection");
		newConnection.setAutoCommit(false);
		newConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		return newConnection;
	}	
	
	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;

		if (!freeDbConnections.isEmpty()) {
			connection = (Connection) freeDbConnections.get(0);
			freeDbConnections.remove(0);

			try {
				if (connection.isClosed())
					connection = getConnection();
			} catch (SQLException e) {
				connection.close();
				connection = getConnection();
			}
		} else {
			connection = createDBConnection();		
		}

		return connection;
	}
	
	public static synchronized void releaseConnection(Connection connection) 
			throws SQLException {
			freeDbConnections.add(connection);
		}
			
	}	

	

