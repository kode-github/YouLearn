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
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import javax.naming.NoPermissionException;
import javax.servlet.http.Part;

import com.mysql.jdbc.CallableStatement;

import bean.CommentoBean;
import bean.CorsoBean;
import bean.LezioneBean;
import connection.DriverManagerConnectionPool;
import exception.DatiErratiException;
import exception.NotFoundException;
import exception.NotWellFormattedException;

public class LezioneManager {
	
	private static LezioneManager istanza;
	private AccountManager accountManager;
	private CorsoManager corsoManager;
	
	private LezioneManager() { }
	
	public static LezioneManager getIstanza() {
		if(istanza==null)
			istanza=new LezioneManager();
		return istanza;
	}


	/**
	 * Modifica l'ordine delle lezioni di un corso
	 * Per ordinare vedi algoritmo su quaderno
	 * @param lezioni Collezione di lezioni col numero lezione da aggiornare
	 * @param corso a cui appartengono le lezioni
	 * @throws SQLException 
	 * @throws DatiErratiException 
	 * @throws NotWellFormattedException 
	 * @throws NotFoundException 
	 * @throws NoPermissionException 
	 */
	public synchronized void modificaOrdine(int corso,String coppie) throws SQLException, DatiErratiException, NoPermissionException, NotFoundException, NotWellFormattedException {
		corsoManager=CorsoManager.getIstanza();
		//Recupera le lezioni di un corso
		LinkedList<LezioneBean> lezione=(LinkedList<LezioneBean>)corsoManager.doRetrieveByKey(corso).getLezioni();
		
		//Controllo la coerenza della lista
		if(coppie==null || !coppie.matches("^([0-9]+[-][0-9]+[,])*([0-9]+[-][0-9]+)$")) throw new DatiErratiException("dati errati");
    	String[] couple=coppie.split(",");
    	ArrayList<Integer> numeriLezione=new ArrayList<>();
    	
    	//creo un mapping tra le lezioni e i loro nuovi numeri lezione
    	HashMap<Integer, Integer> map=new HashMap<Integer, Integer>();
    	for(String s: couple) {
    		//Controllo di non avere lezioni e numeriLezione duplicati
    		if(map.put(Integer.parseInt(s.substring(0, s.indexOf('-'))), Integer.parseInt(s.substring(s.indexOf('-')+1, s.length())))!=null)
    			throw new DatiErratiException("valori duplicati");
    		if(numeriLezione.contains(Integer.parseInt(s.substring(s.indexOf('-')+1, s.length())))) throw new DatiErratiException("Numeri lezione duplicati");
    		numeriLezione.add(Integer.parseInt(s.substring(s.indexOf('-')+1, s.length())));
    	}
    	//Controllo che i numeri lezione siano consecutivi e partano da 1
    	numeriLezione.sort(new Comparator<Integer>() {
    		@Override
    		public synchronized int compare(Integer o1, Integer o2) {
    			if(o1>o2)
    				return 1;
    			if(o1<o2) 
    				return -1;
    			return 0;
    		}
		});
    	
    	//STAMPIAMO I NUMERI LEZIONE
    	for(int i=0;i<lezione.size();i++)
    		System.out.println("idLezione: "+lezione.get(i).getIdLezione()+" nuovo numero: "+map.get(lezione.get(i).getIdLezione())+"\n");
    	
    	for(int i=0;i<numeriLezione.size();i++) {
    		if(numeriLezione.get(i)!=i+1) throw new DatiErratiException("I numeri lezione non sono consecutivi");
    	}
    	//controllo che ci siano numeri per ogni lezione effettiva e lo associo
    	int i;
    	for(i=0;i<lezione.size();i++) {
    		if(map.containsKey(lezione.get(i).getIdLezione()))
    			lezione.get(i).setNumeroLezione(map.get(lezione.get(i).getIdLezione()));
    		else throw new DatiErratiException("Esiste una lezione non coperta");
    	}
		
    	//Adesso ho un insieme di lezioni relative allo stesso corso con numeri incrementali
		Connection c=null;
		try {
			c=DriverManagerConnectionPool.getConnection();
			c.setAutoCommit(false);
			for(LezioneBean l: lezione) 
				changeNumeroLezione(l, c);
			if(!c.getAutoCommit())
				c.commit();
			System.out.println("Sono dopo il commit");
		}catch(SQLException | DatiErratiException  e) {
			c.rollback();
			e.printStackTrace();
		}finally {
			DriverManagerConnectionPool.releaseConnection(c);
		}
		
	}

	
	
	/**
	 * Cambia il numero di lezione
	 * @throws SQLException
	 * @throws DatiErratiException 
	 */
	private void changeNumeroLezione(LezioneBean lezione,Connection c) throws SQLException, DatiErratiException {
		PreparedStatement statement=null;
		String sql="Update Lezione set numeroLezione=? where idLezione=?";
		try {
			statement=c.prepareStatement(sql);
			statement.setInt(1, lezione.getNumeroLezione());
			statement.setInt(2, lezione.getIdLezione());
			System.out.println("Modifica numero lezione: "+statement.toString());
			int res=statement.executeUpdate();
			if(res==0) throw new DatiErratiException("non esiste una lezione con questo codice oppure non appartiene a questo corso");
		}finally {
				if(statement!=null)
					statement.close();
		}
	}
	
	/**
	 * Inserisce una serie di lezioni nel database
	 * In caso di errore nell'inserimento di una lezione, l'operazione corrente viene annullata ma gli inserimenti 
	 * precedenti non vengono annullati
	 * @param lezioni
	 * @param files
	 * @throws DatiErratiException
	 * @throws NotWellFormattedException
	 * @throws SQLException
	 * @throws IOException
	 */
//	public synchronized void insLezioniMultiple(ArrayList<LezioneBean> lezioni,ArrayList<Part> files) throws DatiErratiException, NotWellFormattedException, SQLException, IOException {
//		if(lezioni==null || files==null || lezioni.size()!=files.size())
//									throw new DatiErratiException("Non c'� un file per ogni lezione");
//		/**Inizio le modifiche */
//		corsoManager= CorsoManager.getIstanza();
//		int i=0;
//		Connection c=DriverManagerConnectionPool.getConnection();
//		c.setAutoCommit(false);
//		for(Part file: files) {
//			insLezione(lezioni.get(i++),file,c);
//		}
//		DriverManagerConnectionPool.releaseConnection(c);
//	}
	
	/**
	 * Inserisce una lezione nel database e la salva in un file
	 * @param lezione la lezione da inserire
	 * @param file il file della lezione
	 * @param c la connessione al db: necessaria per evitare che ne venga creata una per ogni inserimento
	 * @throws NotWellFormattedException la lezione non � ben formattata 
	 * @throws SQLException Errore nella connessione al db o nell'inserimento
	 * @throws DatiErratiException la lezione esiste gi� o non � collegata al corso giusto
	 * @throws IOException Errore nella scrittura del file su disco
	 */
	 public synchronized void insLezione(LezioneBean lezione,Part file) throws NotWellFormattedException, SQLException, DatiErratiException, IOException {
		corsoManager=CorsoManager.getIstanza();
		 if(lezione.getIdLezione()!=null || lezione.getCorso().getIdCorso()==null ||
									!corsoManager.checkCorso(lezione.getCorso().getIdCorso())) 
			throw new DatiErratiException("la lezione esiste gi� o il corso non esiste");
		if(!lezioneIsWellFormatted(lezione) )  
										throw new NotWellFormattedException("la lezione non � ben formattata");
		
		PreparedStatement statement=null;
		Connection c = null;
		String sql="Insert into Lezione (nome,filepath,corsoIdCorso) values (?,?,?)";
		try{
			c=DriverManagerConnectionPool.getConnection();
			c.setAutoCommit(false);
			//
			Path path=Paths.get("C:\\Users\\Antonio\\Documents\\Universita\\IS\\Progetto\\"
					+ "YouLearn\\Code\\WebContent\\Resources\\"+lezione.getCorso().getIdCorso()+"\\Lezioni");
			if(!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
					Files.createDirectories(path); 
			String filename=UUID.randomUUID().toString();
			String type=file.getSubmittedFileName().substring(file.getSubmittedFileName().lastIndexOf('.'));
			System.out.println("Estensione: "+type+"\n");
			if(!type.equals(".mp4")) throw new DatiErratiException("Il tipo del file non � .mp4");
			path=Paths.get("C:\\Users\\Antonio\\Documents\\Universita\\IS\\Progetto\\"
					+ "YouLearn\\Code\\WebContent\\Resources\\"+lezione.getCorso().getIdCorso()+"\\Lezioni"+File.separator+
																				filename+type);
			statement=c.prepareStatement(sql);
			statement.setString(1, lezione.getNome());
			statement.setString(2, filename+type);
			statement.setInt(3, lezione.getCorso().getIdCorso());
			statement.executeUpdate(); //inserisce nel db
			
			file.write(path.toString()); //Scrivo il file sul disco
			
			if(!c.getAutoCommit())
				c.commit(); //conferma l'inserimento nel db
		}catch(SQLException | IOException e) {
			e.printStackTrace();
			//C'� stato un errore nel salvataggio del file o nell'inserimento
			//In ogni caso la scrittura nel db viene eliminata
			c.rollback();
		}finally {
			try{
				if(statement!=null)
					statement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(c);
			}	
		}
	}
	 
	    
	
	/**
	 * Eimina una lezione dal Db e dal FileSystem 
	 * @param lezione
	 * @throws SQLException 
	 * @throws DatiErratiException 
	 * @throws NotFoundException 
	 */
	public synchronized void delLezione(LezioneBean lezione) throws SQLException, DatiErratiException, NotFoundException {
		if(!checkLezione(lezione.getIdLezione())) throw new NotFoundException("La lezione non esiste");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		java.sql.CallableStatement callableStatement;

		String deleteSQL = "DELETE FROM lezione WHERE idLezione=?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, lezione.getIdLezione());
			System.out.println("doDelete: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			//Sistemo le lezioni
			callableStatement=connection.prepareCall("{ call adjustLezioni }");
			callableStatement.execute();
			//Elimino il file dal disco
			Path path=Paths.get("C:\\Users\\Antonio\\Documents\\Universita\\IS\\Progetto\\"
					+ "YouLearn\\Code\\WebContent\\Resources\\"+lezione.getCorso().getIdCorso()
					+"\\Lezioni\\"+lezione.getFilePath());
			Files.delete(path);
			
			if(!connection.getAutoCommit())
				connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (IOException e) {
			//Il file non esiste
			connection.commit();
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	private boolean checkLezione(int idLezione) throws SQLException {
		Connection c=null;
		PreparedStatement statement=null;
		String sql="Select * from lezione where idLezione=?";
		try {
			c=DriverManagerConnectionPool.getConnection();
			statement=c.prepareStatement(sql);
			statement.setInt(1, idLezione);
			
			return (statement.executeQuery()).next();
		}finally {
			try{
				if(statement!=null)
					statement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(c);
			}	
		}
	}

	public synchronized boolean checkLezione(LezioneBean lezione) throws SQLException, DatiErratiException {
		if(lezione==null || lezione.getIdLezione()==null || !lezioneIsWellFormatted(lezione) ) 
											throw new DatiErratiException("La lezione non � corretta");
		
		Connection c=null;
		PreparedStatement statement=null;
		String sql="Select * from lezione where idLezione=? AND numeroLezione=? AND nome=? AND visualizzazione=? AND filepath=? "
																					+ "AND corsoIdCorso=?";
		try {
			c=DriverManagerConnectionPool.getConnection();
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
				DriverManagerConnectionPool.releaseConnection(c);
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
	public synchronized Collection<LezioneBean> retrieveLezioniByCorso(CorsoBean corso) throws NotWellFormattedException, SQLException{
		corsoManager=CorsoManager.getIstanza();
		if(corso.getIdCorso()==null || !corsoManager.isWellFormatted(corso)) 
								throw new NotWellFormattedException("Il corso non � ben formattato");
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<LezioneBean> collection=new LinkedList<LezioneBean>();
		String sql="Select * from lezione where corsoIdCorso=?";
		
		try {
			connection=DriverManagerConnectionPool.getConnection();
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
				DriverManagerConnectionPool.releaseConnection(connection);
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
	public synchronized CommentoBean retrieveCommentoById(int id) throws SQLException, NotFoundException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		CommentoBean temp=new CommentoBean();
		
		String sql="SELECT* FROM commento WHERE idcommento=?";		
		try {
			connection=DriverManagerConnectionPool.getConnection();
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
				DriverManagerConnectionPool.releaseConnection(connection);
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
	public synchronized boolean delCommento(int code) throws Exception {
		if(!checkCommento(code)) throw new NotFoundException("Questo commento non esiste");
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM commento WHERE id = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, code);

			System.out.println("doDelete: "+ preparedStatement.toString());
			result = preparedStatement.executeUpdate();
			if(!connection.getAutoCommit())
				connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
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
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setInt(1, code);
			System.out.println("Query: " + preparedStatement.toString());
			return (preparedStatement.executeQuery()).next();
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}

	/**
	 * Inserisce un nuovo commento nel database
	 * @param product
	 * @throws SQLException 
	 * @throws Exception
	 */
	public synchronized void insCommento(CommentoBean product) throws NotWellFormattedException, SQLException {
		if(product.getIdCommento()!=null || !commentoIsWellFormatted(product)) throw new NotWellFormattedException("Il commento non"
																					+ "� ben formattato");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String sql="INSERT INTO commento VALUES(?,?,?,?)";
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, product.getIdCommento());
			preparedStatement.setInt(2, product.getLezione().getIdLezione());
			preparedStatement.setString(3, product.getTesto());
			preparedStatement.setString(4, product.getAccountCreatore().getMail());
			System.out.println("Inserisci commento: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			if(!connection.getAutoCommit())
				connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
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
	public synchronized Collection<CommentoBean> retrieveCommentiByLezione(LezioneBean lezione) throws SQLException,NotFoundException, NotWellFormattedException {
		accountManager=AccountManager.getIstanza();
		if(!lezioneIsWellFormatted(lezione) ) throw new NotWellFormattedException("La lezione non � ben formattata");
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<CommentoBean> list= new LinkedList<>();
		
		String sql="SELECT* FROM commento WHERE idLezione=?";		
		try {
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement= connection.prepareStatement(sql);
			System.out.println(lezione.getIdLezione());
			preparedStatement.setInt(1, lezione.getIdLezione());
			System.out.println("retrieveCommentiByLezione: " + preparedStatement.toString());
			
			ResultSet rs= preparedStatement.executeQuery();
			
			while(rs.next()) {
				CommentoBean temp= new CommentoBean();
				temp.setIdCommento(rs.getInt("idCommento"));
				temp.setTesto(rs.getString("Testo"));
				temp.setLezione(lezione);
				temp.setAccountCreatore(accountManager.doRetrieveByKey(rs.getString("accountMail")));
				list.add(temp);
			}
		}finally {
			try {
			if(preparedStatement!=null)
				preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return list;
	}

	/**
	 * Controlla se un commento � ben formattato
	 * Da notare che un commento � considerato valido anche se l'id � null (in caso di inserimento in quanto AutoIncrement)
	 * @param commento
	 * @return
	 */
	public synchronized boolean commentoIsWellFormatted(CommentoBean commento) {
		accountManager=AccountManager.getIstanza();
		//idcommento, testo, accountCreatore, lezione
		return commento.getTesto()!=null && commento.getTesto().matches("^[a-zA-Z0-9]{1,1024}") &&
				commento.getAccountCreatore()!=null && accountManager.isWellFormatted(commento.getAccountCreatore()) &&
				commento.getLezione()!=null && lezioneIsWellFormatted(commento.getLezione());
		
	}
	
	/**
	 * Controlla se una lezione � ben formattata
	 * Da notare che una lezione � considerata valida anche se l'id � null (in caso di inserimento in quanto AutoIncrement)
	 * @param lezione
	 * @return
	 */
	public synchronized boolean lezioneIsWellFormatted(LezioneBean lezione) {
		corsoManager=CorsoManager.getIstanza();
		if(lezione.getIdLezione()!=null)
			if(lezione.getFilePath()==null || !lezione.getFilePath().matches("^[a-zA-Z0-9\\.-]{10,2048}") || lezione.getNumeroLezione()<=0 
			|| lezione.getVisualizzazioni()<0)
				return false;
		return lezione.getNome()!=null && lezione.getNome().matches("^[a-zA-Z0-9\\s:-]{5,40}") &&
				  lezione.getCorso()!=null;
				
				
	}

	/**
	 * Modifica il nome e, opzionalmente, la path del nuovo file della lezione salvandolo sul disco
	 * @param lezione la lezione col nome modificato
	 * @param part il file da inserire (nullable)
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws NotWellFormattedException
	 * @throws IOException
	 */
	public synchronized void modificaLezione(LezioneBean lezione, Part part) throws SQLException, NotFoundException, NotWellFormattedException, IOException {
		if(!checkLezione(lezione.getIdLezione())) throw new NotFoundException("La lezione non esiste");
		if(!lezioneIsWellFormatted(lezione)) throw new NotWellFormattedException("La lezione non � ben formattata");
		
		PreparedStatement statement=null;
		Connection c = null;
		String filename = null,type=null;
		String sql="Update Lezione set nome=?  where idLezione=?";
		if(part!=null) {
			filename=UUID.randomUUID().toString();
			type=part.getSubmittedFileName().substring(part.getSubmittedFileName().indexOf('.'));
			if(!type.equals(".mp4")) throw new IllegalArgumentException("La lezione non ha il formato sperato");
			sql="Update Lezione set nome=?, filepath=? where idLezione=?"; //Modifico la stringa
		}
		
		try {
			c=DriverManagerConnectionPool.getConnection();
			c.setAutoCommit(false);
			statement=c.prepareStatement(sql);
			statement.setString(1, lezione.getNome());
			if(part!=null)
				statement.setString(2, filename+type);
			statement.executeUpdate();
		
			//salvo il nuovo file sul disco
			if(part!=null) {
				Path path=Paths.get("C:\\Users\\Antonio\\Documents\\Universita\\IS\\Progetto\\"
					+ "YouLearn\\Code\\WebContent\\\\Resources\\"+lezione.getCorso().getIdCorso()+"\\Lezioni"+File.separator+
																				filename+type);
				lezione.setFilePath(path.toString());
				part.write(path.toString());
			}
			if(!c.getAutoCommit())
				c.commit();
		}catch (SQLException e) {
			c.rollback();
		}
		finally {
				try{
					if(statement!=null)
						statement.close();
				}finally {
					DriverManagerConnectionPool.releaseConnection(c);
				}		
		}
		
	}
	
	
}
