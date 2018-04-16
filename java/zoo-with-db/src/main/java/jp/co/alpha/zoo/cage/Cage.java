package jp.co.alpha.zoo.cage;

import java.util.Collections;
import java.util.List;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.db.DBAccess;
import jp.co.alpha.zoo.exception.BusinessException;

/**
 * 檻インターフェース
 */
public abstract class Cage {
	/**
	 * コード
	 */
	private int cd;
	
	/**
	 * 檻名
	 */
	private String name;

	/**
	 * コンストラクタ
	 */
	protected Cage(String name) {
		this.name = name;
	}
	
	/**
	 * この檻で管理している動物のオブジェクトをリストで取得
	 * @return
	 */
	public List<Animal> getAllAnimals() {
		return Collections.unmodifiableList(DBAccess.INSTANCE.getAnimals(this));
	}
	
	/**
	 * この檻に指定の動物を格納
	 * @param animal
	 * @throws BusinessException
	 */
	public void in(int animalCd, Animal animal) throws BusinessException {
		// 格納可能チェック
		check(animal);
		// 檻に動物オブジェクトを格納
		DBAccess.INSTANCE.addAnimalToCage(this, animalCd, animal.getWeight());
	}
	
	/**
	 * 指定の動物を格納可能かを判定
	 * @param animal
	 * @throws BusinessException 格納条件を満たさない場合
	 */
	abstract void check(Animal animal) throws BusinessException;

	/**
	 * @return the cd
	 */
	public int getCd() {
		return cd;
	}

	/**
	 * @param cd the cd to set
	 */
	public void setCd(int cd) {
		this.cd = cd;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
