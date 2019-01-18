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
import exception.DatiErratiException;
import exception.NotFoundException;
import manager.AccountManager;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    AccountManager manager;   
    
    public LoginServlet() {
        super();
        manager=new AccountManager();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String mail,password;
		mail=request.getParameter("email");
		password=request.getParameter("password");
		HttpSession sessione= request.getSession();
		try {
				AccountBean account;
				account = manager.login(mail, password);
				sessione.setAttribute("Account", account);
				if(account.getTipo().equals(Ruolo.Utente))
					response.sendRedirect(request.getContextPath()+"\\HomepageUtente.jsp");
				else
					response.sendRedirect(request.getContextPath()+"\\HomepageSupervisore.jsp");
			} catch (NoPermissionException e) {
				//Non dovrebbe MAI essere qui
			} catch (DatiErratiException e) {
				sessione.setAttribute("passwordErrata", true);
				response.sendRedirect(request.getContextPath()+"\\Welcome.jsp");
			} catch (NotFoundException e) {
				sessione.setAttribute("ErroreLogin", true);
				response.sendRedirect(request.getContextPath()+"\\Welcome.jsp");
			} catch( SQLException e) {
				sessione.setAttribute("ErroreConnessione", true);
				response.sendRedirect(request.getContextPath()+"\\Welcome.jsp");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
