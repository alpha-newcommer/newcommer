package jp.co.alpha.zoo.animal;

public abstract class Animal {
	private int weight = 0;
	/**
	 * @return the nAME
	 */
	public abstract String getName();
	
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
}
