package behaviours;

import actions.FollowPath;
import application.Main;
import components.MovementC;
import eventSystem.EventSystem;
import main.Entity;

public class PlayerBeh extends Behaviour{
	
	public PlayerBeh(Entity actor) {
		this.actor = actor;
	}
	public void update() {
		super.update();
		EventSystem.setPlayerTurn(true);
		if(!Main.player.get(MovementC.class).path.isEnded()) {
			FollowPath.execute(Main.player);
		}
	}

}
