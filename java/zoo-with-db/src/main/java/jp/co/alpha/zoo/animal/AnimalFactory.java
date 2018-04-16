package jp.co.alpha.zoo.animal;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

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
	 * 動物のコードとクラスの管理マップ
	 */
	private final List<AnimalType> animalTypeList;

	/**
	 * コンストラクタ
	 */
	private AnimalFactory() {
		animalTypeList = DBAccess.INSTANCE.getAnimalTypeList();
	}

	/**
	 * 指定の名前と体重の動物オブジェクトを作成して取得
	 * 
	 * @param animalName
	 * @param weight
	 * @return
	 */
	public static Animal createAnimal(int cd, int id, int weight) {
		Animal animal = null;
		try {
			for (AnimalType animalType: INSTANCE.animalTypeList) {
				if (animalType.getCd() == cd) {
					animal = animalType.getType().getConstructor(Integer.class, Integer.class).newInstance(id, weight);
					break;
				}
			}
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
		return DBAccess.INSTANCE.getAnimal(id);
	}
}
