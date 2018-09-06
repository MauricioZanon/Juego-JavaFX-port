package application;

import java.awt.Dimension;
import java.awt.Toolkit;

import eventSystem.EventSystem;
import factories.EntityFactory;
import gameScreen.GameScreen;
import javafx.application.Application;
import javafx.stage.Stage;
import main.Entity;
import mainScreen.MainScreen;
import map.Map;
import player.PlayerBuilder;
import system.RenderSystem;
import time.Clock;
import world.WorldBuilder;

public class Main extends Application { 
	
	public static Entity player;
	
	public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	EntityFactory.initialize();
    	Clock.initialize();
    	player = PlayerBuilder.createBasePlayer();
        
        configureStage(primaryStage);
        RenderSystem.initialize(primaryStage);
        RenderSystem.changeScene(MainScreen.getInstance());
        primaryStage.show();
        
        
    }
    
    private void configureStage(Stage ps) {
    	ps.setTitle("Rogue World");
//    	ps.setMaximized(true);
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	double width = screenSize.getHeight() + (screenSize.getWidth() - screenSize.getHeight()) / 2;
    	double height = screenSize.getHeight();
    	ps.setWidth(width);
    	ps.setHeight(height);
    }
    
    public static void startNewGame() {
//    	StateSaver.createInitialSave();
    	WorldBuilder.createWorld();
    	Map.getTile(0, 0, 0).put(Main.player);
    	Map.refresh();
    	RenderSystem.changeScene(GameScreen.getInstance());
    	startGameLoop();
    }
    
    public static void startGameLoop() {
    	new Thread(() -> {
    		while(!Thread.currentThread().isInterrupted()) {
    			if(!EventSystem.waitingOnPlayerInput) {
    				long tiempo = System.currentTimeMillis();
    				EventSystem.update();
    				
    				long sleepTime = 32 - (System.currentTimeMillis() - tiempo);
    				if(sleepTime > 0) {
    					try {
    						Thread.sleep(sleepTime);
    					} catch (InterruptedException e) {}
    				}
    			}
    		}
    	}).start();
    }
    
}
