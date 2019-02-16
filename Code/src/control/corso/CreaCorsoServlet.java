package control.corso;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        manager=CorsoManager.getIstanza(getServletContext().getRealPath(""));

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
			Date date=new Date(LocalDateTime.now().getYear()-1900, LocalDateTime.now().getMonthValue()-1, LocalDateTime.now().getDayOfMonth());
			CorsoBean temp=new CorsoBean(null,nome,descrizione,date,
					dataScadenza,prezzo,categoria,"temp",Stato.Completamento,0,
					0,new LinkedList<IscrizioneBean>(),account,null, new LinkedList<LezioneBean>());
			
			manager.creaCorso(temp,copertina);
			request.getSession().setAttribute("creato", "true");
			response.sendRedirect(request.getContextPath()+"/HomepageUtente.jsp");
			
		}catch (NullPointerException | IllegalArgumentException | NotWellFormattedException e) {
			e.printStackTrace();
			request.getSession().setAttribute("creato", "false");
			response.sendRedirect(request.getContextPath()+"/SettingCorso.jsp");
		} catch (NoPermissionException | NotFoundException | SQLException e) {
			response.sendRedirect(request.getContextPath()+"/Error.jsp");
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
