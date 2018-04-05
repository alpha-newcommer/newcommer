package jp.co.alpha.zoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.animal.AnimalFactory;
import jp.co.alpha.zoo.cage.Cage;
import jp.co.alpha.zoo.cage.CageFactory;
import jp.co.alpha.zoo.exception.BusinessException;
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
			switch (cmd) {
			case "1":
				putAnimal(in);
				break;
			case "2":
				printAllAnimals();
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

	private void putAnimal(BufferedReader in) throws IOException {
		String cmd = null;

		// ケージ番号のいずれかを入力させる
		Map<String, String> menuMap = new LinkedHashMap<>();
		List<String> cageNames = CageFactory.getCageNames();
		for (int i = 0; i < cageNames.size(); i++) {
			menuMap.put(String.valueOf(i + 1), cageNames.get(i));
		}
		cmd = getCommand(menuMap, in, "ケージ番号選択", "入力誤り。ケージ番号を入力してください。");
		Cage cage = CageFactory.getCage(cageNames.get(Integer.parseInt(cmd) - 1));

		// 動物の番号のいずれかを入力するまでループ
		menuMap.clear();
		List<String> animalNames = AnimalFactory.getAnimalNames();
		for (int i = 0; i < animalNames.size(); i++) {
			menuMap.put(String.valueOf(i + 1), animalNames.get(i));
		}
		cmd = getCommand(menuMap, in, "動物の番号選択", "入力誤り。動物の番号を入力してください。");
		String animalName = animalNames.get(Integer.parseInt(cmd) - 1);

		// 動物の体重を入力するまでループ
		cmd = null;
		while (!StringUtils.isNumeric(cmd)) {
			System.out.print(animalName + "の体重（kg）を入力＞");
			cmd = in.readLine();
		}
		int weight = Integer.valueOf(cmd);

		// 動物名と体重から動物オブジェクト生成
		Animal animal = AnimalFactory.createAnimal(animalName, weight);

		// ケージに動物を入れる
		try {
			cage.in(animal);
		} catch (BusinessException e) {
			System.err.println(e.getMessage());
		}

	}

	private String menue(BufferedReader in) throws IOException {
		Map<String, String> menuMap = new LinkedHashMap<>();
		menuMap.put("1", "檻に動物を入れる");
		menuMap.put("2", "動物にリボン贈呈");
		menuMap.put("3", "全動物リスト表示");
		menuMap.put("4", "リボン付き動物リスト表示");
		menuMap.put("9", "アプリ終了");
		String cmd = getCommand(menuMap, in, "メニュー選択", "入力誤り。メニュー番号を入力してください。");

		return cmd;
	}

	private String getCommand(Map<String, String> menuMap, BufferedReader in, String orderMsg, String errMsg)
			throws IOException {
		String cmd = null;
		// ケージ番号のいずれかを入力するまでループ
		while (StringUtils.isEmpty(cmd)) {
			for (Entry<String, String> entry : menuMap.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			System.out.print(orderMsg + "＞");
			cmd = in.readLine();
			if (!ArrayUtils.contains(menuMap.keySet().toArray(new String[0]), cmd)) {
				System.err.println(errMsg);
				cmd = null;
				continue;
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
