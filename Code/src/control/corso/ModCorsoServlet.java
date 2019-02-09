package control.corso;

import java.io.IOException;
import java.sql.Date;
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
import bean.CorsoBean.Categoria;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import manager.CorsoManager;

/**
 * Modifica un corso esistente
 */
@WebServlet("/ModCorsoServlet")
@MultipartConfig(fileSizeThreshold= 1024*1024*2, maxFileSize=1024*1024*10, maxRequestSize=1024*1024*50 )
public class ModCorsoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	CorsoManager manager;
	
    public ModCorsoServlet() {
        super();
        manager=CorsoManager.getIstanza();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		AccountBean account=(AccountBean)request.getSession().getAttribute("account");
		int idCorso=Integer.parseInt(request.getParameter("idCorso"));
		CorsoBean corso=account.getCorsoTenuto(idCorso);
		try{
			String nome=request.getParameter("nome");
			String descrizione=request.getParameter("descrizione");
			Integer prezzo=Integer.parseInt(request.getParameter("prezzo"));
			String dataTemp=request.getParameter("dataScadenza");
			System.out.println("Data:  "+dataTemp+"\n"+"nome: "+nome+"\n");
			Date dataScadenza=Date.valueOf(dataTemp);
			Categoria categoria=Categoria.valueOf(request.getParameter("categoria"));
			
			Part copertina=request.getPart("CARICA FILE");
			if(copertina.getSize()==0)
				copertina=null;
			
			
			//Crea un oggetto temporaneo in cui inserire i dati
			CorsoBean temp=new CorsoBean(corso.getIdCorso(),nome,descrizione,corso.getDataCreazione(),
					dataScadenza,prezzo,categoria,corso.getCopertina(),corso.getStato(),corso.getnIscritti(),
					corso.getnLezioni(),corso.getIscrizioni(),corso.getDocente(),corso.getSupervisore(), corso.getLezioni());
			
			manager.modificaCorso(temp, copertina);
			account.removeCorsoTenuto(corso);
			account.AddCorsoTenuto(temp);
			response.sendRedirect(request.getContextPath()+"/HomepageUtente.jsp?corsoModificato=true");
			
		}catch (NullPointerException | IllegalArgumentException | NotWellFormattedException e) {
			//TODO dati non ben formattati
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/SettingCorso.jsp?idCorso="+idCorso);
		} catch (NoPermissionException e) {
			//TODO Pagina di errore, non dovrebbe mai essere qui
			e.printStackTrace();
		} catch (NotFoundException e) {
			//TODO Pagina di errore, non dovrebbe mai essere qui
			//Non ha trovato il corso
			e.printStackTrace();
		} catch (SQLException e) {
			//TODO non si connette al database
			e.printStackTrace();
		}
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
