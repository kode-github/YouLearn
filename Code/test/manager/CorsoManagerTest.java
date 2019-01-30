package manager;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.naming.NoPermissionException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import bean.CorsoBean;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class CorsoManagerTest {

	
	private CorsoManager managerCorso;
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	
	@Before
	public void setUp() throws Exception {
		managerCorso = new CorsoManager(); 
		assertNotNull(managerCorso);
	}

	@Test
	public void testDoRetrieveByKey() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean corso = managerCorso.doRetrieveByKey(3);
		assertNotNull(corso);
	}
	
	@Test (expected=NotFoundException.class)
	public void testDoRetrieveByKeyNotFound() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean corso = managerCorso.doRetrieveByKey(10000);
		assertNull(corso);
	}

	@Test
	public void testSearchCorso() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreaCorso() {
		fail("Not yet implemented");
	}

	@Test
	public void testDoUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testModificaCorso() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveCorso() {
		fail("Not yet implemented");
	}

	@Test
	public void testConvalidaCorso() {
		fail("Not yet implemented");
	}

	@Test
	public void testConfermaCorso() {
		fail("Not yet implemented");
	}

	@Test
	public void testRetrieveByCreatore() {
		fail("Not yet implemented");
	}

	@Test
	public void testDoRetrieveBySupervisore() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckCorsoCorsoBean() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckCorsoInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsWellFormatted() {
		fail("Not yet implemented");
	}

}
