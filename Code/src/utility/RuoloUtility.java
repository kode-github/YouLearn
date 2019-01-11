package utility;

import bean.AccountBean.Ruolo;

public class RuoloUtility {
	
	/**
	 * Converte un oggetto Ruolo in un 0 o 1 per il salvataggio nel DB
	 * @param r Oggetto Ruolo
	 * @return 0=Utente 1=Supervisore
	 */
	public static int ruoloParser(Ruolo r) {
		return r.equals(Ruolo.Utente)? 0:1;
	}
	
	public static Ruolo ruoloParser(int r) {
		return r==0 ? Ruolo.Utente:Ruolo.Supervisore;
	}

}
