package jp.co.alpha.zoo.ribbon;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.db.DBAccess;
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
	 * コンストラクタ
	 */
	private RibbonManager() {
		ribbonTypeList = DBAccess.INSTANCE.getRibbonTypes();
	}

	/**
	 * リボン設定
	 * @param ribbonName
	 * @param animal
	 * @throws BusinessException
	 */
	public static void setRibbon(int cd, Animal animal) throws BusinessException {
		RibbonType type = getRibbonType(cd);
		if (type == null) {
			throw new BusinessException("未定義のリボンコードが指定されました。リボンコード：" + cd);
		}
		DBAccess.INSTANCE.setRibbon(cd, animal);
	}
	
	/**
	 * リボン名のリスト取得
	 * @return
	 */
	public static List<RibbonType> getRibbonTypes() {
		return Collections.unmodifiableList(INSTANCE.ribbonTypeList);
	}
	
	/**
	 * リボンと動物のマッピングを取得
	 * @return
	 */
	public static Map<RibbonType, Animal> getRibbonMap() {
		return DBAccess.INSTANCE.getRibbonMap();
	}
	
	public static RibbonType getRibbonType(int cd) {
		RibbonType targetType = null;
		for (RibbonType type : INSTANCE.ribbonTypeList) {
			if (type.getCd() == cd) {
				targetType = type;
				break;
			}
		}
		
		return targetType;
	}
}
