package control.lezione;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.NoPermissionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AccountBean;
import bean.CorsoBean;
import bean.LezioneBean;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import manager.LezioneManager;


@WebServlet("/GetLezioniServlet")
public class GetLezioniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	LezioneManager manager;
	
    public GetLezioniServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manager=LezioneManager.getIstanza(getServletContext().getRealPath(""));
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session=request.getSession();
		try {
			AccountBean account= (AccountBean)session.getAttribute("account");
			int idCorso=Integer.parseInt(request.getParameter("idCorso"));
			CorsoBean corso=account.getCorsoTenuto(idCorso);
			Collection<LezioneBean> lezione=manager.retrieveLezioniByCorso(corso);
			account.removeCorsoTenuto(corso);
			corso.setLezioni(new LinkedList<LezioneBean>()); //Annullo le lezioni che contiene
			corso.setLezioni(lezione);
			account.AddCorsoTenuto(corso);
			request.getSession().removeAttribute("updated");
			response.sendRedirect(request.getContextPath()+"//SettingLezione.jsp?idCorso="+corso.getIdCorso());
		} catch ( SQLException |NumberFormatException | NotWellFormattedException e) {
			response.sendRedirect(request.getContextPath()+File.separator+"Error.jsp");
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
