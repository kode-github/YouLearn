package controltest.corso;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.NoPermissionException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import bean.AccountBean;
import bean.CartaDiCreditoBean;
import bean.CorsoBean;
import bean.IscrizioneBean;
import bean.AccountBean.Ruolo;
import bean.CartaDiCreditoBean.CartaEnum;
import bean.CorsoBean.Categoria;
import bean.CorsoBean.Stato;
import connection.DriverManagerConnectionPool;
import control.corso.VisualCorsoServlet;
import exception.AlreadyExistingException;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import manager.AccountManager;
import manager.CorsoManager;
import manager.IscrizioneManager;

public class VisualCorsoTest extends Mockito {

	@Test
    public void testServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        HttpSession sessione=mock(HttpSession.class);
        ServletContext context=mock(ServletContext.class);
        when(request.getSession()).thenReturn(sessione);
        when(request.getServletContext()).thenReturn(context);
        when(request.getContextPath()).thenReturn("Mockito!");
        when(context.getRealPath("")).thenReturn("Mockito!");
        //Sono il docente
        
        when(sessione.getAttribute("account")).thenReturn(tmpAccount);
        when(request.getParameter("idCorso")).thenReturn("10000");
        System.out.println("Corso tenuto: "+tmpAccount.getCorsiTenuti().iterator().next().getIdCorso());
        new VisualCorsoServlet().doPost(request, response);
        	
        //verify(response).sendRedirect(request.getContextPath()+"\\HomepageUtente.jsp");
    }
	
	
	
	private void createTmpComponentCorso() throws NoPermissionException, NotWellFormattedException, AlreadyExistingException, SQLException, NotFoundException {
		System.out.println("INIZIO LA CREAZIONE\n");
		
		//Carta docente
		tmpCarta= new CartaDiCreditoBean("1111111111111111","10","2022", "Mario Sessa", CartaEnum.PAYPAL, tmpAccount);
		//Account docente
		tmpAccount = new AccountBean("Mario", "Sessa", "PentiumD", "Prova@mail.com", Ruolo.Utente, true, tmpCarta);
		
		//Corso
		tmpCorso = new CorsoBean(10000,"Informatica", "Informatica per principianti in linguaggio C", Date.valueOf("2018-01-10"), Date.valueOf("2020-10-10"), 12, Categoria.Informatica, "Desert.jsp", Stato.Attivo,1,0, tmpAccount, null); //Non ha un supervisore 
		
		
		//Setting relazione account-carta
		tmpAccount.setCarta(tmpCarta);
		tmpCarta.setAccount(tmpAccount);
		tmpCorso.setDocente(tmpAccount);
		
		//Setting relazione accountDocente-corso
		tmpAccount.addCorsoTenuto(tmpCorso);
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
			managerIscrizione=IscrizioneManager.getIstanza();
			assertNotNull(managerCorso);
			assertNotNull(managerAccount);
			assertNotNull(managerIscrizione);
			createTmpComponentCorso();
		}
	
}
