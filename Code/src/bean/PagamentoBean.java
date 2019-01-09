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

	public String getAccountMail() {
		return accountMail;
	}
	
	public void setAccountMail(String accountMail) {
		this.accountMail = accountMail;
	}
	
	public int getCorsoIdCorso() {
		return corsoIdCorso;
	}
	
	public void setCorsoIdCorso(int corsoIdCorso) {
		this.corsoIdCorso = corsoIdCorso;
	}
	
	public String getDataPagamento() {
		return dataPagamento;
	}
	
	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public double getImporto() {
		return importo;
	}
	
	public void setImporto(double importo) {
		this.importo = importo;
	}
	
	public String getFattura() {
		return fattura;
	}
	
	public void setFattura(String fattura) {
		this.fattura = fattura;
	}
	
	
	
}
