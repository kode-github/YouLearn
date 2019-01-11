package bean;

import java.sql.Date;

/***
 * 
 * Classe rappresentante un entità CartaDiCredito persistente
 * @author Mario Sessa
 * @version 1.0
 * @since 8/01/2019
 */

public class CartaDiCreditoBean {
	
	public enum CartaEnum{PAYPAL, POSTEPAY, AMERICANEXPRESS};
	private String nomeIntestatario;
	private String numeroCarta;
	private Date meseScadenza;
	private Date annoScadenza;
	private CartaEnum tipo;
	
	/**
	 * Costruttore della classe generico, viene utilizzato per le operazioni con il database
	 * @param String nomeIntestatario numeroCarta meseScadenza annoScadenza accountMail
	 * @param CartaEnum tipo
	 * 
	 */
	
	public CartaDiCreditoBean(String nomeIntestatario, String numeroCarta, Date meseScadenza, Date annoScadenza, CartaEnum tipo) {
		this.nomeIntestatario = nomeIntestatario;
		this.numeroCarta = numeroCarta;
		this.meseScadenza = meseScadenza;
		this.annoScadenza = annoScadenza;
		this.tipo = tipo;
	}

	public CartaDiCreditoBean() {
		nomeIntestatario=numeroCarta="";
		tipo=null;
		meseScadenza=annoScadenza=null;
	}
	
	/**
	 * Ritorna il nome dell'intestatario
	 * @return String : nomeIntestatario
	 */
	
	public String getNomeIntestatario() {
		return nomeIntestatario;
	}

	/**
	 * Modifica il valore del nome dell'intestatario della carta
	 * @param String nomeIntestatario
	 */
	public void setNomeIntestatario(String nomeIntestatario) {
		this.nomeIntestatario = nomeIntestatario;
	}

	/**
	 * Ritorna il numero della carta
	 * @return String : numeroCarta
	 */
	
	public String getNumeroCarta() {
		return numeroCarta;
	}

	/**
	 * Modifica il numero della carta di credito
	 * @param String numeroCarta
	 */
	public void setNumeroCarta(String numeroCarta) {
		this.numeroCarta = numeroCarta;
	}

	/**
	 * Ritorna il mese di scadenza della carta
	 * @return String : meseScadenza
	 */
	
	public Date getMeseScadenza() {
		return meseScadenza;
	}

	public void setMeseScadenza(Date meseScadenza) {
		this.meseScadenza = meseScadenza;
	}

	/**
	 * Ritorna l'anno di scadenza della carta di credito
	 * @return String : annoScadenza
	 */
	
	public Date getAnnoScadenza() {
		return annoScadenza;
	}

	/**
	 * Imposta l'anno di scadenza della carta
	 * @param String annoScadenza
	 */
	public void setAnnoScadenza(Date annoScadenza) {
		this.annoScadenza = annoScadenza;
	}

	/**
	 * Ritorna il tipo di carta
	 * @return CartaEnum : nomeIntestatario
	 */
	
	public CartaEnum getTipo() {
		return tipo;
	}

	/**
	 * Imposta il tipo di carta di credito
	 * @param CartaEnum tipo
	 */
	public void setTipo(CartaEnum tipo) {
		this.tipo = tipo;
	}

	
	
	
}
