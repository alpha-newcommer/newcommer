package jp.co.alpha.zoo.cage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.animal.Rabbit;
import jp.co.alpha.zoo.exception.BusinessException;

public class CuteCageTest {
	private CuteCage cage;
	
	@Before
	public void init() {
		cage = new CuteCage("かわゆす");
		cage.setCd(1);
	}

	@Test
	public void testCheck1() {
		assertThat(cage.getName(), is("かわゆす"));
		assertThat(cage.getCd(), is(1));
		
		cage.setName("げきかわ");
		assertThat(cage.getName(), is("げきかわ"));
	}
	
	@Test
	public void testCheck2() {
		Animal animal = new Rabbit(2, 1);
		
		try {
			cage.in(animal);
			assertTrue(cage.getAllAnimals().get(0).equals(animal));
		} catch (BusinessException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testCheck3() {
		Animal animal = new Rabbit(2, 3);
		
		try {
			cage.in(animal);
			fail("ビジネスエラーにならないとおかしい");
		} catch (BusinessException e) {
			assertThat(e.getMessage(), is("1匹2kgまでの制限を超えました。重量：3"));
		}
	}

}
