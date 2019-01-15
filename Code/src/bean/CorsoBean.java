package bean;

import java.util.Collection;
import java.util.Iterator;

/**
 * Classe identificante una classe Corso persistente
 * @author Mario Sessa
 * @vesione 1.1
 * @since 11/01/2019
 */

public class CorsoBean {

	public enum Categoria{Informatica, Elettronica, Musica, Fotografia, Danza};
	public enum Stato{Attesa,Completamento, Attivo, Disattivo};
	private String nome;
	private String descrizione;
	private String dataCreazione;
	private String dataFine;
	private int nIscritti;
	private String copertina;
	private int idCorso;
	private AccountBean docente;
	private AccountBean supervisore;
	private Collection<IscrizioneBean> iscrizioni; /* Da modificare se si vuole aggiungere direttamente uno studente ad un corso e non indirettamente tramite i iscrizioni*/
	private Collection<LezioneBean> lezioni; 
	private Categoria categoria;
	private Stato stato;
	
	/**
	 * Costruttore generico del corso
	 * @param String nome descrizione dataCreazione dataFine copertina accountCreatore accountSupervisore categoria
	 * @param int nIscritti idCorso
	 * 
	 */
	public CorsoBean() {
	   
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

	/**
	 * Preleva lo stato del corso
	 * @return Stato : stato
	 */
	
	public Stato getStato() {
		return this.stato;
	}
	
	/**
	 * Modifica lo stato del corso
	 * @param Stato newStato
	 */
	
	public void setStato(Stato newStato) {
		this.stato = newStato;
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
		
		if(this.docente != docente) {
			AccountBean old = this.docente;
		this.docente = docente;
		
		if(this.docente != null) {
		   this.docente.AddCorsoTenuto(this);
		}
		if(old != null) {
			old.removeCorsoTenuto(this);
		}
		
		}
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
		if(this.supervisore != supervisore) {
			AccountBean old = this.supervisore;
		this.supervisore = supervisore;
		
		if(this.supervisore != null) {
		   
		   this.supervisore.addCorsoDaSupervisionare(this);
		}
		if(old != null) {
			old.removeCorsoDaSupervisionare(this);
		}
		
		}
	}

	
	/**
	 * Ritorna la collezione dei iscrizioni associati agli utenti che hanno acquistato il corso, ossia gli studenti del corso stesso.
	 * @return Collection<AccountBean> : studenti
	 */
	
	public Collection<IscrizioneBean> getiscrizioni() {
		return this.iscrizioni;
	}

	/**
	 * Aggiunge una collezione di iscrizioni associati agli studenti che hanno acquistato il corso.
	 * @param Collection<AccountBean> studenti
	 */
	
	
	public void addiscrizioni(Collection<IscrizioneBean> newiscrizioni) {
		 Iterator<IscrizioneBean>  iscrizioniDaAggiungere =  (Iterator<IscrizioneBean>) newiscrizioni.iterator();
		   while(iscrizioniDaAggiungere.hasNext()) {
			  IscrizioneBean addedPagamento = iscrizioniDaAggiungere.next();
			   this.iscrizioni.add(addedPagamento);
			   addedPagamento.setCorso(this);
		   }
	}
	
	/**
	 * Rimuove un iscrizione dal corso in associazione.
	 * @param IscrizioneBean iscrizioneRimosso
	 */
	
	public void removePagamento(IscrizioneBean iscrizioneRimosso) {
		this.iscrizioni.remove(iscrizioneRimosso);
		iscrizioneRimosso.setCorso(null);
	}


	/**
	 * Aggiunge un singolo iscrizione alla lista dei iscrizioni legati all'iscrizione del corso
	 * @param IscrizioneBean iscrizioneBean
	 */
	
	public void addPagamento(IscrizioneBean iscrizioneAggiunto) {
		this.iscrizioni.add(iscrizioneAggiunto);
		iscrizioneAggiunto.setCorso(this);
		
	}
	
	/**
	 * Aggiunte una collezione di lezioni alla collezione di lezioni associata al corso.
	 * @param Collection<LezioneBean> newLezioni
	 */
	
	public void addLezioni(Collection<LezioneBean> newLezioni) {
		 Iterator<LezioneBean>  lezioniDaAggiungere =  (Iterator<LezioneBean>) newLezioni.iterator();
		   while(lezioniDaAggiungere.hasNext()) {
			  LezioneBean addedLezione = lezioniDaAggiungere.next();
			   this.lezioni.add(addedLezione);
			   addedLezione.setCorso(this);
		   }	
	}
	
	/**
	 * Rimuove la lezione che viene passata come parametro
	 * @param LezioneBean lezioneDaRimuovere
	 */
	
	public void removeLezione(LezioneBean lezioneDaRimuovere) {
		this.lezioni.remove(lezioneDaRimuovere);
		lezioneDaRimuovere.setCorso(null);
		
	}

	/**
	 * Aggiunte la lezione passato come parametro alla lista delle lezioni del corso.
	 * @param  LezioneBean lezioneAggiunta
	 */

	public void addLezione(LezioneBean lezioneAggiunta) {
		this.lezioni.add(lezioneAggiunta);
		lezioneAggiunta.setCorso(this);		
		
	}


	
	
	
}
