package manager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import bean.AccountBeanTest;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	AccountManagerTest.class,
	CartaDiCreditoManagerTest.class,
	CorsoManagerTest.class,
	IscrizioneManagerTest.class,
	LezioneManagerTest.class,
	AccountBeanTest.class
})
public class ManagerTest {

	public static TestSuite suite() { 
		return new TestSuite(ManagerTest.class); 
		}
	
	public static void main(String args[]) { 
		junit.textui.TestRunner.run(suite()); 
		}
	
	
}
