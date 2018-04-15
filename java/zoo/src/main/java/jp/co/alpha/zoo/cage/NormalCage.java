package jp.co.alpha.zoo.cage;

import java.util.List;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

public class NormalCage extends Cage {
	private static final String NAME = "普通の檻";

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public void check(Animal animal) throws BusinessException {
		List<Animal> animalList = getAllAnimals();
		int num = animalList.size() + 1;
		int totalWeight = animalList.stream().mapToInt(Animal::getWeight).sum() + animal.getWeight();
		if ((num > 2) || (totalWeight > 250)) {
			throw new BusinessException("250kgを2頭までの制限を超えました。重量：" + totalWeight + "、頭数：" + num);
		}
	}

}
