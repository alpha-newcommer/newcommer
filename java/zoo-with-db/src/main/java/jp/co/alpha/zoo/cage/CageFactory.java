package jp.co.alpha.zoo.cage;

import java.util.Collections;
import java.util.List;

import jp.co.alpha.zoo.db.DBAccess;

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
		cageList = DBAccess.INSTANCE.getCages();
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
