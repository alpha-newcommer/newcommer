package jp.co.alpha.zoo.exception;

/**
 * ビジネス例外
 */
public class BusinessException extends Exception {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param msg
	 */
	public BusinessException(String msg) {
		super(msg);
	}
}
