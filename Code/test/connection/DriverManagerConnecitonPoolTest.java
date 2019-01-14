package connection;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.junit.*;
import junit.extensions.PrivilegedAccessor;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DriverManagerConnecitonPoolTest extends TestCase {
	
	public DriverManagerConnecitonPoolTest() {
		super();
	}
	
	
	//TEST CREAZIONE CONNESSIONE?
	
	@Test
	public void testReleaseConnessione() throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
		Connection connection=(Connection) PrivilegedAccessor.invokeMethod(DriverManagerConnectionPool.class, "createDBConnection()");
		DriverManagerConnectionPool.releaseConnection(connection);
		assertEquals("la connessione creata non è valida",1, ((List<Connection>)(PrivilegedAccessor.getValue(DriverManagerConnectionPool.class, "freeDbConnections"))).size());
	}
	
	@Test
	public void testReleaseConnessioneNull() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException, NoSuchFieldException {
		PrivilegedAccessor.setValue(DriverManagerConnectionPool.class, "freeDbConnections", new LinkedList<Connection>());
		DriverManagerConnectionPool.releaseConnection(null);
		assertEquals("Ha accettato una connessione null",0, ((List<Connection>)(PrivilegedAccessor.getValue(DriverManagerConnectionPool.class, "freeDbConnections"))).size());
	}
	
	@Test
	public void testReleaseConnessioneWrongDB() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException, NoSuchFieldException {
		PrivilegedAccessor.setValue(DriverManagerConnectionPool.class, "freeDbConnections", new LinkedList<Connection>());
		String ip = "localhost";
		String port = "3306";
		String db = "ottico";
		String username = "root";
		String password = "PentiumD";
		
		Connection newConnection = null;
		newConnection=DriverManager.getConnection("jdbc:mysql://"+ ip+":"+ 
				port+"/"+db+"?zeroDateTimeBehavior=convertToNull&useSSL=false", username, password);
		
		DriverManagerConnectionPool.releaseConnection(newConnection);
		assertEquals("Ha accettato una connessione ad un db differente",0, ((List<Connection>)(PrivilegedAccessor.getValue(DriverManagerConnectionPool.class, "freeDbConnections"))).size());
	}
	
	public static TestSuite suite() { 
		return new TestSuite(DriverManagerConnecitonPoolTest.class); 
		}
	
	public static void main(String args[]) { 
		junit.textui.TestRunner.run(suite()); 
		}
	

}
