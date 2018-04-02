package jp.co.alpha.zoo.cage;

import java.util.ArrayList;
import java.util.List;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

public class NoramalCage implements Cage {
	private List<Animal> animalList;

	public NoramalCage() {
		animalList = new ArrayList<>();
	}

	@Override
	public void in(Animal animal) throws BusinessException {
		int num = animalList.size() + 1;
		int totalWeight = animalList.stream().mapToInt(Animal::getWeight).sum() + animal.getWeight();
		if ((num > 2) || (totalWeight > 250)) {
			throw new BusinessException("250kgを2頭までの制限を超えました。重量：" + totalWeight + "、頭数：" + num);
		}
		
		animalList.add(animal);
	}

}
