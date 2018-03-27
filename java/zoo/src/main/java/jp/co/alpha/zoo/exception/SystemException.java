package jp.co.alpha.zoo.exception;

public class SystemException extends RuntimeException {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 1L;

	public SystemException(String msg) {
		super(msg);
	}
}
