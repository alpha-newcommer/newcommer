package jp.co.alpha.thread;

public class App {

	public static void main(String[] args) {
		Ledger ledger = new Ledger();

		// たろーさんの毎月の支払設定
		Paymenter paymenter1 = new Paymenter(ledger, "たろぉ", 100);

		// こてつさんの毎月の支払設定
		Paymenter paymenter2 = new Paymenter(ledger, "こてっ", 50);

		// こじろおさんの毎月の支払設定
		Paymenter paymenter3 = new Paymenter(ledger, "こじろぅ", 20);
		
		// お支払い開始
		paymenter1.start();
		paymenter2.start();
		paymenter3.start();
		
		// みんなのお支払が終わるまで待つ。
		try {
			paymenter3.join();
			paymenter2.join();
			paymenter1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// お支払い状況表示
		System.out.println(ledger.printPaymentList());
	}

}
