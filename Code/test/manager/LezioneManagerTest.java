/**
 * 
 */
package manager;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.naming.NoPermissionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.CorsoBean;
import bean.LezioneBean;
import exception.DatiErratiException;
import exception.NotFoundException;
import exception.NotWellFormattedException;

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
		lezioneManager=LezioneManager.getIstanza();
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
	
//	@Test
//	public void testDelete() throws SQLException, DatiErratiException, NotFoundException {
//		CorsoBean x= new CorsoBean();
//		x.setIdCorso(5);
//		LezioneBean lezione=new LezioneBean();
//		lezione.setIdLezione(7);
//		lezione.setCorso(x);
//		lezione.setFilePath("Lezione1.mp4");
//		lezioneManager.delLezione(lezione);
//	}
//	
	@Test
	public void testModificaOrdine(){
		String test="15-4,16-3,17-1,18-2";
		try {
			lezioneManager.modificaOrdine(5, test);
		} catch (NoPermissionException | SQLException | DatiErratiException | NotFoundException
				| NotWellFormattedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
