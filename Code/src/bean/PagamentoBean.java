package bean;

/**
 * 
 * Classe che rappresenta un Pagamento persistente
 * @author Mario Sessa
 * @version 1.0
 * @since 09/01/2019
 *
 */
public class PagamentoBean {

	private String accountMail;
	private int corsoIdCorso;
	private String dataPagamento;
	private double importo;
	private String fattura;
	
	
	public PagamentoBean(String accountMail, int corsoIdCorso, String dataPagamento, double importo, String fattura) {
		super();
		this.accountMail = accountMail;
		this.corsoIdCorso = corsoIdCorso;
		this.dataPagamento = dataPagamento;
		this.importo = importo;
		this.fattura = fattura;
	}
	
	public PagamentoBean() {}
	
	/**
	 * Preleva l'email dell'account collegata al pagamento
	 * @return String : accountMail
	 */
	
	public String getAccountMail() {
		return accountMail;
	}
	
	/**
	 * Modifica la mail dell'account collegata al pagamento. 
	 * @param accountMail
	 */
	
	public void setAccountMail(String accountMail) {
		this.accountMail = accountMail;
	}
	
	/**
	 * Preleva l'id del corso collegato al pagamento 
	 * @return int : corsoIdCorso
	 */
	
	public int getCorsoIdCorso() {
		return corsoIdCorso;
	}
	
	/**
	 * Modifica l'id del corso affiliato al pagamento 
	 * @param int corsoIdCorso
	 */
	
	public void setCorsoIdCorso(int corsoIdCorso) {
		this.corsoIdCorso = corsoIdCorso;
	}
	
	/**
	 * Preleva la data di quando è stato effettuato il pagamento 
	 * @return String : dataPagamento
	 */
	
	public String getDataPagamento() {
		return dataPagamento;
	}
	
	/**
	 * Modifica la data del pagamento con il valore del parametro 
	 * @param String : dataPagamento
	 */
	
	
	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	/**
	 * Preleva l'importo del pagamento 
	 * @return double : importo
	 */
	
	public double getImporto() {
		return importo;
	}
	
	/**
	 * Modifica l'importo del pagamento
	 * @param double importo
	 */
	public void setImporto(double importo) {
		this.importo = importo;
	}
	
	/**
	 * Preleva la fattura del pagamento 
	 * @return String : fattura
	 */
	
	public String getFattura() {
		return fattura;
	}
	
	/**
	 * Modifica la fattura del pagamento
	 * @param String fattura
	 */
	
	public void setFattura(String fattura) {
		this.fattura = fattura;
	}
	
	
	
}
