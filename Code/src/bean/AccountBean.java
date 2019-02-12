package bean;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/***
 * 
 * Classe rappresentante le informazioni di un entità Account persistente
 * @author Mario Sessa
 * @version 1.1
 * @since 11/01/2019
 *
 */

public class AccountBean{
	
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
	private Collection<IscrizioneBean> iscrizioni; /* Da modificare se l'associazione tra un corso e un utente che segue tale corso non è creato attraverso l'entità pagamento*/
	private Collection<CommentoBean> commentiScritti;
	
	public AccountBean() {
		nome=cognome=password=mail="";
		tipo=null;
		carta=null;
		corsiTenuti=new LinkedList<CorsoBean>();
		corsiDaSupervisionare=new LinkedList<CorsoBean>();
		iscrizioni=new LinkedList<IscrizioneBean>();
		commentiScritti=new LinkedList<CommentoBean>();
		
	}
	
	public void setCorsiTenuti(Collection<CorsoBean> collection) {
		corsiTenuti=collection;
	}
	
	public void setIscrizioni(Collection<IscrizioneBean> iscrizioni) {
		this.iscrizioni=iscrizioni;
	}
	
	public void setCorsiSupervisionati(Collection<CorsoBean> collection) {
		corsiDaSupervisionare=collection;
	}
	
	/**
	 * Costruttore della classe generico, gli usi di tale costruttore sono molteplici. In genere viene utilizzato per prelevare informazioni dal database.
	 * @param CartaDiCreditoBean carta
	 */
	
	public AccountBean(CartaDiCreditoBean carta) {
		setCarta(carta);
		corsiTenuti=new LinkedList<CorsoBean>();
		corsiDaSupervisionare=new LinkedList<CorsoBean>();
		iscrizioni=new LinkedList<IscrizioneBean>();
		commentiScritti=new LinkedList<CommentoBean>();
		
	}

	
	/**
	 * Costruttore generico
	 * @param nome
	 * @param cognome
	 * @param password
	 * @param email
	 * @param ruolo
	 * @param verificato
	 * @param carta
	 */
	public AccountBean(String nome, String cognome, String password, String email, Ruolo ruolo, boolean verificato,
			CartaDiCreditoBean carta) {
		    this.nome = nome;
		    this.cognome = cognome;
		    this.password = password;
		    this.mail = email;
		    this.tipo = ruolo;
		    this.verificato = verificato;
		    this.carta = carta;
		    corsiTenuti=new LinkedList<CorsoBean>();
			corsiDaSupervisionare=new LinkedList<CorsoBean>();
			iscrizioni=new LinkedList<IscrizioneBean>();
			commentiScritti=new LinkedList<CommentoBean>();
	}

	/**
	 * Sostituisce il parametro legato alla verifica dell'email dell'account
	 * @param boolean verificato
	 */

	public void setVerificato(boolean verificato) {
		this.verificato = verificato;
	}

	/**
	 * Ritorna 0 o 1 a secondo del valore dell'attributo di verifica
	 * @return String : cognome
	 */
	
	public boolean getVerificato() {
		return verificato;
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
	
	
	public void setCarta(CartaDiCreditoBean newCarta) {
		
		if(this.carta != newCarta) {
		
			CartaDiCreditoBean old = this.carta;
		this.carta = newCarta;
		
		if(this.carta != null) {
		   this.carta.setAccount(this);
		}
		
		if(old != null) {
			old.setAccount(null);
		}
		
		}
	}
	

	/**
	 * Restituisce la collezione dei pagamenti per l'iscrizione ad un corso dell'account
	 * @return Collection<IscrizioneBean> : iscrizioni
	 */

	public Collection<IscrizioneBean> getIscrizioni() {
		return iscrizioni;
	}

	/**
	 * Aggiunge una collezione degli pagamenti per l'iscrizione ad un corso da parte dell'account
	 * @param IscrizioneBean iscrizioni
	 */
	

	public void addIscrizioni(Collection<IscrizioneBean> nuoviAcquisti) {
		Iterator<IscrizioneBean> pagamentiDaAggiungere = (Iterator<IscrizioneBean>) nuoviAcquisti.iterator();
		while(pagamentiDaAggiungere.hasNext()) {
			IscrizioneBean added = pagamentiDaAggiungere.next();
			this.iscrizioni.add(added);
			added.setAccount(this);
		}
	}
	
	/**
	 * Rimuove un pagamento collegato all'account
	 * @param IscrizioneBean pagamentoToRemove
	 */
	
	public void removeIscrizione(IscrizioneBean pagamentoToRemove) {
		this.iscrizioni.remove(pagamentoToRemove);
		pagamentoToRemove.setAccount(null);
	}
	
	/**
	 * Aggiungi un singolo iscrizione alla lista dei pagamenti associati all'account 
	 * @param IscrizioneBean pagamentoAggiunto
	 */

	public void addIscrizione(IscrizioneBean pagamentoAggiunto) {
		this.iscrizioni.add(pagamentoAggiunto);
		pagamentoAggiunto.setAccount(this);
		
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


	public void AddCorsiDaSupervisionare(Collection<CorsoBean> corsiDaSupervisionare) {
		   Iterator<CorsoBean> corsiDaAggiungere =  (Iterator<CorsoBean>) corsiDaSupervisionare.iterator();
		   while(corsiDaAggiungere.hasNext()) {
			   CorsoBean corso = corsiDaAggiungere.next();
			   this.corsiDaSupervisionare.add(corso);
			   corso.setSupervisore(this);
		   }
		}

	/**
	 * Rimuove l'associazione tra il docente e il suo corso, questo metodo viene richiamato ad esempio quando un corso viene rifiutato.
	 * @param CorsoBean corsoTenuto
	 */
	
	public void removeCorsoDaSupervisionare(CorsoBean corsoDaSupervisionare) {
		this.corsiTenuti.remove(corsoDaSupervisionare);
		corsoDaSupervisionare.setSupervisore(null);
	}
	
	/**
	 * Aggiunge un singolo corso da supervisionare alla collezione dei corsi da supervisionare dall'account
	 * @param CorsoBean corsoDaAggiungere
	 */
	
	public void addCorsoDaSupervisionare(CorsoBean corsoDaAggiungere) {
		System.out.println("IL CORSO DA AGGIUNGERE E" + corsoDaAggiungere);
		this.corsiDaSupervisionare.add(corsoDaAggiungere);
		corsoDaAggiungere.setSupervisore(this);
		
	}

	/**
	 * Restituisce la collezione di corsi tenuti dall'account
	 * @return Collection<CorsoBean> : corsiTenuti
	 */
	
	public Collection<CorsoBean> getCorsiTenuti() {
		return corsiTenuti;
	}

	/**
	 * Aggiunge una collezione di corsi tenuti dall'account
	 * @param CorsoBean corsiTenuti
	 */
	
	public void AddCorsiTenuti(Collection<CorsoBean> corsiTenuti) {
	   Iterator<CorsoBean> corsiDaAggiungere =  (Iterator<CorsoBean>) corsiTenuti.iterator();
	   while(corsiDaAggiungere.hasNext()) {
		   CorsoBean corso = corsiDaAggiungere.next();
		   this.corsiTenuti.add(corso);
		   corso.setDocente(this);
	   }
	}
	
	public void addCorsoTenuto(CorsoBean corso) {
		LinkedList<CorsoBean> l=new LinkedList<>();
		l.add(corso);
		AddCorsiTenuti(l);
	}
	
	
	/**
	 * Aggiunge un singolo corso tenuto dall'account alla lista dei corsi tenuti dall'account
	 * @param CorsoBean corso
	 */
	
	public void AddCorsoTenuto(CorsoBean corso) {
		this.corsiTenuti.add(corso);
		corso.setDocente(this);
		
	}
	
	/**
	 * Retrieve di un corso tenuto 
	 * @param id id del corso
	 * @return il corso, null se non esiste o l'id del corso � null
	 */
	public CorsoBean getCorsoTenuto(int id) {
		if(corsiTenuti==null) return null;
		Iterator<CorsoBean> i=corsiTenuti.iterator();
		while(i.hasNext()) {
			CorsoBean corso=i.next();
			if(corso.getIdCorso()!=null && corso.getIdCorso()==id)
				return corso;
		}
		return null;
	}
	

	/**
	 * Rimuove l'associazione tra il docente e il suo corso, questo metodo viene richiamato ad esempio quando un corso viene rifiutato.
	 * @param CorsoBean corsoTenuto
	 */
	
	public void removeCorsoTenuto(CorsoBean corsoTenuto) {
		this.corsiTenuti.remove(corsoTenuto);
		corsoTenuto.setDocente(null);
	}
	
	/**
	 * Restituisce la collezione dei commenti scritti dall'account
	 * @return Collection<CommentoBean> : commentiScritti
	 */


	public Collection<CommentoBean> getCommentiScritti() {
		return commentiScritti;
	}

	
	/**
	 * Aggiunge commenti alla collezione dei commenti scritti dall'account
	 * @param CommentoBean commentiScritti
	 */


	public void addCommentiScritti(Collection<CommentoBean> commenti) {
		Iterator<CommentoBean> commentiDaAggiungere = (Iterator<CommentoBean>) commenti.iterator();
		while(commentiDaAggiungere.hasNext()) {
			CommentoBean added = commentiDaAggiungere.next();
			this.commentiScritti.add(added);
			added.setAccountCreatore(this);
		}
	}

	/**
	 * Rimuove un commento scritto dall'account
	 * @param CommentoBean commento
	 */
	
	public void removeCommento(CommentoBean commenti) {
		this.commentiScritti.remove(commenti);
		commenti.setAccountCreatore(null);
	}
	
	/**
	 * Aggiunge un singolo commento alla collezione dei commenti scritti dall'account
	 * @param CommentoBean commentoAggiunto
	 */
	
	public void addCommentoScritto(CommentoBean commentoAggiunto) {
		this.commentiScritti.add(commentoAggiunto);
		commentoAggiunto.setAccountCreatore(this);		
	}


	public CorsoBean getCorsoSupervisionato(int idCorso) {
		if(corsiTenuti==null) return null;
		Iterator<CorsoBean> i=corsiDaSupervisionare.iterator();
		while(i.hasNext()) {
			CorsoBean corso=i.next();
			if(corso.getIdCorso()!=null && corso.getIdCorso()==idCorso)
				return corso;
		}
		return null;
	}


	

	


}


