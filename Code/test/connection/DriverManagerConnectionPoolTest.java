package connection;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.junit.*;


import junit.extensions.PrivilegedAccessor;
import junit.framework.TestCase;

public class DriverManagerConnectionPoolTest extends TestCase {
	
	public DriverManagerConnectionPoolTest() {
		super();
	}
	
	
	@Test 
	public void TestCreateConnection() throws SQLException {
		Connection c = DriverManagerConnectionPool.getConnection();
		c.close();
		assertTrue(c.isClosed());
	}
	
	@Test
	public void testReleaseConnessione() throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
		Connection connection=(Connection) PrivilegedAccessor.invokeMethod(DriverManagerConnectionPool.class, "createDBConnection()");
		DriverManagerConnectionPool.releaseConnection(connection);
		assertEquals("la connessione creata non � valida",1, ((List<Connection>)(PrivilegedAccessor.getValue(DriverManagerConnectionPool.class, "freeDbConnections"))).size());
	}
	
	@Test
	public void testReleaseConnessioneNull() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException, NoSuchFieldException {
		PrivilegedAccessor.setValue(DriverManagerConnectionPool.class, "freeDbConnections", new LinkedList<Connection>());
		DriverManagerConnectionPool.releaseConnection(null);
		assertEquals("Ha accettato una connessione null",0, ((List<Connection>)(PrivilegedAccessor.getValue(DriverManagerConnectionPool.class, "freeDbConnections"))).size());
	}
	
	
	
	

}
