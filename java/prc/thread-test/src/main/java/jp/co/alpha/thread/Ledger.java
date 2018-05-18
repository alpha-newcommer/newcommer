package jp.co.alpha.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	public Ledger() {
		paymentList = Collections.synchronizedList(new ArrayList<>());
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public void recordPayment() {
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
