//package map;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//import RNG.RNG;
//import chunk.Chunk;
//import chunk.EmptyChunk;
//import factories.EntityFactory;
//import main.Type;
//import tile.Tile;
//
//public class ChunkTests {
//	
//	@Test
//	public void searchEntities() {
//		Chunk c = new EmptyChunk(0, 0, 0);
//		Tile[][] map = c.getMap();
//		int addedEntities = RNG.nextInt(100);
//		int i = 0;
//		while(i < addedEntities) {
//			Tile t = map[RNG.nextInt(Chunk.SIZE)][RNG.nextInt(Chunk.SIZE)];
//			if(t.get(Type.ACTOR) == null) {
//				t.put(EntityFactory.create("slime"));
//				i++;
//			}
//		}
//		assertEquals(addedEntities, c.getEntities(e -> e.type == Type.ACTOR).size());
//	}
//	
//	@Test
//	public void posAsStringTest() {
//		Chunk c = new EmptyChunk(4, -2, -7);
//		String pos = c.getPosAsString();
//		assertEquals("4:-2:-7", pos);
//	}
//
//}
