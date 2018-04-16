package jp.co.alpha.zoo.ribbon;

/**
 * リボン種
 */
public class RibbonType {
	private int cd;
	private String name;
	
	/**
	 * @param cd
	 * @param name
	 */
	public RibbonType(int cd, String name) {
		this.cd = cd;
		this.name = name;
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
	
}
