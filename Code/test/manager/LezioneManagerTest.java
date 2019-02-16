/**
 * 
 */
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
import org.junit.Before;
import org.junit.Test;

import bean.AccountBean;
import bean.CartaDiCreditoBean;
import bean.CorsoBean;
import bean.IscrizioneBean;
import bean.LezioneBean;
import bean.AccountBean.Ruolo;
import bean.CartaDiCreditoBean.CartaEnum;
import bean.CommentoBean;
import bean.CorsoBean.Categoria;
import bean.CorsoBean.Stato;
import connection.DriverManagerConnectionPool;
import exception.AlreadyExistingException;
import exception.DatiErratiException;
import exception.NotFoundException;
import exception.NotWellFormattedException;

/**
 * @author Antonio
 *
 */
public class LezioneManagerTest {

	private CommentoBean tmpCommento;
	private LezioneManager managerLezione;
	private CorsoManager managerCorso;
	private AccountManager managerAccount;
	private LezioneBean tmpLezione;
	private LezioneBean tmpLezione2;
	private CorsoBean tmpCorso;
	private AccountBean tmpAccount; //Account docente
	private CartaDiCreditoBean tmpCarta; //carta docente
	private AccountBean tmpAccount2; //Account studente
	private CartaDiCreditoBean tmpCarta2; //carta studente

	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		managerLezione=LezioneManager.getIstanza(System.getProperty("user.dir")+"\\WebContent");
		managerAccount = AccountManager.getIstanza();
		managerCorso = CorsoManager.getIstanza(System.getProperty("user.dir")+"\\WebContent");		
		createTmpComponentLezione();
	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		deleteTmpComponentLezione();
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
	public void increaseVis() throws SQLException, NotFoundException {
		managerLezione.increaseVisualizzazioni(tmpLezione);
	}
	
	@Test
	public void modificaLezione() throws SQLException, NotFoundException, NotWellFormattedException, IOException, DatiErratiException {
		managerLezione.modificaLezione(tmpLezione, new Part() {
			
			@Override
			public void write(String arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getSubmittedFileName() {
				return "azzzz.mp4";
			}
			
			@Override
			public long getSize() {
				return 0;
			}
			
			@Override
			public String getName() {
				return "azzzz.mp4";
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				return new InputStream() {
					
					@Override
					public int read() throws IOException {
						return -1;
					}
				};
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
	public void insLezione() throws NotWellFormattedException, SQLException, DatiErratiException, IOException, NotFoundException {
		LezioneBean tmp=new LezioneBean(tmpCorso, "lezioneBellaTest", 0, 1000, null, "azzz.mp4", new LinkedList<CommentoBean>());
		managerLezione.insLezione(tmp, new Part() {
			
			@Override
			public void write(String arg0) throws IOException {
				
				
			}
			
			@Override
			public String getSubmittedFileName() {
				return "aazzzz.mp4";
			}
			
			@Override
			public long getSize() {
				return 1;
			}
			
			@Override
			public String getName() {
				return "aazzzz.mp4";
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				return new InputStream() {
					
					@Override
					public int read() throws IOException {
						return -1;
					}
				};
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
		Connection c=DriverManagerConnectionPool.getConnection();
		PreparedStatement preparedStatement=c.prepareStatement("select idLezione\r\n" + 
				"from lezione\r\n" + 
				"where idLezione=(select max(idLezione)\r\n" + 
				"				from lezione)");
		ResultSet rs=preparedStatement.executeQuery();
		rs.next();
		tmp.setIdLezione((rs.getInt("idLezione")));
		managerLezione.delLezione(tmp);
		preparedStatement.close();
		DriverManagerConnectionPool.releaseConnection(c);
	}
	
	
	@Test
	public void testModificaOrdine(){
		String test="10000-1,10001-2";
		try {
			managerLezione.modificaOrdine(10000, test);
		} catch (NoPermissionException | SQLException | DatiErratiException | NotFoundException
				| NotWellFormattedException e) {
			
			e.printStackTrace();
		}
	}
	
	
	@Test(expected=DatiErratiException.class)
	public void testModificaOrdineErrorData() throws NoPermissionException, SQLException, DatiErratiException, NotFoundException, NotWellFormattedException{
		String test="";
		String test2="HELLOWORLD";
		managerLezione.modificaOrdine(10000, null);
		
	}
	

	/**
	 * Testa l'eliminazione della lezione, viene eseguita su due lezioni
	 * @throws SQLException
	 * @throws DatiErratiException
	 * @throws NotFoundException
	 */

	@Test
	public void testDelLezione() throws SQLException, DatiErratiException, NotFoundException {	
		managerLezione.delLezione(tmpLezione);
		managerLezione.delLezione(tmpLezione2);
		
	}
	

	/**
	 * Ritorna true se la lezione esiste, false altrimenti
	 * @throws SQLException
	 * @throws DatiErratiException
	 */
	@Test
	public void testCheckLezione() throws SQLException, DatiErratiException {
		
		assertTrue(managerLezione.checkLezione(tmpLezione));
	}
		

	
	/**
	 * Ritorna tutte le lezioni di un corso
	 * @throws NotWellFormattedException
	 * @throws SQLException
	 */
	@Test
	public void testRetrieveLezioniByCorso() throws NotWellFormattedException, SQLException {
		assertNotNull(managerLezione.retrieveLezioniByCorso(tmpCorso));
		assertEquals(managerLezione.retrieveLezioniByCorso(tmpCorso).size(), 2);
	}

	/**
	 * Ritorna il commento corrispondente ad uno specifico id, indipendentemente da quale lezione appartiene
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	@Test
	public void testRetrieveCommentoById() throws SQLException, NotFoundException {
//		assertNull(managerLezione.retrieveCommentoById(tmpCommento.getIdCommento()));
	}

	/**
	 * Test di cancellazione del commento
	 * @throws Exception
	 */
	@Test
	public void testDelCommento() throws Exception {
		CommentoBean tmp=managerLezione.retrieveCommentiByLezione(tmpLezione).iterator().next();
		managerLezione.delCommento(tmp.getIdCommento());
		}
		
	

	/**
	 * Test di inserimento del commento
	 * @throws NotWellFormattedException
	 * @throws SQLException
	 * @throws NotFoundException 
	 */
	@Test
	public void testInsCommento() throws NotWellFormattedException, SQLException, NotFoundException {
		CommentoBean commento = new CommentoBean(98, "Hello Text",tmpLezione, tmpAccount2);
		managerLezione.insCommento(commento);
	}

	/**
	 * Test sul prelievo dei commenti partendo dalla lezione in cui sono contenuti
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 */
	@Test
	public void testRetrieveCommentiByLezione() throws SQLException, NotFoundException, NotWellFormattedException {
		assertNotNull(managerLezione.retrieveCommentiByLezione(tmpLezione2));
	}

	/**
	 * Test sulla forma di un commento, ritorna vero se è ben formattano e falso altrimenti
	 */
	@Test
	public void testCommentoIsWellFormatted() {
		
		
		assertTrue(managerLezione.commentoIsWellFormatted(tmpCommento));
		
		AccountBean creatore = tmpCommento.getAccountCreatore();
		tmpCommento.setAccountCreatore(null); //Un commento senza creatore
		assertFalse(managerLezione.commentoIsWellFormatted(tmpCommento));
		tmpCommento.setAccountCreatore(creatore);
		String testo = tmpCommento.getTesto();
		tmpCommento.setTesto(null);
		assertFalse(managerLezione.commentoIsWellFormatted(tmpCommento));
		tmpCommento.setTesto(testo);
		
		
		
	}

	/**
	 * Test sulla formattazione delle lezione, torna vero se la forma è corretta e falso altrimenti
	 */
	@Test
	public void testLezioneIsWellFormatted() {
		assertTrue(managerLezione.lezioneIsWellFormatted(tmpLezione));
	}
	
	
//	@Test
//	public void testchangeNumeroLezione() throws SQLException, DatiErratiException {
//		Connection c = DriverManagerConnectionPool.getConnection();
//		tmpLezione.setNumeroLezione(tmpLezione.getNumeroLezione()+1);
//		managerLezione.changeNumeroLezione(tmpLezione, c);
//		DriverManagerConnectionPool.releaseConnection(c);
//	}

	
	
	private void createTmpComponentLezione() throws NoPermissionException, NotWellFormattedException, AlreadyExistingException, SQLException, NotFoundException, DatiErratiException, IOException {
		System.out.println("INIZIO LA CREAZIONE\n");
		
		//Carta docente
		tmpCarta= new CartaDiCreditoBean("0258789654123654","10","2022", "Mario Sessa", CartaEnum.PAYPAL, tmpAccount);
		//Account docente
		tmpAccount = new AccountBean("Mario", "Sessa", "PentiumD", "Prova@mail.com", Ruolo.Utente, true, tmpCarta);
		
		tmpCarta2= new CartaDiCreditoBean("0258789654123600","10","2022", "Mario Sessa", CartaEnum.PAYPAL, tmpAccount2);

		tmpAccount2 = new AccountBean("Mario", "Sessa", "PentiumD", "Prova2@mail.com", Ruolo.Utente, true, tmpCarta2);
		//Corso e lezioni
		tmpCorso = new CorsoBean(10000,"Informatica", "Informatica per principianti in linguaggio C", Date.valueOf("2018-01-10"), Date.valueOf("2020-10-10"), 12, Categoria.Informatica, "/User/kode/git/YouLearn/WebContent/Resources/Desert.jsp", Stato.Attivo,2,0, tmpAccount, null); //Non ha un supervisore 
		tmpLezione = new LezioneBean(null, "Lezione", 0, 1, 10000, "AAAAAAA.mp4", new LinkedList<CommentoBean>());
		tmpLezione2 = new LezioneBean(null, "LezioneDue", 0, 2, 10001, "BBBBBBB.mp4", new LinkedList<CommentoBean>());
		
		// commento della prima lezione di test
		
		tmpCommento = new CommentoBean(40, "Hello Text",tmpLezione, tmpAccount2);
		//Setting relazione account-carta
		tmpAccount.setCarta(tmpCarta);
		tmpCarta.setAccount(tmpAccount);
		tmpAccount2.setCarta(tmpCarta2);
		tmpCarta2.setAccount(tmpAccount2);
		tmpCorso.setDocente(tmpAccount);
		tmpLezione.setCorso(tmpCorso);
		tmpLezione2.setCorso(tmpCorso);
		tmpCorso.addLezione(tmpLezione);
		tmpCorso.addLezione(tmpLezione2);
		tmpCommento.setLezione(tmpLezione);
		tmpCommento.setAccountCreatore(tmpAccount2);
		
		
		
		

		
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
		
		// Inserimento lezione 1
		PreparedStatement statement=null;
		Connection c = null;
		String sql2="INSERT INTO lezione (nome, visualizzazione, numeroLezione, IdLezione, filePath, corsoIdCorso) VALUES (?,?,?,?,?,?);";
		try{
			c=DriverManagerConnectionPool.getConnection();
			c.setAutoCommit(false);
			
			statement=c.prepareStatement(sql2);
			statement.setString(1, tmpLezione.getNome());
			statement.setInt(2, tmpLezione.getVisualizzazioni());
			statement.setInt(3, tmpLezione.getNumeroLezione());
			statement.setInt(4, tmpLezione.getIdLezione());
			statement.setString(5, tmpLezione.getFilePath());
			statement.setInt(6, tmpLezione.getCorso().getIdCorso());
			
//			statement.setString(7, tmpLezione2.getNome());
//			statement.setInt(8, tmpLezione2.getVisualizzazioni());
//			statement.setInt(9, tmpLezione2.getNumeroLezione());
//			statement.setInt(10, tmpLezione2.getIdLezione());
//			statement.setString(11, tmpLezione2.getFilePath());
//			statement.setInt(12, tmpLezione2.getCorso().getIdCorso());
//			System.out.println("Esecuzione dell'inserimentom delle lezioni: " + statement.toString());
			statement.executeUpdate(); //inserisce nel db
			
		

			if(!c.getAutoCommit())
				c.commit(); //conferma l'inserimento nel db
		}catch(SQLException e) {
			e.printStackTrace();
			//C'� stato un errore nel salvataggio del file o nell'inserimento
			//In ogni caso la scrittura nel db viene eliminata
			c.rollback();
		}finally {
			try{
				if(statement!=null)
					statement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(c);
			}	
		}
		
		
		// Inserimento lezione 2
		PreparedStatement statement2=null;
		Connection c2 = null;
		String sql3="INSERT INTO lezione (nome, visualizzazione, numeroLezione, IdLezione, filePath, corsoIdCorso) VALUES (?,?,?,?,?,?);";
		try{
			c2=DriverManagerConnectionPool.getConnection();
			c2.setAutoCommit(false);
			
			statement2=c2.prepareStatement(sql3);
			statement2.setString(1, tmpLezione2.getNome());
			statement2.setInt(2, tmpLezione2.getVisualizzazioni());
			statement2.setInt(3, tmpLezione2.getNumeroLezione());
			statement2.setInt(4, tmpLezione2.getIdLezione());
			statement2.setString(5, tmpLezione2.getFilePath());
			statement2.setInt(6, tmpLezione2.getCorso().getIdCorso());
			
//			statement.setString(7, tmpLezione2.getNome());
//			statement.setInt(8, tmpLezione2.getVisualizzazioni());
//			statement.setInt(9, tmpLezione2.getNumeroLezione());
//			statement.setInt(10, tmpLezione2.getIdLezione());
//			statement.setString(11, tmpLezione2.getFilePath());
//			statement.setInt(12, tmpLezione2.getCorso().getIdCorso());
//			System.out.println("Esecuzione dell'inserimentom delle lezioni: " + statement.toString());
			statement2.executeUpdate(); //inserisce nel db
			
		

			if(!c2.getAutoCommit())
				c2.commit(); //conferma l'inserimento nel db
		}catch(SQLException e) {
			e.printStackTrace();
			//C'� stato un errore nel salvataggio del file o nell'inserimento
			//In ogni caso la scrittura nel db viene eliminata
			c2.rollback();
		}finally {
			try{
				if(statement2!=null)
					statement2.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(c2);
			}	
		}
		
		assertNotNull(tmpCommento.getIdCommento());
		assertTrue(managerLezione.commentoIsWellFormatted(tmpCommento));

		managerLezione.insCommento(tmpCommento);
		System.out.println("Dopo inserisci commento");
		//managerIscrizione.iscriviStudente(tmpIscrizione);
		
	}
	
		private void deleteTmpComponentLezione() throws SQLException {
				
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
