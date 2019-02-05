package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.NoPermissionException;

import bean.AccountBean;
import bean.CorsoBean;
import bean.IscrizioneBean;
import connection.DriverManagerConnectionPool;
import bean.AccountBean.Ruolo;
import bean.CorsoBean.Categoria;
import bean.CorsoBean.Stato;
import exception.AlreadyExistingException;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class IscrizioneManager {

	AccountManager accountManager;
	CorsoManager corsoManager;
	LezioneManager lezioneManager;
	
	
	public IscrizioneManager() {	
	}
	
	/**
	 * Questo metodo non viene mai usato ma si dovr� controllare
	 * Utile nell'eventualit� si debba analizzare un'iscrizione da parte di un supervisore
	 * @param id
	 * @param mail
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException
	 */
	public IscrizioneBean doRetrieveByKey(int id,String mail) throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		IscrizioneBean iscrizione=new IscrizioneBean();
		accountManager= new AccountManager();
		corsoManager= new CorsoManager();
		
		String sql="SELECT* FROM iscrizione WHERE accountmail=? AND corsoIdCorso=?";		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, mail);
			preparedStatement.setInt(2, id);
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("L'iscrizione non � stato trovata"); //controllo che esista l'iscrizione
	
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
	 * Recupera tutti i corsi a cui e' iscritto un Utente
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
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non pu� avere corsi creati");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<IscrizioneBean> collection= new LinkedList<IscrizioneBean>();
		
		String sql="SELECT* FROM iscrizione join corso on corsoIdCorso=idCorso WHERE accountMail=?";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
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
				CorsoBean corso=new CorsoBean();
				corso.setIdCorso(rs.getInt("corsoIdCorso"));
				corso.setNome(rs.getString("nome"));
				corso.setDescrizione(rs.getString("Descrizione"));
				corso.setDataCreazione(rs.getDate("DataCreazione"));
				corso.setDataFine(rs.getDate("dataFine"));
				corso.setCopertina(rs.getString("Copertina"));
				corso.setPrezzo(rs.getInt("Prezzo"));
				corso.setStato(Stato.valueOf(rs.getString("stato")));
				corso.setCategoria(Categoria.valueOf(rs.getString("categoria")));
				corso.setnLezioni(rs.getInt("nLezioni"));
				corso.setnIscritti(rs.getInt("nIscritti"));
				corso.setDocente(accountManager.doRetrieveByKey(rs.getString("accountCreatore")));
				iscrizione.setAccount(account);
				iscrizione.setCorso(corso);
				collection.add(iscrizione);
			}
			account.setIscrizioni(collection);
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

//	
//	/**
//	 * Recupera tutti gli utenti iscritti ad un corso
//	 * @param corso
//	 * @return
//	 * @throws SQLException
//	 * @throws NotFoundException
//	 * @throws NoPermissionException
//	 */
//	public Collection<IscrizioneBean> getIscrittiCorso(int corso) throws SQLException, NotFoundException, NoPermissionException {
//		accountManager= new AccountManager();
//		lezioneManager= new LezioneManager();
//		corsoManager=new CorsoManager();
//		if(!corsoManager.checkCorso(corso)) throw new NotFoundException("Questo corso non esiste");
//		CorsoBean tmp=new CorsoBean();
//		tmp.setIdCorso(corso);
//		Connection connection=null;
//		PreparedStatement preparedStatement=null;
//		Collection<IscrizioneBean> collection= new LinkedList<IscrizioneBean>();
//		
//		String sql="SELECT* FROM iscrizione WHERE corsoIdCorso=?";
//		
//		try {
//			connection=DriverManagerConnectionPool.getConnection();
//			connection.setAutoCommit(false);
//			preparedStatement= connection.prepareStatement(sql);
//			preparedStatement.setInt(1, corso);
//			ResultSet rs= preparedStatement.executeQuery();
//			while(rs.next()) {
//				IscrizioneBean iscrizione= new IscrizioneBean();
//				iscrizione.setDataPagamento(rs.getDate("DataPagamento"));
//				iscrizione.setFattura(rs.getString("fattura"));
//				iscrizione.setImporto(rs.getInt("importo"));
//				iscrizione.setCorso(tmp); //aggiungo il corso
//				AccountBean account=accountManager.doRetrieveByKey(rs.getString("accountMail")); //recupero l'account
//				iscrizione.setAccount(account); //aggiungo iscrizione all'account e viceversa
//				collection.add(iscrizione);
//			}
//			connection.commit();
//		}finally {
//			try {
//			if(preparedStatement!=null)
//				preparedStatement.close();
//			}finally {
//				connection.close();
//			}
//		}
//		return collection;
//	}
//	
	
	
	
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
		if(!corsoManager.checkCorso(corso)) throw new NotFoundException("Questo corso non esiste");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<IscrizioneBean> collection= new LinkedList<IscrizioneBean>();
		
		String sql="SELECT* FROM iscrizione WHERE corsoIdCorso=?";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
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
	 * @throws AlreadyExistingException L'iscrizione esiste gi�
	 * @throws NoPermissionException 
	 * @throws NotWellFormattedException 
	 */
	public void iscriviStudente(IscrizioneBean iscrizione) throws SQLException, NotFoundException, AlreadyExistingException, NoPermissionException, NotWellFormattedException {
		accountManager=new AccountManager();
		corsoManager= new CorsoManager();
		//TODO Verificare perchè si usano solo le key per i check
		if(checkIscrizione(iscrizione.getCorso().getIdCorso(),iscrizione.getAccount().getMail())) 
												throw new AlreadyExistingException("L'iscrizione esiste gi�");
		if(!corsoManager.checkCorso(iscrizione.getCorso())) throw new NotFoundException("Il corso non esiste");
		if(!accountManager.checkAccount(iscrizione.getAccount())) throw new NotFoundException("L'account non esiste");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="insert into iscrizione values(?,?,?,?,?)";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1,iscrizione.getAccount().getMail());
			preparedStatement.setInt(2,iscrizione.getCorso().getIdCorso());
			preparedStatement.setDate(3,iscrizione.getDataPagamento());
			preparedStatement.setDouble(4,iscrizione.getImporto());
			preparedStatement.setInt(5, Integer.parseInt(iscrizione.getFattura()));
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
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs;
		String sql="SELECT accountMail FROM iscrizione WHERE accountMail=? AND corsoIdCorso=?";		
		try {
			System.out.println(mail);
			System.out.println(id);
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, mail);
			preparedStatement.setInt(2, id);
			rs= preparedStatement.executeQuery();
			
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				connection.close();
			}
		}
		return rs.next();
	}
}
