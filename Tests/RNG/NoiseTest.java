package RNG;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NoiseTest {
	
	@Test
	public void noiseBetweenZeroAndOne() {
		float[][] noiseMap = Noise.generatePerlinNoise(1000, 1000, 5);
		for(int i = 0; i < noiseMap.length; i++) {
			for(int j = 0; j < noiseMap[0].length; j++) {
				float value = noiseMap[i][j];
				assertTrue(0 <= value && value <= 1);
			}
		}
		
	}

}
