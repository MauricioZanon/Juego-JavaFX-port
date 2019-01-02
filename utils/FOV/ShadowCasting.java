package FOV;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import components.LightSourceC;
import components.PositionC;
import components.VisionC;
import main.Att;
import main.Entity;
import main.Type;
import map.Map;
import observerPattern.Notification;
import player.PlayerInfo;
import tile.Tile;
import world.WorldBuilder;

public class ShadowCasting {
	
	/** Guarda la distancia desde un tile origen hasta los tiles cercanos para acelerar el shadow casting  */
	private static float[][] distancesChart = createDistanceChart();
	
	private ShadowCasting() {}

	public static void calculateFOV(Entity entity) {
		Tile origin = entity.get(PositionC.class).getTile();
		VisionC vc = entity.get(VisionC.class);
		if(entity.TYPE == Type.PLAYER) {
			PlayerInfo.viewedTiles.addAll(vc.visionMap);
		}
		vc.visionMap.clear();
		int range = vc.sightRange;
		Predicate<Tile> isTranslucent = t -> t.isTranslucent();
		Consumer<Tile> addToVisionMap = t -> vc.visionMap.add(t);
		addToVisionMap.accept(origin);
		for (int octant = 0; octant < 8; octant++) {
			compute(octant, origin, range, 1, new Slope(1, 1), new Slope(0, 1), isTranslucent, addToVisionMap);
		}
	}
	
	/**
	 * Ilumina el area al rededor de una entidad
	 * @param origin el origen de la luz
	 * @param addingLight true para cuando se agrega una fuente de luz, false para cuando se remueve
	 */
	public static void calculateIllumination(Entity entity, boolean addingLight) {
		Tile origin = entity.get(PositionC.class).getTile();
		if(origin == null) return;
		if(WorldBuilder.isBuilding) return;
		float lightIntensity = entity.get(Att.LIGHT_INTENSITY);
		int lightRange = (int) (lightIntensity / 0.15f);
		Set<Tile> illuminatedTiles = entity.get(LightSourceC.class).illuminatedTiles;
		
		Predicate<Tile> isTranslucent = t -> t.isTranslucent();
		
		if(addingLight) {
			Consumer<Tile> illuminate = t -> {
				if(!illuminatedTiles.contains(t)){
					double distance = getDistance(origin, t);
					float intensity = (float) ((lightIntensity - (0.15f * distance)));
					if(intensity > 0) {
						t.changeLightLevel(intensity);
						t.addObserver(Notification.RECALCULATE_LIGHT, entity);
					}
					illuminatedTiles.add(t);
				}
			};
			illuminate.accept(origin);
			
			for (int octant = 0; octant < 8; octant++) {
				compute(octant, origin, lightRange, 1, new Slope(1, 1), new Slope(0, 1), isTranslucent, illuminate);
			}
		}
		else {
			illuminatedTiles.forEach(t -> {
				if(illuminatedTiles.contains(t)){
					double distance = getDistance(origin, t);
					float intensity = (float) -((lightIntensity - (0.15f * distance)));
					if(intensity < 0) {
						t.changeLightLevel(intensity);
						t.removeObserver(Notification.RECALCULATE_LIGHT, entity);
					}
				}
			});
			illuminatedTiles.clear();
		}
	}
	
	private static void compute(int octant, Tile origin, int range, int x, Slope top, Slope bottom, Predicate<Tile> condition, Consumer<Tile> action) {
		for (; x <= range; x++) {
			int topY = top.X == 1 ? x : ((x * 2 + 1) * top.Y + top.X - 1) / (top.X * 2);
			int bottomY = bottom.Y == 0 ? 0 : ((x * 2 - 1) * bottom.Y + bottom.X) / (bottom.X * 2);

			int brokeCondition = -1; // 0:false, 1:true, -1:not applicable
			for (int y = topY; y >= bottomY; y--) {
				int tx = origin.pos.coord[0], ty = origin.pos.coord[1];
				switch (octant) {
				case 0:
					tx += x;
					ty -= y;
					break;
				case 1:
					tx += y;
					ty -= x;
					break;
				case 2:
					tx -= y;
					ty -= x;
					break;
				case 3:
					tx -= x;
					ty -= y;
					break;
				case 4:
					tx -= x;
					ty += y;
					break;
				case 5:
					tx -= y;
					ty += x;
					break;
				case 6:
					tx += y;
					ty += x;
					break;
				case 7:
					tx += x;
					ty += y;
					break;
				}
				
				Tile evaluatedTile = Map.getTile(tx, ty, origin.pos.coord[2]);
				action.accept(evaluatedTile);

				if (x != range) {
					if (!condition.test(evaluatedTile)) {
						if (brokeCondition == 0) { 
							// if we found a transition from clear to opaque, this sector is done in this column, so
							// adjust the bottom vector upwards and continue processing it in the next column.
							Slope newBottom = new Slope(y * 2 + 1, x * 2 - 1);
							boolean inRange = getDistance(origin, evaluatedTile) <= range;
							if (!inRange || y == bottomY) {
								bottom = newBottom;
								break;
							}
							else {
								compute(octant, origin, range, x + 1, top, newBottom, condition, action);
							}
						}
						brokeCondition = 1;
					} else {
						if (brokeCondition > 0)
							top = new Slope(y * 2 + 1, x * 2 + 1);
						brokeCondition = 0;
					}
				}
			}

			if (brokeCondition != 0) {
				break; // if the column ended in a clear tile, continue processing the current sector
			}
		}
	}
	
	
	private static float[][] createDistanceChart() {
		float[][] chart = new float[40][40];
		
		for(int x = 0; x < chart.length; x++) {
			for(int y = 0; y < chart[0].length; y++) {
				chart[x][y] = (float) Math.sqrt(x*x + y*y);
			}
		}
		
		return chart;
	}
	
	private static float getDistance(Tile t1, Tile t2) {
		return distancesChart[Math.abs(t1.pos.coord[0] - t2.pos.coord[0])][Math.abs(t1.pos.coord[1] - t2.pos.coord[1])];
	}

}
