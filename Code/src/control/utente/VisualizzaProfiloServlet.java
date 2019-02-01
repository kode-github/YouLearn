package control.utente;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NoPermissionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;
import bean.AccountBean.Ruolo;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import manager.CorsoManager;
import manager.IscrizioneManager;


@WebServlet("/VisualizzaProfiloServlet")
public class VisualizzaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	CorsoManager manager;
	IscrizioneManager iscrizione;
    public VisualizzaProfiloServlet() {
        super();
        manager=new CorsoManager();
        iscrizione=new IscrizioneManager();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		AccountBean account=(AccountBean) request.getSession().getAttribute("account");
				try {
					if(account.getTipo().equals(Ruolo.Utente)) {
						manager.retrieveByCreatore(account);
						iscrizione.getIscrizioniUtente(account);
						response.sendRedirect(request.getContextPath()+"\\HomepageUtente.jsp?seguiti=true&tenuti=true");
					}
					else {
						manager.doRetrieveBySupervisore(account);
						response.sendRedirect(request.getContextPath()+"\\HomepageSupervisore.jsp?sup=true");
					}
					
				} catch (NoPermissionException | SQLException | NotFoundException | NotWellFormattedException e) {
					e.printStackTrace();
					//TODO Errore di consistenza dei parametri
				}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
