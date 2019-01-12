package test;

import junit.framework.*;
import java.util.*;
import repository.*;
import bean.AccountBean;

public class TestRepositoryAccount  extends TestCase{

	public TestRepositoryAccount(String name) { 
		super(name); 
		}
	
	public void testSetNome() {
		AccountBean x= new AccountBean();
		
	}
	
	public static Test suite() { 
		return new TestSuite(TestRepositoryAccount.class); 
		}
	
	public static void main(String args[]) { 
		junit.textui.TestRunner.run(suite());
		}
	
}
