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
import bean.AccountBean.Ruolo;
import bean.CorsoBean;
import bean.CorsoBean.Categoria;
import bean.CorsoBean.Stato;
import exception.NotFoundException;
import utility.RuoloUtility;
import utility.StatoUtility;

public class CorsoManager {
	
	AccountManager accountManager;
	LezioneManager lezioneManager;
	DataSource dataSource;
	
	public CorsoManager() {
		dataSource=new DataSource();
	}
	
	
	
	/**
	 * Recupera gli account tenuti da un docente
	 * @param account
	 * @return
	 * @throws NoPermissionException
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	public Collection<CorsoBean> retrieveByCreatore(AccountBean account) throws NoPermissionException, SQLException, NotFoundException {
		accountManager= new AccountManager();
		lezioneManager= new LezioneManager();
		if(accountManager.checkAccount(account)) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non può avere corsi creati");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<CorsoBean> collection= new LinkedList<CorsoBean>();
		
		String sql="SELECT* FROM Corso WHERE accountCreatore=?";
		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			ResultSet rs= preparedStatement.executeQuery();
			while(rs.next()) {
				CorsoBean corso= new CorsoBean();
				 corso.setIdCorso(rs.getInt("idcorso"));
				 corso.setNome(rs.getString("nome"));
				 corso.setDescrizione(rs.getString("Descrizione"));
				 corso.setCopertina(rs.getString("copertina"));
				 corso.setStato(StatoUtility.parserTipoCarta(rs.getInt("stato")));
				 corso.setDataCreazione(rs.getDate("dataCreazione"));
				 corso.setDataFine(rs.getDate("DataFine"));
				 corso.setnLezioni(rs.getInt("nLezioni"));
				 corso.setnIscritti(rs.getInt("nIscritti"));
				 corso.setCategoria(Categoria.valueOf(rs.getString("Categoria")));
				 corso.setStato(Stato.valueOf(rs.getString("stato")));
				 corso.setDocente(account);
				 lezioneManager.retrieveLezioniByCorso(corso);
				 //non carichiamo il supervisore
				 collection.add(corso);
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

	/**
	 * Recupera i corsi supervisionati da un certo supervisore
	 * @param account
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NoPermissionException
	 */
	public Collection<CorsoBean> doRetrieveBySupervisore(AccountBean account) throws SQLException, NotFoundException, NoPermissionException {
		accountManager= new AccountManager();
		lezioneManager= new LezioneManager();
		if(!accountManager.checkMail(account.getMail())) throw new NotFoundException("Questo account non esiste");
		if(!account.getTipo().equals(Ruolo.Utente)) throw new NoPermissionException("Questo utente non può avere corsi creati");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<CorsoBean> collection= new LinkedList<CorsoBean>();
		
		String sql="SELECT* FROM Corso WHERE accountSupervisore=?";
		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getMail());
			ResultSet rs= preparedStatement.executeQuery();
			while(rs.next()) {
				CorsoBean corso= new CorsoBean();
				 corso.setIdCorso(rs.getInt("idcorso"));
				 corso.setNome(rs.getString("nome"));
				 corso.setDescrizione(rs.getString("Descrizione"));
				 corso.setCopertina(rs.getString("copertina"));
				 corso.setStato(StatoUtility.parserTipoCarta(rs.getInt("stato")));
				 corso.setDataCreazione(rs.getDate("dataCreazione"));
				 corso.setDataFine(rs.getDate("DataFine"));
				 corso.setnLezioni(rs.getInt("nLezioni"));
				 corso.setnIscritti(rs.getInt("nIscritti"));
				 corso.setCategoria(Categoria.valueOf(rs.getString("Categoria")));
				 corso.setStato(Stato.valueOf(rs.getString("stato")));
				 corso.setSupervisore(account);
				 lezioneManager.retrieveLezioniByCorso(corso);
				 //non carichiamo il docente
				 collection.add(corso);
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



	public CorsoBean doRetrieveByKey(int int1) {
		// TODO Auto-generated method stub
		return null;
	}



	public boolean checkCorso(CorsoBean corso) {
		// TODO Auto-generated method stub
		return false;
	}

}
