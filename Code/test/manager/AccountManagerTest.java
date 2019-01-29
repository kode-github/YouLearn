/**
 * 
 */
package manager;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.naming.NoPermissionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.AccountBean;
import bean.AccountBean.Ruolo;
import bean.CartaDiCreditoBean;
import bean.CartaDiCreditoBean.CartaEnum;
import exception.AlreadyExistingException;
import exception.DatiErratiException;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import connection.ConfiguredDataSource;
import connection.DriverManagerConnecitonPoolTest;
import junit.framework.TestSuite;

/**
 * @author Mario Sessa
 * @version 1.1
 *
 */
public class AccountManagerTest {

	/**
	 * @throws java.lang.Exception
	 */
	
	private static int increment;
	AccountManager managerAccount;
	CartaDiCreditoManager managerCarta;
	
	@Before
	public void setUp() throws Exception {
		
		managerAccount = new AccountManager();
		managerCarta = new CartaDiCreditoManager();
		assertNotNull(managerAccount);
		assertNotNull(managerCarta);
		
	}
	

	/**
	 * Test methods for {@link manager.AccountManager#doRetrieveByKey(java.lang.String)}.
	 * @throws SQLException
	 * @throws NotFoundException
	 */

	@Test (expected = NotFoundException.class)
	public void testDoRetrieveByKeyNotFound() throws SQLException, NotFoundException {     
		managerAccount.doRetrieveByKey("giacomo@mail.com");
	}
	
	
	
	@Test
	public void testDoRetrieveByKey() throws SQLException, NotFoundException {
		AccountBean account = managerAccount.doRetrieveByKey("pasquale@mail.com");
		assertEquals(account.getMail(), "pasquale@mail.com");
	}
	
	

     /**
	 * Test methods for {@link manager.AccountManager#modificaPassword(java.lang.String, java.lang.String)}.
     * @throws NotWellFormattedException 
     * @throws NotFoundException 
     * @throws SQLException 
     * @throws NoPermissionException 
	 */
	
	@Test
	public void testModificaPassword() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {		
	    managerAccount.modificaPassword("pasquale@mail.com", "pass123");
	    AccountBean account = managerAccount.doRetrieveByKey("pasquale@mail.com");
		assertEquals(account.getMail(),"pasquale@mail.com");
	    assertEquals(account.getPassword(), "pass123");
	}

	
	/**
	 * Test method for {@link manager.AccountManager#modificaMail(java.lang.String, java.lang.String)}.
	 * @throws AlreadyExistingException 
	 * @throws NotFoundException 
	 * @throws SQLException 
	 * @throws NoPermissionException 
	 */
	
		
	@Test
	public void testModificaMail() throws NoPermissionException, SQLException, AlreadyExistingException, NotFoundException {
		
		/* try { // Codice da rendere sequenziale per testing multipli
		
			AccountBean _accountOne = managerAccount.doRetrieveByKey("mail_one@mail.com");
		assertNotNull(managerAccount.checkAccount(_accountOne));
		
		managerAccount.modificaMail("mail_" + "one"+ "@mail.com", "mail_" + "two" + "@mail.com");
		
		AccountBean _accountTwo = managerAccount.doRetrieveByKey("mail_two@mail.com");
		
		assertNotNull(managerAccount.checkAccount(_accountTwo));
		} catch(NotFoundException e) {
			
			AccountBean _accountOne = managerAccount.doRetrieveByKey("mail_one@mail.com");
			assertNotNull(managerAccount.checkAccount(_accountOne));
			
			managerAccount.modificaMail("mail_" + "two"+ "@mail.com", "mail_" + "one" + "@mail.com");
			
			AccountBean _accountTwo = managerAccount.doRetrieveByKey("mail_two@mail.com");
			assertNotNull(managerAccount.checkAccount(_accountTwo)); 
			
		} */
		
	}
	
	

	/**
	 * Test method for {@link manager.AccountManager#login(java.lang.String, java.lang.String)}.
	 * @throws NotWellFormattedException 
	 * @throws DatiErratiException 
	 * @throws NotFoundException 
	 * @throws SQLException 
	 * @throws NoPermissionException 
	 */
	
	@Test(expected=DatiErratiException.class)
	public void testLoginDatiErrati() throws NoPermissionException, SQLException, NotFoundException, DatiErratiException, NotWellFormattedException {
		managerAccount.login("pasquale@mail.com", "pass1234");
	}
	
	@Test
	public void testLogin() throws NoPermissionException, SQLException, NotFoundException, DatiErratiException, NotWellFormattedException {
		managerAccount.login("pasquale@mail.com", "pass123");
	}

	/**
	 * Test method for {@link manager.AccountManager#setRegistration(bean.AccountBean)}.
	 * @throws SQLException 
	 * @throws AlreadyExistingException 
	 * @throws NotWellFormattedException 
	 * @throws NoPermissionException 
	 */
	
	/*@Test
	public void testSetRegistration() throws NoPermissionException, NotWellFormattedException, AlreadyExistingException, SQLException {
		AccountBean account = new AccountBean();
		CartaDiCreditoBean newCarta = new CartaDiCreditoBean();
		
		
		account.setNome("Test");
		account.setCognome("Test");
		account.setMail("mail"+increment++ +"@mail.com");
		account.setPassword("12345");
		account.setTipo(Ruolo.Utente);
		account.setVerificato(false);
	
		newCarta.setAnnoScadenza("2022");
		newCarta.setNomeIntestatario("TestName");
		newCarta.setMeseScadenza("12");
		newCarta.setNumeroCarta("000000000000000" + increment++);
		newCarta.setTipo(CartaEnum.PAYPAL);
		newCarta.setAccount(account);
		account.setCarta(newCarta);*/
		/* managerAccount.setRegistration(account); Test eseguito con successo, bisogna iterare i dati per test sequenziali */
	//}

	/**
	 * Test method for {@link manager.AccountManager#checkMail(java.lang.String)}.
	 * @throws SQLException 
	 * @throws NoPermissionException 
	 */
	@Test
	public void testCheckMail() throws NoPermissionException, SQLException {
		managerAccount.checkMail("pasquale@mail.com");
	}

	/**
	 * Test method for {@link manager.AccountManager#checkAccount(bean.AccountBean)}.
	 * @throws NotFoundException 
	 * @throws SQLException 
	 */
	
	@Test
	public void testCheckAccount() throws SQLException, NotFoundException {
		AccountBean account = managerAccount.doRetrieveByKey("pasquale@mail.com");
		 managerAccount.checkAccount(account);
	}

	/**
	 * Test method for {@link manager.AccountManager#isWellFormatted(bean.AccountBean)}.
	 * @throws NotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void testIsWellFormatted() throws SQLException, NotFoundException {
		AccountBean account = managerAccount.doRetrieveByKey("pasquale@mail.com");
		managerAccount.isWellFormatted(account);
	}

	/**
	 * Test method for {@link manager.AccountManager#retrieveLessUsedSupervisor()}.
	 */
	@Test
	public void testRetrieveLessUsedSupervisor() {
		// Devo finire di testare il CorsoManager per questo!
	}

	
	

}
