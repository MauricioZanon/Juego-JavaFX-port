package RNG;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;

import main.Entity;
import main.Type;

public class RNGTests {
	
	@Test
	public void randomFromEmptyCollections(){
		Set<Object> set = new HashSet<>();
		assertEquals("No se devolvió null cuando se pidió un elemento random de un set vacío", null, RNG.getRandom(set));
		
		List<Object> list = new LinkedList<>();
		assertEquals("No se devolvió null cuando se pidió un elemento random de una lista vacía", null, RNG.getRandom(list));
		
		Object[][] array = new Object[10][10];
		assertEquals("No se devolvió null cuando se pidió un elemento random de un array vacío", null, RNG.getRandom(array));
	}

	@Test
	public void randomFromEmptyCollectionsWithPredicate(){
		Predicate<Object> pred = o -> o.toString().equals("0");
		Set<Object> set = new HashSet<>();
		assertEquals("No se devolvió null cuando se pidió un elemento random de un set vacío", null, RNG.getRandom(set, pred));
		
		List<Object> list = new LinkedList<>();
		assertEquals("No se devolvió null cuando se pidió un elemento random de una lista vacía", null, RNG.getRandom(list, pred));
	}
	
	@Test
	public void randomFromCollectionWithPredicate() {
		Predicate<Entity> pred = e -> e.ID == 0;
		Entity foundEntity;
		
		Set<Entity> set = new HashSet<>();
		for(int i = 0; i < 100; i++) {
			set.add(new Entity(Type.ACTOR, i, "name"));
		}
		foundEntity = RNG.getRandom(set, pred);
		assertTrue("No se devolvió null cuando se pidió un elemento random de un set vacío", foundEntity != null && foundEntity.ID == 0);
		
		List<Entity> list = new LinkedList<>();
		for(int i = 0; i < 100; i++) {
			list.add(new Entity(Type.ACTOR, i, "name"));
		}
		foundEntity = RNG.getRandom(list, pred);
		assertTrue("No se devolvió null cuando se pidió un elemento random de un set vacío", foundEntity != null && foundEntity.ID == 0);
		
		Entity[][] array = new Entity[10][10];
		for(int i = 0; i < array.length; i++){
			for (int j = 0; j < array[0].length; j++) {
				array[i][j] = new Entity(Type.ACTOR, i, "name");
			}
		}
		foundEntity = RNG.getRandom(array, pred);
		assertTrue("No se devolvió null cuando se pidió un elemento random de un set vacío", foundEntity != null && foundEntity.ID == 0);
	}
	
	@Test
	@RepeatedTest(1000)
	public void nextIntBetweenTwoValues() {
		int a = 100;
		int b = 200;
		int result = RNG.nextInt(a, b);
		assertTrue(a <= result && result < b);
	}
	
	@Test
	public void nextIntBetweenTwoConsecutiveValues() {
		int a = 1;
		int b = 2;
		int result = RNG.nextInt(a, b);
		assertTrue(result == a);
	}
	
	@Test
	public void nextIntBetweenTwoEqualValues() {
		int a = 1;
		int b = 1;
		int result = RNG.nextInt(a, b);
		assertTrue(result == a);
	}
	
	@Test
	public void nextIntBetweenNegativeAnPositiveValues() {
		int a = -100;
		int b = 100;
		int result = RNG.nextInt(a, b);
		assertTrue(a <= result && result < b);
	}
	
	@Test
	@RepeatedTest(1000)
	public void gaussianValues() {
		int mean = 100;
		int variation = 30;
		int min = mean - variation;
		int max = mean + variation;
		int result = RNG.nextGaussian(mean, variation);
		assertTrue("El gaussiano se salió del límite " + result, min <= result && result <= max);
		
		variation = 1;
		min = mean - variation;
		max = mean + variation;
		result = RNG.nextGaussian(mean, variation);
		assertTrue("El gaussiano se salió del límite " + result, min <= result && result <= max);

		variation = 0;
		min = mean - variation;
		max = mean + variation;
		result = RNG.nextGaussian(mean, variation);
		assertTrue("El gaussiano se salió del límite " + result, min <= result && result <= max);
	}

}
