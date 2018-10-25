package factories;

import java.util.ArrayList;
import java.util.HashMap;

import main.Entity;

public class FeatureFactory extends EntityFactory{
	
	protected static HashMap<String, Entity> features = new HashMap<>();
	protected static ArrayList<Entity> featuresByID = new ArrayList<>();
	
	private FeatureFactory() {}

	public static Entity createFeature(String name){
		if(!features.containsKey(name)) {
			return null;
		}else {
			return features.get(name).clone();
		}
	}
	
	public static Entity createFeature(int ID) {
		int correctedID = ID - 2000;
		if(correctedID >= featuresByID.size()) {
			System.out.println("ID de feature incorrecta, el máximo es " + (featuresByID.size()-1) + " y se pidió " + correctedID);
			return null;
		}else {
			return featuresByID.get(correctedID).clone();
		}
	}
	
	
}
