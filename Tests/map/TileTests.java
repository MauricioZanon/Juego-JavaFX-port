package map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import application.Main;
import components.GraphicC;
import factories.EntityFactory;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;
import tile.Tile;
import tile.TilePool;

public class TileTests {
	
	private Tile tile = TilePool.get(0,0,0);;
	
	@Before
	public void resetTile() {
		tile.remove(Type.ACTOR);
		tile.remove(Type.FEATURE);
		tile.remove(Type.ITEM);
		tile.put(EntityFactory.create("grass floor"));
	}
	
	@Test
	public void putNullInTile() {
		int entitiesInTile = tile.getEntities().size();
//		tile.put(null);
		assertEquals("Se agregó una entidad null al tile", entitiesInTile, tile.getEntities().size());
	}
	
	@Test
	public void putPresentEntity() {
		Entity e = EntityFactory.create("concrete wall");
		tile.put(e);
		int entitiesInTile = tile.getEntities().size();
		tile.put(e);
		assertEquals("Se agregó un duplicado de la misma entidad al tile", entitiesInTile, tile.getEntities().size());
	}
	
	@Test
	public void putPresentTypeEntity() {
		Entity npc = EntityFactory.create(1);
		Entity npc2 = EntityFactory.create(0);
		String nameNpc2 = npc2.name;
		tile.put(npc);
		tile.put(npc2);
		assertEquals("La segunda entidad agregada no reemplazó a la primera", nameNpc2, tile.get(Type.ACTOR).name);
	}
	
	@Test
	public void getNotPresentType() {
		Entity e = tile.get(Type.ACTOR);
		assertEquals("Se pidió una entidad que no estaba y se devolvió algo distinto a null", e, null);
	}
	
	@Test
	public void removeEntity() {
		tile.put(EntityFactory.create("concrete wall"));
		Entity e = tile.remove(Type.TERRAIN);
		assertEquals(null, tile.get(Type.TERRAIN));
		assertTrue(e != null && e.type == Type.TERRAIN);
	}
	
	@Test
	public void removeNotPresentEntity() {
		Entity e = tile.remove(Type.ACTOR);
		assertEquals(null, e);
	}
	
	
	//GraphicsTests
	
	@Test
	public void emptyTileTest() {
		tile.remove(Type.TERRAIN);
		
		assertEquals("", tile.getASCII());
		assertEquals(Color.BLACK, tile.getFrontColor());
		assertEquals(Color.BLACK, tile.getBackColor());
	}
	
	@Test
	public void addEntityOnEmptyTileTest() {
		Entity terrain = EntityFactory.create("concrete floor");
		tile.put(terrain);
		assertEquals(terrain.get(GraphicC.class).ASCII, tile.getASCII());
		assertEquals(terrain.get(GraphicC.class).color, tile.getFrontColor());
	}
	
	@Test
	public void addEntityOnNotEmptyTile() {
		tile.put(EntityFactory.create("concrete floor"));
		Entity feature = EntityFactory.create("tree");
		tile.put(feature);
		assertEquals(feature.get(GraphicC.class).ASCII, tile.getASCII());
		assertEquals(feature.get(GraphicC.class).color, tile.getFrontColor());
	}
	
	@Test
	public void removeEntityTest() {
		Entity terrain = EntityFactory.create("concrete floor");
		tile.put(terrain);
		tile.put(EntityFactory.create("tree"));
		tile.remove(Type.FEATURE);
		
		assertEquals(terrain.get(GraphicC.class).ASCII, tile.getASCII());
		assertEquals(terrain.get(GraphicC.class).color, tile.getFrontColor());
	}
	
	@Test
	public void removeAllEntitiesTest() {
		tile.put(EntityFactory.create("concrete floor"));
		tile.put(EntityFactory.create("tree"));
		tile.put(Main.player);
		
		tile.remove(Type.ACTOR);
		tile.remove(Type.FEATURE);
		tile.remove(Type.TERRAIN);
		
		assertEquals("", tile.getASCII());
		assertEquals(Color.BLACK, tile.getFrontColor());
		assertEquals(Color.BLACK, tile.getBackColor());
	}

}
