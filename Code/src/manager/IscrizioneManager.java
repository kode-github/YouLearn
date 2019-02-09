package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
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

	private static IscrizioneManager istanza;
	private AccountManager accountManager;
	private LezioneManager lezioneManager;
	private CorsoManager corsoManager;
	
	private IscrizioneManager() { }
	
	public static IscrizioneManager getIstanza() {
		if(istanza==null)
			istanza=new IscrizioneManager();
		return istanza;
	}
	
	
	
	
	/**
	 * Questo metodo non viene mai usato ma si dovrï¿½ controllare
	 * Utile nell'eventualitï¿½ si debba analizzare un'iscrizione da parte di un supervisore
	 * @param id
	 * @param mail
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException
	 * @throws NotWellFormattedException
	 */
	public synchronized IscrizioneBean doRetrieveByKey(int id,String mail) throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		IscrizioneBean iscrizione=new IscrizioneBean();
		accountManager= AccountManager.getIstanza();
		corsoManager= CorsoManager.getIstanza();
		
		String sql="SELECT* FROM iscrizione WHERE accountmail=? AND corsoIdCorso=?";		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, mail);
			preparedStatement.setInt(2, id);
			ResultSet rs= preparedStatement.executeQuery();
			
			if(!rs.next()) throw new NotFoundException("L'iscrizione non ï¿½ stato trovata"); //controllo che esista l'iscrizione
	
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
				DriverManagerConnectionPool.releaseConnection(connection);
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
	public synchronized Collection<IscrizioneBean> getIscrizioniUtente(AccountBean account) throws SQLException, NotFoundException, NoPermissionException, NotWellFormattedException {
		accountManager= AccountManager.getIstanza();
		lezioneManager= LezioneManager.getIstanza();
		corsoManager=CorsoManager.getIstanza();
		if(!accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non puï¿½ avere corsi creati");
		
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
				DriverManagerConnectionPool.releaseConnection(connection);//KONO DIO DA
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
	public synchronized Collection<IscrizioneBean> getIscrittiCorso(CorsoBean corso) throws SQLException, NotFoundException, NoPermissionException {
		accountManager= AccountManager.getIstanza();
		lezioneManager= LezioneManager.getIstanza();
		corsoManager=CorsoManager.getIstanza();
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
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return collection;
	}

	/**
	 * Inserisce una nuova iscrizione
	 * @param iscrizione la nuova iscrizione
	 * @throws SQLException
	 * @throws NotFoundException L'account o il corso non esistono
	 * @throws AlreadyExistingException L'iscrizione esiste giï¿½
	 * @throws NoPermissionException 
	 * @throws NotWellFormattedException 
	 */
	public synchronized void iscriviStudente(IscrizioneBean iscrizione) throws SQLException, NotFoundException, AlreadyExistingException, NoPermissionException, NotWellFormattedException {
		accountManager=AccountManager.getIstanza();
		corsoManager= CorsoManager.getIstanza();
		//TODO Verificare perchÃ¨ si usano solo le key per i check
		if(!isWellFormatted(iscrizione)) throw new NotWellFormattedException("L'iscrizione non è ben formattata");
		if(checkIscrizione(iscrizione.getCorso().getIdCorso(),iscrizione.getAccount().getMail())) 
												throw new AlreadyExistingException("L'iscrizione esiste giï¿½");
		if(!corsoManager.checkCorso(iscrizione.getCorso())) throw new NotFoundException("Il corso non esiste");
		if(!accountManager.checkAccount(iscrizione.getAccount())) throw new NotFoundException("L'account non esiste");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="insert into iscrizione values(?,?,?,?,?)";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1,iscrizione.getAccount().getMail());
			preparedStatement.setInt(2,iscrizione.getCorso().getIdCorso());
			preparedStatement.setDate(3,iscrizione.getDataPagamento());
			preparedStatement.setDouble(4,iscrizione.getImporto());
			preparedStatement.setInt(5, Integer.parseInt(iscrizione.getFattura()));
			preparedStatement.executeUpdate();
			if(!connection.getAutoCommit())
				connection.commit();
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	private AccountBean account;
	private CorsoBean corso;
	private Date dataPagamento;
	private double importo;
	private String fattura;

	public synchronized boolean isWellFormatted(IscrizioneBean iscrizione) {
		Date dataOdierna = new Date();
		return account!=null && corso!=null && dataPagamento!=null && !dataPagamento.before(dataOdierna) && importo>=0 
				&& fattura!=null && fattura.matches("^[0-9]$"); //TODO Quanto è grande la fattura?
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
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return rs.next();
	}
}
