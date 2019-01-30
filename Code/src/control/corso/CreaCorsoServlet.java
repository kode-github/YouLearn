package control.corso;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

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
import bean.CorsoBean.Stato;
import bean.IscrizioneBean;
import bean.LezioneBean;
import exception.NotFoundException;
import exception.NotWellFormattedException;
import manager.CorsoManager;

@WebServlet("/CreaCorsoServlet")
@MultipartConfig(fileSizeThreshold= 1024*1024*2, maxFileSize=1024*1024*10, maxRequestSize=1024*1024*50 )
public class CreaCorsoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	CorsoManager manager;
  
    public CreaCorsoServlet() {
        super();
        manager=new CorsoManager();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		AccountBean account=(AccountBean)request.getSession().getAttribute("account");
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
			@SuppressWarnings("deprecation")
			Date date=new Date(Calendar.YEAR-1900,Calendar.MONTH, Calendar.DAY_OF_MONTH);
			CorsoBean temp=new CorsoBean(null,nome,descrizione,date,
					dataScadenza,prezzo,categoria,"temp",Stato.Completamento,0,
					0,new LinkedList<IscrizioneBean>(),account,null, new LinkedList<LezioneBean>());
			
			manager.creaCorso(temp,copertina);
			account.AddCorsoTenuto(temp);
			response.sendRedirect(request.getContextPath()+"/HomepageUtente.jsp?corsoCreato=true");
			
		}catch (NullPointerException | IllegalArgumentException | NotWellFormattedException e) {
			//TODO dati non ben formattati
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/SettingCorso.jsp?erroreInserimento=true");
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
