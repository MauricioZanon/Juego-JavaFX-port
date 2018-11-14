package gameScreen;

import application.Main;
import components.BodyComponent;
import components.ContainerComponent;
import components.SkillsComponent;
import components.SkillsComponent.Skill;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;
import spells.Spell;

public class InputConfig {
	
	private InputConfig() {}
	
	//TODO mouse action deberia ser un enum
	private static final SimpleStringProperty MOUSE_ACTION = new SimpleStringProperty("go to");
	private static final SimpleBooleanProperty IS_PROJECTILE = new SimpleBooleanProperty(false);
	private static final SimpleIntegerProperty MAX_DISTANCE = new SimpleIntegerProperty(Integer.MAX_VALUE);
	private static final SimpleIntegerProperty AFFECTED_RADIUS = new SimpleIntegerProperty(1);
	
	private static final SimpleStringProperty THROWN_ITEM_NAME = new SimpleStringProperty();
	
	public static void setGoToInput() {
		IS_PROJECTILE.set(false);
		MAX_DISTANCE.set(Integer.MAX_VALUE);
		AFFECTED_RADIUS.set(1);
		THROWN_ITEM_NAME.set("");
		MOUSE_ACTION.set("go to");
	}
	
	public static void setThrowInput(Entity thrownItem) {
		IS_PROJECTILE.set(true);
		float itemWeight = thrownItem.get("weight");
		if(itemWeight < 1) itemWeight = 1;
		int maxDistance = (int) ((Main.player.get("STR") / itemWeight) * 10);
		if(maxDistance > 15) maxDistance = 15;
		else if(maxDistance < 1) maxDistance = 1;
		MAX_DISTANCE.set(maxDistance);
		AFFECTED_RADIUS.set(1);
		THROWN_ITEM_NAME.set(thrownItem.name);
		MOUSE_ACTION.set("throw");
	}
	
	public static void setShootInput() {
		Entity weapon = Main.player.get(BodyComponent.class).getWeapon();
		if(weapon == null) {
			Console.addMessage("You don't have anything to fire with.\n");
		}else if(!weapon.TYPE.is(Type.BOW)) {
			Console.addMessage("You can't fire with your- " + weapon.name + "-.\n", Color.WHITE, Color.CADETBLUE, Color.WHITE);
		}else if(Main.player.get(ContainerComponent.class).get(Type.ARROW).size() == 0) {
			Console.addMessage("You have no more arrows.\n");
		}
		else {
			IS_PROJECTILE.set(true);
			int maxDistance = (int) (Main.player.get("STR") / 10 + weapon.get("range"));
			MAX_DISTANCE.set(maxDistance);
			AFFECTED_RADIUS.set(1);
			THROWN_ITEM_NAME.set("");
			MOUSE_ACTION.set("shoot");
		}
	}
	
	public static void setJumpInput() {
		IS_PROJECTILE.set(true);
		int maxDistance = Main.player.get(SkillsComponent.class).get(Skill.ACROBATICS) + 1;
		MAX_DISTANCE.set(maxDistance);
		AFFECTED_RADIUS.set(1);
		THROWN_ITEM_NAME.set("");
		MOUSE_ACTION.set("jump");
	}
	
	public static void setKickInput() {
		IS_PROJECTILE.set(false);
		MAX_DISTANCE.set(0);
		AFFECTED_RADIUS.set(1);
		THROWN_ITEM_NAME.set("");
		MOUSE_ACTION.set("kick");
	}
	
	public static void setCastInput(Spell spell) {
		IS_PROJECTILE.set(spell.isProjectile());
		MAX_DISTANCE.set(spell.getRange());
		AFFECTED_RADIUS.set(spell.getArea());
		THROWN_ITEM_NAME.set(spell.getName());
		MOUSE_ACTION.set("cast");
	}

	public static void setExamineInput() {
		IS_PROJECTILE.set(false);
		MAX_DISTANCE.set(0);
		AFFECTED_RADIUS.set(1);
		THROWN_ITEM_NAME.set("");
		MOUSE_ACTION.set("examine");
	}

	public static void setCloseInput() {
		IS_PROJECTILE.set(false);
		MAX_DISTANCE.set(0);
		AFFECTED_RADIUS.set(1);
		THROWN_ITEM_NAME.set("");
		MOUSE_ACTION.set("close");
	}
	
	protected static String getMouseAction() {
		return MOUSE_ACTION.get();
	}
	
	protected static void setMouseAction(String action) {
		MOUSE_ACTION.set(action);
	}
	
	protected static boolean isProjectile() {
		return IS_PROJECTILE.get();
	}
	
	protected static int getMaxDistance() {
		return MAX_DISTANCE.get();
	}
	
	protected static int getAffectedRadius() {
		return AFFECTED_RADIUS.get();
	}
	
	protected static String getThrownItemName() {
		return THROWN_ITEM_NAME.get();
	}

	protected static void setChangeListener(ChangeListener<String> listener) {
		MOUSE_ACTION.addListener(listener);
	}

}
