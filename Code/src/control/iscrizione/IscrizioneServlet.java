package control.iscrizione;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.naming.NoPermissionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;
import bean.CorsoBean;
import bean.IscrizioneBean;
import exception.AlreadyExistingException;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import manager.CorsoManager;
import manager.IscrizioneManager;



@WebServlet("/IscrizioneServlet")
public class IscrizioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CorsoManager managerCorso;
	private IscrizioneManager managerIscrizione;
	
    public IscrizioneServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		managerCorso=CorsoManager.getIstanza("");
		managerIscrizione=IscrizioneManager.getIstanza();
		AccountBean account=(AccountBean) request.getSession().getAttribute("account");
		int idCorso=Integer.parseInt(request.getParameter("idCorso"));
		CorsoBean corso = null;
		try {
			corso = managerCorso.doRetrieveByKey(idCorso);
			IscrizioneBean iscrizione=new IscrizioneBean();
			iscrizione.setImporto(corso.getPrezzo());
			iscrizione.setFattura("1111111111");
			@SuppressWarnings("deprecation")
			Date date=new Date(LocalDateTime.now().getYear()-1900, LocalDateTime.now().getMonthValue()-1, LocalDateTime.now().getDayOfMonth());
			iscrizione.setDataPagamento(date);
			iscrizione.setAccount(account);
			iscrizione.setCorso(corso);
			managerIscrizione.iscriviStudente(iscrizione);
			request.getSession().setAttribute("iscrizioneEffettuata", "true");
			response.sendRedirect(request.getContextPath()+"\\Corso.jsp?idCorso="+idCorso);
		} catch (NoPermissionException | SQLException | NotFoundException | NotWellFormattedException e) {
			response.sendRedirect(request.getContextPath()+"\\Error.jsp");
			e.printStackTrace();
		} catch (AlreadyExistingException e) {
			//request.getSession().setAttribute("iscrizioneEffettuata", "false");
			account.addCorsoTenuto(corso);
			response.sendRedirect(request.getContextPath()+"\\Corso.jsp?idCorso="+idCorso);
			e.printStackTrace();
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
