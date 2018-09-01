package map;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import Chunk.Chunk;
import Chunk.EmptyChunk;
import RNG.RNG;
import factories.EntityFactory;
import main.JavaFXThreadingRule;
import main.Type;

public class ChunkTests {
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Test
	public void searchEntities() {
		Chunk c = new EmptyChunk(0, 0, 0);
		Tile[][] map = c.getMap();
		int addedEntities = RNG.nextInt(100);
		int i = 0;
		while(i < addedEntities) {
			Tile t = map[RNG.nextInt(Chunk.SIZE)][RNG.nextInt(Chunk.SIZE)];
			if(t.get(Type.ACTOR) == null) {
				t.put(EntityFactory.create(0));
				i++;
			}
		}
		assertEquals(addedEntities, c.getEntities(e -> e.getType() == Type.ACTOR).size());
		//TODO cuando se terminen de implementar las factories hay que probar con todos los tipos de entidad
	}

}
