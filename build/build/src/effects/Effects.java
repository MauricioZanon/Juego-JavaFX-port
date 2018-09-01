package effects;

import components.PositionComponent;
import gameScreen.PlayerView;
import main.Entity;
import map.Map;
import map.Tile;

public abstract class Effects {
	
	public static void move(Entity actor, PositionComponent newPos) { //TODO test
		Tile oldTile = actor.get(PositionComponent.class).getTile();
		Tile newTile = newPos.getTile();
		oldTile.remove(actor.getType());
		
		if(actor.id == 0) {
			actor.add(newPos);
			Map.refresh();
			PlayerView.getInstance().refresh();
		}
//		VisionCalculator.calculateVision(entity);
		newTile.put(actor);
//		Mappers.statusEffectsMap.get(entity).affect(Trigger.MOVES);
		
	}

}
