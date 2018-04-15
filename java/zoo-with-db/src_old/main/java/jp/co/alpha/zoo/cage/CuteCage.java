package jp.co.alpha.zoo.cage;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

public class CuteCage extends Cage {
	private static final String NAME = "かわいい檻";

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	void check(Animal animal) throws BusinessException {
		if (animal.getWeight() > 2) {
			throw new BusinessException("1匹2kgまでの制限を超えました。重量：" + animal.getWeight());
		}
	}

}
