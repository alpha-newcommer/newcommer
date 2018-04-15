package jp.co.alpha.zoo.cage;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

public class LargeCage extends Cage {
	private static final String NAME = "広い檻";

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	void check(Animal animal) throws BusinessException {
		int totalWeight = animalList.stream().mapToInt(Animal::getWeight).sum() + animal.getWeight();
		if (totalWeight > 1000) {
			throw new BusinessException("1000kgまでの制限を超えました。重量：" + totalWeight);
		}
	}

}
