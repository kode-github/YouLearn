package manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;
import javax.servlet.http.Part;
import connection.ConfiguredDataSource;
import bean.CommentoBean;
import bean.CorsoBean;
import bean.LezioneBean;
import exception.DatiErratiException;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class LezioneManager {
	
	ConfiguredDataSource dataSource;
	AccountManager accountManager;
	CorsoManager corsoManager;
	
	public LezioneManager() {
		dataSource=new ConfiguredDataSource();
	}

	/**
	 * Modifica l'ordine delle lezioni di un corso
	 * Per ordinare vedi algoritmo su quaderno
	 * @param lezioni Collezione di lezioni col numero lezione da aggiornare
	 * @param corso a cui appartengono le lezioni
	 * @throws SQLException 
	 * @throws DatiErratiException 
	 */
	public void modificaOrdine(LinkedList<LezioneBean> lezioni,CorsoBean corso) throws SQLException, DatiErratiException {
		//Controllo che le lezioni siano relative allo stesso corso
		
		Connection c=null;
		try {
			c=dataSource.getConnection();
			c.setAutoCommit(false);
			for(LezioneBean l: lezioni) 
				changeNumeroLezione(l.getIdLezione(), l.getNumeroLezione(),corso.getIdCorso(), c);
			checkCoerenza(corso);
			c.commit();
		}catch(SQLException | DatiErratiException |NotWellFormattedException  e) {
			c.rollback();
		}finally {
			c.close();
		}
		
	}
	
	/**
	 * Sta roba è brutta ma pacienz
	 * Controlla se, in un certo corso, esistono lezioni con lo stesso 
	 * @param idCorso
	 * @return
	 * @throws SQLException 
	 * @throws NotWellFormattedException 
	 */
	private void checkCoerenza(CorsoBean corso) throws NotWellFormattedException, SQLException {
		LinkedList<LezioneBean> l=(LinkedList<LezioneBean>) retrieveLezioniByCorso(corso);
		for(LezioneBean lezione: l) 
			for(LezioneBean lezione2: l)
				if(lezione.getIdLezione()!=lezione2.getIdLezione() && lezione.getNumeroLezione()==lezione2.getNumeroLezione()) 
									throw new NotWellFormattedException("");

	}

	
	
	/**
	 * Cambia il numero di lezione
	 * @param from numero di partenza
	 * @param to nuovo numero
	 * @throws SQLException
	 * @throws DatiErratiException 
	 */
	private void changeNumeroLezione(int idLezione,int numeroLezione,int idCorso,Connection c) throws SQLException, DatiErratiException {
		PreparedStatement statement=null;
		String sql="Update Lezione set numeroLezione=? where idLezione=? AND corsoIdCorso=?";
		try {
			c=dataSource.getConnection();
			statement=c.prepareStatement(sql);
			statement.setInt(1, numeroLezione);
			statement.setInt(2, idLezione);
			statement.setInt(3, idCorso);
			
			int res=statement.executeUpdate();
			if(res==0) throw new DatiErratiException("non esiste una lezione con questo codice oppure non appartiene a questo corso");
		}finally {
				if(statement!=null)
					statement.close();
		}
	}
	
	/**
	 * Inserisce una serie di lezioni nel database
	 * In caso di errore nell'inserimento di una lezione, l'operazione viene annullata ma gli inserimenti 
	 * precedenti non vengono annullati
	 * @param lezioni
	 * @param files
	 * @throws DatiErratiException
	 * @throws NotWellFormattedException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void insLezioniMultiple(ArrayList<LezioneBean> lezioni,ArrayList<Part> files) throws DatiErratiException, NotWellFormattedException, SQLException, IOException {
		if(lezioni==null || files==null || lezioni.size()!=files.size())
									throw new DatiErratiException("Non c'è un file per ogni lezione");
		/**Inizio le modifiche */
		corsoManager= new CorsoManager();
		int i=0;
		Connection c=dataSource.getConnection();
		c.setAutoCommit(false);
		for(Part file: files) {
			insLezione(lezioni.get(i++),file,c);
		}
		c.close();
	}
	
	/**
	 * Inserisce una lezione nel database e la salva in un file
	 * @param lezione la lezione da inserire
	 * @param file il file della lezione
	 * @param c la connessione al db: necessaria per evitare che ne venga creata una per ogni inserimento
	 * @throws NotWellFormattedException la lezione non è ben formattata 
	 * @throws SQLException Errore nella connessione al db o nell'inserimento
	 * @throws DatiErratiException la lezione esiste già o non è collegata al corso giusto
	 * @throws IOException Errore nella scrittura del file su disco
	 */
	private void insLezione(LezioneBean lezione,Part file,Connection c) throws NotWellFormattedException, SQLException, DatiErratiException, IOException {
		//Va controllato che non esista un'altra lezione con lo stesso numero per quell'idCorso
		if(!lezioneIsWellFormatted(lezione) || lezione.getCorso().getIdCorso()==null)  
										throw new NotWellFormattedException("la lezione non è ben formattata");
		if(checkLezione(lezione) || !corsoManager.checkCorso(lezione.getCorso().getIdCorso())) 
										throw new DatiErratiException("la lezione esiste già o il corso non esiste");
		PreparedStatement statement=null;
		String sql="Insert into Lezione values (?,?,?,?,?,?)";
		try{
			
			Path path=Paths.get("C:\\Users\\Antonio\\Documents\\Universita\\IS\\Progetto\\"
					+ "YouLearn\\Code\\Resources\\Lezioni\\"+lezione.getCorso().getIdCorso().toString());
			if(!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
					Files.createDirectories(path); 
			String filename=UUID.randomUUID().toString();
			String type=file.getSubmittedFileName().substring(file.getSubmittedFileName().indexOf('.'));
			path=Paths.get("C:\\Users\\Antonio\\Documents\\Universita\\IS\\Progetto\\"
					+ "YouLearn\\Code\\Resources\\Lezioni\\"+lezione.getCorso().getIdCorso().toString()+File.separator+
																				filename+File.separator+type);
			statement=c.prepareStatement(sql);
			statement.setString(1, lezione.getNome());
			statement.setInt(2, lezione.getVisualizzazioni());
			statement.setInt(3, lezione.getNumeroLezione());
			statement.setInt(4, lezione.getIdLezione());
			statement.setString(5, path.toString());
			statement.setInt(6, lezione.getCorso().getIdCorso());
			statement.executeUpdate(); //inserisce nel db
			
			file.write(path.toString()); //Scrivo il file sul disco
			
			c.commit(); //conferma l'inserimento nel db
		}catch(SQLException | IOException e) {
			//C'è stato un errore nel salvataggio del file o nell'inserimento
			//In ogni caso la scrittura nel db viene eliminata
			c.rollback();
		}finally {
			if(statement!=null)
				statement.close();
		}
	}
	
	/**
	 * Eimina una lezione dal Db e dal FileSystem 
	 * @param lezione
	 * @throws SQLException 
	 * @throws DatiErratiException 
	 * @throws NotFoundException 
	 */
	public void delLezione(int idLezione) throws SQLException, DatiErratiException, NotFoundException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM lezione WHERE idLezione=?";

		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, idLezione);
			System.out.println("doDelete: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				connection.close();
			}
		}
	}
	
	private boolean checkLezione(int idLezione) throws SQLException {
		Connection c=null;
		PreparedStatement statement=null;
		String sql="Select * from lezione where idLezione=?";
		try {
			c=dataSource.getConnection();
			statement=c.prepareStatement(sql);
			statement.setInt(1, idLezione);
			
			return (statement.executeQuery()).next();
		}finally {
			try{
				if(statement!=null)
					statement.close();
			}finally {
				c.close();
			}	
		}
	}

	public boolean checkLezione(LezioneBean lezione) throws SQLException, DatiErratiException {
		if(lezione==null || lezione.getIdLezione()==null || !lezioneIsWellFormatted(lezione) ) 
											throw new DatiErratiException("La lezione non è corretta");
		
		Connection c=null;
		PreparedStatement statement=null;
		String sql="Select * from lezione where idLezione=? AND numeroLezione=? AND nome=? AND visualizzazione=? AND filepath=? "
																					+ "AND corsoIdCorso=?";
		try {
			c=dataSource.getConnection();
			statement=c.prepareStatement(sql);
			statement.setInt(1, lezione.getIdLezione());
			statement.setInt(2, lezione.getNumeroLezione());
			statement.setString(3, lezione.getNome());
			statement.setInt(4,lezione.getVisualizzazioni());
			statement.setString(5, lezione.getFilePath());
			statement.setInt(6, lezione.getCorso().getIdCorso());
			
			return (statement.executeQuery()).next();
		}finally {
			try{
				if(statement!=null)
					statement.close();
			}finally {
				c.close();
			}	
		}
	}
	
	/**
	 * Recupera le lezioni di un corso (commenti esclusi)
	 * @param corso
	 * @return
	 * @throws NotWellFormattedException 
	 * @throws SQLException 
	 */
	public Collection<LezioneBean> retrieveLezioniByCorso(CorsoBean corso) throws NotWellFormattedException, SQLException{
		corsoManager=new CorsoManager();
		if(corso.getIdCorso()==null || !corsoManager.isWellFormatted(corso)) 
								throw new NotWellFormattedException("Il corso non è ben formattato");
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<LezioneBean> collection=new LinkedList<LezioneBean>();
		String sql="Select * from lezione where corsoIdCorso=?";
		
		try {
			connection=dataSource.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, corso.getIdCorso());
			ResultSet rs=preparedStatement.executeQuery();
			
			while(rs.next()) {
				LezioneBean lezione=new LezioneBean();
				lezione.setNome(rs.getString("nome"));
				lezione.setNumeroLezione(rs.getInt("numeroLezione"));
				lezione.setVisualizzazioni(rs.getInt("visualizzazione"));
				lezione.setFilePath(rs.getString("filePath"));
				lezione.setIdLezione(rs.getInt("idLezione"));
				lezione.setCorso(corso);
				collection.add(lezione);
			}
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
		
		String sql="INSERT INTO commento VALUES(?,?,?,?)";
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, product.getIdCommento());
			preparedStatement.setInt(2, product.getLezione().getIdLezione());
			preparedStatement.setString(3, product.getTesto());
			preparedStatement.setString(4, product.getAccountCreatore().getMail());
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
		
		String sql="SELECT* FROM commento WHERE idLezione=?";		
		try {
			connection=dataSource.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, lezione.getIdLezione());
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

	/**
	 * Controlla se un commento è ben formattato
	 * Da notare che un commento è considerato valido anche se l'id è null (in caso di inserimento in quanto AutoIncrement)
	 * @param commento
	 * @return
	 */
	public boolean commentoIsWellFormatted(CommentoBean commento) {
		accountManager=new AccountManager();
		//idcommento, testo, accountCreatore, lezione
		return commento.getTesto()!=null && commento.getTesto().matches("^[a-zA-Z0-9]{1,1024}") &&
				commento.getAccountCreatore()!=null && accountManager.isWellFormatted(commento.getAccountCreatore()) &&
				commento.getLezione()!=null && lezioneIsWellFormatted(commento.getLezione());
		
	}
	
	/**
	 * Controlla se una lezione è ben formattata
	 * Da notare che una lezione è considerata valida anche se l'id è null (in caso di inserimento in quanto AutoIncrement)
	 * @param lezione
	 * @return
	 */
	public boolean lezioneIsWellFormatted(LezioneBean lezione) {
		corsoManager=new CorsoManager();
		if(lezione.getIdLezione()!=null)
			if(lezione.getFilePath()==null || !lezione.getFilePath().matches("^[a-zA-Z0-9\\.-]{10,2048}"))
				return false;
		return lezione.getNome()!=null && /*lezione.getNome().matches("")*/ 
				lezione.getNumeroLezione()>=0 && lezione.getCorso()!=null && corsoManager.isWellFormatted(lezione.getCorso());
				
				
	}
	
	
}
