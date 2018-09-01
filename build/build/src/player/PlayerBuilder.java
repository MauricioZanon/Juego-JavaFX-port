package player;

import application.Main;
import components.AttributeComponent;
import components.DescriptionComponent;
import components.GraphicComponent;
import components.HealthComponent;
import components.InventoryComponent;
import components.MovementComponent;
import components.PositionComponent;
import components.TimedComponent;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;

public abstract class PlayerBuilder {
	
	public static Entity createBasePlayer() { //TODO test
		if(Main.PLAYER != null) return null;
		
		Entity p = new Entity(Type.ACTOR, 0);
		
		AttributeComponent att = new AttributeComponent();
		att.set("strength", 10);
		att.set("constitution", 10);
		att.set("dexterity", 10);
		att.set("cunning", 10);
		att.set("intelligence", 10);
		att.set("wisdom", 10);
		p.add(att);
		
		PositionComponent pos = new PositionComponent();
		pos.coord = new int[] {0,0,0};
		p.add(pos);
		
		DescriptionComponent des = new DescriptionComponent();
		des.name = "player";
		p.add(des);
		
		GraphicComponent gc = new GraphicComponent();
		gc.ASCII = "@";
		gc.color = Color.WHITE;
		p.add(gc);
		
		HealthComponent hp = new HealthComponent();
		hp.maxHP = 100;
		hp.curHP = 100;
		hp.HPreg = 0.1f;
		p.add(hp);
		
		p.add(new InventoryComponent());
		p.add(new MovementComponent());
		p.add(new TimedComponent());
		
		return p;
	}

}
