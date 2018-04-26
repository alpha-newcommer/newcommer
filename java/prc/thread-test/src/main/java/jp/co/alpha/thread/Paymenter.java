package jp.co.alpha.thread;

public class Paymenter extends Thread {
	private final Ledger ledger;
	private final String name;
	private final int value;

	public Paymenter(Ledger ledger, String name, int value) {
		super();
		this.ledger = ledger;
		this.name = name;
		this.value = value;
	}
	
	public void payment() {
		// 12回払い
		for (int i = 0; i < 12; i++) {
			Payment payment = new Payment();
			payment.setName(name);
			payment.setValue(value);
			ledger.setPayment(payment);
			ledger.recordPayment();
		}
	}
	
	@Override
	public void run() {
		payment();
	}
}
