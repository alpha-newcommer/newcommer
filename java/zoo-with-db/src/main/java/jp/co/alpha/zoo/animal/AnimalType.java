package jp.co.alpha.zoo.animal;

public class AnimalType {
	/**
	 * id
	 */
	private int id;
	/**
	 * 種族名
	 */
	private String name;
	
	/**
	 * コンストラクタ
	 * @param id
	 * @param name
	 */
	public AnimalType(int id, String name) {
		this.id = id;
		this.name = name;
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
	
	
}
