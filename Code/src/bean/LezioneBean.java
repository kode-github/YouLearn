package bean;

/**
 * 
 * Classe che rappresenta una lezione persistente
 * @author Mario Sessa
 * @version 1.0
 * @since 09/01/2019
 *
 */
public class LezioneBean {

	private int corsoIdCorso;
	private String nome;
	private int visualizzazioni;
	private int numeroLezione;
	
	/**
	 * 
	 * Costruttore generico delle lezioni
	 * @param int corsoIdCorso visualizzazioni numeroLezione
	 * @param String nome
	 */
	
	public LezioneBean(int corsoIdCorso, String nome, int visualizzazioni, int numeroLezione) {
		super();
		this.corsoIdCorso = corsoIdCorso;
		this.nome = nome;
		this.visualizzazioni = visualizzazioni;
		this.numeroLezione = numeroLezione;
	}
	
	public LezioneBean() {}

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
