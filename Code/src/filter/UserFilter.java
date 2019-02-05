package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;
import bean.AccountBean.Ruolo;


@WebFilter("/UserFilter")
public class UserFilter implements Filter {


    public UserFilter() {
    }


	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest=(HttpServletRequest) request;
		HttpServletResponse httpResponse=(HttpServletResponse) response;
		AccountBean account=(AccountBean) httpRequest.getSession().getAttribute("account");
		String uri=httpRequest.getRequestURI();
		//DICIAMO CHE FORSE ERA MEGLIO NEL WEB.XML
		if(uri.contains("Welcome.jsp") || uri.contains("/CSS/") || uri.contains("/JS/") || uri.contains("/Images") 
				|| uri.contains("LoginServlet") || uri.contains("/Resources") || uri.contains("/HomepageSupervisore.jsp") 
				|| uri.contains("/VerificaCorsoServlet") || uri.contains("/VisualizzaProfiloServlet") 
				|| uri.contains("/VisualCorsoServlet") || uri.contains("Logout") || uri.contains("SearchCorso")
				|| uri.contains("/RegistrazioneServlet") || uri.contains("/CambiaMail") || uri.contains("/CambiaPass") 
				|| uri.contains("/Lezione.jsp"))
			chain.doFilter(request, response);
		else {
			if(!account.getTipo().equals(Ruolo.Utente)) {
				httpResponse.sendRedirect(httpRequest.getContextPath()+"/HomepageSupervisore.jsp");
				return;
			}else
				chain.doFilter(request, response);
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
