package jp.co.alpha.zoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.animal.AnimalFactory;
import jp.co.alpha.zoo.cage.Cage;
import jp.co.alpha.zoo.cage.CageFactory;
import jp.co.alpha.zoo.exception.BusinessException;
import jp.co.alpha.zoo.exception.SystemException;
import jp.co.alpha.zoo.ribbon.RibbonManager;

/**
 * メインクラス
 */
public class App {
	/**
	 * ロガー
	 */
	private static Logger logger = LoggerFactory.getLogger(App.class);
	
	/**
	 * メインメニュー
	 */
	private final Map<Integer, String> mainMenuMap;


	/**
	 * コンストラクタ
	 */
	public App() {
		mainMenuMap = new LinkedHashMap<>();
		mainMenuMap.put(1, "檻に動物を入れる");
		mainMenuMap.put(2, "動物にリボン贈呈");
		mainMenuMap.put(3, "全動物リスト表示");
		mainMenuMap.put(4, "リボン付き動物リスト表示");
		mainMenuMap.put(9, "アプリ終了");
	}

	/**
	 * 処理開始
	 * @throws IOException
	 */
	private void execute() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int cmdId;
		boolean isExit = false;
		while (!isExit) {
			cmdId = menue(in);
			switch (cmdId) {
			case 1:
				// 動物を檻に入れる
				putAnimal(in);
				break;
			case 2:
				// 動物にリボン贈呈
				setRibbon(in);
				break;
			case 3:
				// 全動物リスト表示
				printAllAnimals();
				break;
			case 4:
				// リボン付き動物リスト表示
				printRibbonAnimals();
				break;
			case 9:
				isExit = true;
				break;
			default:
				throw new SystemException("有り得ないメニュー番号:" + cmdId);
			}
		}
	}

	/**
	 * メニュー表示
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private int menue(BufferedReader in) throws IOException {
		return getCommand(mainMenuMap, in, "メニュー選択", "入力誤り。メニュー番号を入力してください。");
	}

	/**
	 * リボン付き動物リスト表示
	 */
	private void printRibbonAnimals() {
		Map<String, Animal> ribbonMap = RibbonManager.getRibbonMap();
		int id = 1;
		for (Entry<String, Animal> entry : ribbonMap.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(id).append("\t");
			sb.append(entry.getKey()).append("\t");
			sb.append(entry.getValue().getName()).append("\t");
			sb.append(entry.getValue().getWeight());
			System.out.println(sb.toString());
			id++;
		}
	}

	/**
	 * 動物にリボン贈呈
	 * @param in
	 * @throws IOException
	 */
	private void setRibbon(BufferedReader in) throws IOException {
		// リボンの選択
		Map<Integer, String> menuMap = new LinkedHashMap<>();
		List<String> ribbonNames = RibbonManager.getRibbonNames();
		for (int i = 0; i < ribbonNames.size(); i++) {
			menuMap.put(i + 1, ribbonNames.get(i));
		}
		int cmdId = getCommand(menuMap, in, "リボン番号選択", "入力誤り。リボン番号を入力してください。");
		String targetRibbonName = menuMap.get(cmdId);
		
		// 対象の動物の選択
		menuMap.clear();
		List<Animal> allAnimalList = new ArrayList<>();
		int id = 1;
		for (Cage cage : CageFactory.getAllCages()) {
			for (Animal animal : cage.getAllAnimals()) {
				StringBuilder sb = new StringBuilder();
				sb.append(cage.getName()).append("\t");
				sb.append(animal.getName()).append("\t");
				sb.append(animal.getWeight()).append("\t");
				sb.append("");

				menuMap.put(id, sb.toString());
				allAnimalList.add(animal);
				id++;
			}
		}
		cmdId = getCommand(menuMap, in, "動物番号選択", "入力誤り。動物番号を入力してください。");
		Animal targetAnimal = allAnimalList.get(cmdId - 1);

		try {
			// 動物にリボン設定
			RibbonManager.setRibbon(targetRibbonName, targetAnimal);
		} catch (BusinessException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * 全動物リスト表示
	 */
	private void printAllAnimals() {
		int id = 1;
		for (Cage cage : CageFactory.getAllCages()) {
			for (Animal animal : cage.getAllAnimals()) {
				StringBuilder sb = new StringBuilder();
				sb.append(id).append("\t");
				sb.append(cage.getName()).append("\t");
				sb.append(animal.getName()).append("\t");
				sb.append(animal.getWeight()).append("\t");
				sb.append("");
				System.out.println(sb.toString());
				id++;
			}

		}
	}

	/**
	 * 檻に動物を入れる処理
	 * @param in
	 * @throws IOException
	 */
	private void putAnimal(BufferedReader in) throws IOException {
		int cmdId = 0;

		// 檻の選択
		Map<Integer, String> menuMap = new LinkedHashMap<>();
		List<String> cageNames = CageFactory.getCageNames();
		for (int i = 0; i < cageNames.size(); i++) {
			menuMap.put(i + 1, cageNames.get(i));
		}
		cmdId = getCommand(menuMap, in, "ケージ番号選択", "入力誤り。ケージ番号を入力してください。");
		Cage cage = CageFactory.getCage(cageNames.get(cmdId - 1));

		// 動物の選択
		menuMap.clear();
		List<String> animalNames = AnimalFactory.getAnimalNames();
		for (int i = 0; i < animalNames.size(); i++) {
			menuMap.put(i + 1, animalNames.get(i));
		}
		cmdId = getCommand(menuMap, in, "動物の番号選択", "入力誤り。動物の番号を入力してください。");
		String animalName = animalNames.get(cmdId - 1);

		// 動物の体重を入力するまでループ
		String cmd = null;
		while (!StringUtils.isNumeric(cmd)) {
			System.out.print(animalName + "の体重（kg）を入力＞");
			cmd = in.readLine();
		}
		int weight = Integer.parseInt(cmd);

		// 動物名と体重から動物オブジェクト生成
		Animal animal = AnimalFactory.createAnimal(animalName, weight);

		// ケージに動物を入れる
		try {
			cage.in(animal);
		} catch (BusinessException e) {
			System.err.println(e.getMessage());
		}

	}

	/**
	 * 指定したメニューのいずれかをユーザ入力するまでループする処理。
	 * 
	 * @param menuMap ユーザへ表示するメニュー番号とメニュー内容のマップ
	 * @param in 入力ストリーム
	 * @param orderMsg ユーザへ入力を促すメッセージ
	 * @param errMsg メニュー番号以外を入力された場合のユーザへ警告を行うメッセージ
	 * @return ユーザが入力したメニュー番号
	 * @throws IOException
	 */
	private int getCommand(Map<Integer, String> menuMap, BufferedReader in, String orderMsg, String errMsg)
			throws IOException {
		String cmd = null;
		int cmdId = 0;
		// メニューのいずれかを入力するまでループ
		while (StringUtils.isEmpty(cmd)) {
			// メニュー表示
			for (Entry<Integer, String> entry : menuMap.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			
			// 入力受付
			System.out.print(orderMsg + "＞");
			cmd = in.readLine();
			// 入力誤りの場合はエラーメッセージ表示後に再度メニューへ
			if (StringUtils.isNumeric(cmd)) {
				cmdId = Integer.parseInt(cmd);
				if (!menuMap.keySet().contains(cmdId)) {
					System.err.println(errMsg);
					cmd = null;
					continue;
				}
			}
		}

		return cmdId;
	}

	/**
	 * メイン処理
	 * @param args
	 */
	public static void main(String[] args) {
		App app = new App();
		try {
			app.execute();
			System.out.println("アプリ終了");
		} catch (Exception e) {
			System.err.println("異常終了");
			logger.error("異常発生", e);
		}
	}

}
