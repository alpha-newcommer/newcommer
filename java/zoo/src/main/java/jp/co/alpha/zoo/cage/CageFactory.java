package jp.co.alpha.zoo.cage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CageFactory {
	private static CageFactory INSTANCE = new CageFactory();
	
	Map<String, Cage> cageMap;
	
	private CageFactory() {
		cageMap = new HashMap<>();
		cageMap.put("normal", new NormalCage());
		cageMap.put("cute", new CuteCage());
		cageMap.put("hardy", new HardyCage());
		cageMap.put("large", new LargeCage());
	}
	
	public static Cage getCage(String cageName) {
		return INSTANCE.cageMap.get(cageName);
	}
	
	public static List<String> getCageNames() {
		return Collections.unmodifiableList(new ArrayList<>(INSTANCE.cageMap.keySet()));
	}
	
	public static List<Cage> getAllCages() {
		return Collections.unmodifiableList(new ArrayList<>(INSTANCE.cageMap.values()));
	}
}
