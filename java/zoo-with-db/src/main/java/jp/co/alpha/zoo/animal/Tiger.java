package jp.co.alpha.zoo.animal;

public class Tiger implements Animal {
	public static final String NAME = "トラ";
	private int id;
	private int weight;

	public Tiger(Integer id, Integer weight) {
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
