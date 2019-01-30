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

public class IscrizioneManagerTest {

	private IscrizioneManager managerIscrizione;
	private AccountManager managerAccount;
	private CorsoManager managerCorso;
	
	@Before
	public void setUp() throws Exception {
		managerIscrizione = new IscrizioneManager();
		managerCorso = new CorsoManager();
		managerAccount = new AccountManager();
		assertNotNull(managerIscrizione);
	}


	@Test
	public void testDoRetrieveByKey() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		assertNotNull(managerAccount.doRetrieveByKey("luigi@mail.com"));
		assertNotNull(managerCorso.doRetrieveByKey(3));
		assertNotNull(managerIscrizione.doRetrieveByKey(3, "luigi@mail.com"));
	}
	
	@Test(expected = NotFoundException.class)
	public void testDoRetrieveByKeyNotFound() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		assertNull(managerIscrizione.doRetrieveByKey(2, "mail_fail@mail.com"));
	}

	@Test
	public void testGetIscrizioniCorso() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		CorsoBean corso = managerCorso.doRetrieveByKey(0);
		assertNotNull(corso);
	    Collection<IscrizioneBean> x = managerIscrizione.getIscrittiCorso(corso);
	    Assert.assertTrue(!x.isEmpty()); // Se non è vuoto
	
		
	}

	@Test
	public void testGetIscrittiUtente() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		 
		AccountBean account = managerAccount.doRetrieveByKey("luigi@mail.com");
		assertNotNull(account);
		Collection<IscrizioneBean> x = managerIscrizione.getIscrizioniUtente(account); //Too many connection - Exception
		assertTrue(!x.isEmpty()); //Se non è vuoto
		
	}

	@Test
	public void testIscriviStudente() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException, AlreadyExistingException {
		
		Date date = Date.valueOf("2000-10-10"); //Formato SQL -> java.sql.Date
		assertNotNull(date);
		AccountBean account = managerAccount.doRetrieveByKey("mail0@mail.com");
		
		assertNotNull(account);
		CorsoBean corso =  managerCorso.doRetrieveByKey(3);
		assertNotNull(corso);
		IscrizioneBean newIscrizione = new IscrizioneBean();
		assertNotNull(newIscrizione);
		newIscrizione.setAccount(account);
		newIscrizione.setCorso(corso);
		newIscrizione.setDataPagamento(date);
		newIscrizione.setFattura("1234567890");
		newIscrizione.setImporto(10.20);
		managerIscrizione.iscriviStudente(newIscrizione);
	}
	
	@Test(expected=AlreadyExistingException.class)
	public void testIscriviStudenteAlreadyExistException() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException, AlreadyExistingException {
		
		Date date = Date.valueOf("2000-10-11"); //Formato SQL -> java.sql.Date
		assertNotNull(date);
		AccountBean account = managerAccount.doRetrieveByKey("luigi@mail.com");
		
		assertNotNull(account);
		CorsoBean corso =  managerCorso.doRetrieveByKey(3);
		assertNotNull(corso);
		IscrizioneBean newIscrizione = new IscrizioneBean();
		assertNotNull(newIscrizione);
		newIscrizione.setAccount(account);
		newIscrizione.setCorso(corso);
		newIscrizione.setDataPagamento(date);
		newIscrizione.setFattura("0987654321");
		newIscrizione.setImporto(10.20);
		managerIscrizione.iscriviStudente(newIscrizione);
	}
	
	

}
