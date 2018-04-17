package jp.co.alpha.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 台帳クラス
 */
public class Ledger {
	/**
	 * 支払情報
	 */
	private Payment payment;

	/**
	 * 台帳
	 */
	private final List<Payment> paymentList;

	private Random rand = new Random();
	
	public Ledger() {
		paymentList = Collections.synchronizedList(new ArrayList<>());
	}

	public void payment(String name, int value) throws InterruptedException {
		payment = new Payment();
		payment.setValue(value);
		// ちょっと色々人によって時間がかかる。
		Thread.sleep(rand.nextInt(5) * 100);
		
		payment.setName(name);
		paymentList.add(payment);
	}

	public String printPaymentList() {
		StringBuilder sb = new StringBuilder();

		for (Payment p : paymentList) {
			if (p == null) {
				sb.append("null\r\n");
			} else {
				sb.append(p.getName()).append(" : ");
				sb.append(p.getValue()).append("万円");
				sb.append("\r\n");
			}
		}

		return sb.toString();
	}

}
