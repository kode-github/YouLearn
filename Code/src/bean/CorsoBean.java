package bean;

import java.util.Collection;

/**
 * 
 * Classe identificante una classe Corso persistente
 * @author Mario Sessa
 * @vesione 1.1
 * @since 11/01/2019
 */
public class CorsoBean {

	public enum Categoria{Informatica, Elettronica, Musica, Fotografia, Danza};
	private String nome;
	private String descrizione;
	private String dataCreazione;
	private String dataFine;
	private int nIscritti;
	private String copertina;
	private int idCorso;
	private AccountBean docente;
	private AccountBean supervisore;
	private Collection<AccountBean> studenti;
	private Categoria categoria;
	
	/**
	 * Costruttore generico del corso
	 * @param String nome descrizione dataCreazione dataFine copertina accountCreatore accountSupervisore categoria
	 * @param int nIscritti idCorso
	 * 
	 */
	public CorsoBean(String nome, String descrizione, String dataCreazione, String dataFine, int nIscritti,
			String copertina, int idCorso, AccountBean docente, AccountBean supervisore, Categoria categoria, Collection<AccountBean> studenti) {
		super();
		this.nome = nome;
		this.descrizione = descrizione;
		this.dataCreazione = dataCreazione;
		this.dataFine = dataFine;
		this.nIscritti = nIscritti;
		this.copertina = copertina;
		this.idCorso = idCorso;
		this.docente = docente;
		this.supervisore = supervisore;
		this.studenti = studenti;
		this.categoria = categoria;
	}

	/**
	 * Ritorna la collezione degli studenti del corso
	 * @return Collection<AccountBean> : studenti
	 */
	
	public Collection<AccountBean> getStudenti() {
		return studenti;
	}

	/**
	 * Assegna una collezione di studenti al corso
	 * @param Collection<AccountBean> studenti
	 */
	
	
	public void setStudenti(Collection<AccountBean> studenti) {
		this.studenti = studenti;
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
	 * @return AccountBean : doc ente
	 */
	
	public AccountBean getDocente() {
		return docente;
	}

	/**
	 * Modifica della mail dell'account del creatore 
	 * @param AccountBean docente
	 */
	
	public void setDocente(AccountBean docente) {
		this.docente = docente;
	}

	/**
	 * 
	 * Prelieva la mail dell'account del supervisore
	 * @return AccountBean : supervisore
	 */
	public AccountBean getSupervisore() {
		return supervisore;
	}

	/**
	 * Modifica la mail dell'account del supervisore 
	 * @param AccountBean supervisore
	 */
	
	public void setSupervisore(AccountBean supervisore) {
		this.supervisore = supervisore;
	}

	/**
	 * Preleva la categoria del corso 
	 * @return Categoria : categoria
	 */
	
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * Modifica la categoria del corso
	 * @param Categoria categoria
	 */
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
