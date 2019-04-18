package states;

import RNG.RNG;
import actions.ActionType;
import actions.Bump;
import actions.EndTurn;
import components.AIC;
import components.PositionC;
import main.Entity;
import world.Direction;

public class IdleState extends State{ //TODO test
	
	public IdleState() {}
	
	/** Con este estado la entidad se mantiene en el lugar o se mueve a un tile adyacente */
	public IdleState(Entity actor) {
		this.actor = actor;
	}
	
	public void update() {
		super.update();
		
		if(enemySighted()) {
			actor.get(AIC.class).setState(StateType.COMBAT);
		}else {
			if(RNG.nextInt(100) > 60)
				Bump.execute(actor.get(PositionC.class), RNG.getRandom(Direction.values()), true);
			else
				EndTurn.execute(actor, ActionType.WAIT);
		}
	}

	@Override
	public void enterState() {}

	@Override
	public void exitState() {}

	
}
