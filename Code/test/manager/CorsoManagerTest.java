package manager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.LinkedList;
import java.util.UUID;

import javax.naming.NoPermissionException;
import javax.servlet.http.Part;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.AssertionFailedException;

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

public class CorsoManagerTest {

	
	private CorsoManager managerCorso;
	private AccountManager managerAccount;
	private AccountBean tmpAccount; //Account docente
	private AccountBean tmpAccount2; //Account utente
	private CartaDiCreditoBean tmpCarta; //Carta docente
	private CorsoBean tmpCorso; //Corso in fase di Attesa
	private CartaDiCreditoBean tmpCarta2;
	private IscrizioneBean tmpIscrizione;
	private IscrizioneManager managerIscrizione;
	
	@After
	public void tearDown() throws Exception{
		deleteTmpComponentCorso();
	}
	
	@Before
	public void setUp() throws Exception {
		managerCorso = CorsoManager.getIstanza(System.getProperty("user.dir")+"\\WebContent");
		managerAccount = AccountManager.getIstanza();
		managerIscrizione = IscrizioneManager.getIstanza();
		assertNotNull(managerCorso);
		assertNotNull(managerAccount);
		assertNotNull(managerIscrizione);
		createTmpComponentCorso();
	}
	
	@Test
	public void creaCorso() throws NoPermissionException, SQLException, NotWellFormattedException, NotFoundException {
		tmpCorso.setIdCorso(null);
		managerCorso.creaCorso(tmpCorso, new Part() {
			
			@Override
			public void write(String arg0) throws IOException {
				return;
				
			}
			
			@Override
			public String getSubmittedFileName() {
				return "azz.jpg";
			}
			
			@Override
			public long getSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getName() {
				return "azz";
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<String> getHeaders(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<String> getHeaderNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getHeader(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void delete() throws IOException {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Test
	public void modificaCorso() throws NoPermissionException, NotFoundException, NotWellFormattedException, SQLException, IOException {
			tmpCorso.setStato(Stato.Completamento);
			managerCorso.modificaCorso(tmpCorso,  new Part() {
			
			@Override
			public void write(String arg0) throws IOException {
				return;
				
			}
			
			@Override
			public String getSubmittedFileName() {
				return "azz.jpg";
			}
			
			@Override
			public long getSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getName() {
				return "azz";
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<String> getHeaders(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<String> getHeaderNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getHeader(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void delete() throws IOException {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

	/**
	 * Test di prelevazione di un corso tramite l'uso del suo id.
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 */
	@Test
	public void testDoRetrieveByKey() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		managerCorso.checkCorso(managerCorso.doRetrieveByKey(tmpCorso.getIdCorso()));
		
	}
	
	 
	/**
	 * Test per il prelievo dei corsi in seguito alla digitura di una keyword di esempio
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	
	@Test
	public void testSearchCorso() throws SQLException, NotFoundException {
		Collection<CorsoBean> result = managerCorso.searchCorso("Info");
		assertEquals(result.size(),1);
	}

	/**
	 * Test di modifica di un corso
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 */
	
	@Test
	public void testDoUpdate() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		CorsoBean restored = managerCorso.doRetrieveByKey(tmpCorso.getIdCorso());
		CorsoBean updated = managerCorso.doRetrieveByKey(tmpCorso.getIdCorso());
		System.out.println(restored.getIdCorso());
		System.out.println(updated.getIdCorso());
		updated.setCategoria(Categoria.Informatica);
		updated.setNome("Programmazione C");
		updated.setCopertina("/User/local/lib/img.png"); //I controlli sull'URL vengono effettuati nell'isWellFormatted 
		Date date = Date.valueOf("2018-02-02");
		Date date2 = Date.valueOf("2020-11-11");
		
		updated.setDataCreazione(date);
		updated.setDataFine(date2);
		updated.setDescrizione("ModificataDi10caratterialmeno");
		managerCorso.doUpdate(updated);
		CorsoBean check = managerCorso.doRetrieveByKey(tmpCorso.getIdCorso());
		
		assertEquals(check.getCategoria(), Categoria.Informatica);
		assertEquals(check.getNome(), "Programmazione C");
		assertEquals(check.getCopertina(), "/User/local/lib/img.png");
		assertEquals(check.getDataCreazione(), date);
		assertEquals(check.getDataFine(), date2);
		assertEquals(check.getDescrizione(), "ModificataDi10caratterialmeno");
		
		managerCorso.doUpdate(restored);
		
		
	}



	/**
	 * Test di rimozione di un corso
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 * @throws IOException 
	 */
	@Test
	public void testRemoveCorso() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException, IOException {

		tmpCorso.setStato(Stato.Completamento);
		managerCorso.doUpdate(tmpCorso); //Facciamo solo l'update dato che l'onDelete
		assertEquals(tmpCorso.getStato(), Stato.Completamento);
		managerCorso.removeCorso(tmpCorso.getIdCorso());
		assertTrue(!managerCorso.checkCorso(tmpCorso.getIdCorso()));
		
		
	
	}
	
	/**
	 * Aggiorna il corso temporaneo, non è possibile modificare il docente poichè il tierDown elimina in cascade partendo dall'account docente
	 * @param corso
	 * @param docenteMail
	 * @throws SQLException
	 * @deprecated 
	 */
	
	
//	private void updateTmpCorsoTest(CorsoBean corso, String docenteMail) throws SQLException {
//		
//		System.out.println("Update su " + corso.getIdCorso() + "con docente " + docenteMail);
//		Connection connection=null;
//		PreparedStatement preparedStatement=null;
//		
//		String sql="UPDATE corso SET stato=? WHERE idCorso=?";
//		
//		try {
//			
//			connection=DriverManagerConnectionPool.getConnection();
//			connection.setAutoCommit(false);
//			preparedStatement= connection.prepareStatement(sql);
//			preparedStatement.setString(1, corso.getStato().toString());
//			preparedStatement.setInt(2, corso.getIdCorso());
//			
//			System.out.println("UPDATE TEMPORANEO:" + preparedStatement.toString());
//			preparedStatement.executeUpdate();
//			System.out.println("UPDATE ESEGUITO");
//			preparedStatement.close();
//			
//		}finally {
//			try{
//				if (preparedStatement != null)
//					preparedStatement.close();
//			}finally {
//				connection.close();
//			}	
//		}
//		
//	}

	
	/**
	 * Test per la convalida di un corso, il corso deve essere sempre nello stato di atteso per non lanciare un permesso negato, bisogna anche controllare gli altri casi di eccezione attraverso test appositi
	 * La convalida prevede il passaggio di stato di un corso da Atteso a:
	 *  
	 * Completamento - Se rifiutato
	 * Attivo - Se confermato
	 * 
	 * Operazione propria di un supervisore
	 * 
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 */
	
	@Test
	public void testConvalidaCorsoConfermaSupervisore() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		
		tmpCorso.setStato(Stato.Attesa);
		managerCorso.doUpdate(tmpCorso); //Facciamo solo l'update dato che l'onDelete
		assertEquals(managerCorso.doRetrieveByKey(tmpCorso.getIdCorso()).getStato(), Stato.Attesa);
		assertEquals(managerCorso.checkCorso(tmpCorso.getIdCorso()), true);
		managerCorso.convalidaCorso(true, tmpCorso); //Accetta il corso
		assertEquals(tmpCorso.getStato(), Stato.Attivo);
		
		
	}
	
	/**
	 * Test per la convalida di un corso, il corso deve essere sempre nello stato di atteso per non lanciare un permesso negato, bisogna anche controllare gli altri casi di eccezione attraverso test appositi
	 * La convalida prevede il passaggio di stato di un corso da Atteso a:
	 *  
	 * Completamento - Se rifiutato
	 * Attivo - Se confermato
	 * 
	 * Operazione propria di un supervisore
	 * 
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 */
	
	@Test
	public void testConvalidaCorsoRifiutoSupervisore() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		
		tmpCorso.setStato(Stato.Attesa);
		managerCorso.doUpdate(tmpCorso); //Facciamo solo l'update dato che l'onDelete
		assertEquals(managerCorso.doRetrieveByKey(tmpCorso.getIdCorso()).getStato(), Stato.Attesa);
		assertEquals(managerCorso.checkCorso(tmpCorso.getIdCorso()), true);
		managerCorso.convalidaCorso(false, tmpCorso); //Rifiuta il corso
		assertEquals(tmpCorso.getStato(), Stato.Completamento);
		
	}
	
	
	/**
	 * Test per la convalida di un corso, il corso deve essere sempre nello stato di atteso per non lanciare un permesso negato, bisogna anche controllare gli altri casi di eccezione attraverso test appositi
	 * La convalida prevede il passaggio di stato di un corso da Atteso a:
	 *  
	 * Completamento - Se rifiutato
	 * Attivo - Se confermato
	 * 
	 * Operazione propria di un supervisore
	 * 
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 */
	
	@Test(expected=NotFoundException.class)
	public void testConvalidaCorsoNotFoundException() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		int id = tmpCorso.getIdCorso();
		tmpCorso.setIdCorso(tmpCorso.getIdCorso()+21);
		managerCorso.convalidaCorso(true, tmpCorso);
		tmpCorso.setIdCorso(id);
	}
	
	/**
	 * Test per la convalida di un corso, il corso deve essere sempre nello stato di atteso per non lanciare un permesso negato, bisogna anche controllare gli altri casi di eccezione attraverso test appositi
	 * La convalida prevede il passaggio di stato di un corso da Atteso a:
	 *  
	 * Completamento - Se rifiutato
	 * Attivo - Se confermato
	 * 
	 * Operazione propria di un supervisore
	 * 
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 */
	
	@Test(expected=NoPermissionException.class)
	public void testConvalidaCorsoNoPermissionException() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		managerCorso.convalidaCorso(true, tmpCorso);
	}
	
	/**
	 * Test di conferma di un corso, tale operazione prevede di passare un corso dallo stato di completamento allo stato di attesa.
	 * Tale operazione è propria per un utente che sta creando un corso in cui sarà poi docente. Al termine della creazione, provvederà tramite una funzionalità collegata
	 * a passare il suo corso alla fase di supervisionamento. Tale funzionalità è rappresentata all'interno dai manager dal metodo di conferma a cui fanno riferimento i test appena selezionati.
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 */
	
	@Test(expected=NoPermissionException.class)
	public void testConfermaCorsoNoPermissionException() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		managerCorso.confermaCorso(tmpCorso);
	}
	
	/**
	 * Test di conferma di un corso, tale operazione prevede di passare un corso dallo stato di completamento allo stato di attesa.
	 * Tale operazione è propria per un utente che sta creando un corso in cui sarà poi docente. Al termine della creazione, provvederà tramite una funzionalità collegata
	 * a passare il suo corso alla fase di supervisionamento. Tale funzionalità è rappresentata all'interno dai manager dal metodo di conferma a cui fanno riferimento i test appena selezionati.
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 */
	
	@Test(expected=NotFoundException.class)
	public void testConfermaCorsoNotFoundException() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		int id = tmpCorso.getIdCorso();
		tmpCorso.setIdCorso(id+10);
		assertTrue(tmpCorso.getIdCorso() == (id+10));
		managerCorso.confermaCorso(tmpCorso);
		tmpCorso.setIdCorso(id);
		assertTrue(tmpCorso.getIdCorso() == id);
		
	}
	
	/**
	 * Test di conferma di un corso, tale operazione prevede di passare un corso dallo stato di completamento allo stato di attesa.
	 * Tale operazione è propria per un utente che sta creando un corso in cui sarà poi docente. Al termine della creazione, provvederà tramite una funzionalità collegata
	 * a passare il suo corso alla fase di supervisionamento. Tale funzionalità è rappresentata all'interno dai manager dal metodo di conferma a cui fanno riferimento i test appena selezionati.
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 */
	
	@Test
	public void testConfermaCorso() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		
		
		tmpCorso.setStato(Stato.Completamento);
		managerCorso.doUpdate(tmpCorso); //Facciamo solo l'update dato che l'onDelete
		assertEquals(managerCorso.doRetrieveByKey(tmpCorso.getIdCorso()).getStato(), Stato.Completamento);
		assertEquals(managerCorso.checkCorso(tmpCorso.getIdCorso()), true);
		managerCorso.confermaCorso(tmpCorso); //Accetta il corso
		
		
	}

	/**
	 * Prende i corsi legati ad un docente
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException
	 */
	
	@Test
	public void testRetrieveByCreatore() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		AccountBean account = managerAccount.doRetrieveByKey(tmpCorso.getDocente().getMail());
		assertNotNull(account);
		Collection<CorsoBean> corsi =managerCorso.retrieveByCreatore(account);
		assertNotNull(corsi);
		assertTrue(corsi.size() == 1); //Corrispondeeal docente prototipo
		
		
	}
	
	/**
	 * Prende i corsi legati ad un account affrontando il caso in cui l'account non è presente.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException
	 */
	
	@Test(expected=NotFoundException.class)
	public void testRetrieveByCreatoreNotFound() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		AccountBean account = managerAccount.doRetrieveByKey("mario100@gmail.com");
		assertNotNull(account);
		Collection<CorsoBean> corso = managerCorso.retrieveByCreatore(account);
		assertNotNull(corso);
		assertEquals(corso.size(), 2);
	}
	
	
	

	/**
	 * Prelievo dei corsi in attesa collegati ad un supervisore
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException
	 * @throws AlreadyExistingException 
	 */
	
	@Test
	public void testDoRetrieveBySupervisore() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException, AlreadyExistingException {
		
		//Creiamo un supervisore fittizio e associamolo al nostro corso prototipo
		AccountBean supervisore = new AccountBean("Mario", "Sessa", "PentiumD", "ProvaSupervisore@mail.com", Ruolo.Supervisore, true, null); //Il supervisore non ha una carta
		
		 //Inserimento nel DB
		System.out.println(tmpCorso.toString());
		System.out.println(supervisore.toString());
		tmpCorso.setStato(Stato.Attesa);
		managerAccount.setRegistration(supervisore);
		tmpCorso.setSupervisore(supervisore);
		Collection<CorsoBean> corsi = new LinkedList<CorsoBean>();
		corsi.add(tmpCorso);
		supervisore.AddCorsiDaSupervisionare(corsi);
		managerCorso.doUpdate(tmpCorso);
		assertNotNull(managerAccount.checkMail(supervisore.getMail()));
		Collection<CorsoBean> corso = managerCorso.doRetrieveBySupervisore(supervisore);
		assertNotNull(corso);
		assertEquals(corso.size(), 1);
		
		
	}
	
	@Test(expected=NoPermissionException.class)
	public void testRetrieveBySupervisoreNotPermission() throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		
		assertNull(managerCorso.doRetrieveBySupervisore(tmpCorso.getDocente()));
		
		
	}

	@Test
	public void testCheckCorsoCorsoBean() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		CorsoBean corso = managerCorso.doRetrieveByKey(tmpCorso.getIdCorso());
		assertEquals(managerCorso.checkCorso(corso),true);
	}

	@Test
	public void testCheckCorsoInt() throws SQLException {
		assertEquals(managerCorso.checkCorso(tmpCorso.getIdCorso()),true);
	}

	@Test
	public void testIsWellFormatted() throws NoPermissionException, SQLException, NotFoundException, NotWellFormattedException {
		assertEquals(managerCorso.isWellFormatted(tmpCorso), true);
	}

	
	
	/**
	 * Metodi di creazione ed eliminazione dei componenti soggetti a test locale
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException
	 * @throws AlreadyExistingException
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	
	private void createTmpComponentCorso() throws NoPermissionException, NotWellFormattedException, AlreadyExistingException, SQLException, NotFoundException {
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
			preparedStatement.setInt(1, tmpCorso.getIdCorso());
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
		
		
		
		//managerIscrizione.iscriviStudente(tmpIscrizione);
		
	}
	
		private void deleteTmpComponentCorso() throws SQLException {
				
				Connection connection = null;
				PreparedStatement preparedStatement = null;
	
				String deleteSQL = "DELETE FROM account WHERE email=? OR email=? OR email=?";
	
				try {
					connection = DriverManagerConnectionPool.getConnection();
					preparedStatement = connection.prepareStatement(deleteSQL);
					preparedStatement.setString(1, tmpAccount.getMail());
					preparedStatement.setString(2, tmpAccount2.getMail());
					preparedStatement.setString(3, "ProvaSupervisore@mail.com");
				
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
