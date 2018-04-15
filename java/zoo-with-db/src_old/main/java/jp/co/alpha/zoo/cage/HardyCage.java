package jp.co.alpha.zoo.cage;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

public class HardyCage extends Cage {
	private static final String NAME = "頑丈な檻";

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	void check(Animal animal) throws BusinessException {
		int num = animalList.size() + 1;
		if (num > 3) {
			throw new BusinessException("3頭までの制限を超えました。頭数：" + num);
		}
	}

}
