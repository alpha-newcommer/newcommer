package jp.co.alpha.thread;

import java.util.Random;

public class Paymenter extends Thread {
	private final Ledger ledger;
	private final Payment monthlyPayment;

	public Paymenter(Ledger ledger, Payment monthlyPayment) {
		super();
		this.ledger = ledger;
		this.monthlyPayment = monthlyPayment;
	}

	@Override
	public void run() {
		try {
			Random rand = new Random();
			// 12回払い
			for (int i = 0; i < 12; i++) {
				ledger.setValue(monthlyPayment.getValue());
				Thread.sleep(rand.nextInt(5) * 100);
				ledger.payment(monthlyPayment.getName());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
