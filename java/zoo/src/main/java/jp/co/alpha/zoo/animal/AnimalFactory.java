package jp.co.alpha.zoo.animal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.alpha.zoo.exception.SystemException;

public class AnimalFactory {
	private static AnimalFactory INSTANCE = new AnimalFactory();
	Map<String, Class<? extends Animal>> animalMap;

	private AnimalFactory() {
		animalMap = new HashMap<>();
		animalMap.put("rabbit", Rabbit.class);
		animalMap.put("tiger", Tiger.class);
		animalMap.put("zebra", Zebra.class);
	}

	public static Animal createAnimal(String animalName, int weight) {
		Animal animal = null;
		try {
			animal = INSTANCE.animalMap.get(animalName).getConstructor(Integer.class).newInstance(weight);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new SystemException("存在しない動物名を指定されました。");
		}

		return animal;
	}
	
	public static List<String> getAnimalNames() {
		return Collections.unmodifiableList(new ArrayList<String>(INSTANCE.animalMap.keySet()));
	}
}
