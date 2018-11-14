package player;

import application.Main;
import behaviours.PlayerBeh;
import components.AIComponent;
import components.AbilitiesComponent;
import components.BodyComponent;
import components.BodyComponent.BodyPart;
import components.ContainerComponent;
import components.GraphicComponent;
import components.HealthComponent;
import components.MovementComponent;
import components.PositionComponent;
import components.SkillsComponent;
import components.SkillsComponent.Skill;
import components.StatusEffectsComponent;
import components.VisionComponent;
import factories.ItemFactory;
import javafx.scene.paint.Color;
import main.Entity;
import main.Type;
import spells.Dig;
import spells.SelfTeleport;
import spells.Summon;

public abstract class PlayerBuilder {
	
	public static Entity createBasePlayer() { //TODO test
		if(Main.player != null) return null;
		
		Entity p = new Entity(Type.PLAYER, -1, "player");
		
		p.setAttribute("STR", 10);
		p.setAttribute("CON", 10);
		p.setAttribute("DEX", 10);
		p.setAttribute("CUN", 10);
		p.setAttribute("INT", 10);
		p.setAttribute("WIS", 10);
		p.setAttribute("PER", 10);
		p.setAttribute("damage", 10);
		p.setAttribute("move speed", 10);
		p.setAttribute("attack speed", 10);
		p.setAttribute("cast speed", 10);
		
		PositionComponent pos = new PositionComponent();
		pos.coord = new int[] {0,0,0};
		p.addComponent(pos);
		
		GraphicComponent gc = new GraphicComponent();
		gc.ASCII = "@";
		gc.color = Color.WHITE;
		p.addComponent(gc);
		
		HealthComponent hp = new HealthComponent();
		hp.maxHP = 100;
		hp.curHP = 100;
		hp.HPreg = 0.1f;
		p.addComponent(hp);
		PlayerInfo.CUR_HP.set(hp.curHP);
		PlayerInfo.MAX_HP.set(hp.maxHP);
		
		AIComponent AI = new AIComponent();
		AI.changeBeh(new PlayerBeh(p));
		p.addComponent(AI);
		
		ContainerComponent inv = new ContainerComponent();
		inv.add(ItemFactory.createPotion());
		inv.add(ItemFactory.createPotion());
		inv.add(ItemFactory.createPotion());
		inv.add(ItemFactory.createPotion());
		inv.add(ItemFactory.createPotion());
		inv.add(ItemFactory.createArmor());
		inv.add(ItemFactory.createArmor());
		inv.add(ItemFactory.createArmor());
		inv.add(ItemFactory.createWeapon());
		p.addComponent(inv);
		
		BodyComponent body = new BodyComponent();
		body.add(BodyPart.HEAD);
		body.add(BodyPart.TORSO);
		body.add(BodyPart.L_ARM);
		body.add(BodyPart.R_ARM);
		body.add(BodyPart.L_HAND);
		body.add(BodyPart.R_HAND);
		body.add(BodyPart.L_LEG);
		body.add(BodyPart.R_LEG);
		body.add(BodyPart.L_FOOT);
		body.add(BodyPart.R_FOOT);
		p.addComponent(body);
		
		AbilitiesComponent ac = new AbilitiesComponent();
		ac.addSpell(Dig.getInstance());
		ac.addSpell(SelfTeleport.getInstance());
		ac.addSpell(Summon.getInstance());
		p.addComponent(ac);
		
		p.addComponent(new MovementComponent());
		p.addComponent(new VisionComponent());
		p.addComponent(new StatusEffectsComponent());
		p.addComponent(new SkillsComponent());
		
		p.get(SkillsComponent.class).set(Skill.CARPENTRY, 10);
		p.get(SkillsComponent.class).set(Skill.FLETCHERY, 10);
		Entity saw = ItemFactory.createArmor();
		saw.name = "saw";
		p.get(ContainerComponent.class).add(saw);
		for(int i = 0; i < 20; i++) {
			Entity string = ItemFactory.createArmor();
			string.name = "string";
			p.get(ContainerComponent.class).add(string);
			Entity wood = ItemFactory.createArmor();
			wood.name = "wood";
			p.get(ContainerComponent.class).add(wood);
			Entity feather = ItemFactory.createArmor();
			feather.name = "feather";
			p.get(ContainerComponent.class).add(feather);
		}
		
		return p;
	}

}
