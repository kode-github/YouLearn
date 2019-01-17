package utility;

import bean.CorsoBean.Stato;

public class StatoUtility {

	public static Stato parserTipoCarta(int string) {
		Stato tmp = null;
		switch(string) {
		case 0:  tmp=Stato.Completamento; break;
		case 1:  tmp=Stato.Attesa; break;
		case 2:  tmp=Stato.Attivo; break;
		case 3:  tmp=Stato.Disattivo; break;
		}
		return tmp;
	}
	
	/** Restituisce un oggetto Stato in base ai valori 0,1,2 usati nel DB per salvarla
	 * @param string
	 * @return
	 */
	public static int parserTipoCarta(Stato enumCarta) {
		int tmp=0;
		switch(enumCarta) {
		case Completamento:  tmp=0; break;
		case Attesa:  tmp=1; break;
		case Attivo:  tmp=2; break;
		case Disattivo: tmp=3; break;
		}
		return tmp;
	}
	
}
