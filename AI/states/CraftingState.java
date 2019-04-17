package states;

import actions.ActionType;
import actions.EndTurn;
import application.Main;
import components.AIC;
import components.ContainerC;
import eventSystem.EventSystem;
import factories.EntityFactory;
import gameScreen.Console;
import player.Recipe;

public class CraftingState extends State{
	
	private int duration;
	private Recipe recipe;
	
	public CraftingState(Recipe recipe) {
		actor = Main.player;
		duration = recipe.craftTime;
		this.recipe = recipe;
	}
	
	/* TODO: las cosas que se craftean tiene que tardar el tiempo que dice en craftTime, 6000 serian 6000 segundos, pero tarda mucho
	 * porque cada segundo que avanza se tiene que renderizar la pantalla de nuevo y calcular el FOV, capaz fraccionando el tiempo
	 * e implementando otro metodo EndTurn que reciva un float con el tiempo hasta el siguiente turno se haga mas facil
	 */
	public void update() {
		super.update();
		EventSystem.setPlayerTurn(true);
		duration -= 50;
		EndTurn.execute(Main.player, ActionType.CRAFT);
		
		if(duration <= 0) {
			Main.player.get(ContainerC.class).add(EntityFactory.create(recipe.name));
			recipe.consumeMaterials();
			Console.addMessage("You crafted a " + recipe.name);
			Main.player.get(AIC.class).setState(StateType.IDLE);
		}
	}

	@Override
	public void enterState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitState() {
		// TODO Auto-generated method stub
		
	}

}
