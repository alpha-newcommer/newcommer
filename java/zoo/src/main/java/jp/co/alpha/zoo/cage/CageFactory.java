package jp.co.alpha.zoo.cage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 檻ファクトリー
 */
public class CageFactory {
	/**
	 * インスタンス
	 */
	private static final CageFactory INSTANCE = new CageFactory();

	/**
	 * 檻の管理リスト
	 */
	private final List<Cage> cageList;

	/**
	 * コンストラクタ
	 */
	private CageFactory() {
		cageList = new ArrayList<>();
		cageList.add(new NormalCage("普通の檻"));
		cageList.add(new CuteCage("可愛い檻"));
		cageList.add(new HardyCage("頑丈な檻"));
		cageList.add(new LargeCage("広い檻"));
		// コード割振り
		int id = 1;
		for (Cage cage : cageList) {
			cage.setCd(id++);
		}
	}

	/**
	 * 指定の名前の檻を取得
	 * 
	 * @param cd
	 * @return
	 */
	public static Cage getCage(int cd) {
		Cage targetCage = null;
		for (Cage cage : INSTANCE.cageList) {
			if (cage.getCd() == cd) {
				targetCage = cage;
				break;
			}
		}
		return targetCage;
	}

	/**
	 * 管理している檻オブジェクトをリストで取得
	 * 
	 * @return
	 */
	public static List<Cage> getAllCages() {
		return Collections.unmodifiableList(INSTANCE.cageList);
	}
}
