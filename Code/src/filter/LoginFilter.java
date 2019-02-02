package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;


@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {


    public LoginFilter() {
    }


	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest=(HttpServletRequest) request;
		HttpServletResponse httpResponse=(HttpServletResponse) response;
		AccountBean account=(AccountBean) httpRequest.getSession().getAttribute("account");
		String uri=httpRequest.getRequestURI();
		System.out.println("uri: "+uri);
		if(uri.contains("Welcome.jsp") || uri.contains("/CSS/") || uri.contains("/JS/") || uri.contains("/Images") 
				|| uri.contains("LoginServlet") || uri.contains("/Resources"))
			chain.doFilter(request, response);
		else {
			if(account==null) {
				httpRequest.getSession().setAttribute("logged", "false"); //Avviso che si è provato ad accedere senza essser loggati
				httpResponse.sendRedirect(httpRequest.getContextPath()+"/Welcome.jsp");
				return;
			}else
				chain.doFilter(request, response);
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
