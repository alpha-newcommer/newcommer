package jp.co.alpha.zoo.animal;

public class AnimalType {
	private int cd;
	private String name;
	private Class<? extends Animal> type;
	
	public AnimalType(int cd, String name, Class<? extends Animal> type) {
		this.cd = cd;
		this.name = name;
		this.type = type;
	}
	
	/**
	 * @return the cd
	 */
	public int getCd() {
		return cd;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the type
	 */
	public Class<? extends Animal> getType() {
		return type;
	}
}
