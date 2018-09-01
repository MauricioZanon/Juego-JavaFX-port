package actions;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import application.Main;
import components.ContainerComponent;
import components.PositionComponent;
import factories.FeatureFactory;
import factories.ItemFactory;
import factories.NPCFactory;
import factories.TerrainFactory;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;
import world.Direction;

public class ActionsTests {
	
	@Test
	public void bumpEmptyTileTest() {
		Tile t1 = Map.getTile(0, 0, 0);
		t1.remove(Type.FEATURE);
		t1.remove(Type.ACTOR);
		Tile t2 = Map.getTile(1, 1, 0);
		t2.remove(Type.FEATURE);
		t2.remove(Type.ACTOR);
		//TODO quitar esto de la posicion cuando se implemente una manera correcta de agregar NPCs al mapa
		Entity NPC = NPCFactory.createNPC();
		NPC.addComponent(new PositionComponent());
		NPC.get(PositionComponent.class).coord = new int[] {0, 0, 0};
		t1.put(NPC);
		
		Bump.execute(t1.getPos(), Direction.SE);
		
		assertTrue(!t1.has(Type.ACTOR) && t2.has(Type.ACTOR));
		
		t2.remove(Type.ACTOR);
	}
	
	@Test
	public void bumpNonTransitableTileTest() {
		Tile t1 = Map.getTile(0, 0, 0);
		t1.remove(Type.FEATURE);
		t1.remove(Type.ACTOR);
		Tile t2 = Map.getTile(1, 1, 0);
		t2.put(TerrainFactory.get("concrete wall"));
		t2.remove(Type.FEATURE);
		t2.remove(Type.ACTOR);
		//TODO quitar esto de la posicion cuando se implemente una manera correcta de agregar NPCs al mapa
		Entity NPC = NPCFactory.createNPC();
		NPC.addComponent(new PositionComponent());
		NPC.get(PositionComponent.class).coord = new int[] {0, 0, 0};
		t1.put(NPC);
		
		Bump.execute(t1.getPos(), Direction.SE);
		
		assertTrue(t1.has(Type.ACTOR) && !t2.has(Type.ACTOR));
		
		t1.remove(Type.ACTOR);
		t2.put(TerrainFactory.get("grass floor"));
	}
	
	@Test
	public void bumpTileWithEnemyTest() {
		//TODO
	}
	
	@Test 
	public void bumpTileWithFeatureTest() {
		//TODO
	}
	
	@Test
	public void pickUpTest() {
		PositionComponent playerPos = Main.player.get(PositionComponent.class);
		Entity item = ItemFactory.createRandomItem();
		playerPos.getTile().put(item);
		PickUp.execute(Main.player);
		
		ContainerComponent inventory = Main.player.get(ContainerComponent.class);
		assertTrue(inventory.items.keySet().contains(item.name));
	}
	
	@Test
	public void kickActorTest() {
		PositionComponent playerPos = Main.player.get(PositionComponent.class);
		PositionComponent kickedPos = playerPos.clone();
		kickedPos.coord[0]++;
		Tile kickedTile = kickedPos.getTile();
		Entity kickedActor = NPCFactory.createNPC();
		kickedActor.addComponent(kickedPos);
		kickedTile.put(kickedActor);
		
		Kick.execute(Main.player, kickedTile);
		
		int kickerSTR = (int) Main.player.get("STR");
		int kickedCON = (int) kickedActor.get("CON");
		int distance = kickerSTR - kickedCON;
		PositionComponent landingPos = kickedPos.clone();
		landingPos.coord[0] += distance;
		assertTrue("La entidad pateada no está donde debería", landingPos.getTile().has(Type.ACTOR));
		
		landingPos.getTile().remove(Type.ACTOR);
	}
	
	@Test
	public void kickDoorTest() {
		PositionComponent playerPos = Main.player.get(PositionComponent.class);
		PositionComponent kickedPos = playerPos.clone();
		kickedPos.coord[0]++;
		Tile kickedTile = kickedPos.getTile();
		Entity kickedDoor = FeatureFactory.createFeature("closed door");
		kickedDoor.addComponent(kickedPos);
		kickedTile.put(kickedDoor);
		
		Kick.execute(Main.player, kickedTile);
		
		assertTrue("La puerta no se pateó correctamente", kickedTile.has(Type.FEATURE) && kickedTile.get(Type.FEATURE).ID == 2005);
		
		kickedTile.remove(Type.FEATURE);
	}
	
}
