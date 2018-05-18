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
		DBAccess dba = DBAccess.getInstance();
		cageList = dba.getCages();
	}

	public static CageFactory getInstance() {
		return INSTANCE;
	}
	
	/**
	 * 指定の名前の檻を取得
	 * 
	 * @param cd
	 * @return
	 */
	public Cage getCage(int cd) {
		Cage targetCage = null;
		for (Cage cage : cageList) {
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
	public List<Cage> getAllCages() {
		return Collections.unmodifiableList(cageList);
	}
}
