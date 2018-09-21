package gameScreen;

import input.GameScreenKeyboardListener;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import playerViews.EntitiesLayer;

public class GameScreen extends Scene{
	
	private static GameScreen instance;
	
	/** BorderPane que contiene todo lo de la pantalla */
	public static final BorderPane PARENT = new BorderPane();
	
	public static final StackPane screen = new StackPane();
	public static final SideBar SIDE_BAR = new SideBar(2);

	private GameScreen(Parent arg0) {
		super(arg0);
	}
	
	private static void createInstance() {
		PARENT.setStyle("-fx-background-color: black");
		PARENT.rightProperty().set(SIDE_BAR);
		PARENT.leftProperty().set(screen);
		
		screen.getChildren().add(EntitiesLayer.getInstance());
	   
	    instance = new GameScreen(PARENT);
	    instance.refresh();
	    instance.setOnKeyPressed(new GameScreenKeyboardListener());
	}
	
	public void changePlayerView() {
//		if(playerView instanceof RegularPlayerView) {
//			playerView = MapPlayerView.getInstance();
//		}else {
//			playerView = RegularPlayerView.getInstance();
//		}
//		PARENT.leftProperty().set(playerView);
//		playerView.refresh();
	}
	
	public void refresh() {
		EntitiesLayer.getInstance().refresh();
		SIDE_BAR.refresh();
	}
	
	public void refreshPlayerView() {
		Platform.runLater(() -> EntitiesLayer.getInstance().refresh());
		
	}
	
	/**
	 * @param menu: inventario, lista de skills, etc
	 */
	public void showMenu(Node inventoryMenu) {
		screen.getChildren().clear();
		screen.getChildren().add(inventoryMenu);
	}
	
	public void hideMenu() {
		screen.getChildren().clear();
		screen.getChildren().add(EntitiesLayer.getInstance());
	}
	
	public static GameScreen getInstance() {
		if(instance == null) {
			createInstance();
		}
		return instance;
	}


}
