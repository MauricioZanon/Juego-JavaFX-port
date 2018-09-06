package gameScreen;

import java.util.HashMap;
import java.util.Map;

import animatefx.animation.Flash;
import application.Main;
import components.HealthComponent;
import console.Console;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import resourceBars.ResourceBar;
import system.RenderSystem;

/**
 * Es el menu del costado derecho de la pantalla, aca van todas las barras de recursos, la consola y cualquier información situacional que sea necesaria
 * TODO: métodos para agregar o quitar nodos
 * @author Mauro
 *
 */
public class SideBar extends VBox{
	
	private Map<String, ResourceBar> resourcesBars = new HashMap<>();
	
	public SideBar(int nodeSeparation) {
		super(nodeSeparation);
		setPrefWidth(RenderSystem.getSceneWidth() - RenderSystem.getSceneHeight());
		 
		setAlignment(Pos.TOP_LEFT);
		
		HealthComponent hp = Main.player.get(HealthComponent.class);
	    ResourceBar hpBar = new ResourceBar("Health", Color.DARKSEAGREEN , () -> hp.maxHP/100*hp.curHP);
	    getChildren().add(hpBar);
	    resourcesBars.put("health", hpBar);
	    
	    getChildren().add(PlayerPosLabel.getInstance());
	    getChildren().add(Console.getInstance());

	    Text text = new Text("AnimateFX");
	    text.setFill(Color.WHITE);
	    Flash anim = new Flash(text);
	    anim.playOnFinished(anim);
	    anim.play();
	    getChildren().add(text);
	    
	    getChildren().add(ClockLabel.getInstance());
	    
	}
	
	public void refreshResourceBar(String barName) {
		if(resourcesBars.containsKey(barName)) {
			resourcesBars.get(barName).refresh();
		}
	}

	public void refresh() {
		
	}

}
