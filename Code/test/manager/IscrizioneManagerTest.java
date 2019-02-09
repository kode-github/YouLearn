package manager;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

import javax.naming.NoPermissionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.AccountBean;
import bean.CorsoBean;
import bean.IscrizioneBean;
import exception.AlreadyExistingException;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import junit.framework.Assert;
import junit.framework.TestCase;

public class IscrizioneManagerTest{

	private IscrizioneManager managerIscrizione;
	private AccountManager managerAccount;
	private CorsoManager managerCorso;
	
	@Before
	public void setUp() throws Exception {
		managerIscrizione = IscrizioneManager.getIstanza();
		managerCorso = CorsoManager.getIstanza();
		managerAccount = AccountManager.getIstanza();
		assertNotNull(managerIscrizione);
		assertNotNull(managerCorso);
		assertNotNull(managerAccount);
	}


	@After
	public void tearDown() throws Exception {
		managerIscrizione = null;
		managerCorso = null;
		managerAccount = null;
		assertNull(managerIscrizione);
		assertNull(managerCorso);
		assertNull(managerAccount);
	}
	
	@Test
	public void testDoRetrieveByKey() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		assertNotNull(managerAccount.doRetrieveByKey("pasquale@gmail.com"));
		assertNotNull(managerCorso.doRetrieveByKey(1));
		assertNotNull(managerIscrizione.doRetrieveByKey(1, "pasquale@gmail.com"));
	}
	
	@Test(expected = NotFoundException.class)
	public void testDoRetrieveByKeyNotFound() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		assertNull(managerIscrizione.doRetrieveByKey(1, "mail@gmail.com"));
	}

	@Test
	public void testGetIscrizioniCorso() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		CorsoBean corso = managerCorso.doRetrieveByKey(1);
		assertNotNull(corso);
	    Collection<IscrizioneBean> x = managerIscrizione.getIscrittiCorso(corso);
	    Assert.assertTrue(x.size() == 1); // Se non è vuoto
		
	}

	@Test
	public void testGetIscrittiUtente() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		 
		AccountBean account = managerAccount.doRetrieveByKey("luigi@gmail.com");
		assertNotNull(account);
		Collection<IscrizioneBean> x = managerIscrizione.getIscrizioniUtente(account); //Too many connection - Exception
		assertTrue(x.isEmpty()); //Se non è vuoto
		
		AccountBean account2 = managerAccount.doRetrieveByKey("pasquale@gmail.com");
		assertNotNull(account2);
		Collection<IscrizioneBean> y = managerIscrizione.getIscrizioniUtente(account2);
		assertEquals(y.size(),1);
		
	}

	@Test
	public void testIscriviStudente() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException, AlreadyExistingException {
		
		Date date = Date.valueOf("2000-10-10"); //Formato SQL -> java.sql.Date
		assertNotNull(date);
		AccountBean account = managerAccount.doRetrieveByKey("luigi@gmail.com");
		
		assertNotNull(account);
		CorsoBean corso =  managerCorso.doRetrieveByKey(1);
		assertNotNull(corso);
		IscrizioneBean newIscrizione = new IscrizioneBean();
		assertNotNull(newIscrizione);
		newIscrizione.setAccount(account);
		newIscrizione.setCorso(corso);
		newIscrizione.setDataPagamento(date);
		newIscrizione.setFattura("1234567899");
		newIscrizione.setImporto(20);
		managerIscrizione.iscriviStudente(newIscrizione);
	}
	
	@Test(expected=AlreadyExistingException.class)
	public void testIscriviStudenteAlreadyExistException() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException, AlreadyExistingException {
		
		Date date = Date.valueOf("2018-10-11"); //Formato SQL -> java.sql.Date
		assertNotNull(date);
		AccountBean account = managerAccount.doRetrieveByKey("pasquale@gmail.com");
		
		assertNotNull(account);
		CorsoBean corso =  managerCorso.doRetrieveByKey(1);
		assertNotNull(corso);
		IscrizioneBean newIscrizione = new IscrizioneBean();
		assertNotNull(newIscrizione);
		newIscrizione.setAccount(account);
		newIscrizione.setCorso(corso);
		newIscrizione.setDataPagamento(date);
		System.out.println(date.toString());
		newIscrizione.setFattura("1234567890");
		newIscrizione.setImporto(20.0);
		managerIscrizione.iscriviStudente(newIscrizione);
	}
	
	

}
