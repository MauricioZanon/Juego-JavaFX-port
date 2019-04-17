//package actions;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import org.junit.jupiter.api.Test;
//
//import application.Main;
//import components.ContainerC;
//import components.PositionC;
//import factories.EntityFactory;
//import main.Att;
//import main.Entity;
//import main.Type;
//import map.Map;
//import tile.Tile;
//import world.Direction;
//
//public class ActionsTests {
//	
//	@Test
//	public void bumpEmptyTileTest() {
//		Tile t1 = Map.getTile(0, 0, 0);
//		t1.remove(Type.FEATURE);
//		t1.remove(Type.ACTOR);
//		Tile t2 = Map.getTile(1, 1, 0);
//		t2.remove(Type.FEATURE);
//		t2.remove(Type.ACTOR);
//		Entity NPC = EntityFactory.createRandom(Type.NPC);
//		t1.put(NPC);
//		
//		Bump.execute(t1.pos, Direction.SE);
//		
//		assertTrue(!t1.has(Type.ACTOR) && t2.has(Type.ACTOR));
//		
//		t2.remove(Type.ACTOR);
//	}
//	
//	@Test
//	public void bumpNonTransitableTileTest() {
//		Tile t1 = Map.getTile(0, 0, 0);
//		t1.remove(Type.FEATURE);
//		t1.remove(Type.ACTOR);
//		Tile t2 = Map.getTile(1, 1, 0);
//		t2.put(EntityFactory.create("concrete wall"));
//		t2.remove(Type.FEATURE);
//		t2.remove(Type.ACTOR);
//		Entity NPC = EntityFactory.createRandom(Type.NPC);
//		t1.put(NPC);
//		
//		Bump.execute(t1.pos, Direction.SE);
//		
//		assertTrue(t1.has(Type.ACTOR) && !t2.has(Type.ACTOR));
//		
//		t1.remove(Type.ACTOR);
//		t2.put(EntityFactory.create("grass floor"));
//	}
//	
//	@Test
//	public void bumpTileWithEnemyTest() {
//		//TODO
//	}
//	
//	@Test 
//	public void bumpTileWithFeatureTest() {
//		//TODO
//	}
//	
//	@Test
//	public void pickUpTest() {
//		PositionC playerPos = Main.player.get(PositionC.class);
//		Entity item = EntityFactory.createRandom(Type.ITEM);
//		playerPos.getTile().put(item);
//		PickUp.execute(Main.player);
//		
//		ContainerC inventory = Main.player.get(ContainerC.class);
//		assertTrue(inventory.items.keySet().contains(item.name));
//	}
//	
//	@Test
//	public void kickActorTest() {
//		PositionC playerPos = Main.player.get(PositionC.class);
//		PositionC kickedPos = playerPos.clone();
//		kickedPos.coord[0]++;
//		Tile kickedTile = kickedPos.getTile();
//		Entity kickedActor = EntityFactory.createRandom(Type.NPC);
//		kickedActor.setAttribute(Att.CON, 0);
//		kickedTile.put(kickedActor);
//		
//		Kick.execute(Main.player, kickedTile);
//		
//		assertFalse("La entidad pateada no está donde debería", kickedPos.getTile().has(Type.ACTOR));
//		
//		kickedActor.get(PositionC.class).getTile().remove(Type.ACTOR);
//	}
//	
//	@Test
//	public void kickDoorTest() {
//		PositionC playerPos = Main.player.get(PositionC.class);
//		PositionC kickedPos = playerPos.clone();
//		kickedPos.coord[0]++;
//		Tile kickedTile = kickedPos.getTile();
//		Entity kickedDoor = EntityFactory.create("closed door");
//		kickedDoor.addComponent(kickedPos);
//		kickedTile.put(kickedDoor);
//		
//		Kick.execute(Main.player, kickedTile);
//		
//		assertTrue("La puerta no se pateó correctamente", !kickedTile.has(Type.FEATURE));
//		
//		kickedTile.remove(Type.FEATURE);
//	}
//	
//}
