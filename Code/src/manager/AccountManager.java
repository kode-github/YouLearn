package manager;

import bean.AccountBean;

public class AccountManager {
	
	/**
	 * Modifica la password di un Account
	 * @param cf
	 * @param pass
	 * @return
	 */
	public boolean modificaPassword(String cf, String pass) {
		return false;
		
	}
	
	/**
	 * Modifica la mail di un Account
	 * @param cf
	 * @param mail
	 * @return
	 */
	public boolean modificaMail(String cf, String mail) {
		return false;
		
	}
	
	/**
	 * Effettua il login di un Account
	 * @param cf
	 * @param password
	 * @return
	 */
	public AccountBean authenticateUser(String cf, String password) {
		
		return null;
	}
	
	/**
	 * Registra un nuovo utente
	 * @param user
	 * @return
	 */
	public boolean setRegistrazion(AccountBean user) {
		return false;
		
	}
	
	/**
	 * Verifica se una certa mail esiste già nel database
	 * @param email la mail da verificare
	 * @return
	 */
	public boolean checkMail(String email) {
		return false;
		
	}
	
	 /**
	  * Sta roba non ha senso
	  * Verifica se la password per un certo user è corretta
	  * @param password
	  * @param cf
	  * @return
	  */
	public boolean checkPassword(String password, String cf) {
		return false;
		
	}
	
	/**
	 * Controlla se un account è Utente o Supervisore
	 * @param cf
	 * @return
	 */
	public boolean checkTipoUser(String cf) {
		return false;
		
	}
}
