package bean;

/**
 * 
 * Classe identificante un entità Commento persistente
 * @author Mario Sessa
 * @version 1.0
 * @since 09/01/2019
 */

public class CommentoBean {

	private int lezioneIdLezione;
	private int idCommento;
	private String testo;
	private String accountMail;
	
	/**
	 * Costruttore generico degli oggetti di tipo commento
	 * @param int lezioneIdLezione idCommento
	 * @param String testo accountMail
	 * 
	 */
	public CommentoBean(int lezioneIdLezione, int idCommento, String testo, String accountMail) {
		this.lezioneIdLezione = lezioneIdLezione;
		this.idCommento = idCommento;
		this.testo = testo;
		this.accountMail = accountMail;
	}

	/**
	 * Preleva l'id della lezione a cui è collegato il commento
	 * @return int : lezioneIdLezione
	 */
	
	public int getLezioneIdLezione() {
		return lezioneIdLezione;
	}

	/**
	 * Modifica l'id della lezione a cui è collegato il commento secondo il parametro collegato
	 * @param int lezioneIdLezione
	 */
	
	public void setLezioneIdLezione(int lezioneIdLezione) {
		this.lezioneIdLezione = lezioneIdLezione;
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
	 * Ritorna la mail dell'account collegata al creatore del commento
	 * @return String : accountMail
	 */
	
	public String getAccountMail() {
		return accountMail;
	}

	/**
	 * Modifica la mail dell'account del creatore del commento
	 * @param String accountMail
	 */
	
	public void setAccountMail(String accountMail) {
		this.accountMail = accountMail;
	}
	
	
	
	
	
	
}
