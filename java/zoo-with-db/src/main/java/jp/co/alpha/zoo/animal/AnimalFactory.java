package jp.co.alpha.zoo.animal;

import java.util.List;

import jp.co.alpha.zoo.db.DBAccess;
import jp.co.alpha.zoo.exception.SystemException;

/**
 * 動物ファクトリー
 */
public class AnimalFactory {

	/**
	 * コンストラクタ
	 */
	private AnimalFactory() {
	}

	/**
	 * 指定の名前と体重の動物オブジェクトを作成して取得
	 * 
	 * @param animalName
	 * @param weight
	 * @return
	 */
	public static Animal createAnimal(String animalName, int weight) {
		List<String> nameList = getAnimalNames();
		if (!nameList.contains(animalName)) {
			throw new SystemException("存在しない動物名を指定されました。");
		}
		
		AnimalImpl animal = new AnimalImpl();

		animal.setName(animalName);
		animal.setWeight(weight);

		return animal;
	}

	/**
	 * 管理している動物名をリストで取得
	 * 
	 * @return
	 */
	public static List<String> getAnimalNames() {
		return DBAccess.INSTANCE.getAnimalNames();
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
