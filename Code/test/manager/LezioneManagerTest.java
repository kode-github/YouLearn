/**
 * 
 */
package manager;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.CorsoBean;
import bean.LezioneBean;
import exception.DatiErratiException;
import exception.NotFoundException;

/**
 * @author Antonio
 *
 */
public class LezioneManagerTest {

	
	LezioneManager lezioneManager;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		lezioneManager=new LezioneManager();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDelete() throws SQLException, DatiErratiException, NotFoundException {
		CorsoBean x= new CorsoBean();
		x.setIdCorso(5);
		LezioneBean lezione=new LezioneBean();
		lezione.setIdLezione(7);
		lezione.setCorso(x);
		lezione.setFilePath("Lezione1.mp4");
		lezioneManager.delLezione(lezione);
	}

}
