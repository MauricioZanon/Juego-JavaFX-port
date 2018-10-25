package application;

import java.io.File;

import Persistency.StateSaver;
import eventSystem.EventSystem;
import factories.EntityFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import main.Entity;
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
    	EntityFactory.loadEntities();
    	Clock.initialize();
    	player = PlayerBuilder.createBasePlayer();
    	
    	RenderSystem.getInstance().initialize(primaryStage);
    	RenderSystem.getInstance().changeScene("MainMenuScreen.fxml");
    	primaryStage.show();
    }
    
    public static void startNewGame() {
    	File saveFile = new File("assets/Saves/world.db");
    	if(saveFile.exists()) {
    		saveFile.delete();
    	}
    	WorldBuilder.createWorld();
    	StateSaver.getInstance().savingThread.start();
    	Map.getTile(0, 0, 0).put(Main.player);
    	Map.refresh();
    	RenderSystem.getInstance().changeScene("GameScreen.fxml");
    	startGameLoop();
    }
    
    public static void loadGame() {
    	StateSaver.getInstance().savingThread.start();
    	Map.getTile(0, 0, 0).put(Main.player);
    	Map.refresh();
    	RenderSystem.getInstance().changeScene("GameScreen.fxml");
    	startGameLoop();
    }
    
    public static void startGameLoop() {
    	new Thread(() -> {
    		while(!Thread.currentThread().isInterrupted()) {
    			if(!EventSystem.isPlayersTurn()) {
    				long tiempo = System.currentTimeMillis();
    				EventSystem.update();
    				
    				long sleepTime = 50 - (System.currentTimeMillis() - tiempo);
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
