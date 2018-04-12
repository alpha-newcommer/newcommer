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
	private final Map<String, Class<? extends Animal>> animalClassMap;
	/**
	 * 生成した動物の個体識別IDとオブジェクトの管理マップ
	 */
	private final Map<Integer, Animal> animalMap;
	/**
	 * 個体識別ID生成用変数
	 */
	private int idCounter;

	/**
	 * コンストラクタ
	 */
	private AnimalFactory() {
		animalClassMap = new HashMap<>();
		animalClassMap.put("rabbit", Rabbit.class);
		animalClassMap.put("tiger", Tiger.class);
		animalClassMap.put("zebra", Zebra.class);
		animalClassMap.put("owl", Owl.class);

		animalMap = new HashMap<>();
		idCounter = 1;
	}

	/**
	 * 指定の名前と体重の動物オブジェクトを作成して取得
	 * 
	 * @param animalName
	 * @param weight
	 * @return
	 */
	public static Animal createAnimal(String animalName, int weight) {
		Animal animal = null;
		try {
			int id = INSTANCE.idCounter++;
			Class<? extends Animal> targetAnimalClass = INSTANCE.animalClassMap.get(animalName);
			animal = targetAnimalClass.getConstructor(Integer.class, Integer.class).newInstance(id, weight);
			INSTANCE.animalMap.put(id, animal);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new SystemException("存在しない動物名を指定されました。", e);
		}

		return animal;
	}

	/**
	 * 管理している動物名をリストで取得
	 * 
	 * @return
	 */
	public static List<String> getAnimalNames() {
		return Collections.unmodifiableList(new ArrayList<String>(INSTANCE.animalClassMap.keySet()));
	}
	
	/**
	 * 指定の個体識別IDの動物オブジェクトを取得
	 * @param id
	 * @return
	 */
	public static Animal getAnimal(int id) {
		return INSTANCE.animalMap.get(id);
	}
}
