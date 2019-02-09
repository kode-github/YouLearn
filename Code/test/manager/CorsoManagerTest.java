package manager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

import javax.naming.NoPermissionException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.AssertionFailedException;

import bean.AccountBean;
import bean.CorsoBean;
import bean.CorsoBean.Categoria;
import bean.CorsoBean.Stato;
import connection.DriverManagerConnectionPool;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import junit.framework.Assert;

public class CorsoManagerTest {

	
	private CorsoManager managerCorso;
	private AccountManager managerAccount;
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	
	@Before
	public void setUp() throws Exception {
		managerCorso = CorsoManager.getIstanza();
		managerAccount = AccountManager.getIstanza();
		assertNotNull(managerCorso);
	}

	@Test
	public void testDoRetrieveByKey() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean corso = managerCorso.doRetrieveByKey(3);
		assertNotNull(corso);
	}
	
	 
	@Test
	public void testSearchCorso() throws SQLException, NotFoundException {
		Collection<CorsoBean> result = managerCorso.searchCorso("Info");
		assertEquals(result.size(),2);
	}

	@Test
	public void testDoUpdate() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		CorsoBean restored = managerCorso.doRetrieveByKey(3);
		CorsoBean updated = managerCorso.doRetrieveByKey(3);
		updated.setCategoria(Categoria.Informatica);
		updated.setNome("Informatica 2.0");
		updated.setCopertina("testString");
		Date date = Date.valueOf("2020-10-10");
		Date date2 = Date.valueOf("2020-11-11");
		
		updated.setDataCreazione(date);
		updated.setDataFine(date2);
		updated.setDescrizione("Modificata");
		managerCorso.doUpdate(updated);
		CorsoBean check = managerCorso.doRetrieveByKey(3);
		
		assertEquals(check.getCategoria(), Categoria.Informatica);
		assertEquals(check.getNome(), "Informatica 2.0");
		assertEquals(check.getCopertina(), "testString");
		assertEquals(check.getDataCreazione(), date);
		assertEquals(check.getDataFine(), date2);
		assertEquals(check.getDescrizione(), "Modificata");
		
		managerCorso.doUpdate(restored);
		
		
	}



	@Test
	public void testRemoveCorso() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		CorsoBean corso = managerCorso.doRetrieveByKey(3);
		assertNotNull(corso);
		
		managerCorso.removeCorso(corso.getIdCorso());
		assertTrue(corso.getIdCorso() == 3);
		assertTrue(!managerCorso.checkCorso(corso.getIdCorso()));
		//FAIL test
		//addCorsoTest(managerCorso.doRetrieveByKey(3);
	}
	
	private void addCorsoTest(CorsoBean corso) throws SQLException {
		
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO Corso VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			
			connection=DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, 3);
			preparedStatement.setString(2, corso.getDocente().getMail());
			preparedStatement.setString(3, corso.getSupervisore().getMail());
			preparedStatement.setString(4, corso.getNome());
			preparedStatement.setString(5, corso.getDescrizione());
			preparedStatement.setDate(6, corso.getDataCreazione());
			preparedStatement.setDate(7, corso.getDataFine());
			preparedStatement.setString(8, corso.getCopertina());
			preparedStatement.setInt(9, corso.getPrezzo());
			preparedStatement.setString(10, corso.getStato().toString());
			preparedStatement.setString(11, corso.getCategoria().toString());
			preparedStatement.setInt(12, 2);
			preparedStatement.setInt(13, 0);
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
		}finally {
			try{
				if (preparedStatement != null)
					preparedStatement.close();
			}finally {
				connection.close();
			}	
		}
		
	}

	@Test
	public void testConvalidaCorso() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean convalida = managerCorso.doRetrieveByKey(2);
		assertNotNull(convalida);
		assertTrue(convalida.getIdCorso() == 2);
		managerCorso.convalidaCorso(true, convalida);
		
		convalida.setStato(Stato.Attesa);
		managerCorso.doUpdate(convalida);
		CorsoBean checked = managerCorso.doRetrieveByKey(2);
		
		assertEquals(checked.getStato().toString(), "Attesa");
	}
	
	@Test(expected=NotFoundException.class)
	public void testConvalidaCorsoNotFoundException() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean convalida = managerCorso.doRetrieveByKey(2);
		assertNotNull(convalida);
		convalida.setIdCorso(92);
		assertTrue(convalida.getIdCorso() == 92);
		managerCorso.convalidaCorso(true, convalida);
		
	}
	
	@Test(expected=NoPermissionException.class)
	public void testConvalidaCorsoNoPermissionException() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean convalida = managerCorso.doRetrieveByKey(3);
		assertNotNull(convalida);
		assertTrue(convalida.getIdCorso() == 3);
		managerCorso.convalidaCorso(true, convalida);
	}
	
	@Test
	public void testConvalidaCorsoNonAccettato() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean convalida = managerCorso.doRetrieveByKey(2);
		assertNotNull(convalida);
		assertTrue(convalida.getIdCorso() == 2);
		managerCorso.convalidaCorso(false, convalida);
		
		convalida.setStato(Stato.Attesa);
		managerCorso.doUpdate(convalida);
		CorsoBean checked = managerCorso.doRetrieveByKey(2);
		
		assertEquals(checked.getStato().toString(), "Attesa");
	}

	
	@Test(expected=NoPermissionException.class)
	public void testConfermaCorsoNoPermissionException() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean conferma = managerCorso.doRetrieveByKey(2);
		assertNotNull(conferma);
		assertTrue(conferma.getIdCorso() == 2);
		managerCorso.confermaCorso(conferma);
	}
	
	@Test(expected=NotFoundException.class)
	public void testConfermaCorsoNotFoundException() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean conferma = managerCorso.doRetrieveByKey(2);
		assertNotNull(conferma);
		conferma.setIdCorso(92);
		assertTrue(conferma.getIdCorso() == 92);
		
		managerCorso.confermaCorso(conferma);
		
	}
	
	@Test
	public void testConfermaCorso() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean conferma = managerCorso.doRetrieveByKey(4);
		assertNotNull(conferma);
		assertTrue(conferma.getIdCorso() == 4);
		managerCorso.confermaCorso(conferma);
		
		conferma.setStato(Stato.Completamento);
		managerCorso.doUpdate(conferma);
		CorsoBean checked = managerCorso.doRetrieveByKey(4);
		
		assertEquals(checked.getStato().toString(), "Completamento");
	}

	@Test
	public void testRetrieveByCreatore() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		AccountBean account = managerAccount.doRetrieveByKey("mario@gmail.com");
		assertNotNull(account);
		Collection<CorsoBean> corso = managerCorso.retrieveByCreatore(account);
		assertNotNull(corso);
		assertEquals(corso.size(), 2);
	}
	
	@Test(expected=NotFoundException.class)
	public void testRetrieveByCreatoreNotFound() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		AccountBean account = managerAccount.doRetrieveByKey("mario100@gmail.com");
		assertNotNull(account);
		Collection<CorsoBean> corso = managerCorso.retrieveByCreatore(account);
		assertNotNull(corso);
		assertEquals(corso.size(), 2);
	}
	
	@Test(expected=NoPermissionException.class)
	public void testRetrieveByCreatoreNotPermission() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		AccountBean account = managerAccount.doRetrieveByKey("francesco@gmail.com"); //Impostiamo un supervisore
		assertNotNull(account);
		managerCorso.retrieveByCreatore(account);
		
	}
	

	@Test
	public void testDoRetrieveBySupervisore() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		AccountBean account = managerAccount.doRetrieveByKey("admin.luigi@gmail.com");
		assertNotNull(account);
		Collection<CorsoBean> corso = managerCorso.doRetrieveBySupervisore(account);
		assertNotNull(corso);
		assertEquals(corso.size(), 1);
	}
	
	@Test(expected=NoPermissionException.class)
	public void testRetrieveBySupervisoreNotPermission() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		AccountBean account = managerAccount.doRetrieveByKey("mario@gmail.com"); //Impostiamo un utente
		assertNotNull(account);
		Collection<CorsoBean> corso = managerCorso.doRetrieveBySupervisore(account);
		
		
	}

	@Test
	public void testCheckCorsoCorsoBean() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean corso = managerCorso.doRetrieveByKey(1);
		assertEquals(managerCorso.checkCorso(corso),true);
	}

	@Test
	public void testCheckCorsoInt() throws SQLException {
		assertEquals(managerCorso.checkCorso(1),true);
	}

	@Test
	public void testIsWellFormatted() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		assertEquals(managerCorso.isWellFormatted(managerCorso.doRetrieveByKey(26)), true);
	}

}
