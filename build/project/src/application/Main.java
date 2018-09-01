package application;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.stage.Stage;
import main.Entity;
import mainScreen.MainScreen;
import player.PlayerBuilder;
import system.RenderSystem;

public class Main extends Application { 
	
	public static final Entity PLAYER = PlayerBuilder.createBasePlayer();
	
	public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	configureStage(primaryStage);
    	
        RenderSystem.initialize(primaryStage);
        
        RenderSystem.changeScene(MainScreen.getInstance());
        primaryStage.show();
        
    }
    
    private void configureStage(Stage ps) {
    	ps.setTitle("Rogue World");
    	ps.setMaximized(true);
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	double width = screenSize.getWidth();
    	double height = screenSize.getHeight();
    	ps.setWidth(width);
    	ps.setHeight(height);
    }
    
}
