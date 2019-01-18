package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.naming.NoPermissionException;
import org.apache.tomcat.jdbc.pool.DataSource;
import bean.AccountBean;
import bean.CorsoBean;
import bean.IscrizioneBean;
import bean.AccountBean.Ruolo;
import exception.NotFoundException;

public class IscrizioneManager {

	DataSource dataSource;
	AccountManager accountManager;
	CorsoManager corsoManager;
	LezioneManager lezioneManager;
	
	public Collection<IscrizioneBean> getIscrizioniUtente(AccountBean account) throws SQLException, NotFoundException, NoPermissionException {
		accountManager= new AccountManager();
		lezioneManager= new LezioneManager();
		if(accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non può avere corsi creati");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<IscrizioneBean> collection= new LinkedList<IscrizioneBean>();
		
		String sql="SELECT* FROM Iscrizione WHERE accountMail=?";
		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			ResultSet rs= preparedStatement.executeQuery();
			while(rs.next()) {
				IscrizioneBean iscrizione= new IscrizioneBean();
				iscrizione.setDataPagamento(rs.getDate("DataPagamento"));
				iscrizione.setFattura(rs.getString("fattura"));
				iscrizione.setImporto(rs.getInt("importo"));
				iscrizione.setAccount(account); //aggiungo l'account
				CorsoBean corso=corsoManager.doRetrieveByKey(rs.getInt("corsoIdCorso")); //recupero il corso
				corso.addIscrizione(iscrizione); //aggiungo iscrizione a corso e viceversa
				collection.add(iscrizione);
			}
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				dataSource.close();
			}
		}
		return collection;
	}

	public Collection<IscrizioneBean> getIscrittiCorso(CorsoBean corso) throws SQLException, NotFoundException, NoPermissionException {
		accountManager= new AccountManager();
		lezioneManager= new LezioneManager();
//		if(accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
//		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non può avere corsi creati");
		//Precondizioni che non sappiamo come gestire
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<IscrizioneBean> collection= new LinkedList<IscrizioneBean>();
		
		String sql="SELECT* FROM Iscrizione WHERE corsoIdCorso=?";
		
		try {
			connection=dataSource.getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, corso.getIdCorso());
			ResultSet rs= preparedStatement.executeQuery();
			while(rs.next()) {
				IscrizioneBean iscrizione= new IscrizioneBean();
				iscrizione.setDataPagamento(rs.getDate("DataPagamento"));
				iscrizione.setFattura(rs.getString("fattura"));
				iscrizione.setImporto(rs.getInt("importo"));
				iscrizione.setCorso(corso); //aggiungo il corso
				AccountBean account=accountManager.doRetrieveByKey(rs.getString("accountMail")); //recupero il corso
				account.addIscrizione(iscrizione); //aggiungo iscrizione all'account e viceversa
				collection.add(iscrizione);
			}
			connection.commit();
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				dataSource.close();
			}
		}
		return collection;
	}

	public void iscriviStudente(IscrizioneBean iscrizione) throws SQLException {
		//Il corso deve esistere
		//l'account deve esistere
		//L'iscrizione non esiste già
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="Insert into Iscrizione values(?,?,?,?,?)";
		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1,iscrizione.getAccount().getMail());
			preparedStatement.setInt(2,iscrizione.getCorso().getIdCorso());
			preparedStatement.setDate(3,iscrizione.getDataPagamento());
			preparedStatement.setDouble(4,iscrizione.getImporto());
			preparedStatement.setString(5, iscrizione.getFattura());
			preparedStatement.executeUpdate();
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				dataSource.close();
			}
		}
	}
}
