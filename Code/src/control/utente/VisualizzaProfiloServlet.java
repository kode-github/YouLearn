package control.utente;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NoPermissionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        iscrizione=IscrizioneManager.getIstanza();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        manager=CorsoManager.getIstanza(getServletContext().getRealPath(""));
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session=request.getSession();
		AccountBean account=(AccountBean) request.getSession().getAttribute("account");
		System.out.println(getServletContext().getRealPath(""));
		
				try {
					if(account.getTipo().equals(Ruolo.Utente)) {
						manager.retrieveByCreatore(account);
						iscrizione.getIscrizioniUtente(account);
						session.setAttribute("tenuti", "true");
						session.setAttribute("seguiti", "true");
						response.sendRedirect(request.getContextPath()+"\\HomepageUtente.jsp");
					}
					else {
						manager.doRetrieveBySupervisore(account);
						session.setAttribute("sup", "true");
						response.sendRedirect(request.getContextPath()+"\\HomepageSupervisore.jsp");
					}
					
				} catch (NoPermissionException | SQLException | NotFoundException | NotWellFormattedException e) {
					e.printStackTrace();
					//TODO Errore di consistenza dei parametri
					response.sendRedirect(request.getContextPath()+"\\Error.jsp");
				}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
