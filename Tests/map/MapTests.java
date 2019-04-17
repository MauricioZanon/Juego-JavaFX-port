//package map;
//
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.Set;
//
//import org.junit.Test;
//
//import RNG.RNG;
//import chunk.Chunk;
//import componentsomponents.PositionC;
//import tile.Tile;
//import tile.TilePool;
//import world.Direction;
//
//public class MapTests {
//	
//	@Test
//	public void searchChunkByCoord() {
//		int[] coord = new int[] {RNG.nextInt(500),RNG.nextInt(500),RNG.nextInt(500)};
//		Chunk c = Map.getChunk(coord[0], coord[1], coord[2]);
//		assertArrayEquals("No se encuentran los chunks correctos", coord, c.getCoord());
//		
//		coord = new int[] {RNG.nextInt(-500, 0),RNG.nextInt(-500, 0),RNG.nextInt(-500, 0)};
//		c = Map.getChunk(coord[0], coord[1], coord[2]);
//		assertArrayEquals("No se encuentran los chunks correctos cuando se busca en coordenadas negativas", coord, c.getCoord());
//	}
//	
//	@Test
//	public void getChunkByTile() {
//		Tile t = TilePool.get(RNG.nextInt(500),RNG.nextInt(500),RNG.nextInt(500));
//		int[] coord = t.pos.coord;
//		Chunk c = Map.getChunk(t);
//		int[] chunkCoord = c.getCoord();
//		assertTrue(chunkCoord[0] == coord[0]/Chunk.SIZE && chunkCoord[1] == coord[1]/Chunk.SIZE && chunkCoord[2] == coord[2]);
//
//		t = TilePool.get(RNG.nextInt(500),RNG.nextInt(500),RNG.nextInt(500));
//		coord = t.pos.coord;
//		c = Map.getChunk(t);
//		chunkCoord = c.getCoord();
//		assertTrue(chunkCoord[0] == coord[0]/Chunk.SIZE && chunkCoord[1] == coord[1]/Chunk.SIZE && chunkCoord[2] == coord[2]);
//	}
//	
//	@Test
//	public void getTile() {
//		int[] coord = new int[] {RNG.nextInt(500),RNG.nextInt(500),RNG.nextInt(500)};
//		Tile t = Map.getTile(coord[0], coord[1], coord[2]);
//		assertArrayEquals("No se encuentran los tiles correctos", coord, t.pos.coord);
//		
//		coord = new int[] {RNG.nextInt(-500, 0),RNG.nextInt(-500, 0),RNG.nextInt(-500, 0)};
//		t = Map.getTile(coord[0], coord[1], coord[2]);
//		assertArrayEquals("No se encuentran los tiles correctos cuando se busca en coordenadas negativas", coord, t.pos.coord);
//	}
//	
//	@Test
//	public void getPositionByDirection() {
//		PositionC pos0 = TilePool.get(0, 0, 0).pos;
//		Direction dir = Direction.E;
//		PositionC pos = Map.getPosition(pos0, dir);
//		assertEquals(pos0.coord[0]+1, pos.coord[0]); 
//		
//		pos0 = TilePool.get(0, 0, 0).pos;
//		dir = Direction.W;
//		pos = Map.getPosition(pos0, dir);
//		assertEquals(pos0.coord[0]-1, pos.coord[0]); 
//		
//		pos0 = TilePool.get(0, 0, 0).pos;
//		dir = Direction.N;
//		pos = Map.getPosition(pos0, dir);
//		assertEquals(pos0.coord[1]-1, pos.coord[1]); 
//		
//		pos0 = TilePool.get(0, 0, 0).pos;
//		dir = Direction.S;
//		pos = Map.getPosition(pos0, dir);
//		assertEquals(pos0.coord[1]+1, pos.coord[1]); 
//		
//		pos0 = TilePool.get(0, 0, 0).pos;
//		dir = Direction.NE;
//		pos = Map.getPosition(pos0, dir);
//		assertEquals(pos0.coord[0]+1, pos.coord[0]); 
//		assertEquals(pos0.coord[1]-1, pos.coord[1]); 
//		
//		pos0 = TilePool.get(0, 0, 0).pos;
//		dir = Direction.NW;
//		pos = Map.getPosition(pos0, dir);
//		assertEquals(pos0.coord[0]-1, pos.coord[0]); 
//		assertEquals(pos0.coord[1]-1, pos.coord[1]); 
//		
//		pos0 = TilePool.get(0, 0, 0).pos;
//		dir = Direction.SE;
//		pos = Map.getPosition(pos0, dir);
//		assertEquals(pos0.coord[0]+1, pos.coord[0]); 
//		assertEquals(pos0.coord[1]+1, pos.coord[1]); 
//		
//		pos0 = TilePool.get(0, 0, 0).pos;
//		dir = Direction.SW;
//		pos = Map.getPosition(pos0, dir);
//		assertEquals(pos0.coord[0]-1, pos.coord[0]); 
//		assertEquals(pos0.coord[1]+1, pos.coord[1]); 
//	}
//	
//	@Test
//	public void getAdjacentTilesWithoutCondition() {
//		Set<Tile> tiles = Map.getAdjacentTiles(TilePool.get(0, 0, 0), t -> true);
//		assertTrue(tiles.size() == 8);
//		
//		assertTrue(tiles.contains(Map.getTile(1, 0, 0)));
//		assertTrue(tiles.contains(Map.getTile(1, 1, 0)));
//		assertTrue(tiles.contains(Map.getTile(0, 1, 0)));
//		assertTrue(tiles.contains(Map.getTile(-1, 1, 0)));
//		assertTrue(tiles.contains(Map.getTile(-1, 0, 0)));
//		assertTrue(tiles.contains(Map.getTile(-1, -1, 0)));
//		assertTrue(tiles.contains(Map.getTile(1, -1, 0)));
//		assertTrue(tiles.contains(Map.getTile(0, -1, 0)));
//	}
//	
//	@Test
//	public void getAdjacentTilesWithCondition() {
//		Set<Tile> tiles = Map.getAdjacentTiles(TilePool.get(0, 0, 0), t -> t.pos.coord[0] == 1);
//		assertTrue(tiles.size() == 3);
//		
//		assertTrue(tiles.contains(Map.getTile(1, 0, 0)));
//		assertTrue(tiles.contains(Map.getTile(1, 1, 0)));
//		assertTrue(tiles.contains(Map.getTile(1, -1, 0)));
//	}
//	
//	@Test
//	public void getSquareArea() {
//		int width = RNG.nextInt(100);
//		int height = RNG.nextInt(100);
//		Set<Tile> tiles = Map.getSquareAreaAsSet(TilePool.get(RNG.nextInt(-50, 50),RNG.nextInt(-50, 50),0).pos, width, height);
//		assertEquals(width*height, tiles.size());
//		
//		width = 0;
//		height = 0;
//		tiles = Map.getSquareAreaAsSet(TilePool.get(RNG.nextInt(-50, 50),RNG.nextInt(-50, 50),0).pos, width, height);
//		assertEquals(0, tiles.size());
//	}
//	
//	@Test
//	public void getDistance() {
//		PositionC pos1 = TilePool.get(0, 0, 0).pos;
//		PositionC pos2 = TilePool.get(500, 0, 0).pos;
//		assertEquals(500, Map.getDistance(pos1, pos2), 0.1f);
//		
//		pos1 = TilePool.get(0, 0, 0).pos;
//		pos2 = TilePool.get(-500, 0, 0).pos;
//		assertEquals(500, Map.getDistance(pos1, pos2), 0.1f);
//		
//		pos1 = TilePool.get(0, 0, 0).pos;
//		pos2 = TilePool.get(17, -31, 0).pos;
//		assertEquals(35.3f, Map.getDistance(pos1, pos2), 0.1f);
//	}
//	
//	@Test
//	public void getDirectionBetweenTwoTiles() {
//		Tile t1 = TilePool.get(0,0,0);
//		Tile t2 = TilePool.get(1, 0,0);
//		assertEquals(Direction.E, Direction.get(t1, t2));
//
//		t2 = TilePool.get(-1, 0,0);
//		assertEquals(Direction.W, Direction.get(t1, t2));
//		
//		t2 = TilePool.get(0, 1,0);
//		assertEquals(Direction.S, Direction.get(t1, t2));
//		
//		t2 = TilePool.get(0, -1,0);
//		assertEquals(Direction.N, Direction.get(t1, t2));
//		
//		t2 = TilePool.get(1, 1,0);
//		assertEquals(Direction.SE, Direction.get(t1, t2));
//		
//		t2 = TilePool.get(1, -1,0);
//		assertEquals(Direction.NE, Direction.get(t1, t2));
//		
//		t2 = TilePool.get(-1, 1,0);
//		assertEquals(Direction.SW, Direction.get(t1, t2));
//		
//		t2 = TilePool.get(-1, -1,0);
//		assertEquals(Direction.NW, Direction.get(t1, t2));
//	}
//	
//}
