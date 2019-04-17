package persistency;

import chunk.Chunk;
import chunk.EmptyChunk;
import components.AbilitiesC;
import components.BodyC;
import components.Component;
import components.ContainerC;
import components.HealthC;
import components.MaterialC;
import components.MovementC;
import components.SkillsC;
import components.StatusEffectsC;
import factories.EntityFactory;
import main.Entity;
import tile.Tile;

public class Deserializer {
	
	public static Chunk deserialize(String chunkPos, String chunkString) {
		Chunk chunk  = new EmptyChunk(chunkPos);
		
		int size = Chunk.SIZE;
		Tile[][] chunkMap = chunk.getMap();
		
		String[] tileStrings = chunkString.split("/");
    	for(int i = 0; i < tileStrings.length; i++) {
    		String[] entitiesStrings = tileStrings[i].split(",");
    		int tileX = i/size;
    		int tileY = i%size;
    		Tile t = chunkMap[tileX][tileY];
    		if(entitiesStrings.length > 1) {
    			t.setViewed(Boolean.parseBoolean(entitiesStrings[0]));
    			for(int j = 1; j < entitiesStrings.length; j++) {
    				if(!entitiesStrings[j].equals("")) {
    					t.put(deserializeEntity(entitiesStrings[j]));
    				}
    			}
    		}
    	}
		
		return chunk;
	}
	
	private static Entity deserializeEntity(String entityString) {
		String[] components = entityString.split("-");
		
		Entity e = EntityFactory.create(Integer.parseInt(components[0]));
		
		for(int i = 1; i < components.length; i++) {
			deserializeComponent(components[i], e);
		}
		
		return e;
	}

	private static void deserializeComponent(String componentString, Entity e) {
		String[] componentInfo = componentString.split(":");
		
		Component c = switch(componentInfo[0]) {
			case "ABI" -> new AbilitiesC();
			case "BOD" -> new BodyC();
			case "CON" -> new ContainerC();
			case "HEA" -> new HealthC();
			case "MAT" -> new MaterialC();
			case "MOV" -> new MovementC();
			case "SKI" -> new SkillsC();
			case "STA" -> new StatusEffectsC();
			default -> null;
		};
		
		if(c != null) {
			c.deserialize(componentInfo[1]);
			e.addComponent(c);
		}
		else {
			System.out.println("Se intentó deserializar un string de component inválido:\n\t" + componentString);
		}
	}
	
}
