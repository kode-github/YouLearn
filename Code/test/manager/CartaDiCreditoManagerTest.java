package manager;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.NoPermissionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.AccountBean;
import bean.AccountBean.Ruolo;
import bean.CartaDiCreditoBean;
import bean.CartaDiCreditoBean.CartaEnum;
import connection.DriverManagerConnectionPool;
import exception.AlreadyExistingException;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class CartaDiCreditoManagerTest {

	
	private CartaDiCreditoManager managerCarta;
	private AccountManager managerAccount;
	private CartaDiCreditoBean tmpCarta;
	private AccountBean tmpAccount;
	private AccountBean tmpAccount2;
	private CartaDiCreditoBean tmpCarta2;
	

	
	@Before
	public void setUp() throws Exception {
		Field instance = CartaDiCreditoManager.class.getDeclaredField("istanza");
		instance.setAccessible(true);
		instance.set(null, null);
		
		Field istanza = AccountManager.class.getDeclaredField("istanza");
		   istanza.setAccessible(true);
		   istanza.set(null, null);
		
		managerCarta = CartaDiCreditoManager.getIstanza();
		managerAccount = AccountManager.getIstanza();
		createTmpComponent();
		assertNotNull(managerCarta);
		assertNotNull(managerAccount);
	}

	@After
	public void TierDown() throws Exception{
		
		deleteTmpComponent();
		//assertFalse(managerAccount.checkMail(tmpAccount.getMail()));
		//assertFalse(managerCarta.checkCarta(tmpCarta.getNumeroCarta()));
		//managerCarta = null;
		//managerAccount = null;
		//assertNull(managerAccount);
		//assertNull(managerCarta);
	}
	
	

	/**
	 * Tests methods for {@link manager.CartaDiCreditoManager#doRetrieveByKey(java.lang.String)}.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 */
	@Test
	public void testDoRetrieveByKey() throws NoPermissionException, SQLException, NotFoundException {
	
		assertNotNull(managerCarta.doRetrieveByKey(tmpCarta.getNumeroCarta()));
	}

	
	@Test(expected=NotFoundException.class)
	public void TestDoRetrieveByKeyNotFound() throws NoPermissionException, SQLException, NotFoundException {
		
		assertNull(managerCarta.doRetrieveByKey("1234567890123456")); 
	}
	
	
	/**
	 * Tests methods for {@link manager.CartaDiCreditoManager#modifyCard(bean.CartaDiCreditoBean, java.lang.String)}.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 */
	
	@Test
	public void testModifyCard() throws NoPermissionException, SQLException, NotFoundException, AlreadyExistingException, NotWellFormattedException {
		
		CartaDiCreditoBean oldCarta = managerCarta.doRetrieveByKey(tmpCarta.getNumeroCarta());
		CartaDiCreditoBean newCarta = managerCarta.doRetrieveByKey(tmpCarta2.getNumeroCarta());
		
		newCarta.setNumeroCarta("999999999991");
		System.out.println(oldCarta.getNumeroCarta());
		assertNotNull(newCarta);
		assertEquals(newCarta.getNumeroCarta(),"999999999991");
		managerCarta.modifyCard(newCarta, oldCarta.getNumeroCarta());
		//managerCarta.modifyCard(oldCarta, newCarta.getNumeroCarta());
		System.out.println(managerCarta.checkCarta(newCarta.getNumeroCarta()));
		
		
	}

	/**
	 * Tests methods for {@link manager.CartaDiCreditoManager#retrieveByAccount(bean.AccountBean)}.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 */
	
	@Test
	public void testRetrieveByAccount() throws NoPermissionException, SQLException, NotFoundException {
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAA" + tmpCarta.getNumeroCarta());
		AccountBean account = managerAccount.doRetrieveByKey(tmpCarta.getAccount().getMail());
		managerCarta.retrieveByAccount(account);
	}

	/**
	 * Test methods for {@link manager.CartaDiCreditoBean#isWellFormatted()}.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 */
	
	@Test
	public void testIsWellFormatted() throws NoPermissionException, SQLException, NotFoundException {
		assertEquals(managerCarta.isWellFormatted(tmpCarta),true);
	}
	
	/**
	 * Setting parameters for JUnit Test cases
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException
	 * @throws AlreadyExistingException
	 * @throws SQLException
	 */

	private void createTmpComponent() throws NoPermissionException, NotWellFormattedException, AlreadyExistingException, SQLException {
		
		System.out.println("INIZIO LA CREAZIONE\n");
		tmpCarta= new CartaDiCreditoBean("000011118888222","10","2022", "Mario Sessa", CartaEnum.PAYPAL, tmpAccount);
		
		tmpAccount = new AccountBean("Mario", "Sessa", "PentiumD", "Prova@mail.com", Ruolo.Utente, true, tmpCarta);
		tmpCarta.setAccount(tmpAccount);
		tmpAccount.setCarta(tmpCarta);
		
		tmpCarta2= new CartaDiCreditoBean("000011118888333","10","2022", "Mario Sessa", CartaEnum.PAYPAL, tmpAccount2);
		tmpAccount2 = new AccountBean("Mario", "Sessa", "PentiumD", "Prova2@mail.com", Ruolo.Utente, true, tmpCarta2);
		tmpAccount2.setCarta(tmpCarta2);
		tmpCarta2.setAccount(tmpAccount2);
		
		managerAccount.setRegistration(tmpAccount);
		managerAccount.setRegistration(tmpAccount2);
		System.out.println("FINISCO LA CREAZIONE\n");
		
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

