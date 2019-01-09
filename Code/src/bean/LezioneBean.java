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
	 * @param corsoIdCorso
	 * @param nome
	 * @param visualizzazioni
	 * @param numeroLezione
	 */
	
	public LezioneBean(int corsoIdCorso, String nome, int visualizzazioni, int numeroLezione) {
		super();
		this.corsoIdCorso = corsoIdCorso;
		this.nome = nome;
		this.visualizzazioni = visualizzazioni;
		this.numeroLezione = numeroLezione;
	}

	public int getCorsoIdCorso() {
		return corsoIdCorso;
	}

	public void setCorsoIdCorso(int corsoIdCorso) {
		this.corsoIdCorso = corsoIdCorso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getVisualizzazioni() {
		return visualizzazioni;
	}

	public void setVisualizzazioni(int visualizzazioni) {
		this.visualizzazioni = visualizzazioni;
	}

	public int getNumeroLezione() {
		return numeroLezione;
	}

	public void setNumeroLezione(int numeroLezione) {
		this.numeroLezione = numeroLezione;
	}
	
	
	
	
}
