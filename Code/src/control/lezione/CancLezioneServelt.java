package control.lezione;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;
import bean.LezioneBean;
import exception.DatiErratiException;
import exception.NotFoundException;
import manager.LezioneManager;


@WebServlet("/CancLezioneServelt")
public class CancLezioneServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    LezioneManager manager;
	
    public CancLezioneServelt() {
        super();

    }



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manager=LezioneManager.getIstanza(getServletContext().getRealPath(""));
		response.getWriter().append("Served at: ").append(request.getContextPath());

        	try {
        		AccountBean account=(AccountBean) request.getSession().getAttribute("account");
        		int idCorso=Integer.parseInt(request.getParameter("idCorso"));
            	int idLezione=Integer.parseInt(request.getParameter("idLezione"));
            	LezioneBean lezione=account.getCorsoTenuto(idCorso).getLezione(idLezione);
				manager.delLezione(lezione);
				account.getCorsoTenuto(idCorso).removeLezione(lezione);
	        	response.sendRedirect(request.getContextPath()+"/SettingLezione.jsp?idCorso="+idCorso);
			} catch (SQLException e) {
				response.sendRedirect(request.getContextPath()+File.separator+"Error.jsp");
				e.printStackTrace();
			} catch (DatiErratiException e) {
				response.sendRedirect(request.getContextPath()+File.separator+"Error.jsp");
				e.printStackTrace();
			} catch (NotFoundException e) {
				response.sendRedirect(request.getContextPath()+File.separator+"Error.jsp");
				e.printStackTrace();
			}
        
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
