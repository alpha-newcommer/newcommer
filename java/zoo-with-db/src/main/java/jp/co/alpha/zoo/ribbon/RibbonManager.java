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
		DBAccess dba = DBAccess.getInstance();

		ribbonTypeList = dba.getRibbonTypes();
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
		DBAccess dba = DBAccess.getInstance();

		RibbonType type = getRibbonType(cd);
		if (type == null) {
			throw new BusinessException("未定義のリボンコードが指定されました。リボンコード：" + cd);
		}
		dba.setRibbon(cd, animal);
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
	public Map<RibbonType, Animal> getRibbonMap() {
		DBAccess dba = DBAccess.getInstance();

		return dba.getRibbonMap();
	}
	
	public RibbonType getRibbonType(int cd) {
		RibbonType targetType = null;
		for (RibbonType type : ribbonTypeList) {
			if (type.getCd() == cd) {
				targetType = type;
				break;
			}
		}
		
		return targetType;
	}
}
