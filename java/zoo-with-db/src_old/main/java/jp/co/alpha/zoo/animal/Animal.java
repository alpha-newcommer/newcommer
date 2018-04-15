package jp.co.alpha.zoo.animal;

/**
 * 動物クラス
 */
public class Animal {
	/**
	 * 個体識別ID
	 */
	private int id;
	
	/**
	 * 種族名取得
	 * @return
	 */
	private String name;
	
	/**
	 * 体重取得
	 * @return
	 */
	private int weight;
	
	/**
	 * コンストラクタ
	 * @param id
	 * @param name
	 * @param weight
	 */
	public Animal(int id, String name, int weight) {
		this.id = id;
		this.name = name;
		this.weight = weight;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	
}
