package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.naming.NoPermissionException;
import connection.ConfiguredDataSource;
import bean.AccountBean;
import bean.CorsoBean;
import bean.IscrizioneBean;
import bean.AccountBean.Ruolo;
import exception.AlreadyExistingException;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class IscrizioneManager {

	ConfiguredDataSource dataSource;
	AccountManager accountManager;
	CorsoManager corsoManager;
	LezioneManager lezioneManager;
	
	
	public IscrizioneManager() {	
		dataSource=new ConfiguredDataSource();
	}
	
	public IscrizioneBean doRetrieveByKey(int id,String mail) throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		IscrizioneBean iscrizione=new IscrizioneBean();
		accountManager= new AccountManager();
		corsoManager= new CorsoManager();
		
		String sql="SELECT* FROM Iscrizione WHERE accountmail=? AND corsoIdCorso=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, mail);
			preparedStatement.setInt(2, id);
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("L'iscrizione non è stato trovata"); //controllo che esista l'iscrizione
	
			iscrizione.setDataPagamento(rs.getDate("DataPagamento"));
			iscrizione.setFattura(rs.getString("fattura"));
			iscrizione.setImporto(rs.getInt("importo"));
			iscrizione.setAccount(accountManager.doRetrieveByKey(rs.getString("accountMail")));
			iscrizione.setCorso(corsoManager.doRetrieveByKey(rs.getInt("corsoIdCorso")));
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				connection.close();
			}
		}
		return iscrizione;
	}
	
	/**
	 * Recupera tutti i corsi a cui è iscritto un Utente
	 * @param account
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException 
	 */
	public Collection<IscrizioneBean> getIscrizioniUtente(AccountBean account) throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		accountManager= new AccountManager();
		lezioneManager= new LezioneManager();
		corsoManager=new CorsoManager();
		if(!accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non può avere corsi creati");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<IscrizioneBean> collection= new LinkedList<IscrizioneBean>();
		
		String sql="SELECT* FROM Iscrizione WHERE accountMail=?";
		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			System.out.println("Iscrizioni account: "+preparedStatement.toString());
			ResultSet rs= preparedStatement.executeQuery();
			while(rs.next()) {
				IscrizioneBean iscrizione= new IscrizioneBean();
				iscrizione.setDataPagamento(rs.getDate("DataPagamento"));
				iscrizione.setFattura(rs.getString("fattura"));
				iscrizione.setImporto(rs.getInt("importo"));
				iscrizione.setAccount(account); //aggiungo l'account
				System.out.println(""+rs.getInt("corsoIdCorso"));
				CorsoBean corso=new CorsoBean();
				corso=corsoManager.doRetrieveByKey(rs.getInt("corsoIdCorso")); //recupero il corso
				corso.addIscrizione(iscrizione); //aggiungo iscrizione a corso e viceversa
				collection.add(iscrizione);
			}
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				connection.close();//KONO DIO DA
				//SEKAI ICHI!
				//NANIII!?!?
			}
		}
		return collection;
	}

	/**
	 * Recupera tutti gli utenti iscritti ad un corso
	 * @param corso
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException
	 */
	public Collection<IscrizioneBean> getIscrittiCorso(CorsoBean corso) throws SQLException, NotFoundException, NoPermissionException {
		accountManager= new AccountManager();
		lezioneManager= new LezioneManager();
		corsoManager=new CorsoManager();
		if(!corsoManager.checkCorso(corso)) throw new NotFoundException("Questo account non esiste");
		
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
				AccountBean account=accountManager.doRetrieveByKey(rs.getString("accountMail")); //recupero l'account
				iscrizione.setAccount(account); //aggiungo iscrizione all'account e viceversa
				iscrizione.setCorso(corso); //aggiungo all'iscrizione il corso e viceversa
				collection.add(iscrizione);
			}
			connection.commit();
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				connection.close();
			}
		}
		return collection;
	}

	/**
	 * Inserisce una nuova iscrizione
	 * @param iscrizione la nuova iscrizione
	 * @throws SQLException
	 * @throws NotFoundException L'account o il corso non esistono
	 * @throws AlreadyExistingException L'iscrizione esiste già
	 * @throws NoPermissionException 
	 * @throws NotWellFormattedException 
	 */
	public void iscriviStudente(IscrizioneBean iscrizione) throws SQLException, NotFoundException, AlreadyExistingException, NoPermissionException, NotWellFormattedException {
		accountManager=new AccountManager();
		corsoManager= new CorsoManager();
		if(checkIscrizione(iscrizione.getCorso().getIdCorso(),iscrizione.getAccount().getMail())) 
												throw new AlreadyExistingException("L'iscrizione esiste già");
		if(!corsoManager.checkCorso(iscrizione.getCorso())) throw new NotFoundException("Il corso non esiste");
		if(!accountManager.checkAccount(iscrizione.getAccount())) throw new NotFoundException("L'account non esiste");
		
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
				connection.close();
			}
		}
	}

	/**
	 * Controlla se una certa iscrizione esiste
	 * @param iscrizione
	 * @return
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotWellFormattedException 
	 */
	private boolean checkIscrizione(int id ,String mail) throws NoPermissionException, SQLException, NotWellFormattedException {
		try {
			doRetrieveByKey(id, mail);
			return true;
		}catch(NotFoundException e) {
			return false;
		}
	}
}
