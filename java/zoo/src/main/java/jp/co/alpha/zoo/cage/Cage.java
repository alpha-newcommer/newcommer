package jp.co.alpha.zoo.cage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.exception.BusinessException;

public abstract class Cage {
	protected List<Animal> animalList;

	protected Cage() {
		animalList = new ArrayList<>();
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	public List<Animal> getAllAnimals() {
		return Collections.unmodifiableList(animalList);
	}
	
	public void in(Animal animal) throws BusinessException {
		check(animal);
		animalList.add(animal);	
	}
	
	abstract void check(Animal animal) throws BusinessException;
}
