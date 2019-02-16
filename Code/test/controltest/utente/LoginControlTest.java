package controltest.utente;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.junit.Test;
import org.mockito.Mockito;

import control.utente.LoginServlet;

public class LoginControlTest extends Mockito{
	
	 @Test
	    public void testServlet() throws Exception {
	        HttpServletRequest request = mock(HttpServletRequest.class);       
	        HttpServletResponse response = mock(HttpServletResponse.class);    
	        when(request.getParameter("email")).thenReturn("luigicrisci1997@gmail.com");
	        when(request.getParameter("password")).thenReturn("PentiumD");
	        when(request.getSession()).thenReturn(new HttpSession() {
				
				@Override
				public void setMaxInactiveInterval(int arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setAttribute(String arg0, Object arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void removeValue(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void removeAttribute(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void putValue(String arg0, Object arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean isNew() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void invalidate() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public String[] getValueNames() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Object getValue(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public HttpSessionContext getSessionContext() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public ServletContext getServletContext() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public int getMaxInactiveInterval() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public long getLastAccessedTime() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public String getId() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public long getCreationTime() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public Enumeration<String> getAttributeNames() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Object getAttribute(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
			});
	        
	        StringWriter stringWriter = new StringWriter();
	        PrintWriter writer = new PrintWriter(stringWriter);
	        when(response.getWriter()).thenReturn(writer);

	        new LoginServlet().doPost(request, response);

	        //verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
	        writer.flush(); // it may not have been flushed yet...
	        //assertTrue(stringWriter.toString().contains("My expected string"));
	    }

}
