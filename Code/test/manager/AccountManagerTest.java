/**
 * 
 */
package manager;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NoPermissionException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.AfterClass;
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
import hthurow.tomcatjndi.TomcatJNDI;
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
	
	static AccountManager managerAccount;
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
		managerAccount.doRetrieveByKey("peppino@mail.com");
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
		
		AccountBean _accountOne = managerAccount.doRetrieveByKey("mail_one@mail.com");
		assertNotNull(managerAccount.checkAccount(_accountOne));
		managerAccount.modificaMail("mail_" + "one"+ "@mail.com", "mail_" + "two" + "@mail.com");
		AccountBean _accountTwo = managerAccount.doRetrieveByKey("mail_two@mail.com");
		assertNotNull(managerAccount.checkAccount(_accountTwo));
		
		/* Reset */
		managerAccount.modificaMail("mail_" + "two"+ "@mail.com", "mail_" + "one" + "@mail.com");
		assertNotNull(managerAccount.checkAccount(_accountOne));
		
		
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
	
	@Test
	public void testSetRegistration() throws NoPermissionException, NotWellFormattedException, AlreadyExistingException, SQLException {
		AccountBean account = new AccountBean();
		CartaDiCreditoBean newCarta = new CartaDiCreditoBean();
		
		
		account.setNome("Test");
		account.setCognome("Test");
		account.setMail("mailRegTest@mail.com");
		account.setPassword("12345");
		account.setTipo(Ruolo.Utente);
		account.setVerificato(false);
	
		newCarta.setAnnoScadenza("2022");
		newCarta.setNomeIntestatario("TestName");
		newCarta.setMeseScadenza("12");
		newCarta.setNumeroCarta("0000000000000000");
		newCarta.setTipo(CartaEnum.PAYPAL);
		newCarta.setAccount(account);
		account.setCarta(newCarta);
		managerAccount.setRegistration(account); 
		this.resetRegistration(account);
		
	}
	
	/* Metodo da trasferire nel Manager, rimuove un account con la propria carta, eseguito SOLO DOPO un test di registrazione */
	
	private void resetRegistration(AccountBean account) throws SQLException {
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="DELETE FROM account WHERE email=?; "
				+ "DELETE FROM cartadicredito WHERE numeroCarta=?";
		try {
			connection=managerAccount.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			preparedStatement.setString(2, account.getCarta().getNumeroCarta());
			System.out.println("Reset registrazione: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
		}finally {
				try{
					if (preparedStatement != null)
						preparedStatement.close();
				}finally {
					connection.close();
				}		
		}
	}

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
