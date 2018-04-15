package jp.co.alpha.zoo.animal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.alpha.zoo.db.DBAccess;
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
	 * 動物のマスター情報の管理マップ
	 */
	private final List<AnimalType> animalTypes;

	/**
	 * コンストラクタ
	 */
	private AnimalFactory() {
		animalTypes = DBAccess.INSTANCE.getAnimalTypes();
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
		
		// TODO DB add
//		try {
//			int id = INSTANCE.idCounter++;
//			Class<? extends Animal> targetAnimalClass = INSTANCE.animalClassMap.get(animalName);
//			animal = targetAnimalClass.getConstructor(Integer.class, Integer.class).newInstance(id, weight);
//			INSTANCE.animalMap.put(id, animal);
//		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
//				| NoSuchMethodException | SecurityException e) {
//			throw new SystemException("存在しない動物名を指定されました。", e);
//		}

		return animal;
	}

	/**
	 * 管理している動物名をリストで取得
	 * 
	 * @return
	 */
	public static List<String> getAnimalNames() {
		List<String> nameList = new ArrayList<>();
		INSTANCE.animalTypes.forEach(type -> nameList.add(type.getName()));
		return Collections.unmodifiableList(new ArrayList<String>(nameList));
	}
	
	/**
	 * 指定の個体識別IDの動物オブジェクトを取得
	 * @param id
	 * @return
	 */
	public static Animal getAnimal(int id) {
		Animal animal = null;
		// TODO DB get
//		return INSTANCE.animalMap.get(id);
		return animal;
	}
}
