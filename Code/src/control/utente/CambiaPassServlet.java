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
import manager.AccountManager;

@WebServlet("/CambiaPassServlet")
public class CambiaPassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	AccountManager manager;
	
    public CambiaPassServlet() {
        super();
        manager= AccountManager.getIstanza();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String newPassword=request.getParameter("newPass");
		AccountBean account=(AccountBean)request.getSession().getAttribute("account");
			try {
				manager.modificaPassword(account.getMail(), newPassword);
				request.getSession().setAttribute("passwordModificata",true);
			} catch (NoPermissionException e) {
				//Non ci andrà mai
			} catch (SQLException | NotFoundException e) {
				//pagina di errore
		} catch (NotWellFormattedException e) {
			request.getSession().setAttribute("passwordNonModificata",true);
				e.printStackTrace();
			}
			if(account.getTipo().equals(Ruolo.Utente))
				response.sendRedirect(request.getContextPath()+"\\HomepageUtente.jsp");
			else
				response.sendRedirect(request.getContextPath()+"\\HomepageSupervisore.jsp");	
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
