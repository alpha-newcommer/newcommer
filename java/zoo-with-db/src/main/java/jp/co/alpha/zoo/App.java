package jp.co.alpha.zoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.animal.AnimalFactory;
import jp.co.alpha.zoo.animal.AnimalType;
import jp.co.alpha.zoo.cage.Cage;
import jp.co.alpha.zoo.cage.CageFactory;
import jp.co.alpha.zoo.db.DBAccess;
import jp.co.alpha.zoo.exception.BusinessException;
import jp.co.alpha.zoo.exception.SystemException;
import jp.co.alpha.zoo.ribbon.RibbonManager;
import jp.co.alpha.zoo.ribbon.RibbonType;

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
	 * 檻に動物を入れる処理
	 * @param in
	 * @throws IOException
	 */
	private void putAnimal(BufferedReader in) throws IOException {
		// 檻の選択
		Map<Integer, String> menuMap = new LinkedHashMap<>();
		List<Cage> cageList = CageFactory.getAllCages();
		for (Cage cage: cageList) {
			menuMap.put(cage.getCd(), cage.getName());
		}
		int cmdId = getCommand(menuMap, in, "ケージ番号選択", "入力誤り。ケージ番号を入力してください。");
		Cage cage = CageFactory.getCage(cmdId);

		// 動物の選択
		menuMap.clear();
		List<AnimalType> animalTypeList = AnimalFactory.getAnimalTypeList();
		for (AnimalType animalType : animalTypeList) {
			menuMap.put(animalType.getCd(), animalType.getName());
		}
		cmdId = getCommand(menuMap, in, "動物の番号選択", "入力誤り。動物の番号を入力してください。");
		int animalCd = cmdId;
		String animalName = menuMap.get(cmdId);

		// 動物の体重を入力するまでループ
		String cmd = null;
		while (true) {
			System.out.print(animalName + "の体重（kg）を入力＞");
			cmd = in.readLine();
			if (StringUtils.isNumeric(cmd)) {
				break;
			}
			System.err.println("数値を入力してください。");
		}
		int weight = Integer.parseInt(cmd);

		// 動物名と体重から動物オブジェクト生成
		Animal animal = AnimalFactory.createAnimal(0, animalCd, weight);

		// ケージに動物を入れる
		try {
			cage.in(animalCd, animal);
		} catch (BusinessException e) {
			System.err.println(e.getMessage());
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
		List<RibbonType> ribbonTypeList = RibbonManager.getRibbonTypes();
		for (RibbonType type : ribbonTypeList) {
			menuMap.put(type.getCd(), type.getName());
		}
		int robbonCd = getCommand(menuMap, in, "リボン番号選択", "入力誤り。リボン番号を入力してください。");
		
		// 対象の動物を選択するまでループ
		Animal targetAnimal = null;
		String cmd = null;
		while (true) {
			System.out.print("リボンを贈呈する動物の個体識別IDを入力＞");
			cmd = in.readLine();
			if (StringUtils.isNumeric(cmd)) {
				targetAnimal = AnimalFactory.getAnimal(Integer.parseInt(cmd));
			}
			if (targetAnimal != null) {
				break;
			}
			System.err.println("存在する動物の個体識別IDを入力してください。");
		}

		try {
			// 動物にリボン設定
			RibbonManager.setRibbon(robbonCd, targetAnimal);
		} catch (BusinessException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * 全動物リスト表示
	 */
	private void printAllAnimals() {
		List<String> allAnimalInfList = DBAccess.INSTANCE.getAllAnimalInfList(); 
		for (String inf : allAnimalInfList) {
			System.out.println(inf);
		}
	}

	/**
	 * リボン付き動物リスト表示
	 */
	private void printRibbonAnimals() {
		Map<RibbonType, Animal> ribbonMap = RibbonManager.getRibbonMap();
		int id = 1;
		for (Entry<RibbonType, Animal> entry : ribbonMap.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(id).append("\t");
			sb.append(entry.getKey().getName()).append("\t");
			sb.append(entry.getValue().getId()).append("\t");
			sb.append(entry.getValue().getName()).append("\t");
			sb.append(entry.getValue().getWeight());
			System.out.println(sb.toString());
			id++;
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
