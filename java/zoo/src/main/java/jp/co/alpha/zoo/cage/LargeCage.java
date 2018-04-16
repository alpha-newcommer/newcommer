package jp.co.alpha.zoo.cage;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

public class LargeCage extends Cage {
	
	protected LargeCage(String name) {
		super(name);
	}

	@Override
	void check(Animal animal) throws BusinessException {
		int totalWeight = getAllAnimals().stream().mapToInt(Animal::getWeight).sum() + animal.getWeight();
		if (totalWeight > 1000) {
			throw new BusinessException("1000kgまでの制限を超えました。重量：" + totalWeight);
		}
	}

}
