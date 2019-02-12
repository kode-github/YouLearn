package manager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

<<<<<<< HEAD
=======

>>>>>>> fa5548eb42090af89a61f2f39686bff37fa40d6d
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	AccountManagerTest.class,
	CartaDiCreditoManagerTest.class,
	CorsoManagerTest.class,
	IscrizioneManagerTest.class,
	LezioneManagerTest.class
})
public class ManagerTest {

	public static TestSuite suite() { 
		return new TestSuite(ManagerTest.class); 
		}
	
	public static void main(String args[]) { 
		junit.textui.TestRunner.run(suite()); 
		}
	
	
}
