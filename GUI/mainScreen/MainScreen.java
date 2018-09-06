package mainScreen;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class MainScreen extends Scene{
	
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
            	Main.startNewGame();
            	event.consume();
            }
        });
        
        Button loadBtn = new Button("Load game");
        loadBtn.setLayoutX(100);
        loadBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("load game");
				event.consume();
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
        		switch(event.getCode()) {
        		case N:
        			Main.startNewGame();
        			event.consume();
                	break;
        		case ESCAPE:
        			System.exit(0);
        			event.consume();
				default:
					break;
        		}
        	}
        });
	}
	
	public void refresh() {}
}
