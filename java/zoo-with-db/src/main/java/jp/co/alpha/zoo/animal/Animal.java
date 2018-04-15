package jp.co.alpha.zoo.animal;

/**
 * 動物インターフェース
 */
public interface Animal {
	/**
	 * 個体識別ID
	 * @return
	 */
	int getId();
	
	/**
	 * 動物名取得
	 * @return
	 */
	String getName();
	
	/**
	 * 体重取得
	 * @return
	 */
	int getWeight();
}
