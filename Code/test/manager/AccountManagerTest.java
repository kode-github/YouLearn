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
import exception.AlreadyExistingException;
import exception.DatiErratiException;
import exception.NotFoundException;
import exception.NotWellFormattedException;


/**
 * @author Mario Sessa
 * @version 1.1
 *
 */
public class AccountManagerTest {

	/**
	 * @throws java.lang.Exception
	 */
	
	static AccountManager managerAccount;
	CartaDiCreditoManager managerCarta;
	
	
	
	@Before
	public void setUp() throws Exception {
		
	
		managerAccount = new AccountManager();
		managerCarta = new CartaDiCreditoManager();
		assertNotNull(managerAccount);
		assertNotNull(managerCarta);
		
	}
	
	@After
	public void TierDown() {
		managerAccount = null;
		managerCarta = null;
		assertNull(managerAccount);
		assertNull(managerCarta);
	}
	
	/**
	 * Tests methods for {@link manager.AccountManager#doRetrieveByKey(java.lang.String)}.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 */

	@Test (expected = NotFoundException.class)
	public void testDoRetrieveByKeyNotFound() throws SQLException, NotFoundException, NoPermissionException {     
		
		assertFalse(managerAccount.checkMail("mario3@gmail.com"));
		managerAccount.doRetrieveByKey("mario3@gmail.com");
	}
	
	
	
	@Test
	public void testDoRetrieveByKey() throws SQLException, NotFoundException {
		AccountBean account = managerAccount.doRetrieveByKey("mario@gmail.com");
		assertEquals(account.getMail(), "mario@gmail.com");
	}
	
	

     /**
	 * Tests methods for {@link manager.AccountManager#modificaPassword(java.lang.String, java.lang.String)}.
     * @throws NotWellFormattedException 
     * @throws NotFoundException 
     * @throws SQLException 
     * @throws NoPermissionException 
	 */
	
	@Test(expected=NotFoundException.class)
	public void testModificaPasswordNotFoundAccount() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException{
		managerAccount.modificaPassword("mario70@gmail.com", "PentiumD");
	}
	
	
	@Test
	public void testModificaPassword() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {		
	    managerAccount.modificaPassword("mario@gmail.com", "PentiumD");
	    AccountBean account = managerAccount.doRetrieveByKey("mario@gmail.com");
		assertEquals(account.getMail(),"mario@gmail.com");
	    assertEquals(account.getPassword(), "PentiumD");
	}

	
	/**
	 * Tests method for {@link manager.AccountManager#modificaMail(java.lang.String, java.lang.String)}.
	 * @throws AlreadyExistingException 
	 * @throws NotFoundException 
	 * @throws SQLException 
	 * @throws NoPermissionException 
	 */
	
	
	
	@Test(expected=AlreadyExistingException.class)
	public void testModificaMailAlreadyExist() throws NoPermissionException, SQLException, NotFoundException, AlreadyExistingException {
		assertTrue(managerAccount.checkMail("pasquale@gmail.com"));
		assertTrue(managerAccount.checkMail("mario@gmail.com"));
		managerAccount.modificaMail("mario@gmail.com", "pasquale@gmail.com");
	}
	
	@Test(expected=NotFoundException.class)
	public void testModificaMailNotFound() throws NoPermissionException, SQLException, NotFoundException, AlreadyExistingException {
		
		assertFalse(managerAccount.checkMail("mario70@gmail.com"));
		managerAccount.modificaMail("mario70@gmail.com", "mario71@gmail.com");
	}
	
	
		
	@Test
	public void testModificaMail() throws NoPermissionException, SQLException, AlreadyExistingException, NotFoundException {
		

		assertNotNull(managerAccount.checkMail("mario@gmail.com"));
		
		managerAccount.modificaMail("mario@gmail.com", "mario2@gmail.com");
		
		assertNotNull(managerAccount.checkMail("mario2@gmail.com"));
		
		/* Reset */
		
		assertNotNull(managerAccount.checkMail("mario2@gmail.com"));
		
		managerAccount.modificaMail("mario@gmail.com", "mario2@gmail.com");
		
		assertNotNull(managerAccount.checkMail("mario@gmail.com"));
		
	}
	
	

	/**
	 * Tests method for {@link manager.AccountManager#login(java.lang.String, java.lang.String)}.
	 * @throws NotWellFormattedException 
	 * @throws DatiErratiException 
	 * @throws NotFoundException 
	 * @throws SQLException 
	 * @throws NoPermissionException 
	 */
	
	@Test(expected=DatiErratiException.class)
	public void testLoginDatiErrati() throws NoPermissionException, SQLException, NotFoundException, DatiErratiException, NotWellFormattedException {
		managerAccount.login("mario@gmail.com", "PentiumD1");
	}
	
	@Test
	public void testLogin() throws NoPermissionException, SQLException, NotFoundException, DatiErratiException, NotWellFormattedException {
		managerAccount.login("mario@gmail.com", "PentiumD");
	}



	/**
	 * Tests method for {@link manager.AccountManager#checkMail(java.lang.String)}.
	 * @throws SQLException 
	 * @throws NoPermissionException 
	 */
	
	
	@Test
	public void testCheckMail() throws NoPermissionException, SQLException {
		managerAccount.checkMail("mario@gmail.com");
	}

	/**
	 * Tests method for {@link manager.AccountManager#checkAccount(bean.AccountBean)}.
	 * @throws NotFoundException 
	 * @throws SQLException 
	 */
	
	@Test
	public void testCheckAccount() throws SQLException, NotFoundException {
		AccountBean account = managerAccount.doRetrieveByKey("pasquale@gmail.com");
		 managerAccount.checkAccount(account);
	}

	/**
	 * Tests method for {@link manager.AccountManager#isWellFormatted(bean.AccountBean)}.
	 * @throws NotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void testIsWellFormatted() throws SQLException, NotFoundException {
		AccountBean account = managerAccount.doRetrieveByKey("pasquale@gmail.com");
		managerAccount.isWellFormatted(account);
	}

	/**
	 * Tests method for {@link manager.AccountManager#retrieveLessUsedSupervisor()}.
	 * @throws NotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void testRetrieveLessUsedSupervisor() throws SQLException, NotFoundException {
		
		AccountBean account = managerAccount.retrieveLessUsedSupervisor();
		assertNotNull(account);
	}

	
	

}
