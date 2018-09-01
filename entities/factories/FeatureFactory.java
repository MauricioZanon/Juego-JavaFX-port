package factories;

import java.util.HashMap;

import main.Entity;

public abstract class FeatureFactory extends EntityFactory{
	
	protected static HashMap<String, Entity> features = new HashMap<>();
	
	public static Entity createFeature(String name){
		if(!features.containsKey(name)) {
			return null;
		}else {
			return features.get(name).clone();
		}
	}
	
	
}
