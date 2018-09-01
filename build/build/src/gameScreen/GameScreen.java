package gameScreen;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import system.Screen;

public class GameScreen extends Scene implements Screen{
	
	private static GameScreen instance;
	
	public static final PlayerView PLAYER_VIEW = PlayerView.getInstance();
	public static final SideBar SIDE_BAR = new SideBar(2);
	
	public static GameScreen getInstance() {
		if(instance == null) {
			createInstance();
		}
		return instance;
	}

	private GameScreen(Parent arg0) {
		super(arg0);
	}
	
	private static void createInstance() {
		BorderPane bp = new BorderPane();
		
		bp.setStyle("-fx-background-color: black");
		bp.rightProperty().set(SIDE_BAR);
	    bp.leftProperty().set(PLAYER_VIEW);
	   
	    instance = new GameScreen(bp);
	    instance.refresh();
	    instance.setOnKeyPressed(new GameScreenListener());
	}
	
	public void refresh() {
		PLAYER_VIEW.refresh();
	}

}
