package control.corso;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.naming.NoPermissionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.regexp.internal.REUtil;

import bean.AccountBean;
import bean.AccountBean.Ruolo;
import bean.CorsoBean;
import bean.IscrizioneBean;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import manager.CorsoManager;
import manager.IscrizioneManager;


@WebServlet("/VisualCorsoServlet")
public class VisualCorsoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	IscrizioneManager manager;
	CorsoManager corsoManager;
	
    public VisualCorsoServlet() {
        super();
        manager=IscrizioneManager.getIstanza();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
        corsoManager=CorsoManager.getIstanza(getServletContext().getRealPath(""));

		response.getWriter().append("Served at: ").append(request.getContextPath());
			try {
				String ruolo=null;
				Integer idCorso=Integer.parseInt(request.getParameter("idCorso"));
				CorsoBean corso=null;
				
				AccountBean account=(AccountBean) request.getSession().getAttribute("account");
				
				//controllo dove si trovi il corso
				if(account.getTipo().equals(Ruolo.Utente)) {
					Iterator<CorsoBean> corsiTenuti=account.getCorsiTenuti().iterator();
					Iterator<IscrizioneBean> iscrizioni=account.getIscrizioni().iterator();
					Collection<CorsoBean> researched=(Collection<CorsoBean>) request.getSession().getAttribute("research");
					
					//Maronna mij
					while(corsiTenuti.hasNext())  //Se sono il docente
						if((corso=corsiTenuti.next()).getIdCorso()==idCorso) {
							ruolo="docente";
							break;
						}
					if(ruolo==null) //Non sono il docente
						while(iscrizioni.hasNext()) //controllo se sono iscritto
							if((corso=(iscrizioni.next()).getCorso()).getIdCorso()==idCorso) {
								ruolo="iscritto";
								break;
							}
					if(ruolo==null && researched!=null) {//non sono docente, iscritto e vengo da una ricerca
						Iterator<CorsoBean> ricercati=researched.iterator();
						while(ricercati.hasNext())
							if((corso=ricercati.next()).getIdCorso()==idCorso) {
								ruolo="NonIscritto";
								break;
							}
					}
					if(ruolo==null) { //ci sono arrivato direttamente
						corso=corsoManager.doRetrieveByKey(idCorso); //recuperto direttamente il corso
						ruolo="NonIscritto";
						manager.getIscrittiCorso(corso);
					}
				}
				else { //Sono un supervisore
					Iterator<CorsoBean> corsiSup=account.getCorsiDaSupervisionare().iterator();
					CorsoBean tmp=new CorsoBean();
					while(corsiSup.hasNext()) {
						tmp=corsiSup.next();
						if(tmp.getIdCorso()==idCorso) {
							corso=tmp;
							ruolo="supervisore";
							break;
						}
					}
					if(corso==null) throw new NotFoundException("Non è un corso da lui supervisionato");
				}
				manager.getIscrittiCorso(corso); //recupero le iscrizioni
				request.getSession().setAttribute("ruolo", ruolo); //Setto il ruolo in sessione
				request.getSession().setAttribute("corso", corso);
				request.getSession().setAttribute("updated", "true");
				response.sendRedirect(request.getContextPath()+"\\Corso.jsp?idCorso="+idCorso);
			} catch (NoPermissionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotWellFormattedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
