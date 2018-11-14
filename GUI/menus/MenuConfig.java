package menus;

import java.util.function.Consumer;
import java.util.function.Predicate;

import actions.Quaff;
import actions.Wear;
import actions.Wield;
import application.Main;
import gameScreen.InputConfig;
import main.Entity;
import main.Type;
import system.RenderSystem;

public class MenuConfig {
	
	protected static String title;
	protected static Consumer<Entity> action;
	protected static Predicate<Entity> filter;
	
	private MenuConfig() {}
	
	public static void openQuaffMenu() {
		title = "Quaff";
		action = p -> Quaff.execute(Main.player, p);
		filter = i -> i.TYPE.is(Type.POTION);
		RenderSystem.getInstance().changeScene("ItemSelectionMenu.fxml");
	}
	
	public static void openThrowMenu() {
		title = "Throw";
		action = i -> InputConfig.setThrowInput(i);
		filter = i -> i.TYPE.is(Type.ITEM);
		RenderSystem.getInstance().changeScene("ItemSelectionMenu.fxml");
	}

	public static void openWearMenu() {
		title = "Wear";
		action = a -> Wear.execute(Main.player, a); //TODO si esta equipado sacarlo
		filter = i -> i.TYPE.is(Type.ARMOR);
		RenderSystem.getInstance().changeScene("ItemSelectionMenu.fxml");
	}

	public static void openWieldMenu() {
		title = "Wield";
		action = w -> Wield.execute(Main.player, w); //TODO si esta equipado sacarlo
		filter = i -> i.TYPE.is(Type.WEAPON);
		RenderSystem.getInstance().changeScene("ItemSelectionMenu.fxml");
	}

}
