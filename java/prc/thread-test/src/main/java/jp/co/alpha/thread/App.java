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
		Thread th1 = new Thread(paymenter1);
		Thread th2 = new Thread(paymenter2);
		Thread th3 = new Thread(paymenter3);
		th1.start();
		th2.start();
		th3.start();
		
		// みんなのお支払が終わるまで待つ。
		try {
			th1.join();
			th2.join();
			th3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// お支払い状況表示
		System.out.println(ledger.printPaymentList());
	}

}
