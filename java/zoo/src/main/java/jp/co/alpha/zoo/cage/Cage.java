package jp.co.alpha.zoo.cage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

/**
 * 檻インターフェース
 */
public abstract class Cage {
	/**
	 * 動物管理リスト
	 */
	protected final List<Animal> animalList;

	/**
	 * コンストラクタ
	 */
	protected Cage() {
		animalList = new ArrayList<>();
	}
	
	/**
	 * 檻名取得
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * この檻で管理している動物のオブジェクトをリストで取得
	 * @return
	 */
	public List<Animal> getAllAnimals() {
		return Collections.unmodifiableList(animalList);
	}
	
	/**
	 * この檻に指定の動物を格納
	 * @param animal
	 * @throws BusinessException
	 */
	public void in(Animal animal) throws BusinessException {
		// 格納可能チェック
		check(animal);
		// 檻に動物オブジェクトを格納
		animalList.add(animal);	
	}
	
	/**
	 * 指定の動物を格納可能かを判定
	 * @param animal
	 * @throws BusinessException 格納条件を満たさない場合
	 */
	abstract void check(Animal animal) throws BusinessException;
}
