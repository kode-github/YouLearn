package control.corso;

import java.io.File;
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
import javax.servlet.http.HttpSession;

import bean.AccountBean;
import bean.AccountBean.Ruolo;
import bean.CorsoBean;
import bean.IscrizioneBean;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import manager.CorsoManager;
import manager.IscrizioneManager;
import manager.LezioneManager;


@WebServlet("/VisualCorsoServlet")
public class VisualCorsoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	IscrizioneManager manager;
	CorsoManager corsoManager;
	LezioneManager lezioneManager;
	
    public VisualCorsoServlet() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		manager=IscrizioneManager.getIstanza();
		corsoManager=CorsoManager.getIstanza(request.getServletContext().getRealPath(""));
        lezioneManager=LezioneManager.getIstanza("");
        HttpSession session=request.getSession();
		//response.getWriter().append("Served at: ").append(request.getContextPath());
			try {
				String ruolo=null;
				Integer idCorso=Integer.parseInt(request.getParameter("idCorso"));
				CorsoBean corso=null;
				AccountBean account=(AccountBean) session.getAttribute("account");
				
				//controllo dove si trovi il corso
				if(account.getTipo().equals(Ruolo.Utente)) {
					Iterator<CorsoBean> corsiTenuti=account.getCorsiTenuti().iterator();
					Iterator<IscrizioneBean> iscrizioni=account.getIscrizioni().iterator();
					Collection<CorsoBean> researched=(Collection<CorsoBean>) session.getAttribute("research");
					
					//Maronna mij
					while(corsiTenuti.hasNext()) { //Se sono il docente
						corso=corsiTenuti.next();
						System.out.println("Corso tenuto: "+corso.getIdCorso()+" idCorso: "+idCorso);
						if(corso.getIdCorso().equals(idCorso)) {
							ruolo="docente";
							break;
						}
					}
					if(ruolo==null) //Non sono il docente
						while(iscrizioni.hasNext()) //controllo se sono iscritto
							if((corso=(iscrizioni.next()).getCorso()).getIdCorso().equals(idCorso)) {
								System.out.println("SOno un iscritto al corso");
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
					if(corso==null) throw new NotFoundException("Non � un corso da lui supervisionato");
				}
				manager.getIscrittiCorso(corso); //recupero le iscrizioni
				if(ruolo.equals("iscritto"))
					lezioneManager.retrieveLezioniByCorso(corso);
				session.setAttribute("ruolo", ruolo); //Setto il ruolo in sessione
				session.setAttribute("corso", corso);
				session.setAttribute("updated", "true");
				response.sendRedirect(request.getContextPath()+"\\Corso.jsp?idCorso="+idCorso);
			} catch (NoPermissionException |SQLException | NotFoundException |NotWellFormattedException e) {
				response.sendRedirect(request.getContextPath()+File.separator+"Error.jsp");
				e.printStackTrace();
			}
}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
