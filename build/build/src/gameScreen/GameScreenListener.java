package gameScreen;

import actions.ActionType;
import actions.Actions;
import application.Main;
import components.PositionComponent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import world.Direction;

public class GameScreenListener implements EventHandler<KeyEvent>{

	@Override
	public void handle(KeyEvent arg0) {
//		System.out.println(arg0.getCode());
		
		PositionComponent playerPos = Main.PLAYER.get(PositionComponent.class);
		
		switch(arg0.getCode()) {
		case N:
			GameScreen.PLAYER_VIEW.refresh();
        	break;
		case ESCAPE:
			System.exit(0);
			break;
		case NUMPAD1:
			Actions.bump(playerPos, Direction.SW);
			break;
		case NUMPAD2:
			Actions.bump(playerPos, Direction.S);
			break;
		case NUMPAD3:
			Actions.bump(playerPos, Direction.SE);
			break;
		case NUMPAD4:
			Actions.bump(playerPos, Direction.W);
			break;
		case NUMPAD5:
			Actions.endTurn(Main.PLAYER, ActionType.WAIT);
			break;
		case NUMPAD6:
			Actions.bump(playerPos, Direction.E);
			break;
		case NUMPAD7:
			Actions.bump(playerPos, Direction.NW);
			break;
		case NUMPAD8:
			Actions.bump(playerPos, Direction.N);
			break;
		case NUMPAD9:
			Actions.bump(playerPos, Direction.NE);
			break;
		default:
			break;
		}
	}

}
