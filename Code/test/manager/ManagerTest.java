package manager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import controltest.corso.VisualCorsoTest;
import controltest.utente.LoginControlTest;
import controltest.utente.VisualizzaProfiloSupervisoreTest;
import controltest.utente.VisualizzaProfiloUtenteTest;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	AccountManagerTest.class,
	CartaDiCreditoManagerTest.class,
	CorsoManagerTest.class,
	IscrizioneManagerTest.class,
	LezioneManagerTest.class,
	LoginControlTest.class,
	VisualizzaProfiloUtenteTest.class,
	VisualizzaProfiloSupervisoreTest.class,
	VisualCorsoTest.class
})
public class ManagerTest {

	public static TestSuite suite() { 
		return new TestSuite(ManagerTest.class); 
		}
	
	public static void main(String args[]) { 
		junit.textui.TestRunner.run(suite()); 
		}
	
	
}
