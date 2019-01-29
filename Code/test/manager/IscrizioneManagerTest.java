package manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IscrizioneManagerTest {

	private IscrizioneManager managerIscrizione;
	@Before
	public void setUp() throws Exception {
		managerIscrizione = new IscrizioneManager();
		assertNotNull(managerIscrizione);
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testDoRetrieveByKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIscrizioniUtente() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIscrittiCorso() {
		fail("Not yet implemented");
	}

	@Test
	public void testIscriviStudente() {
		fail("Not yet implemented");
	}

}
