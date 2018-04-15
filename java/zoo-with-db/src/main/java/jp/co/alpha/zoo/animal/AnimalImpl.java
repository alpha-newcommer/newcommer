package jp.co.alpha.zoo.animal;

public class AnimalImpl implements Animal {
	private int id;
	private int cageCd;
	private String name;
	private int weight;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	public int getCageCd() {
		return cageCd;
	}

	public void setCageCd(int cageCd) {
		this.cageCd = cageCd;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
