package jp.co.alpha.zoo.animal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.alpha.zoo.exception.SystemException;

/**
 * 動物ファクトリー
 */
public class AnimalFactory {
	/**
	 * インスタンス
	 */
	private static final AnimalFactory INSTANCE = new AnimalFactory();
	/**
	 * 動物の名前とクラスの管理マップ
	 */
	private final Map<String, Class<? extends Animal>> animalMap;

	/**
	 * コンストラクタ
	 */
	private AnimalFactory() {
		animalMap = new HashMap<>();
		animalMap.put("rabbit", Rabbit.class);
		animalMap.put("tiger", Tiger.class);
		animalMap.put("zebra", Zebra.class);
	}

	/**
	 * 指定の名前と体重の動物オブジェクトを作成して取得
	 * @param animalName
	 * @param weight
	 * @return
	 */
	public static Animal createAnimal(String animalName, int weight) {
		Animal animal = null;
		try {
			animal = INSTANCE.animalMap.get(animalName).getConstructor(Integer.class).newInstance(weight);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new SystemException("存在しない動物名を指定されました。", e);
		}

		return animal;
	}
	
	/**
	 * 管理している動物名をリストで取得
	 * @return
	 */
	public static List<String> getAnimalNames() {
		return Collections.unmodifiableList(new ArrayList<String>(INSTANCE.animalMap.keySet()));
	}
}
