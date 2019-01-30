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


	@Test
	public void testDoRetrieveByKey() throws NoPermissionException, SQLException, NotFoundException {
		managerCarta.doRetrieveByKey("0000000000000000");
	}

	@Test(expected=NotFoundException.class)
	public void TestDoRetrieveByKeyNotFound() throws NoPermissionException, SQLException, NotFoundException {
		managerCarta.doRetrieveByKey("00000001010");
		
	}
	
	
	@Test
	public void testRegisterCard() throws NoPermissionException, SQLException, NotWellFormattedException, AlreadyExistingException {
		
		AccountBean account = new AccountBean();
		CartaDiCreditoBean newCarta = new CartaDiCreditoBean();
		
		account.setNome("Test");
		account.setCognome("Test");
		account.setMail("mail" +"@mail.com");
		account.setPassword("12345");
		account.setTipo(Ruolo.Utente);
		account.setVerificato(false);
		
		newCarta.setAnnoScadenza("2022");
		newCarta.setNomeIntestatario("TestName");
		newCarta.setMeseScadenza("12");
		newCarta.setNumeroCarta("0000000000000001");
		newCarta.setTipo(CartaEnum.PAYPAL);
		newCarta.setAccount(account);
		
		managerCarta.registerCard(newCarta);
	}

	@Test
	public void testModifyCard() throws NoPermissionException, SQLException, NotFoundException, AlreadyExistingException, NotWellFormattedException {
		
		CartaDiCreditoBean newCarta = managerCarta.doRetrieveByKey("0000000000000000");
		// managerCarta.modifyCard(newCarta, "0000000000000005"); Cambiamenti da effettuare
	}

	@Test
	public void testRetrieveByAccount() throws NoPermissionException, SQLException, NotFoundException {
		AccountBean account = managerAccount.doRetrieveByKey("pasquale@mail.com");
		managerCarta.retrieveByAccount(account);
	}

	@Test
	public void testIsWellFormatted() {
		// Da modificare
	}

}
