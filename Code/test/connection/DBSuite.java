package connection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({ DriverManagerConnectionPoolTest.class})
public class DBSuite {
	
	public static TestSuite suite() { 
		return new TestSuite(DriverManagerConnectionPoolTest.class); 
		}
	
	public static void main(String args[]) { 
		junit.textui.TestRunner.run(suite()); 
		}
}
