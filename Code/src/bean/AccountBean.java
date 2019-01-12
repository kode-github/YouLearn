package bean;

import java.util.Collection;

/***
 * 
 * Classe rappresentante le informazioni di un entità Account persistente
 * @author Mario Sessa
 * @version 1.1
 * @since 11/01/2019
 *
 */

public class AccountBean {
	
	public enum Ruolo{Utente, Supervisore}; 
	private String nome;
	private String cognome;
	private String password;
	private String mail;
	private Ruolo tipo;
	private boolean verificato = false;
	private CartaDiCreditoBean carta;
	private Collection<CorsoBean> corsiTenuti;
	private Collection<CorsoBean> corsiDaSupervisionare;
	private Collection<PagamentoBean> acquisti;
	private Collection<CommentoBean> commentiScritti;
	
	/**
	 * Costruttore della classe generico, gli usi di tale costruttore sono molteplici. In genere viene utilizzato per prelevare informazioni dal database.
	 * @param String nome cognome password mail
	 * @param int tipo
	 * @param boolean verificato
	 * @param Collection<CorsoBean> corsiTenuti corsiDaSupervisionare
	 * @param Collection<PagamentoBean> acquisti
	 * @param Collection<CommentoBean> commentiScritti;
	 * 
	 */
	
	public AccountBean(String nome, String cognome, String password, String mail, Ruolo tipo, boolean verificato,
			CartaDiCreditoBean carta, Collection<CorsoBean> corsiTenuti, Collection<CorsoBean> corsiDaSupervisionare,
			Collection<PagamentoBean> acquisti, Collection<CommentoBean> commentiScritti) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.password = password;
		this.mail = mail;
		this.tipo = tipo;
		this.verificato = verificato;
		this.carta = carta;
		this.corsiTenuti = corsiTenuti;
		this.corsiDaSupervisionare = corsiDaSupervisionare;
		this.acquisti = acquisti;
		this.commentiScritti = commentiScritti;
	}

	
	
	public AccountBean() {
		nome=cognome=password=mail="";
		verificato=false;
		corsiTenuti=corsiDaSupervisionare=null;
		acquisti=null;
		commentiScritti=null;
	}


	/**
	 * Restituisce la collezione di corsi tenuti dall'account
	 * @return Collection<CorsoBean> : corsiTenuti
	 */
	public Collection<CorsoBean> getCorsiTenuti() {
		return corsiTenuti;
	}


	/**
	 * Sostituzione della collezione di corsi tenuti dall'account
	 * @param Collection<CorsoBean> corsiTenuti
	 */


	public void setCorsiTenuti(Collection<CorsoBean> corsiTenuti) {
		this.corsiTenuti = corsiTenuti;
	}



	/**
	 * Preleva la collezione di corsi supervisionati dall'account
	 * @return Collection<CorsoBean> corsiDaSupervisionare
	 */

	public Collection<CorsoBean> getCorsiDaSupervisionare() {
		return corsiDaSupervisionare;
	}


	/**
	 * Sostituisce della collezione di corsi da supervisionare dall'account
	 * @param Collection<CorsoBean> corsiDaSupervisionare
	 */


	public void setCorsiDaSupervisionare(Collection<CorsoBean> corsiDaSupervisionare) {
		this.corsiDaSupervisionare = corsiDaSupervisionare;
	}


	/**
	 * Restituisce la collezione dei pagamenti per l'iscrizione ad un corso dell'account
	 * @return Collection<PagamentoBean> : acquisti
	 */


	public Collection<PagamentoBean> getAcquisti() {
		return acquisti;
	}


	/**
	 * Sostituisce la collezione degli pagamenti per l'iscrizione ad un corso da parte dell'account
	 * @param acquisti
	 */


	public void setAcquisti(Collection<PagamentoBean> acquisti) {
		this.acquisti = acquisti;
	}


	/**
	 * Restituisce la collezione dei commenti scritti dall'account
	 * @return Collection<CommentoBean> : commentiScritti
	 */


	public Collection<CommentoBean> getCommentiScritti() {
		return commentiScritti;
	}

	/**
	 * Sostituisce la collezione dei commenti scritti dall'account
	 * @param Collection<CommentoBean> commentiScritti
	 */



	public void setCommentiScritti(Collection<CommentoBean> commentiScritti) {
		this.commentiScritti = commentiScritti;
	}



	/**
	 * Sostituisce il parametro legato alla verifica dell'email dell'account
	 * @param verificato
	 */

	public void setVerificato(boolean verificato) {
		this.verificato = verificato;
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
	 * Ritorna 0 o 1 a secondo del valore dell'attributo di verifica
	 * @return String : cognome
	 */
	
	public boolean getVerificato() {
		return verificato;
	}

	/**
	 * Modifica il valore della verifica dell'account, utilizzato per verificare l'email di un account
	 * @param bool
	 */
	
	public void isVerificato(boolean verificato) {
		this.verificato = verificato;
	}

	/**
	 * Restituisce il numero della carta di credito legata all'account
	 * @return CartaDiCreditoBean : carta
	 */
	
	public CartaDiCreditoBean getCarta() {
		return this.carta;
	}
	
	/**
	 * Modifica il numero di carta associata all'account
	 * @param CartaDiCreditoBean numeroCarta
	 */
	
	public void setCarta(CartaDiCreditoBean carta) {
		this.carta= carta;
	}
}

