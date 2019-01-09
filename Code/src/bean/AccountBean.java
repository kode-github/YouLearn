package bean;

/***
 * 
 * Classe rappresentante le informazioni di un entità Account persistente
 * @author Mario Sessa
 * @version 1.0
 * @since 8/01/2019
 */

public class AccountBean {
	
	public enum Ruolo{Utente, Supervisore}; 
	
	private String nome;
	private String cognome;
	private String password;
	private String mail;
	private Ruolo tipo;
	private boolean verificato = false;
	
	
	/**
	 * Costruttore della classe generico, gli usi di tale costruttore sono molteplici. In genere viene utilizzato per prelevare informazioni dal database.
	 * @param String nome cognome password mail
	 * @param int tipo
	 * @param boolean verificato
	 * 
	 */
	
	public AccountBean(String nome, String cognome, String password, String mail, Ruolo tipo, boolean verificato) {
		this.nome = nome;
		this.cognome = cognome;
		this.password = password;
		this.mail = mail;
		this.tipo = tipo;
		this.verificato = verificato;
	}
	
	/**
	 * Costruttore della classe per utenti appena registrati, viene utilizzato questo metodo durante la fase di registrazione
	 * @param String nome cognome password mail 
	 * @param int tipo
	 * @param boolean verificato
	 * 
	 */
	
	public AccountBean(String nome, String cognome, String password, String mail, Ruolo tipo) {
		this.nome = nome;
		this.cognome = cognome;
		this.password = password;
		this.mail = mail;
		this.tipo = tipo;
	}
	
	
	/**
	 * Ritorna il nome dell'account
	 * @return String : nome
	 */
	
	public String getNome() {
		return nome;
	}

	/**
	 * Modifica il nome dell'account nel valore del parametro 
	 * @param String nuovoNome
	 */
	
	public void setNome(String nuovoNome) {
		this.nome = nuovoNome;
	}

	/**
	 * Ritorna il cognome dell'account
	 * @return String : cognome
	 */
	
	public String getCognome() {
		return cognome;
	}

	
	/**
	 * Modifica il cognome dell'account nel valore del parametro 
	 * @param String 
	 */
	
	
	public void setCognome(String nuovoCognome) {
		this.cognome = nuovoCognome;
	}

	/**
	 * Ritorna la password dell'account
	 * @return String : password
	 */
	
	public String getPassword() {
		return password;
	}

	/**
	 * Modifica la password dell'account nel valore del parametro 
	 * @param String  nuovaPassword
	 */
	
	public void setPassword(String nuovaPassword) {
		this.password = nuovaPassword;
	}

	/**
	 * Ritorna la mail dell'account
	 * @return String : mail
	 */
	
	public String getMail() {
		return mail;
	}

	
	/**
	 * Modifica la mail dell'account nel valore del parametro 
	 * @param String
	 */
	
	public void setMail(String nuovaMail) {
		this.mail = nuovaMail;
	}

	/**
	 * Ritorna il tipo del ruolo dell'account
	 * @return Ruolo : tipo
	 */
	
	public Ruolo getTipo() {
		return tipo;
	}
	
	/**
	 * Modifica tipo del Ruolo dell'account nel valore del parametro 
	 * @param String nuovoTipo
	 */
	
	public void setTipo(Ruolo nuovoTipo) {
		this.tipo = nuovoTipo;
	}

	/**
	 * Ritorna true o false a secondo del valore dell'attributo di verifica
	 * @return String : cognome
	 */
	
	public boolean isVerificato() {
		return verificato;
	}

	/**
	 * Modifica il valore della verifica dell'account, utilizzato per verificare l'email di un account
	 * @param bool
	 */
	
	public void setVerificato(boolean verificato) {
		this.verificato = verificato;
	}

	/**
	 *  Funzione di test remoto durante la fase di implementazione della classe, in seguito al test di unità verrà eliminato
	 */
	
	public static void remoteTest() {
		
		
		
	}

}

