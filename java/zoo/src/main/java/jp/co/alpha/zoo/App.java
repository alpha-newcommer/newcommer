package jp.co.alpha.zoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.alpha.zoo.exception.SystemException;

public class App {
	private static Logger logger = LoggerFactory.getLogger(App.class);

	public App() {
		// nothing
	}

	private void execute() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String cmd;
		boolean isExit = false;
		while (!isExit) {
			cmd = menue(in);
			switch(cmd) {
			case "1":
				System.out.println("1");
				break;
			case "2":
				System.out.println("2");
				break;
			case "3":
				System.out.println("3");
				break;
			case "4":
				System.out.println("4");
				break;
			case "9":
				isExit = true;
				break;
			default:
				throw new SystemException("有り得ないメニュー番号:" + cmd);
			}
		}
	}

	private String menue(BufferedReader in) throws IOException {
		String cmd = null;
		
		// 1/2/3/4/9 のいずれかを入力するまでループ
		while(StringUtils.isEmpty(cmd)) {
			System.out.println("■メニュー");
			System.out.println("1:檻に動物を入れる");
			System.out.println("2:動物にリボン贈呈");
			System.out.println("3:全動物リスト表示");
			System.out.println("4:リボン付き動物リスト表示");
			System.out.println("9:アプリ終了");
			System.out.println("メニュー選択＞");
			cmd = in.readLine();
			if (!ArrayUtils.contains(new String[] { "1", "2", "3", "4", "9" }, cmd)) {
				System.err.println("入力誤り。メニュー番号を入力してください。");
				cmd = null;
			}
		}

		return cmd;
	}

	public static void main(String[] args) {
		App app = new App();
		try {
			app.execute();
		} catch (Exception e) {
			System.err.println("異常終了");
			logger.error("異常発生", e);
		}
	}

}
