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
		c.setUrl("jdbc:mysql://localhost:3306/youlearndb"); //TODO Andrà migliorata con altri parametri
		c.setUsername("root");
		c.setPassword("PentiumD");
		
		Connection x=c.getConnection();
		x.setAutoCommit(false);
		PreparedStatement s=x.prepareStatement("Insert into account values ('luigi','a','a','a','aa','1')");
		s.executeUpdate();
//		x.rollback();
		x.commit();
		PreparedStatement l=x.prepareStatement("Select * from account");
		ResultSet test=l.executeQuery();
		test.next();
		System.out.println(""+test.getString("nome"));
	}
	
	
	public static TestSuite suite() {
		return new TestSuite(TestDataSource.class);
	}
	
	public static void main(String args[]) { 
		junit.textui.TestRunner.run(suite()); 
		}
}
