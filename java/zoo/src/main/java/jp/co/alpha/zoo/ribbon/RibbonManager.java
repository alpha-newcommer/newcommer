package jp.co.alpha.zoo.ribbon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

public class RibbonManager {
	private static RibbonManager INSTANCE = new RibbonManager();

	private final Map<String, Animal> ribbonMap;

	private RibbonManager() {
		ribbonMap = new HashMap<>();
		ribbonMap.put("草食アイドルリボン", null);
		ribbonMap.put("肉食系女子リボン", null);
		ribbonMap.put("空の王者風リボン", null);
		ribbonMap.put("ふわふわ代表リボン", null);
		ribbonMap.put("お触られマスターリボン", null);
	}

	public static void setRibbon(String ribbonName, Animal animal) throws BusinessException {
		if (!INSTANCE.ribbonMap.containsKey(ribbonName)) {
			throw new BusinessException("未定義のリボン名が指定されました。リボン名：" + ribbonName);
		}
		INSTANCE.ribbonMap.put(ribbonName, animal);
	}
	
	public static List<String> getRibbonNames() {
		return new ArrayList<>(INSTANCE.ribbonMap.keySet());
	}
	
	public static Map<String, Animal> getRibbonMap() {
		return Collections.unmodifiableMap(INSTANCE.ribbonMap);
	}
}
