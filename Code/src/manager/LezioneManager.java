package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.tomcat.jdbc.pool.DataSource;
import bean.CommentoBean;
import bean.CorsoBean;
import bean.LezioneBean;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class LezioneManager {
	
	DataSource dataSource;
	AccountManager accountManager;
	CorsoManager corsoManager;
	
	public LezioneManager() {
		dataSource=new DataSource();
	}

	/**
	 * TODO Non viene mai usato, ma va controllato
	 * @param id PK di commento
	 * @return Commento
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	public CommentoBean retrieveCommentoById(int id) throws SQLException, NotFoundException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		CommentoBean temp=new CommentoBean();
		
		String sql="SELECT* FROM commento WHERE id=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			System.out.println("retrieveCommento: " + preparedStatement.toString());
			
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
				connection.close();
			}
		}
		return temp;
	
	}
	
	/**
	 * Elimina un commento
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean delCommento(int code) throws Exception {
		if(!checkCommento(code)) throw new NotFoundException("Questo commento non esiste");
		
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
				connection.close();
			}
		}
		return (result != 0);
	}

	/**
	 * Controlla se un commento esiste nel database
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	private boolean checkCommento(int code) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="SELECT* FROM commento WHERE id=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, code);
			System.out.println("Query: " + preparedStatement.toString());
			return (preparedStatement.executeQuery()).next();
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				connection.close();
			}
		}
		
	}

	/**
	 * Inserisce un nuovo commento nel database
	 * @param product
	 * @throws SQLException 
	 * @throws Exception
	 */
	public void insCommento(CommentoBean product) throws NotWellFormattedException, SQLException {
		if(product.getIdCommento()!=null || !commentoIsWellFormatted(product)) throw new NotWellFormattedException("Il commento non"
																					+ "è ben formattato");
		
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
				connection.close();
			}
		}

	}
	
	/**
	 * Recupera i commenti di una lezione
	 * @param nLezione
	 * @param idCorso
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException 
	 */
	public Collection<CommentoBean> retrieveCommentiByLezione(LezioneBean lezione) throws SQLException,NotFoundException, NotWellFormattedException {
		if(!lezioneIsWellFormatted(lezione) ) throw new NotWellFormattedException("La lezione non è ben formattata");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<CommentoBean> list= new LinkedList<>();
		
		String sql="SELECT* FROM commento WHERE numeroLezione=? AND corsoIdCorso=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, lezione.getNumeroLezione());
			preparedStatement.setInt(2, lezione.getCorso().getIdCorso());
			System.out.println("retrieveCommentiByLezione: " + preparedStatement.toString());
			
			ResultSet rs= preparedStatement.executeQuery();
			
			while(rs.next()) {
				CommentoBean temp= new CommentoBean();
				temp.setIdCommento(rs.getInt("idCommento"));
				temp.setTesto(rs.getString("Testo"));
				temp.setLezione(lezione);
				temp.setAccountCreatore(accountManager.doRetrieveByKey(rs.getString("accountCreatore")));
				list.add(temp);
			}
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				connection.close();
			}
		}
		return list;
	}

	public Collection<LezioneBean> retrieveLezioniByCorso(CorsoBean corso) {
		return null;
	}
	
	public boolean commentoIsWellFormatted(CommentoBean commento) {
		return false;
		
	}
	
	public boolean lezioneIsWellFormatted(LezioneBean lezione) {
		return false;
		
	}
	
	
}
