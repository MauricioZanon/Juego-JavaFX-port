package FOV;
//package FOV;
//
//import java.util.HashSet;
//
//import components.PositionComponent;
//import components.VisionComponent;
//import main.Entity;
//import main.Tile;
//import main.Type;
//import map.Map;
//
//public abstract class VisionCalculator {
//	
//	public static void calculateVision(Entity actor){
//		if(actor.ID == 0)
//			calculatePlayerVision(actor);
////		else 
////			calculateNPCVision(actor);
//	}
//
//	private static void calculatePlayerVision(Entity player) {
//		long tiempo = System.currentTimeMillis();
//		
//		int sightRange = player.get(VisionComponent.class).sightRange;
//		HashSet<Tile> visionMap = player.get(VisionComponent.class).visionMap;
//		HashSet<Tile> enemyTiles = player.get(VisionComponent.class).enemyTiles;
//		
//		visionMap.clear();
//		enemyTiles.clear();
//		
//		Tile originTile = player.get(PositionComponent.class).getTile();
//		Tile[][] area = Map.getCircundatingAreaAsArray(sightRange, originTile, true);
//		
//		for(int x = 0; x < area.length; x++) {
//			for(int y = 0; y < area[0].length; y++) {
//				Tile tile = area[x][y];
//				if(tile != null && !visionMap.contains(tile)){
//					calculateLOS(originTile, tile, visionMap, enemyTiles, area);
//				}
//			}
//		}
//		visionMap.forEach(t -> t.discovered = true);
//		
//		System.out.println("vision calculator refresh time: " + (System.currentTimeMillis() - tiempo));
//	}
//	
//
////	private static void calculateNPCVision(Entity npc){
////		Tile originTile = npc.get(PositionComponent.class).getTile();
////		HashSet<Tile> visibleTiles = visionMap.get(npc).visionMap;
////		visibleTiles.clear();
////		HashSet<Tile> enemyTiles = visionMap.get(npc).enemyTiles;
////		enemyTiles.clear();
////		Tile[][] area = Map.getCircundatingAreaAsArray(visionMap.get(npc).sightRange, originTile, true);
//////		Faction faction = factionMap.get(npc);
////		
////		for(int x = 0; x < area.length; x++) {
////			for(int y = 0; y < area[0].length; y++) {
////				Tile tile = area[x][y];
////				if(tile != null){ // TODO volver a agregar la condicion de que el tile tenga algo de interes
////					calculateLOS(originTile, tile, visibleTiles, enemyTiles, faction, area);
////				}
////			}
////		}
////		visionMap.get(npc).visionMap = visibleTiles;
////		visionMap.get(npc).enemyTiles = enemyTiles;
////	}
//	
//	private static void calculateLOS(Tile start, Tile end, HashSet<Tile> visibleTiles, HashSet<Tile> enemyTiles, Tile[][] area){
//		for(Tile tile : Map.getStraigthLine(start.getPos(), end.getPos(), area)){
//			if(tile == null) return;
//			visibleTiles.add(tile);
//			if(tile.has(Type.ACTOR)) {
//				enemyTiles.add(tile);
//			}
//			if(tile.get(Type.TERRAIN) == null || !tile.isTranslucent()){
//				return;
//			}
//		}
//	}
//	
//}