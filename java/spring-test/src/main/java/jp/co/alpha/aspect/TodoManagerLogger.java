package jp.co.alpha.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * TodoManagerのメソッド呼び出し前後の処理.
 *
 */
@Aspect
@Component
public class TodoManagerLogger {

	/**
	 * ロガー
	 */
	private Logger logger = LoggerFactory.getLogger(TodoManagerLogger.class);

	@Around("within(jp.co.alpha.todo..*)")
	public Object arround(ProceedingJoinPoint pjp) throws Throwable {
		// ログ出力処理
		logger.info("メソッド開始:" + pjp.getSignature().getName());
		Object rslt = pjp.proceed();
		// ログ出力処理
		logger.info("メソッド終了:" + pjp.getSignature().getName());
		return rslt;
	}

}
