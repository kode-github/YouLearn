package bean;

import java.sql.Date;

/***
 * 
 * Classe rappresentante un entità CartaDiCredito persistente
 * @author Mario Sessa
 * @version 1.1
 * @since 11/01/2019
 */

public class CartaDiCreditoBean {
	
	public enum CartaEnum{PAYPAL, POSTEPAY, AMERICANEXPRESS, VISA};
	private String nomeIntestatario;
	private String numeroCarta;
	private String meseScadenza;
	private String annoScadenza;
	private CartaEnum tipo;
	private AccountBean account;
	
	/**
	 * Costruttore della classe generico, viene utilizzato per le operazioni con il database
	 * @param AccountBean accountCollegati
	 * 
	 */
	
	public CartaDiCreditoBean(AccountBean accountCollegato) {
		setAccount(accountCollegato);
	}
	
	public CartaDiCreditoBean() {};


	public CartaDiCreditoBean(String numeroCarta, String meseScadenza, String annoScadenza, String nomeIntestatario, CartaEnum tipo,
			AccountBean account) {
		this.numeroCarta = numeroCarta;
		this.meseScadenza = meseScadenza;
		this.annoScadenza = annoScadenza;
		this.nomeIntestatario = nomeIntestatario;
		this.account = account;
		this.tipo = tipo;
		
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
	 * @return Date : meseScadenza
	 */
	
	public String getMeseScadenza() {
		return meseScadenza;
	}

	/**
	 * Modifica il mese di scadenza della carta nel valore del parametro
	 * @
	 */
	public void setMeseScadenza(String meseScadenza) {
		this.meseScadenza = meseScadenza;
	}

	/**
	 * Ritorna l'anno di scadenza della carta di credito
	 * @return Date : annoScadenza
	 */
	
	public String getAnnoScadenza() {
		return annoScadenza;
	}

	/**
	 * Imposta l'anno di scadenza della carta
	 * @param Date annoScadenza
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

	
	/**
	 * Restituisce l'account che utilizza la carta di credito
	 * @return AccountBean : accountCollegati
	 */
	
	public AccountBean getAccount() {
		return account;
	}

	/**
	 * Imposta l'account collegato alla carta di credito
	 * @param AccountBean accountCollegati
	 */
	
	public void setAccount(AccountBean accountCollegato) { 
		if(this.account != accountCollegato) {
			
		AccountBean old = this.account;
		this.account = accountCollegato;
		
		if(this.account != null) {
		   this.account.setCarta(this);
		}
		
		if(old != null) {
			old.setCarta(null);
		}
		
		}
	}
	
}
