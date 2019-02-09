package control.corso;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xpath.internal.operations.Bool;

import bean.AccountBean;
import bean.CorsoBean;
import manager.CorsoManager;


@WebServlet("/VerificaCorsoServlet")
public class VerificaCorsoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	CorsoManager manager;
    
    public VerificaCorsoServlet() {
        super();
        manager=CorsoManager.getIstanza();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			boolean verifica=Boolean.parseBoolean(request.getParameter("verifica"));
			int idCorso=Integer.parseInt(request.getParameter("idCorso"));
			AccountBean supervisore=(AccountBean)request.getSession().getAttribute("account");
			CorsoBean corso=supervisore.getCorsoSupervisionato(idCorso);
			manager.convalidaCorso(verifica, corso);
			request.getSession().setAttribute("verificato", verifica);
			response.sendRedirect(request.getContextPath()+"\\HomepageSupervisore.jsp");
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
