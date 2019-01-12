package bean;

import java.util.Collection;

/**
 * 
 * Classe che rappresenta una lezione persistente
 * @author Mario Sessa
 * @version 1.1
 * @since 11/01/2019
 *
 */
public class LezioneBean {

	private int corsoIdCorso;
	private String nome;
	private int visualizzazioni;
	private int numeroLezione;
	private Collection<CommentoBean> commenti;
	
	/**
	 * 
	 * Costruttore generico delle lezioni
	 * @param int corsoIdCorso visualizzazioni numeroLezione
	 * @param String nome
	 */
	
	public LezioneBean(int corsoIdCorso, String nome, int visualizzazioni, int numeroLezione, Collection<CommentoBean> commenti) {
		super();
		this.corsoIdCorso = corsoIdCorso;
		this.nome = nome;
		this.visualizzazioni = visualizzazioni;
		this.numeroLezione = numeroLezione;
		this.commenti = commenti;
	}
	
	

	public LezioneBean() {}
	
	/**
	 * Ritorna la collezione dei commenti affiliati alla lezione
	 * @return Collection<CommentoBean> : commenti
	 */
	
	public Collection<CommentoBean> getCommenti() {
		return commenti;
	}

	/**
	 * Imposta la collezione dei commenti legati alla lezione
	 * @param Collection<CommentoBean> commenti
	 */
	public void setCommenti(Collection<CommentoBean> commenti) {
		this.commenti = commenti;
	}

	/**
	 * Preleva l'id del corso affiliato alla lezione 
	 * @return int : corsoIdCorso
	 */
	
	public int getCorsoIdCorso() {
		return corsoIdCorso;
	}

	/**
	 *  Modifica l'id del corso affiliato alla lezione
	 * @param int corsoIdCorso
	 */
	
	public void setCorsoIdCorso(int corsoIdCorso) {
		this.corsoIdCorso = corsoIdCorso;
	}

	/**
	 * Prelievo del nome della lezione 
	 * @return String : nome
	 */
	
	public String getNome() {
		return nome;
	}

	/**
	 * Modifica del nome della lezione 
	 * @param String nome
	 */
	
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Ritorna il numero relativo alle visualizzazioni del num
	 * @return int : visualizzazioni
	 */
	
	public int getVisualizzazioni() {
		return visualizzazioni;
	}

	/**
	 * Modifica le visualizzazioni della lezione 
	 * @param int visualizzazioni
	 */
	
	public void setVisualizzazioni(int visualizzazioni) {
		this.visualizzazioni = visualizzazioni;
	}

	
	/**
	 * Preleva il numero della lezione 
	 * @return int : numeroLezione
	 */
	
	public int getNumeroLezione() {
		return numeroLezione;
	}

	/**
	 * Modifica il numero della lezione nel valore del parametro 
	 * @param int : numeroLezione
	 */
	
	
	public void setNumeroLezione(int numeroLezione) {
		this.numeroLezione = numeroLezione;
	}
	
	
	
	
}
