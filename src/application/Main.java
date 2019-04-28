package application;

import java.io.File;

import components.PositionC;
import crafts.RecipeList;
import eventSystem.EventSystem;
import factories.EntityFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import main.Entity;
import map.Map;
import persistency.StateLoader;
import persistency.StateSaver;
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
    	RecipeList.loadRecipes();
    	Clock.initialize();
    	
    	RenderSystem.getInstance().initialize(primaryStage);
    	RenderSystem.getInstance().changeScene("MainMenuScreen.fxml", false);
    	primaryStage.show();
    }
    
    public static void newGame() {
    	String worldName = "world";
    	File saveFile = new File("assets/Saves/" + worldName + ".db");
    	if(saveFile.exists()) {
    		boolean wasDeleted = saveFile.delete();
    		System.out.println("previows save file deleted: " + wasDeleted);
    	}
    	
    	WorldBuilder.createWorld(worldName);
    	
    	StateSaver.getInstance().savingThread.start();
    	
    	player = PlayerBuilder.createBasePlayer();
    	Map.getTile(0, 0, 0).put(player);
    	Map.refresh();
    	
    	RenderSystem.getInstance().changeScene("GameScreen.fxml", false);
    	
    	startGameLoop();
    	
    }
    
    public static void loadGame() {
    	player = PlayerBuilder.createBasePlayer();
    	StateSaver.getInstance().savingThread.start();
    	
    	StateLoader.getInstance().loadWorldState();
    	StateLoader.getInstance().loadPlayer();
    	
    	player.get(PositionC.class).getTile().put(player);
    	Map.refresh();
    	RenderSystem.getInstance().changeScene("GameScreen.fxml", false);
    	startGameLoop();
    }
    
    public static void startGameLoop() {
    	new Thread(() -> {
    		while(!Thread.currentThread().isInterrupted()) {
    			if(!EventSystem.isPlayersTurn()) {
    				long tiempo = System.currentTimeMillis();
    				EventSystem.update();
    				
    				long sleepTime = 70 - (System.currentTimeMillis() - tiempo);
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
