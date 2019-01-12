package bean;

import java.util.Collection;

/***
 * 
 * Classe rappresentante un entità CartaDiCredito persistente
 * @author Mario Sessa
 * @version 1.1
 * @since 11/01/2019
 */

public class CartaDiCreditoBean {
	
	public enum CartaEnum{PAYPAL, POSTEPAY, AMERICANEXPRESS};
	private String nomeIntestatario;
	private String numeroCarta;
	private String meseScadenza;
	private String annoScadenza;
	private CartaEnum tipo;
	private Collection<AccountBean> accountCollegati;
	
	/**
	 * Costruttore della classe generico, viene utilizzato per le operazioni con il database
	 * @param String nomeIntestatario numeroCarta meseScadenza annoScadenza accountMail
	 * @param CartaEnum tipo
	 * @param Collection<AccountBean> accountCollegati
	 * 
	 */
	
	public CartaDiCreditoBean(String nomeIntestatario, String numeroCarta, String meseScadenza, String annoScadenza, CartaEnum tipo, Collection<AccountBean> accountCollegati) {
		this.nomeIntestatario = nomeIntestatario;
		this.numeroCarta = numeroCarta;
		this.meseScadenza = meseScadenza;
		this.annoScadenza = annoScadenza;
		this.tipo = tipo;
		this.accountCollegati = accountCollegati;
	}
	
	public CartaDiCreditoBean() {
		nomeIntestatario=numeroCarta=meseScadenza=annoScadenza="";
		accountCollegati=null;
		tipo=null;
	}

	/**
	 * Restituisce la collezione di account che utilizzano la carta di credito
	 * @return Collection<AccountBean> : accountCollegati
	 */
	
	public Collection<AccountBean> getAccountCollegati() {
		return accountCollegati;
	}

	/**
	 * Sostituisce la collezione di account che utilizzano la carta di credito
	 * @param Collection<AccountBean> accountCollegati
	 */
	
	public void setAccountCollegati(Collection<AccountBean> accountCollegati) {
		this.accountCollegati = accountCollegati;
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
	
	public String getMeseScadenza() {
		return meseScadenza;
	}

	public void setMeseScadenza(String meseScadenza) {
		this.meseScadenza = meseScadenza;
	}

	/**
	 * Ritorna l'anno di scadenza della carta di credito
	 * @return String : annoScadenza
	 */
	
	public String getAnnoScadenza() {
		return annoScadenza;
	}

	/**
	 * Imposta l'anno di scadenza della carta
	 * @param String annoScadenza
	 */
	public void setAnnoScadenza(String annoScadenza) {
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
