package factories;

import java.util.HashMap;

import main.Entity;
import main.Type;

public abstract class FeatureFactory extends EntityFactory{
	
	private static HashMap<Integer, String> featuresId;
	private static HashMap<String, String> featuresName;
	
	public static void initialize() {
		featuresId = loadEntities("../RogueWorld/assets/Data/Entities/Features.xml");
		featuresName = makeMapWithNames(featuresId);
	}
	
	public static Entity createFeature(String name){
		if(!featuresName.keySet().contains(name)) return null;
		Entity feature = create(featuresName.get(name));
		feature.setType(Type.FEATURE);
		return feature;
	}
	
	public static Entity createFeature(int id){
		if(!featuresId.keySet().contains(id)) return null;
		Entity feature = create(featuresId.get(id));
		feature.setType(Type.FEATURE);
		return feature;
	}
	
	public static void main(String[] args) {
		initialize();
		featuresName.keySet().forEach(s -> create(featuresName.get(s)));
		featuresId.keySet().forEach( s -> create(featuresId.get(s)));
	}
	
}
