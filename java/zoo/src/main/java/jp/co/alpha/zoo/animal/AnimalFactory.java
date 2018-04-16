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
	 * 動物のコードとクラスの管理マップ
	 */
	private final List<AnimalType> animalTypeList;
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
		animalTypeList = new ArrayList<>();
		animalTypeList.add(new AnimalType(1, Rabbit.NAME, Rabbit.class));
		animalTypeList.add(new AnimalType(2, Tiger.NAME, Tiger.class));
		animalTypeList.add(new AnimalType(3, Zebra.NAME, Zebra.class));
		animalTypeList.add(new AnimalType(4, Owl.NAME, Owl.class));

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
	public static Animal createAnimal(int cd, int weight) {
		Animal animal = null;
		try {
			int id = INSTANCE.idCounter++;
			for (AnimalType animalType: INSTANCE.animalTypeList) {
				if (animalType.getCd() == cd) {
					animal = animalType.getType().getConstructor(Integer.class, Integer.class).newInstance(id, weight);
					break;
				}
			}
			INSTANCE.animalMap.put(id, animal);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new SystemException("存在しない動物名を指定されました。", e);
		}

		return animal;
	}

	/**
	 * 管理している動物のコードとクラスの管理リストを取得
	 * 
	 * @return
	 */
	public static List<AnimalType> getAnimalTypeList() {
		return Collections.unmodifiableList(INSTANCE.animalTypeList);
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
