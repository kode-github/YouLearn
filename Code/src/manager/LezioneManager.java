package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.tomcat.jdbc.pool.DataSource;

import bean.AccountBean;
import bean.CommentoBean;
import bean.CorsoBean;
import bean.LezioneBean;
import bean.AccountBean.Ruolo;
import connection.DriverManagerConnectionPool;
import exception.NotFoundException;

public class LezioneManager {
	
	DataSource dataSource;
	
	public LezioneManager() {
		dataSource=new DataSource();
	}

	/**
	 * Recupera un commento e le PK delle entità relate
	 * @param id PK di commento
	 * @return Commento
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	public CommentoBean retrieveCommento(int id) throws SQLException, NotFoundException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		CommentoBean temp=new CommentoBean();
		
		String sql="SELECT* FROM commento WHERE id=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			System.out.println("Query: " + preparedStatement.toString());
			
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("Il commento non esiste"); //controllo che il commento esista
	
			temp.setIdCommento(id);
			temp.setTesto(rs.getString("Testo"));
			//Creo la lezione con la sola PK
			LezioneBean l= new LezioneBean();
			l.setNumeroLezione(rs.getInt("NumeroLezione"));
			CorsoBean corso= new CorsoBean();
			corso.setIdCorso(rs.getInt("IdCorso"));
			l.setCorso(corso);
			temp.setLezione(l);
			
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				dataSource.close();
			}
		}
		return temp;
	
	}
	
	public boolean delCommento(int code) throws Exception {
		if(!checkCommento(code)) throw new Exception("Questo commento non esiste");
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM commento WHERE id = ?";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, code);

			System.out.println("doDelete: "+ preparedStatement.toString());
			result = preparedStatement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				dataSource.close();
			}
		}
		return (result != 0);
	}

	private boolean checkCommento(int code) {
		try{
			CommentoBean c=retrieveCommento(code);
			return true;
		}catch(Exception e){ //e se ha un errore di connessione, invece?
			return false;
		}
		
	}

	public void insCommento(CommentoBean product) throws Exception {
		if(checkCommento(product.getIdCommento())) throw new Exception("Esiste già questo commento");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO commento VALUES(?,?,?,?,?)";
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setInt(2, product.getLezione().getNumeroLezione());
			preparedStatement.setInt(1, product.getIdCommento());
			preparedStatement.setInt(3, product.getLezione().getCorso().getIdCorso());
			preparedStatement.setString(4, product.getTesto());
			preparedStatement.setString(5, product.getAccountCreatore().getMail());
			System.out.println("Inserisci commento: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				dataSource.close();
			}
		}

	}
	
	public Collection<CommentoBean> retrieveCommentiByCorso(int nLezione,int idCorso) throws SQLException,NotFoundException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		CommentoBean temp= new CommentoBean();
		Collection<CommentoBean> list= new LinkedList<>();
		
		String sql="SELECT* FROM commento WHERE numeroLezione=? AND corsoIdCorso=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, nLezione);
			preparedStatement.setInt(2, idCorso);
			System.out.println("Query: " + preparedStatement.toString());
			
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("Non esiste questa lezione o questo corso"); //controllo che esista la lezione
			do {
				temp.setIdCommento(rs.getInt("idCommento"));
				temp.setTesto(rs.getString("Testo"));
				//Creo la lezione con la sola PK
				LezioneBean l= new LezioneBean();
				l.setNumeroLezione(rs.getInt("NumeroLezione"));
				CorsoBean corso= new CorsoBean();
				corso.setIdCorso(rs.getInt("IdCorso"));
				l.setCorso(corso);
				temp.setLezione(l);
				list.add(temp);
			}while(rs.next());
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				dataSource.close();
			}
		}
		return list;
	}

	public Collection<LezioneBean> retrieveLezioniByCorso(CorsoBean corso) {
		return null;
		// TODO Auto-generated method stub
		
	}
	
	
}
