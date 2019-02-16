package control.utente;

/**
 * 
 * 
 * @author Luigi Crisci
 */
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
import exception.AlreadyExistingException;
import exception.NotFoundException;
import manager.AccountManager;


@WebServlet("/CambiaMailServlet")
public class CambiaMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	AccountManager manager;
	
    public CambiaMailServlet() {
        super();
        manager= AccountManager.getIstanza();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String newMail=request.getParameter("newMail");
		AccountBean account=(AccountBean)request.getSession().getAttribute("account");
		try {
			manager.modificaMail(account.getMail(), newMail);
			account.setMail(newMail);
			request.getSession().setAttribute("account",account);
			request.getSession().setAttribute("emailModificata",true);
		} catch (SQLException | NotFoundException | NoPermissionException e) {
			e.printStackTrace();
		} catch (AlreadyExistingException e) {
			request.getSession().setAttribute("emailGiaEsistente", true);
			e.printStackTrace();
		}
		if(account.getTipo().equals(Ruolo.Utente))
			response.sendRedirect(request.getContextPath()+"\\HomepageUtente.jsp");
		else
			response.sendRedirect(request.getContextPath()+"\\HomepageSupervisore.jsp");	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
