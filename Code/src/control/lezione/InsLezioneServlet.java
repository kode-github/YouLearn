package control.lezione;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import bean.CorsoBean;
import bean.LezioneBean;
import exception.DatiErratiException;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import manager.LezioneManager;

/**
 * Servlet implementation class InsLezioneServlet
 */
@WebServlet("/InsLezioneServlet")
@MultipartConfig(fileSizeThreshold= 1024*1024*2000, maxFileSize=1024*1024*2000, maxRequestSize=1024*1024*2000 ) //TODO Va controllata la dimensione 
public class InsLezioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   LezioneManager manager;
	
    public InsLezioneServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manager=LezioneManager.getIstanza(getServletContext().getRealPath(""));
		response.getWriter().append("Served at: ").append(request.getContextPath());
		LezioneBean lezione = new LezioneBean();
		CorsoBean corso= null;
		int idCorso = 0;
	        try {
	    		AccountBean account=(AccountBean) request.getSession().getAttribute("account");
	        	Part file = request.getPart("file");
	        	idCorso=Integer.parseInt(request.getParameter("idCorso"));
	        	String nome = request.getParameter("name");
		    	corso=account.getCorsoTenuto(idCorso);
		    	System.out.println(corso.getIdCorso()+"\n");
		    	String filename=getFilename(file);		      
				System.out.println("idCorso in insLezione: "+idCorso+"\n id dell'oggetto corso: "+corso.getIdCorso());
				for(Part p: request.getParts())
	        		System.out.println("\nPart: "+file.toString());
		    	
		        
		        lezione.setNome(nome);
		        lezione.setIdLezione(null);
		        lezione.setCorso(corso);
		        
				manager.insLezione(lezione, file);
		
				
				//request.getSession().setAttribute("updated", "true");
				response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        response.getWriter().write("{\"success\":true,\"format\":\" Fatto! \"}");
			} catch (SQLException |NumberFormatException | NotWellFormattedException | DatiErratiException e) {
				response.sendRedirect(request.getContextPath()+File.separator+"Error.jsp");
				e.printStackTrace();
			}

	        
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

}
