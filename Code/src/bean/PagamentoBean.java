package bean;

/**
 * 
 * Classe che rappresenta un Pagamento persistente
 * @author Mario Sessa
 * @version 1.1
 * @since 11/01/2019
 *
 */
public class PagamentoBean {

	private AccountBean account;
	private CorsoBean corso;
	private String dataPagamento;
	private double importo;
	private String fattura;
	
	
	public PagamentoBean() {}
	
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

	/**
	 * Preleva l'account collegata al pagamento
	 * @return String : accountMail
	 */
	
	public AccountBean getAccount() {
		return this.account;
	}
	
	/**
	 * Modifica l'account collegata al pagamento. 
	 * @param accountMail
	 */
	
	public void setAccount(AccountBean account) {
		if(this.account != account) {
			AccountBean old = this.account;
		this.account = account;
		
		if(this.account != null) {
		   
		   this.account.addAcquisto(this);
		}
		if(old != null) {
			old.removeAcquisto(this);
		}
	}
	
	/**
	 * Preleva il corso collegato al pagamento 
	 * @return int : corsoIdCorso
	 */
	
	public CorsoBean getCorso() {
		return corso;
	}
	
	/**
	 * Modifica il corso affiliato al pagamento 
	 * @param int corsoIdCorso
	 */
	
	public void setCorso(CorsoBean corso) {
		if(this.corso != corso) {
			CorsoBean old = this.corso;
		this.corso = corso;
		
		if(this.corso != null) {
		   
		   this.corso.addPagamento(this);
		}
		if(old != null) {
			old.removePagamento(this);
		}
	}
	
	
	
}
