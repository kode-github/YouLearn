package control.lezione;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NoPermissionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
@MultipartConfig(fileSizeThreshold= 1024*1024*500, maxFileSize=1024*1024*500, maxRequestSize=1024*1024*500 ) //TODO Va controllata la dimensione 
public class ModificaLezioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    LezioneManager manager;
	
    public ModificaLezioneServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manager=LezioneManager.getIstanza(getServletContext().getRealPath(""));
		response.getWriter().append("Served at: ").append(request.getContextPath());
		int idCorso = 0;
		try {
    		AccountBean account=(AccountBean) request.getSession().getAttribute("account");
    		int idLezione=Integer.parseInt(request.getParameter("idLezione"));
    		idCorso=Integer.parseInt(request.getParameter("idCorso"));
        	String tipo=request.getParameter("tipo");
        	System.out.println("idCorso="+idCorso +", idLezione="+ idLezione);
        	LezioneBean lezione=account.getCorsoTenuto(idCorso).getLezione(idLezione);
        	//Setto il nuovo nome della lezione
        	String nome=request.getParameter("name");
        	lezione.setNome(nome);
        	Part part=null;
        	if(tipo==null)
        	for(Part p: request.getParts())
        		part=p;
        	
        	//System.out.println("nome file: "+part.getSubmittedFileName());
			manager.modificaLezione(lezione,part);
        	response.sendRedirect(request.getContextPath()+"/SettingLezione.jsp?idCorso="+idCorso);
		}catch (DatiErratiException | NotFoundException | SQLException |NumberFormatException e) {
			response.sendRedirect(request.getContextPath()+File.separator+"Error.jsp");
			e.printStackTrace();
		}catch (NotWellFormattedException e) {
			request.getSession().setAttribute("invalidLesson", "true");
			response.sendRedirect(request.getContextPath()+File.separator+"SettingLezione.jsp?idCorso="+idCorso);
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
