package jp.co.alpha.zoo.animal;

public class Rabbit implements Animal {
	private static String NAME = "ウサギ";
	private int weight;
	
	public Rabbit(Integer weight) {
		this.weight = weight;
	}
	@Override
	public int getWeight() {
		return weight;
	}
	@Override
	public String getName() {
		return NAME;
	}
}
