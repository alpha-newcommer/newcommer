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

	@Override
	public void run() {
		try {
			// 12回払い
			for (int i = 0; i < 12; i++) {
				ledger.payment(name, value);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
