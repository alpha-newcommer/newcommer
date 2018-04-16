package jp.co.alpha.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 台帳クラス
 */
public class Ledger {
	/**
	 * 金額
	 */
	private int value;

	private final List<Payment> paymentList;

	public Ledger() {
		paymentList = Collections.synchronizedList(new ArrayList<>());
	}

	public void payment(String name) {
		Payment payment = new Payment();
		payment.setName(name);
		payment.setValue(value);
		paymentList.add(payment);
	}

	public String printPaymentList() {
		StringBuilder sb = new StringBuilder();

		for (Payment payment : paymentList) {
			if (payment == null) {
				sb.append("null\r\n");
			} else {
				sb.append(payment.getName()).append(" : ");
				sb.append(payment.getValue()).append("万円");
				sb.append("\r\n");
			}
		}

		return sb.toString();
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

}
