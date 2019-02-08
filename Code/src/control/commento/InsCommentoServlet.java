package control.commento;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;
import bean.CommentoBean;
import bean.LezioneBean;
import manager.LezioneManager;

@WebServlet("/InsCommentoServlet")
public class InsCommentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	LezioneManager manager;
	
    public InsCommentoServlet() {
        super();
        manager= LezioneManager.getIstanza();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String testo=request.getParameter("testoCommento");
		LezioneBean lezione=(LezioneBean)request.getSession().getAttribute("lezione");
		AccountBean account=(AccountBean)request.getSession().getAttribute("Account");
		CommentoBean commento=new CommentoBean(null, testo, lezione,account);
//		try {
//			manager.insCommento(commento);
//			response.sendRedirect(request.getContextPath()+"\\Lezione.jsp?idCorso="+idCorso+"+&numeroLezione="+nLezione);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
