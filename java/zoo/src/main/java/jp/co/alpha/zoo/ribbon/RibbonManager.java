package jp.co.alpha.zoo.ribbon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

/**
 * リボン管理
 */
public class RibbonManager {
	/**
	 * インスタンス
	 */
	private static final RibbonManager INSTANCE = new RibbonManager();
	
	/**
	 * リボン種管理リスト
	 */
	private final List<RibbonType> ribbonTypeList;
	
	/**
	 * リボンと動物のマッピング
	 */
	private final Map<String, Animal> ribbonMap;

	/**
	 * コンストラクタ
	 */
	private RibbonManager() {
		ribbonTypeList = new ArrayList<>();
		ribbonTypeList.add(new RibbonType(1, "草食アイドルリボン"));
		ribbonTypeList.add(new RibbonType(2, "肉食系女子リボン"));
		ribbonTypeList.add(new RibbonType(3, "空の王者風リボン"));
		ribbonTypeList.add(new RibbonType(4, "ふわふわ代表リボン"));
		ribbonTypeList.add(new RibbonType(5, "お触られマスターリボン"));
		ribbonMap = new HashMap<>();
	}
	
	public static RibbonManager getInstance() {
		return INSTANCE;
	}

	/**
	 * リボン設定
	 * @param ribbonName
	 * @param animal
	 * @throws BusinessException
	 */
	public void setRibbon(int cd, Animal animal) throws BusinessException {
		String ribbonName = null;
		for (RibbonType type : ribbonTypeList) {
			if (type.getCd() == cd) {
				ribbonName = type.getName();
				break;
			}
		}
		if (ribbonName == null) {
			throw new BusinessException("未定義のリボンコードが指定されました。リボンコード：" + cd);
		}
		ribbonMap.put(ribbonName, animal);
	}
	
	/**
	 * リボン名のリスト取得
	 * @return
	 */
	public List<RibbonType> getRibbonTypes() {
		return Collections.unmodifiableList(ribbonTypeList);
	}
	
	/**
	 * リボンと動物のマッピングを取得
	 * @return
	 */
	public Map<String, Animal> getRibbonMap() {
		return Collections.unmodifiableMap(ribbonMap);
	}
}
