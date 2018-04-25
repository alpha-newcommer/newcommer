package jp.co.alpha.zoo.animal;

public class Rabbit implements Animal {
	public static final String NAME = "ウサギ";
	private int id;
	private int weight;

	public Rabbit(Integer id, Integer weight) {
		this.id = id;
		this.weight = weight;
	}

	@Override
	public int getId() {
		return id;
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
