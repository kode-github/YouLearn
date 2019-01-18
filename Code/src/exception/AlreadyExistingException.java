package exception;

public class AlreadyExistingException extends Exception {

	private static final long serialVersionUID = 1L;

	public AlreadyExistingException(String e) {
		super(e);
	}
}
