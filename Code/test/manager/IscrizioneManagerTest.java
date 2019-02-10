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
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import javax.naming.NoPermissionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.AccountBean;
import bean.CartaDiCreditoBean;
import bean.CorsoBean;
import bean.IscrizioneBean;
import bean.AccountBean.Ruolo;
import bean.CartaDiCreditoBean.CartaEnum;
import bean.CorsoBean.Categoria;
import bean.CorsoBean.Stato;
import connection.DriverManagerConnectionPool;
import exception.AlreadyExistingException;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import junit.framework.Assert;
import junit.framework.TestCase;

public class IscrizioneManagerTest{

	private IscrizioneManager managerIscrizione;
	private AccountManager managerAccount;
	private CorsoManager managerCorso;
	private AccountBean tmpAccount; //docente
	private CorsoBean tmpCorso; //corso
	private IscrizioneBean tmpIscrizione; //iscrizione
	private CartaDiCreditoBean tmpCarta; //carta Docente
	private CartaDiCreditoBean tmpCarta2; //carta Iscritto
	private AccountBean tmpAccount2; //account iscritto
	
	@Before
	public void setUp() throws Exception {
		managerIscrizione = IscrizioneManager.getIstanza();
		managerCorso = CorsoManager.getIstanza();
		managerAccount = AccountManager.getIstanza();
		assertNotNull(managerIscrizione);
		assertNotNull(managerCorso);
		assertNotNull(managerAccount);
		createTmpComponentIscrizione();
		
	}


	@After
	public void tearDown() throws Exception {
		
		deleteTmpComponentIscrizione();
	}
	
	@Test
	public void testDoRetrieveByKey() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		assertNotNull(managerIscrizione.doRetrieveByKey(tmpIscrizione.getCorso().getIdCorso(), tmpIscrizione.getAccount().getMail()));
	}
	
	@Test(expected = NotFoundException.class)
	public void testDoRetrieveByKeyNotFound() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		assertNull(managerIscrizione.doRetrieveByKey(tmpIscrizione.getCorso().getIdCorso(), tmpCorso.getDocente().getMail())); //Il docente non segue il suo corso
		
	}

	@Test
	public void testGetIscrizioniCorso() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
	
	    Assert.assertTrue(tmpIscrizione.getCorso().getnIscritti() == 1); //Ha solo 1 iscrizione
		
	}

	@Test
	public void testGetIscrittiUtente() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		 
		assertNull(managerIscrizione.getIscrizioniUtente(tmpAccount)); //account docente che non segue corsi
		assertNotNull(managerIscrizione.getIscrizioniUtente(tmpAccount2)); //Account utente che segue 1 corso
		
		
	}

//	@Test
//	public void testIscriviStudente() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException, AlreadyExistingException {
//		
//		Date date = Date.valueOf("2000-10-10"); //Formato SQL -> java.sql.Date
//		assertNotNull(date);
//		AccountBean account = managerAccount.doRetrieveByKey("luigi@gmail.com");
//		
//		assertNotNull(account);
//		CorsoBean corso =  managerCorso.doRetrieveByKey(1);
//		assertNotNull(corso);
//		IscrizioneBean newIscrizione = new IscrizioneBean();
//		assertNotNull(newIscrizione);
//		newIscrizione.setAccount(account);
//		newIscrizione.setCorso(corso);
//		newIscrizione.setDataPagamento(date);
//		newIscrizione.setFattura("1234567899");
//		newIscrizione.setImporto(20);
//		managerIscrizione.iscriviStudente(newIscrizione);
//	}
	
	@Test(expected=AlreadyExistingException.class)
	public void testIscriviStudenteAlreadyExistException() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException, AlreadyExistingException {
		
		managerIscrizione.iscriviStudente(tmpIscrizione); //Iscritto già nel setup
	}
	
	/**
	 * Setting parameters for JUnit Test cases
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException
	 * @throws AlreadyExistingException
	 * @throws SQLException
	 * @throws NotFoundException 
	 */

	private void createTmpComponentIscrizione() throws NoPermissionException, NotWellFormattedException, AlreadyExistingException, SQLException, NotFoundException {
		System.out.println("INIZIO LA CREAZIONE\n");
		
		//Carta docente
		tmpCarta= new CartaDiCreditoBean("1111111111111111","10","2022", "Mario Sessa", CartaEnum.PAYPAL, tmpAccount);
		//Account docente
		tmpAccount = new AccountBean("Mario", "Sessa", "PentiumD", "Prova@mail.com", Ruolo.Utente, true, tmpCarta);
		
		//Corso
		tmpCorso = new CorsoBean(10,"Informatica", "Informatica per principianti in linguaggio C", Date.valueOf("2018-10-10"), Date.valueOf("2020-10-10"), 12, Categoria.Informatica, "/User/kode/git/YouLearn/WebContent/Resources/Desert.jsp", Stato.Attivo,1,0, tmpAccount, null); //Non ha un supervisore 
		
		
		//Setting relazione account-carta
		tmpAccount.setCarta(tmpCarta);
		tmpCarta.setAccount(tmpAccount);
		tmpCorso.setDocente(tmpAccount);
		
		// Setting account iscritto e la sua carta
		
		tmpAccount2 = new AccountBean("Mario", "Sessa", "PentiumD", "Prova2@mail.com", Ruolo.Utente, true, tmpCarta2);
		tmpCarta2= new CartaDiCreditoBean("1111111111114111","10","2022", "Mario Sessa", CartaEnum.PAYPAL, tmpAccount2);
		Date dataPagamento = Date.valueOf("2019-10-10");
		
		
		tmpIscrizione = new IscrizioneBean(tmpAccount2, tmpCorso, dataPagamento, 10, "0987865243");
		
		//Setting relazione account-carta e relazione iscrizione (corso-account)
		
		tmpAccount2.setCarta(tmpCarta2);
		tmpCarta2.setAccount(tmpAccount2);
		tmpIscrizione.setAccount(tmpAccount2);
		tmpIscrizione.setCorso(tmpCorso);
		
		//Inserisci gli account e le carte
		
		managerAccount.setRegistration(tmpAccount);
		managerAccount.setRegistration(tmpAccount2);
		
		//Creazione corso - controllare riferimenti a corso
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO Corso VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			
			
			connection=DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, null);
			preparedStatement.setString(2, tmpCorso.getDocente().getMail());
			preparedStatement.setString(3, null);
			preparedStatement.setString(4, tmpCorso.getNome());
			preparedStatement.setString(5, tmpCorso.getDescrizione());
			preparedStatement.setDate(6, tmpCorso.getDataCreazione());
			preparedStatement.setDate(7, tmpCorso.getDataFine());
			preparedStatement.setString(8, tmpCorso.getCopertina());
			preparedStatement.setInt(9, tmpCorso.getPrezzo());
			preparedStatement.setString(10, tmpCorso.getStato().toString());
			preparedStatement.setString(11, tmpCorso.getCategoria().toString());
			preparedStatement.setInt(12, 0);
			preparedStatement.setInt(13, 0);
			System.out.println("Registrazione corso: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			
			if(!connection.getAutoCommit())
				connection.commit();
		}catch(SQLException e) {
			e.printStackTrace();
			connection.rollback();
			throw new SQLException();
		}finally {
				try{
					if (preparedStatement != null)
						preparedStatement.close();
				}finally {
					DriverManagerConnectionPool.releaseConnection(connection);
				}		
		}
		
		
		
		managerIscrizione.iscriviStudente(tmpIscrizione);
		
	}
	
	private void deleteTmpComponentIscrizione() throws SQLException {
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String deleteSQL = "DELETE FROM account WHERE email=? OR email=?";

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
					DriverManagerConnectionPool.releaseConnection(connection);
				}
			}
		
	}

}
