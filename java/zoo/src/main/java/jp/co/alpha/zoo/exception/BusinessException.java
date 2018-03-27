package jp.co.alpha.zoo.exception;

public class BusinessException extends Exception {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String msg) {
		super(msg);
	}
}
