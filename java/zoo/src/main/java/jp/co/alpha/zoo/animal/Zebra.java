package jp.co.alpha.zoo.animal;

public class Zebra implements Animal {
	private static String NAME = "シマウマ";
	private int weight;
	
	public Zebra(Integer weight) {
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
