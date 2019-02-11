package control.commento;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;
import manager.LezioneManager;


@WebServlet("/CancCommentoServlet")
public class CancCommentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	LezioneManager manager;

    public CancCommentoServlet() {
        super();
    }

    /**
     * Elimina un commento
     * Si potrebbe tranquillamente fare in ajax
     * TODO andrebbe fatto il controllo sull'identità dell'account
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manager=LezioneManager.getIstanza(getServletContext().getRealPath(""));
		response.getWriter().append("Served at: ").append(request.getContextPath());
			try {
				int idCommento=Integer.parseInt(request.getParameter("idCommento"));
				int idLezione=Integer.parseInt(request.getParameter("idLezione"));
				AccountBean account=(AccountBean) request.getSession().getAttribute("account");
				manager.delCommento(idCommento);
				response.sendRedirect(request.getContextPath()+"/Lezione.jsp?idLezione="+idLezione);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
