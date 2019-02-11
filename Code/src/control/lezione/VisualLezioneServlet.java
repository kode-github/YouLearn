package control.lezione;

import java.io.IOException;
import java.sql.SQLException;

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


@WebServlet("/VisualLezioneServlet")
public class VisualLezioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	LezioneManager manager;
	
    public VisualLezioneServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manager=LezioneManager.getIstanza(getServletContext().getRealPath(""));
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session=request.getSession();
			try {
				CorsoBean corso=(CorsoBean) session.getAttribute("corso");
				int idLezione=Integer.parseInt(request.getParameter("idLezione"));
				LezioneBean lezione=corso.getLezione(idLezione);
				manager.retrieveCommentiByLezione(lezione);
				request.getSession().setAttribute("lezione", lezione);
				response.sendRedirect(request.getContextPath()+"/Lezione.jsp?idLezione="+idLezione);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotWellFormattedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
			

		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
