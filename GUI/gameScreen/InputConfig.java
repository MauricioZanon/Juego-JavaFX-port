package gameScreen;

import application.Main;
import components.SkillsComponent;
import components.SkillsComponent.Skill;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import main.Entity;
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
		int maxDistance = (int) ((Main.player.get("STR") / thrownItem.get("weight")) * 10);
		MAX_DISTANCE.set(maxDistance);
		AFFECTED_RADIUS.set(1);
		THROWN_ITEM_NAME.set(thrownItem.name);
		MOUSE_ACTION.set("throw");
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
		MAX_DISTANCE.set(1);
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
