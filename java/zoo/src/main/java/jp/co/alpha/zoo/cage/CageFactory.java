package jp.co.alpha.zoo.cage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 檻ファクトリー
 */
public class CageFactory {
	/**
	 * インスタンス
	 */
	private static final CageFactory INSTANCE = new CageFactory();
	
	/**
	 * 檻の名前とオブジェクトの管理マップ
	 */
	private final Map<String, Cage> cageMap;
	
	/**
	 * コンストラクタ
	 */
	private CageFactory() {
		cageMap = new HashMap<>();
		cageMap.put("normal", new NormalCage());
		cageMap.put("cute", new CuteCage());
		cageMap.put("hardy", new HardyCage());
		cageMap.put("large", new LargeCage());
	}
	
	/**
	 * 指定の名前の檻を取得
	 * @param cageName
	 * @return
	 */
	public static Cage getCage(String cageName) {
		return INSTANCE.cageMap.get(cageName);
	}
	
	/**
	 * 管理している檻の名前をリストで取得
	 * @return
	 */
	public static List<String> getCageNames() {
		return Collections.unmodifiableList(new ArrayList<>(INSTANCE.cageMap.keySet()));
	}
	
	/**
	 * 管理している檻オブジェクトをリストで取得
	 * @return
	 */
	public static List<Cage> getAllCages() {
		return Collections.unmodifiableList(new ArrayList<>(INSTANCE.cageMap.values()));
	}
}
