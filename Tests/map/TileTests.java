package map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import application.Main;
import components.GraphicComponent;
import factories.FeatureFactory;
import factories.TerrainFactory;
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
		tile.put(TerrainFactory.get("grass floor"));
	}
	
	@Test
	public void putNullInTile() {
		int entitiesInTile = tile.getEntities().size();
		tile.put(null);
		assertEquals("Se agregó una entidad null al tile", entitiesInTile, tile.getEntities().size());
	}
	
	@Test
	public void putPresentEntity() {
		Entity e = TerrainFactory.get("concrete wall");
		tile.put(e);
		int entitiesInTile = tile.getEntities().size();
		tile.put(e);
		assertEquals("Se agregó un duplicado de la misma entidad al tile", entitiesInTile, tile.getEntities().size());
	}
	
	@Test @Ignore
	public void putPresentTypeEntity() {
		//TODO cuando se implemente la creacion de npcs (la unica clase de entidad de la cual se crean instancias unicas)
	}
	
	@Test
	public void getNotPresentType() {
		Entity e = tile.get(Type.ACTOR);
		assertEquals("Se pidió una entidad que no estaba y se devolvió algo distinto a null", e, null);
	}
	
	@Test
	public void removeEntity() {
		tile.put(TerrainFactory.get("concrete wall"));
		Entity e = tile.remove(Type.TERRAIN);
		assertEquals(null, tile.get(Type.TERRAIN));
		assertTrue(e != null && e.TYPE == Type.TERRAIN);
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
		Entity terrain = TerrainFactory.get("concrete floor");
		tile.put(terrain);
		assertEquals(terrain.get(GraphicComponent.class).ASCII, tile.getASCII());
		assertEquals(terrain.get(GraphicComponent.class).color, tile.getFrontColor());
	}
	
	@Test
	public void addEntityOnNotEmptyTile() {
		tile.put(TerrainFactory.get("concrete floor"));
		Entity feature = FeatureFactory.createFeature("tree");
		tile.put(feature);
		assertEquals(feature.get(GraphicComponent.class).ASCII, tile.getASCII());
		assertEquals(feature.get(GraphicComponent.class).color, tile.getFrontColor());
	}
	
	@Test
	public void removeEntityTest() {
		Entity terrain = TerrainFactory.get("concrete floor");
		tile.put(terrain);
		tile.put(FeatureFactory.createFeature("tree"));
		tile.remove(Type.FEATURE);
		
		assertEquals(terrain.get(GraphicComponent.class).ASCII, tile.getASCII());
		assertEquals(terrain.get(GraphicComponent.class).color, tile.getFrontColor());
	}
	
	@Test
	public void removeAllEntitiesTest() {
		tile.put(TerrainFactory.get("concrete floor"));
		tile.put(FeatureFactory.createFeature("tree"));
		tile.put(Main.player);
		
		tile.remove(Type.ACTOR);
		tile.remove(Type.FEATURE);
		tile.remove(Type.TERRAIN);
		
		assertEquals("", tile.getASCII());
		assertEquals(Color.BLACK, tile.getFrontColor());
		assertEquals(Color.BLACK, tile.getBackColor());
	}

}
