/**
 * 
 */
package manager;


import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NoPermissionException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import bean.AccountBean;
import bean.CartaDiCreditoBean;
import bean.CartaDiCreditoBean.CartaEnum;
import connection.DriverManagerConnectionPool;
import bean.AccountBean.Ruolo;
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
	static CartaDiCreditoManager managerCarta;
	private  AccountBean tmpAccount;
	private  CartaDiCreditoBean tmpCarta;
	private AccountBean tmpAccount2;
	private CartaDiCreditoBean tmpCarta2;
	
	
	
	@Before
	public void setUp() throws Exception {
		
		
		managerAccount = AccountManager.getIstanza();
		managerCarta = CartaDiCreditoManager.getIstanza();
		assertNotNull(managerAccount);
		assertNotNull(managerCarta);
		createTmpComponent(); //Crea due utenti prototipi per i test
		assertTrue(managerAccount.checkMail("Prova@mail.com"));
		
	}
	
	@After
	public void TierDown() throws SQLException {
		managerAccount = null;
		managerCarta = null;
		assertNull(managerAccount);
		assertNull(managerCarta);
		deleteTmpComponent(); //Cancella gli utenti prototipi
	}
	
	/**
	 * Tests methods for {@link manager.AccountManager#doRetrieveByKey(java.lang.String)}.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 */

	@Test (expected = NotFoundException.class)
	public void testDoRetrieveByKeyNotFound() throws SQLException, NotFoundException, NoPermissionException {     
		
		
		assertNull(managerAccount.doRetrieveByKey("mario1000@gmail.com"));
	}
	
	
	
	@Test
	public void testDoRetrieveByKey() throws SQLException, NotFoundException {
		AccountBean account = managerAccount.doRetrieveByKey(tmpAccount.getMail());
		assertEquals(account.getMail(), tmpAccount.getMail());
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
		managerAccount.modificaPassword("mario70000@gmail.com", "PentiumD");
	}
	
	
	@Test
	public void testModificaPassword() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {		
	    managerAccount.modificaPassword(tmpAccount.getMail(), tmpAccount.getPassword());
	    AccountBean account = managerAccount.doRetrieveByKey(tmpAccount.getMail());
		assertEquals(account.getMail(),tmpAccount.getMail());
	    assertEquals(account.getPassword(), tmpAccount.getPassword());
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
		assertTrue(managerAccount.checkMail(tmpAccount.getMail()));
		managerAccount.modificaMail(tmpAccount.getMail(), tmpAccount.getMail());
		managerAccount.modificaMail("mario@gmail.com", tmpAccount.getMail());
	}
	
	@Test(expected=NotFoundException.class)
	public void testModificaMailNotFound() throws NoPermissionException, SQLException, NotFoundException, AlreadyExistingException {
		
		assertFalse(managerAccount.checkMail("mario70@gmail.com"));
		managerAccount.modificaMail("mario70@gmail.com", "mario71@gmail.com");
	}
	
	
		
	@Test
	public void testModificaMail() throws NoPermissionException, SQLException, AlreadyExistingException, NotFoundException {
		

		assertNotNull(managerAccount.checkMail(tmpAccount.getMail()));
		
		managerAccount.modificaMail(tmpAccount.getMail(), "Prova10291@gmail.com");
		
		assertNotNull(managerAccount.checkMail("Prova10291@gmail.com"));
		
		/* Reset */
		
		
		managerAccount.modificaMail("Prova10291@gmail.com", tmpAccount.getMail());
		
		assertNotNull(managerAccount.checkMail(tmpAccount.getMail()));
		
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
		managerAccount.login(tmpAccount.getMail(), "PentiumD1");
	}
	
	@Test
	public void testLogin() throws NoPermissionException, SQLException, NotFoundException, DatiErratiException, NotWellFormattedException {
		
		managerAccount.login("pasquale@gmail.com", "PentiumD");
	}



	/**
	 * Tests method for {@link manager.AccountManager#checkMail(java.lang.String)}.
	 * @throws SQLException 
	 * @throws NoPermissionException 
	 */
	
	
	@Test
	public void testCheckMail() throws NoPermissionException, SQLException {
		managerAccount.checkMail(tmpAccount.getMail());
	}

	/**
	 * Tests method for {@link manager.AccountManager#checkAccount(bean.AccountBean)}.
	 * @throws NotFoundException 
	 * @throws SQLException 
	 */
	
	@Test
	public void testCheckAccount() throws SQLException, NotFoundException {
		AccountBean account = managerAccount.doRetrieveByKey(tmpAccount.getMail());
		 assertTrue(managerAccount.checkAccount(account));
	}

	/**
	 * Tests method for {@link manager.AccountManager#isWellFormatted(bean.AccountBean)}.
	 * @throws NotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void testIsWellFormatted() throws SQLException, NotFoundException {
		AccountBean account = managerAccount.doRetrieveByKey(tmpAccount.getMail());
		assertTrue(managerAccount.isWellFormatted(account));
		account.setNome("a");
		assertFalse(managerAccount.isWellFormatted(account));
		account.setNome(tmpAccount.getNome());
		account.setCognome(" ");
		assertFalse(managerAccount.isWellFormatted(account));
		account.setCognome(tmpAccount.getCognome());
//		account.setCarta(null);
//		assertFalse(managerAccount.isWellFormatted(account));
//		account.setCarta(tmpAccount.getCarta());
//		account.setMail("hello");
//		assertFalse(managerAccount.isWellFormatted(account));
//		account.setMail(tmpAccount.getMail());
		account.setTipo(null);
		assertFalse(managerAccount.isWellFormatted(account));
		account.setTipo(tmpAccount.getTipo());
		
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
	
	/**
	 * Tests method for {@link manager.AccountManager#setRegistration(bean.AccountBean)}.
	 * @throws SQLException 
	 * @throws AlreadyExistingException 
	 * @throws NotWellFormattedException 
	 * @throws NoPermissionException 
	 */
	
	@Test
	public void testRegistration() throws SQLException, NoPermissionException, NotWellFormattedException, AlreadyExistingException {
		AccountBean registerTester = tmpAccount;
		AccountBean registerTester2 = tmpAccount2;
		deleteTmpComponent();
		managerAccount.setRegistration(registerTester);
		managerAccount.setRegistration(registerTester2);
		assertNotNull(managerAccount.checkMail(registerTester.getMail()));
		assertNotNull(managerAccount.checkMail(registerTester2.getMail()));
		deleteTmpComponent();
		createTmpComponent();
	}
	
	@Test(expected=AlreadyExistingException.class)
	public void testRegistrationAlreadyExist() throws NoPermissionException, NotWellFormattedException, AlreadyExistingException, SQLException {
		managerAccount.setRegistration(tmpAccount);
	}
	
	/**
	 * Setting parameters for JUnit Test cases
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException
	 * @throws AlreadyExistingException
	 * @throws SQLException
	 */

	private void createTmpComponent() throws NoPermissionException, NotWellFormattedException, AlreadyExistingException, SQLException {
		
		tmpCarta= new CartaDiCreditoBean("00001111188882222","10","2022", "Mario Sessa", CartaEnum.PAYPAL, tmpAccount);
		
		tmpAccount = new AccountBean("Mario", "Sessa", "PentiumD", "Prova@mail.com", Ruolo.Utente, true, tmpCarta);
		tmpCarta.setAccount(tmpAccount);
		tmpAccount.setCarta(tmpCarta);
		
		tmpCarta2= new CartaDiCreditoBean("00001111188883333","10","2022", "Mario Sessa", CartaEnum.PAYPAL, tmpAccount2);
		tmpAccount2 = new AccountBean("Mario", "Sessa", "PentiumD", "Prova2@mail.com", Ruolo.Utente, true, tmpCarta2);
		tmpAccount2.setCarta(tmpCarta2);
		tmpCarta2.setAccount(tmpAccount2);
	
		managerAccount.setRegistration(tmpAccount);
		managerAccount.setRegistration(tmpAccount2);
		
	}
	
	private void deleteTmpComponent() throws SQLException {
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String deleteSQL = "DELETE FROM account WHERE email=? OR email=?;";

			try {
				connection = DriverManagerConnectionPool.getConnection();
				preparedStatement = connection.prepareStatement(deleteSQL);
				preparedStatement.setString(1, tmpAccount.getMail());
				preparedStatement.setString(2, tmpAccount2.getMail());
				preparedStatement.executeUpdate();
				connection.commit();
			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					connection.close();
				}
			}
		
	}

}
