package player;

import application.Main;
import components.AIC;
import components.AbilitiesC;
import components.BodyC;
import components.BodyC.BodyPart;
import components.ContainerC;
import components.GraphicC;
import components.HealthC;
import components.LightSourceC;
import components.MovementC;
import components.PositionC;
import components.SkillsC;
import components.SkillsC.Skill;
import components.StatusEffectsC;
import components.VisionC;
import factories.EntityFactory;
import javafx.scene.paint.Color;
import main.Att;
import main.Entity;
import main.Flag;
import main.Type;
import spells.Dig;
import spells.TeleportSelf;
import spells.SummonLesserCreature;
import states.PlayerState;
import states.StateType;

public abstract class PlayerBuilder {
	
	public static Entity createBasePlayer() { //TODO test
		if(Main.player != null) return null;
		
		Entity p = new Entity(Type.PLAYER, -1, "player");
		
		p.setAttribute(Att.STR, 10);
		p.setAttribute(Att.CON, 10);
		p.setAttribute(Att.DEX, 10);
		p.setAttribute(Att.CUN, 10);
		p.setAttribute(Att.INT, 10);
		p.setAttribute(Att.WIS, 10);
		p.setAttribute(Att.PER, 10);
		p.setAttribute(Att.DAMAGE, 10);
		p.setAttribute(Att.MOV_SPEED, 10);
		p.setAttribute(Att.ATTACK_SPEED, 10);
		p.setAttribute(Att.CAST_SPEED, 10);
		
		PositionC pos = new PositionC();
		pos.coord = new int[] {0,0,0};
		p.addComponent(pos);
		
		GraphicC gc = new GraphicC();
		gc.ASCII = "@";
		gc.color = Color.WHITE;
		p.addComponent(gc);
		
		HealthC hp = new HealthC();
		hp.setMaxHP(100);
		hp.setCurHP(100);
		hp.setHPreg(0.1f);
		p.addComponent(hp);
		PlayerInfo.CUR_HP.set(hp.getCurHP());
		PlayerInfo.MAX_HP.set(hp.getMaxHP());
		
		AIC AI = new AIC();
		AI.addState(StateType.IDLE, new PlayerState(p));
		AI.setState(StateType.IDLE);
		p.addComponent(AI);
		
		ContainerC inv = new ContainerC();
		inv.add(EntityFactory.createRandom(Type.POTION));
		inv.add(EntityFactory.createRandom(Type.POTION));
		inv.add(EntityFactory.createRandom(Type.POTION));
		inv.add(EntityFactory.createRandom(Type.POTION));
		inv.add(EntityFactory.createRandom(Type.ARMOR));
		inv.add(EntityFactory.createRandom(Type.ARMOR));
		inv.add(EntityFactory.createRandom(Type.ARMOR));
		inv.add(EntityFactory.createRandom(Type.WEAPON));
		p.addComponent(inv);
		
		BodyC body = new BodyC();
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
		
		AbilitiesC ac = new AbilitiesC();
		ac.addSpell(Dig.getInstance());
		ac.addSpell(TeleportSelf.getInstance());
		ac.addSpell(SummonLesserCreature.getInstance());
		p.addComponent(ac);
		
		p.addComponent(new MovementC());
		p.addComponent(new VisionC());
		p.addComponent(new StatusEffectsC());
		p.addComponent(new SkillsC());
		p.addComponent(new LightSourceC());
		
		p.get(SkillsC.class).set(Skill.CARPENTRY, 10);
		p.get(SkillsC.class).set(Skill.FLETCHERY, 10);
		
		p.getFlags().add(Flag.LIGHT_SOURCE);
		p.setAttribute(Att.LIGHT_INTENSITY, 1.5f);
		
		return p;
	}

}
