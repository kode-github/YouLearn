package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestDataSource extends TestCase{
	
	public void testAccesso() throws SQLException {
		DataSource c=new DataSource();
		c.setDriverClassName("com.mysql.jdbc.Driver");
		c.setUrl("jdbc:mysql://localhost:3306/youlearndb?useSSL=true"); //TODO Andrà migliorata con altri parametri
		c.setUsername("root");
		c.setPassword("PentiumD");
		Connection x=c.getConnection();
		PreparedStatement s=x.prepareStatement("select * from account");
		ResultSet rs=s.executeQuery();
		rs.next();
		System.out.println(""+rs.getString("nome"));
	}
	
	
	public static TestSuite suite() {
		return new TestSuite(TestDataSource.class);
	}
	
	public static void main(String args[]) { 
		junit.textui.TestRunner.run(suite()); 
		}
}
