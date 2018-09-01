package input;

import Menus.InventoryMenu;
import Menus.QuaffMenu;
import RNG.RNG;
import actions.ActionType;
import actions.Attack;
import actions.Bump;
import actions.EndTurn;
import actions.Examine;
import actions.Grab;
import actions.Jump;
import actions.Kick;
import actions.PickUp;
import application.Main;
import components.PositionComponent;
import components.StatusEffectsComponent;
import console.Console;
import effects.Effects;
import eventSystem.EventSystem;
import gameScreen.GameScreen;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import playerViews.WeatherLayer;
import playerViews.WeatherLayer.Weather;
import statusEffects.Poisoned;
import world.Direction;

public class GameScreenKeyboardListener implements EventHandler<KeyEvent>{
	
	@Override
	public void handle(KeyEvent arg0) {
		if(!EventSystem.waitingOnPlayerInput) return;
		
		PositionComponent playerPos = Main.player.get(PositionComponent.class).clone();
		
		switch(arg0.getCode()) {
		case A:
			if(arg0.isShiftDown()) {
				Attack.setListener();
			}
			break;
		case E:
			Examine.setListener();
			break;
		case G:
			Grab.setListener();
			break;
		case I:
			InventoryMenu.getInstance().refresh();
			GameScreen.getInstance().showMenu(InventoryMenu.getInstance());
			break;
		case J:
			Jump.setListener();
			break;
		case K:
			Kick.setListener();
			break;
		case M:
			GameScreen.getInstance().changePlayerView();
			break;
		case Q:
			QuaffMenu.getInstance().refresh();
			GameScreen.getInstance().showMenu(QuaffMenu.getInstance());
			break;
		case S:
			WeatherLayer.getInstance().currentWeather = RNG.getRandom(Weather.values());
			WeatherLayer.getInstance().refresh();
        	break;
		case PAGE_UP:
			Console.getInstance().scroll(-20);
			break;
		case PAGE_DOWN:
			Console.getInstance().scroll(20);
			break;
		case SPACE:
			Main.player.get(StatusEffectsComponent.class).add(new Poisoned(Main.player, 20));
			break;
		case COMMA:
			PickUp.execute(Main.player);
			break;
		case LESS:
			if(arg0.isShiftDown()) {
				playerPos.coord[2]++;
				Effects.move(Main.player, playerPos);
				EndTurn.execute(Main.player, ActionType.WALK);
			}else {
				playerPos.coord[2]--;
				Effects.move(Main.player, playerPos);
				EndTurn.execute(Main.player, ActionType.WALK);
			}
			break;
		case NUMPAD1:
			Bump.execute(playerPos, Direction.SW);
			break;
		case NUMPAD2:
			Bump.execute(playerPos, Direction.S);
			break;
		case NUMPAD3:
			Bump.execute(playerPos, Direction.SE);
			break;
		case NUMPAD4:
			Bump.execute(playerPos, Direction.W);
			break;
		case NUMPAD5:
			EndTurn.execute(Main.player, ActionType.WAIT);
			break;
		case NUMPAD6:
			Bump.execute(playerPos, Direction.E);
			break;
		case NUMPAD7:
			Bump.execute(playerPos, Direction.NW);
			break;
		case NUMPAD8:
			Bump.execute(playerPos, Direction.N);
			break;
		case NUMPAD9:
			Bump.execute(playerPos, Direction.NE);
			break;
		case ESCAPE:
			System.exit(0);
			break;
		default:
			break;
		}
		arg0.consume();
	}

}
