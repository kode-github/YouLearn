package connection;

import org.apache.tomcat.jdbc.pool.DataSource;

public class ConfiguredDataSource extends DataSource {
	
	public ConfiguredDataSource() {
		super();
		setDriverClassName("com.mysql.jdbc.Driver");
		setUrl("jdbc:mysql://localhost:3306/youlearndb?useSSL=false"); //TODO Andrà migliorata con altri parametri
		setUsername("root");
		setPassword("PentiumD");
	}

}
