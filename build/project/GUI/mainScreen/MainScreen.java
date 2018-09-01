package mainScreen;
import application.Main;
import application.StateSaver;
import components.PositionComponent;
import gameScreen.GameScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import map.Map;
import system.RenderSystem;
import system.Screen;
import world.WorldBuilder;

public class MainScreen extends Scene implements Screen{
	
	private static MainScreen instance = null;
	
	public static MainScreen getInstance() {
		if(instance == null) {
			createInstance();
		}
		return instance;
	}

	private MainScreen(Parent root) {
		super(root);
   }

	private static void createInstance() {
		Button btn = new Button("New Game"); 
        btn.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
            	WorldBuilder.createWorld();
            	StateSaver.createInitialSave();
            	Map.getTile(0, 0, 0).put(Main.PLAYER);
            	Map.refresh();
            	RenderSystem.changeScene(GameScreen.getInstance());
            }
        });
        
        Button loadBtn = new Button("Load game");
        loadBtn.setLayoutX(100);
        loadBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("load game");
			}
        });
        
        AnchorPane root = new AnchorPane();
        
        root.setPadding(new Insets(10));
        
        root.getChildren().add(btn);
        root.getChildren().add(loadBtn);
        
        instance = new MainScreen(root);
        
        instance.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	@Override
        	public void handle(KeyEvent event) {
        		System.out.println(event.getCode());
        		switch(event.getCode()) {
        		case N:
        			WorldBuilder.createWorld();
                	StateSaver.createInitialSave();
                	Map.getTile(0, 0, 0).put(Main.PLAYER);
                	Map.refresh();
                	RenderSystem.changeScene(GameScreen.getInstance());
                	break;
        		case ESCAPE:
        			System.exit(0);
				default:
					break;
        		}
        	}
        });
	}
	
	public void refresh() {}
}
