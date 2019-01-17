package utility;

import bean.CartaDiCreditoBean.CartaEnum;

public class CartaEnumUtility {
	
	public static CartaEnum parserTipoCarta(int string) {
		CartaEnum tmp = null;
		switch(string) {
		case 0:  tmp=CartaEnum.POSTEPAY; break;
		case 1:  tmp=CartaEnum.AMERICANEXPRESS; break;
		case 2:  tmp=CartaEnum.PAYPAL; break;
		}
		return tmp;
	}
	
	/** Restituisce un oggetto CartaEnum in base ai valori 0,1,2 usati nel DB per salvarla
	 * @param string
	 * @return
	 */
	public static int parserTipoCarta(CartaEnum enumCarta) {
		int tmp=0;
		switch(enumCarta) {
		case POSTEPAY:  tmp=0; break;
		case AMERICANEXPRESS:  tmp=1; break;
		case PAYPAL:  tmp=2; break;
		}
		return tmp;
	}

}
