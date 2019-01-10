package control.utente;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AccountBean;
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
			AccountBean account=manager.authenticateUser(mail, password);
			sessione.setAttribute("Account", account);
			response.sendRedirect(request.getContextPath()+"\\HomepageUtente.jsp");
		} catch (NotFoundException e) {
			sessione.setAttribute("ErroreLogin", true);
			response.sendRedirect(request.getContextPath()+"\\Welcome.jsp");
		} catch( SQLException e) {
			//Pagina di errore in cui diciamo di collegarsi al sito più tardi
			//oppure la stessa con scritto "Errore di connessione, riprovare più tardi"
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
