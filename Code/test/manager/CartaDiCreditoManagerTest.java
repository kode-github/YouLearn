package manager;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.naming.NoPermissionException;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.AccountBean;
import bean.AccountBean.Ruolo;
import bean.CartaDiCreditoBean;
import bean.CartaDiCreditoBean.CartaEnum;
import exception.AlreadyExistingException;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class CartaDiCreditoManagerTest {

	
	private CartaDiCreditoManager managerCarta;
	private AccountManager managerAccount;

	@Before
	public void setUp() throws Exception {
		managerCarta = new CartaDiCreditoManager();
		managerAccount = new AccountManager();
		assertNotNull(managerCarta);
		assertNotNull(managerAccount);
	}

	@After
	public void TierDown() throws Exception{
		managerCarta = null;
		managerAccount = null;
		assertNull(managerAccount);
		assertNull(managerCarta);
	}

	/**
	 * Tests methods for {@link manager.CartaDiCreditoManager#doRetrieveByKey(java.lang.String)}.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 */
	@Test
	public void testDoRetrieveByKey() throws NoPermissionException, SQLException, NotFoundException {
		assertTrue(managerCarta.checkCarta("1111222233334444"));
		managerCarta.doRetrieveByKey("1111222233334444");
	}

	@Test(expected=NotFoundException.class)
	public void TestDoRetrieveByKeyNotFound() throws NoPermissionException, SQLException, NotFoundException {
		assertFalse(managerCarta.checkCarta("1234567890123456"));
		managerCarta.doRetrieveByKey("1234567890123456"); 
	}
	
	
	/**
	 * Tests methods for {@link manager.CartaDiCreditoManager#modifyCard(bean.CartaDiCreditoBean, java.lang.String)}.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException 
	 */
	
	@Test
	public void testModifyCard() throws NoPermissionException, SQLException, NotFoundException, AlreadyExistingException, NotWellFormattedException {
		
		CartaDiCreditoBean oldCarta = managerCarta.doRetrieveByKey("1111222233334444");
		CartaDiCreditoBean newCarta = managerCarta.doRetrieveByKey("5555666677778888");
		
		newCarta.setNumeroCarta("999999999991");
		System.out.println(oldCarta.getNumeroCarta());
		assertNotNull(newCarta);
		assertEquals(newCarta.getNumeroCarta(),"999999999991");
		managerCarta.modifyCard(newCarta, oldCarta.getNumeroCarta());
		managerCarta.modifyCard(oldCarta, newCarta.getNumeroCarta());
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
		AccountBean account = managerAccount.doRetrieveByKey("mario@gmail.com");
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
		assertEquals(managerCarta.isWellFormatted(managerCarta.doRetrieveByKey("1111222233334444")),true);
	}

}
