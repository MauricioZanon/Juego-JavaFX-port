package states;

import actions.FollowPath;
import components.MovementC;
import eventSystem.EventSystem;
import main.Entity;
import player.PlayerInfo;

public class PlayerState extends State{
	
	public PlayerState(Entity actor) {
		this.actor = actor;
	}
	
	public void update() {
		super.update();
		EventSystem.setPlayerTurn(true);
		if(actor.get(MovementC.class).path != null && !actor.get(MovementC.class).path.isEnded()) {
			FollowPath.execute(actor);
		}
		
		PlayerInfo.HUNGER.set(PlayerInfo.HUNGER.floatValue()+1);
		PlayerInfo.THIRST.set(PlayerInfo.THIRST.floatValue()+1);
		
	}

	@Override
	public void enterState() {}

	@Override
	public void exitState() {}
	
}
