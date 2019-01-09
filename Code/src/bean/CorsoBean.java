package bean;

/**
 * 
 * Classe identificante una classe Corso persistente
 * @author Mario Sessa
 * @vesione 1.0
 * @since 09/01/2019
 */
public class CorsoBean {

	private String nome;
	private String descrizione;
	private String dataCreazione;
	private String dataFine;
	private int nIscritti;
	private String copertina;
	private int idCorso;
	private String accountCreatore;
	private String accountSupervisore;
	private String categoria;
	
	/**
	 * Costruttore generico del corso
	 * @param String nome descrizione dataCreazione dataFine copertina accountCreatore accountSupervisore categoria
	 * @param int nIscritti idCorso
	 * 
	 */
	public CorsoBean(String nome, String descrizione, String dataCreazione, String dataFine, int nIscritti,
			String copertina, int idCorso, String accountCreatore, String accountSupervisore, String categoria) {
		super();
		this.nome = nome;
		this.descrizione = descrizione;
		this.dataCreazione = dataCreazione;
		this.dataFine = dataFine;
		this.nIscritti = nIscritti;
		this.copertina = copertina;
		this.idCorso = idCorso;
		this.accountCreatore = accountCreatore;
		this.accountSupervisore = accountSupervisore;
		this.categoria = categoria;
	}

	
	/**
	 * Preleva il valore del nome del corso.
	 * @return String : nome
	 */
	
	public String getNome() {
		return nome;
	}

	
	/**
	 * Modifica il valore del nome del corso con il valore del parametro
	 * @param String nome
	 */
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Ritorna la descrizione del corso
	 * @return String : descrizione
	 */
	
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * Modifica la descrizione del corso
	 * @param String descrizione
	 */
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 *  Preleva la data di creazione del corso
	 * @return String : dataCreazione
	 */
	
	public String getDataCreazione() {
		return dataCreazione;
	}

	/**
	 * Modifica la data di creazione del corso
	 * @param String dataCreazione
	 */
	
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	/**
	 * Preleva la data di fine del corso 
	 * @return String : dataFine
	 */
	
	public String getDataFine() {
		return dataFine;
	}

	/**
	 * Modifica la data di fine del corso
	 * @param String dataFine
	 */
	
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}

	/**
	 * Preleva il numero di iscritti del corso
	 * @return int : nIscritti
	 */
	
	public int getnIscritti() {
		return nIscritti;
	}

	
	/**
	 * Modifica il numero di iscritti di un corso 
	 * @param int nIscritti
	 */
	
	public void setnIscritti(int nIscritti) {
		this.nIscritti = nIscritti;
	}

	/**
	 * Preleva l'url dell'immagine di copertina del corso 
	 * @return String : copertina
	 */
	
	public String getCopertina() {
		return copertina;
	}

	
	/**
	 * Modifica l'url della copertina del corso 
	 * @param copertina
	 */
	
	public void setCopertina(String copertina) {
		this.copertina = copertina;
	}

	/**
	 * Preleva l'id del corso 
	 * @return int : idCorso
	 */
	
	public int getIdCorso() {
		return idCorso;
	}

	/**
	 * Modifica l'id del corso
	 * @param int idCorso
	 */
	
	public void setIdCorso(int idCorso) {
		this.idCorso = idCorso;
	}

	
	/**
	 * Preleva l'email dell'account del creatore del corso 
	 * @return String : accountCreatore
	 */
	
	public String getAccountCreatore() {
		return accountCreatore;
	}

	/**
	 * Modifica della mail dell'account del creatore 
	 * @param accountCreatore
	 */
	
	public void setAccountCreatore(String accountCreatore) {
		this.accountCreatore = accountCreatore;
	}

	/**
	 * 
	 * Prelieva la mail dell'account del supervisore
	 * @return String : accountSupervisore
	 */
	public String getAccountSupervisore() {
		return accountSupervisore;
	}

	/**
	 * Modifica la mail dell'account del supervisore 
	 * @param String accountSupervisore
	 */
	
	public void setAccountSupervisore(String accountSupervisore) {
		this.accountSupervisore = accountSupervisore;
	}

	/**
	 * Preleva la categoria del corso 
	 * @return String : categoria
	 */
	
	public String getCategoria() {
		return categoria;
	}

	/**
	 * Modifica la categoria del corso
	 * @param String categoria
	 */
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
}
