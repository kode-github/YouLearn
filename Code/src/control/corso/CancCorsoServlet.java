package control.corso;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;
import manager.AccountManager;
import manager.CorsoManager;

/**
 * Cancella un corso 
 */
@WebServlet("/CancCorsoServlet")
public class CancCorsoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    CorsoManager manager;
    public CancCorsoServlet() {
        super();
    }

	/**
	 * TODO va aggiunto controllo su appartenenza di corso ad account?
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        manager=CorsoManager.getIstanza(getServletContext().getRealPath(""));
		response.getWriter().append("Served at: ").append(request.getContextPath());
		AccountBean account=(AccountBean)request.getSession().getAttribute("account");
		int idCorso=Integer.parseInt(request.getParameter("idCorso"));
		try {
			manager.removeCorso(idCorso);
			account.removeCorsoTenuto(account.getCorsoTenuto(idCorso)); //Elimino il corso tenuto dall'oggetto account in sessione
			request.getSession().setAttribute("corsoCancellato", "true");
			response.sendRedirect(request.getContextPath()+File.separator+"HomepageUtente.jsp");
		}catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("corsoCancellato", "false");
			response.sendRedirect(request.getContextPath()+File.separator+"Error.jsp");

		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
