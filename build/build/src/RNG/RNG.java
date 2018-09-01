package RNG;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import javafx.scene.paint.Color;

public abstract class RNG {
	
	private static long seed = System.currentTimeMillis();
	private static Random rng = new Random(seed);
	
	/**========================================================================
	 * ============================COLLECTIONS=================================
	 * ========================================================================*/
	
	public static <T> T getRandom(T[] array){
		return array[nextInt(array.length)];
	}

	public static <T> T getRandom(T[][] array){
		return array[nextInt(array.length)][nextInt(array[0].length)];
	}
	
	public static <T> T getRandom(T[][] array, Predicate<T> cond){
		Set<T> items = new HashSet<>();
		for(T[] subArray : array) {
			items.addAll(Arrays.asList(subArray));
		}
		return getRandom(items, cond);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getRandom(Collection<T> collection){
		if(collection.isEmpty()) return null;
		int index = nextInt(collection.size());
		return (T) collection.toArray()[index];
	}
	
	public static <T> T getRandom(Collection<T> collection, Predicate<T> cond){
		while(!collection.isEmpty()){
			T t = getRandom(collection);
			if(t == null) {
				return null;
			}
			if(cond.test(t)){
				return t;
			}else {
				collection.remove(t);
			}
		}
		return null;
	}
	
	/**========================================================================
	 * ============================NUMBERS=====================================
	 * ========================================================================*/
	
	public static int nextInt(int limit) {
		return rng.nextInt(limit);
	}
	
	/**
	 * @return min <= resultado < max
	 */
	public static int nextInt(int min, int max){
		if(min == max) return min;
		int range = max - min;
		int number = rng.nextInt(range);
		return number + min;
	}
	
	public static float nextFloat(){
		return rng.nextFloat();
	}
	
	public static double nextDouble(){
		return rng.nextDouble();
	}
	
	public static double nextGaussian(){
		return rng.nextGaussian();
	}
	
	/**
	 * @param mean: valor base
	 * @param variation: variación máxima desde el valor base
	 * @return un entero entre (mean - variation) y (mean + variation) que tiende a quedarse cerca del valor de mean
	 */
	public static int nextGaussian(int mean, int variation){
		float result = (float) (rng.nextGaussian() * variation + mean);
		if(result < (mean - variation) || result > (mean + variation))
			return nextGaussian(mean, variation);
		else
			return Math.round(result);
	}
	
	public static boolean nextBoolean(){
		return rng.nextBoolean();
	}
	
	public static long getSeed() {
		return seed;
	}
	
	public static void setSeed(long newSeed) {
		seed = newSeed;
		rng = new Random(newSeed);
	}
	
	
	/**========================================================================
	 * ============================COLORS======================================
	 * ========================================================================*/
	
	public static Color getAproximateColor(Color c) {
		int r = (int) (c.getRed()*255) + nextInt(-3, 3);
		int g = (int) (c.getGreen()*255) + nextInt(-3, 3);
		int b = (int) (c.getBlue()*255) + nextInt(-3, 3);
		return Color.rgb(r, g, b);
	}
}
