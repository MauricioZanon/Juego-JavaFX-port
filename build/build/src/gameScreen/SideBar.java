package gameScreen;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import system.RenderSystem;

public class SideBar extends VBox{
	
	public SideBar(int nodeSeparation) {
		super(nodeSeparation);
		setPrefWidth(RenderSystem.getStageWidth() - RenderSystem.getStageHeight());
		 
		setAlignment(Pos.TOP_LEFT);
		    
	    HBox hp = new HBox();
	    hp.setStyle("-fx-background-color: linear-gradient(to right, mediumSpringGreen, mediumSeaGreen)");
	    hp.getChildren().add(new Label("Health"));
	    getChildren().add(hp);
	    
	    HBox mp = new HBox();
	    mp.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, skyBlue, steelBlue)");
	    mp.getChildren().add(new Label("Mana"));
	    getChildren().add(mp);
	    HBox xp = new HBox();
	    xp.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #f2e7b9, #000000)");
	    xp.getChildren().add(new Label("Experience"));
	    getChildren().add(xp);
	}
	

}
