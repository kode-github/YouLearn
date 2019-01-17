package bean;


/**
 * 
 * Classe identificante un entità Commento persistente
 * @author Mario Sessa
 * @version 1.1
 * @since 11/01/2019
 */

public class CommentoBean {

	private Integer idCommento;
	private String testo;
	private AccountBean accountCreatore;
	private LezioneBean lezione;
	
	/**
	 * Costruttore generico degli oggetti di tipo commento
	 * @param LezioneBean lezione
	 * @param AccountBean accountCreatore
	 * 
	 */
	public CommentoBean(Integer id,String t,LezioneBean lezione, AccountBean accountCreatore) {
		idCommento=id;
		testo=t;
		setLezione(lezione);
		setAccountCreatore(accountCreatore);
	}

	public CommentoBean() {}

	
	/**
	 * Preleva l'id del commento
	 * @return int : idCommento
	 */
	
	public Integer getIdCommento() {
		return idCommento;
	}

	/**
	 * Modifica l'id del commento secondo il valore del parametro
	 * @param idCommento
	 */
	
	public void setIdCommento(Integer idCommento) {
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
	 * Preleva la lezione lezione a cui è collegato il commento
	 * @return int : lezioneIdLezione
	 */
	
	public LezioneBean getLezione() {
		return this.lezione;
	}
	
	/**
	 * Setta la lezione a cui appartiene il commento
	 * @param LezioneBean lezione
	 */

	public void setLezione(LezioneBean lezione) {
		if(this.lezione != lezione) {
			LezioneBean old = this.lezione;
			this.lezione = lezione;
			if(this.lezione != null) {
				this.lezione.addCommento(this);
			}
			if(old != null) {
				old.removeCommento(this);
			}
		}
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
		if(this.accountCreatore != accountCreatore) {
		    AccountBean old = this.accountCreatore;
			this.accountCreatore = accountCreatore;
			if(this.accountCreatore != null) {
				this.accountCreatore.addCommentoScritto(this);
			}
			if(old != null) {
				old.removeCommento(this);
			}
		}
	}
	
	
	
	
	
	
}
