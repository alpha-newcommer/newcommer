package jp.co.alpha.zoo.cage;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

public interface Cage {
	void in(Animal animal) throws BusinessException;
}
