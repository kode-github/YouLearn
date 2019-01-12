package bean;


/**
 * 
 * Classe identificante un entità Commento persistente
 * @author Mario Sessa
 * @version 1.1
 * @since 11/01/2019
 */

public class CommentoBean {

	private CorsoBean corso;
	private int idCommento;
	private String testo;
	private AccountBean accountCreatore;
	private LezioneBean lezione;
	
	/**
	 * Costruttore generico degli oggetti di tipo commento
	 * @param int idCommento
	 * @param String testo
	 * @param AccountBean accountCreatore
	 * @param CorsoBean corso
	 * @param LezioneBean lezione
	 * 
	 */
	public CommentoBean(LezioneBean lezione, int idCommento, String testo, AccountBean accountCreatore, CorsoBean corso) {
		this.lezione = lezione;
		this.idCommento = idCommento;
		this.testo = testo;
		this.accountCreatore = accountCreatore;
		this.corso = corso;
	}

	public CommentoBean() {
		lezione=null;
		corso=null;
		idCommento=0;
		testo="";
		
		
		
	}
	
	/**
	 * Preleva l'id della lezione a cui è collegato il commento
	 * @return int : lezioneIdLezione
	 */
	
	public CorsoBean getCorso() {
		return corso;
	}

	/**
	 * Modifica l'id del corso collegata alla lezione in cui c'è il commento
	 * @param int corsoIdCorso
	 */
	public void setCorso(CorsoBean corso) {
		this.corso = corso;
	}
	

	/**
	 * Preleva l'id del commento
	 * @return int : idCommento
	 */
	
	public int getIdCommento() {
		return idCommento;
	}

	/**
	 * Modifica l'id del commento secondo il valore del parametro
	 * @param idCommento
	 */
	
	public void setIdCommento(int idCommento) {
		this.idCommento = idCommento;
	}

	/**
	 * Preleva il testo del commento
	 * @return String : Testo
	 */
	
	public String getTesto() {
		return testo;
	}
	
	/**
	 * Modifica il testo del commento secondo il valore del parametro
	 * @param String testo
	 */
	
	public void setTesto(String testo) {
		this.testo = testo;
	}

	/**
	 * Restituisce la lezione del commento affiliato
	 * @return LezioneBean : lezione
	 */
	public LezioneBean getLezione() {
		return lezione;
	}
	
	/**
	 * Setta la lezione a cui appartiene il commento
	 * @param LezioneBean lezione
	 */

	public void setLezione(LezioneBean lezione) {
		this.lezione = lezione;
	}

	/**
	 * Ritorna l'account collegata al creatore del commento
	 * @return AccountBean : accountCreatore
	 */
	
	public AccountBean getAccountCreatore() {
		return accountCreatore;
	}

	/**
	 * Modifica l'account creatore del commento
	 * @param AccountBean accountCreatore
	 */
	
	public void setAccountCreatore(AccountBean accountCreatore) {
		this.accountCreatore = accountCreatore;
	}
	
	
	
	
	
	
}
