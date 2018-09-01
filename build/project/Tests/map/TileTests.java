package map;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import factories.EntityFactory;
import main.Entity;
import main.JavaFXThreadingRule;
import main.Type;

public class TileTests {
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	private Tile tile;
	
	@BeforeEach
	public void createTile() {
		tile = new Tile(0,0,0);
	}
	
	@Test
	public void putNullInTile() {
		int entitiesInTile = tile.getEntities().size();
		tile.put(null);
		assertEquals("Se agregó una entidad null al tile", entitiesInTile, tile.getEntities().size());
	}
	
	@Test
	public void putPresentEntity() {
		Entity e = EntityFactory.create(1000);
		tile.put(e);
		int entitiesInTile = tile.getEntities().size();
		tile.put(e);
		assertEquals("Se agregó un duplicado de la misma entidad al tile", entitiesInTile, tile.getEntities().size());
	}
	
	//TODO: prueba de poner una entidad de un tipo que ya se encuentra en el tile
	//TODO: pedir color de fondo, ascii y color frontal
	
	@Test
	public void getNullType() {
		Entity e = tile.get(null);
		assertEquals("Se pidió una entidad sin tipo y se devolvió algo distinto a null", e, null);
	}
	
	@Test
	public void getNotPresentType() {
		Entity e = tile.get(Type.ACTOR);
		assertEquals("Se pidió una entidad que no estaba y se devolvió algo distinto a null", e, null);
	}
	
	@Test
	public void removeEntity() {
		
	}

}
