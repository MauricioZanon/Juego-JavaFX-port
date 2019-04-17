package states;

import actions.ActionType;
import actions.EndTurn;
import actions.Shoot;
import components.AIC;
import components.BodyC;
import components.MovementC;
import components.PositionC;
import components.VisionC;
import main.Entity;
import main.Type;
import map.Map;
import tile.Tile;

public class RangedCombatState extends State{
	
	private Entity target = null;

	public RangedCombatState() {}
	
	public RangedCombatState(Entity actor) {
		this.actor = actor;
	}

	public void update() {
		super.update();
		if(!target.get(AIC.class).isActive) {
			actor.get(AIC.class).setState(StateType.IDLE);
			EndTurn.execute(actor, ActionType.WAIT);
		}
		Entity weapon = actor.get(BodyC.class).getWeapon();
		Tile targetTile = target.get(PositionC.class).getTile();
		if(actor.get(VisionC.class).visionMap.contains(targetTile)) {
			actor.get(AIC.class).setState(StateType.IDLE);
			EndTurn.execute(actor, ActionType.WAIT);
		}
		if(weapon == null || !weapon.type.is(Type.RANGED)) {
//			fleeState
			EndTurn.execute(actor, ActionType.WAIT);
		}
		else if(weapon.type == Type.BOW) {
			Shoot.execute(actor, targetTile);
		}
		
	}
	
	@Override
	public void enterState() {
		Tile actorTile = actor.get(PositionC.class).getTile();
		Tile targetTile = Map.getClosestTile(actorTile, actor.get(VisionC.class).enemyTiles);
		target = targetTile.get(Type.ACTOR);
	}
	
	@Override
	public void exitState() {
		target = null;
		actor.get(MovementC.class).path = null;
	}
	
}
