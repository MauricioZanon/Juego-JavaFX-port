package gameScreen;

import input.GameScreenKeyboardListener;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import playerViews.EntitiesLayer;
import playerViews.WeatherLayer;
import system.Screen;

public class GameScreen extends Scene implements Screen{
	
	private static GameScreen instance;
	
	/** BorderPane que contiene todo lo de la pantalla */
	public static final BorderPane PARENT = new BorderPane();
	
	public static final StackPane screen = new StackPane();
	public static EntitiesLayer playerView = EntitiesLayer.getInstance();
	public static WeatherLayer weatherLayer = WeatherLayer.getInstance();
	public static final SideBar SIDE_BAR = new SideBar(2);

	private GameScreen(Parent arg0) {
		super(arg0);
	}
	
	private static void createInstance() {
		PARENT.setStyle("-fx-background-color: black");
		PARENT.rightProperty().set(SIDE_BAR);
		PARENT.leftProperty().set(screen);
		
		screen.getChildren().add(playerView);
		screen.getChildren().add(weatherLayer);
	   
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
		playerView.refresh();
		SIDE_BAR.refresh();
	}
	
	public void refreshPlayerView() {
		playerView.refresh();
//		weatherView.refresh();
		
	}
	
	/**
	 * TODO:esto no tiene que estar aca, para cambiar de menu o de vista se tiene que usar el @RenderSystem
	 * Muestra el menu que se pasa como parámetro
	 * @param menu: inventario, lista de skills, etc
	 */
	public void showMenu(Node inventoryMenu) {
		screen.getChildren().add(inventoryMenu);
	}
	
	public void hideMenu() {
		screen.getChildren().clear();
		screen.getChildren().add(playerView);
	}
	
	public static GameScreen getInstance() {
		if(instance == null) {
			createInstance();
		}
		return instance;
	}


}
