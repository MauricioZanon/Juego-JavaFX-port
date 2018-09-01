package RNG;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;

import main.JavaFXThreadingRule;

public class RNGTests {
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Test
	public void randomFromEmptyCollections(){
		Set<Object> set = new HashSet<>();
		assertEquals("No se devolvió null cuando se pidió un elemento random de una collección vacía", null, RNG.getRandom(set));
		
		List<Object> list = new LinkedList<>();
		assertEquals("No se devolvió null cuando se pidió un elemento random de una collección vacía", null, RNG.getRandom(list));
		
		Object[][] array = new Object[10][10];
		assertEquals("No se devolvió null cuando se pidió un elemento random de una collección vacía", null, RNG.getRandom(array));
	}

	@Test
	public void randomFromEmptyCollectionsWithPredicate(){
		Predicate<Object> pred = o -> o.toString().equals("0");
		Set<Object> set = new HashSet<>();
		assertEquals("No se devolvió null cuando se pidió un elemento random de una collección vacía", null, RNG.getRandom(set, pred));
		
		List<Object> list = new LinkedList<>();
		assertEquals("No se devolvió null cuando se pidió un elemento random de una collección vacía", null, RNG.getRandom(list, pred));
		
		Object[][] array = new Object[10][10];
		assertEquals("No se devolvió null cuando se pidió un elemento random de una collección vacía", null, RNG.getRandom(array, pred));
	}
	
	@Test
	public void nextIntBetweenTwoValues() {
		for(int i = 0; i < 1000; i++) {
			int a = 100;
			int b = 200;
			int result = RNG.nextInt(a, b);
			assertTrue("Se pidió un int entre dos valores y el resultado fue incorrecto", a <= result && result < b);
			
			a = 1;
			b = 2;
			result = RNG.nextInt(a, b);
			assertTrue("Se pidió un int entre dos valores y el resultado fue incorrecto", result == a);
			
			a = 1;
			b = 1;
			result = RNG.nextInt(a, b);
			assertTrue("Se pidió un int entre dos valores y el resultado fue incorrecto", result == a);
		}
	}
	
	@Test
	public void gaussianValues() {
		for(int i = 0; i < 1000; i++) {
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
	
	

}
