package jp.co.alpha.zoo.cage;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

public class HardyCage extends Cage {
	@Override
	void check(Animal animal) throws BusinessException {
		int num = animalList.size() + 1;
		if (num > 3) {
			throw new BusinessException("3頭までの制限を超えました。頭数：" + num);
		}
	}

}
