package control.corso;

import java.io.IOException;
import java.time.LocalDateTime;
import java.sql.Date;
import java.sql.SQLException;

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
import manager.IscrizioneManager;


@WebServlet("/IscrizioneCorsoServlet")
public class IscrizioneCorsoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	IscrizioneManager iscrizioneManager;

    public IscrizioneCorsoServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		int idCorso = 0;
		int cvs;
			try {
				idCorso=Integer.parseInt(request.getParameter("idCorso"));
				cvs=Integer.parseInt(request.getParameter("cvs"));
				AccountBean accountBean=(AccountBean)request.getSession().getAttribute("account");
				CorsoBean corso=(CorsoBean)request.getSession().getAttribute("corso");
				@SuppressWarnings("deprecation")
				Date date=new Date(LocalDateTime.now().getYear()-1900, LocalDateTime.now().getMonthValue()-1, LocalDateTime.now().getDayOfMonth());
				
				IscrizioneBean iscrizione=new IscrizioneBean();
				iscrizione.setAccount(accountBean);
				iscrizione.setCorso(corso);
				iscrizione.setDataPagamento(date);
				iscrizione.setFattura("aaaaaaaaaaaa");
				
				iscrizioneManager.iscriviStudente(iscrizione);
				request.getSession().setAttribute("iscrizioneEffettuata", "true");
				response.sendRedirect(request.getContextPath()+"/Corso.jsp?idCorso="+corso.getIdCorso());
			} catch (NoPermissionException | SQLException | NotFoundException | AlreadyExistingException
					| NotWellFormattedException e) {
				request.getSession().setAttribute("iscrizioneEffettuata", "false");
				response.sendRedirect(request.getContextPath()+"/Corso.jsp?idCorso="+idCorso);
				e.printStackTrace();
			}catch(NullPointerException e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath()+"/HomepageUtente.jsp");
			}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
