package control.lezione;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.AccountBean;
import bean.LezioneBean;
import exception.DatiErratiException;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import manager.LezioneManager;

/**
 * Servlet implementation class ModificaLezioneServlet
 */
@WebServlet("/ModificaLezioneServlet")
public class ModificaLezioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    LezioneManager manager;
	
    public ModificaLezioneServlet() {
        super();
        manager=new LezioneManager();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
    		AccountBean account=(AccountBean) request.getSession().getAttribute("account");
    		int idCorso=Integer.parseInt(request.getParameter("idCorso"));
        	int idLezione=Integer.parseInt(request.getParameter("idLezione"));
        	LezioneBean lezione=account.getCorsoTenuto(idCorso).getLezione(idLezione);
        	//Setto il nuovo nome della lezione
        	String nome=request.getParameter("nomeLezione");
        	lezione.setNome(nome);
        	Part part=request.getPart("file");
			manager.modificaLezione(lezione,part);
        	response.sendRedirect(request.getContextPath()+"/SettingLezione.jsp?idCorso="+idCorso);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotWellFormattedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
