package control.lezione;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InsLezioneServlet
 */
@WebServlet("/InsLezioneServlet")
@MultipartConfig(fileSizeThreshold= 1024*1024*2, maxFileSize=1024*1024*10, maxRequestSize=1024*1024*50 ) //TODO Va controllata la dimensione 
public class InsLezioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public InsLezioneServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
