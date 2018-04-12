package jp.co.alpha.zoo.exception;

/**
 * システム例外
 */
public class SystemException extends RuntimeException {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 */
	public SystemException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param e
	 */
	public SystemException(String msg, Exception e) {
		super(msg, e);
	}
}
