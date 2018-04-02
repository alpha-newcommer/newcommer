package jp.co.alpha.zoo.animal;

public class Tiger implements Animal {
	private static String NAME = "トラ";
	private int weight;
	
	public Tiger(Integer weight) {
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
