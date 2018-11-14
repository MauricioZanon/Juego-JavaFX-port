package gameScreen;

import java.util.Set;

import actions.ActionType;
import actions.Bump;
import actions.Close;
import actions.EndTurn;
import actions.Examine;
import actions.FollowPath;
import actions.Jump;
import actions.Kick;
import actions.PickUp;
import actions.Shoot;
import actions.Throw;
import application.Main;
import components.AbilitiesComponent;
import components.GraphicComponent;
import components.MovementComponent;
import components.PositionComponent;
import components.VisionComponent;
import effects.Effects;
import eventSystem.EventSystem;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import main.Type;
import map.Map;
import menus.MenuConfig;
import pathFind.AStar;
import pathFind.Path;
import player.PlayerInfo;
import spells.Spell;
import system.RenderSystem;
import tile.Tile;
import time.Clock;
import world.Direction;

//TODO poner el selectionLayer en otro controller
public class GameScreenController {

	@FXML public Canvas entitiesLayer;
	@FXML public Canvas selectionLayer;
	@FXML public VBox sideBar;
	@FXML public VBox resourceBars;
	@FXML public TextFlow console;
	@FXML public Label clockLabel;
	
	private int tileQuantity = 45;
	private int tileSize;
	private GraphicsContext gc;
	private GraphicsContext sgc;
	
	private Tile selectedTile = null;
	
	public void initialize() {
		double sceneHeight = RenderSystem.getInstance().getSceneHeight();
		tileSize = (int) (sceneHeight/tileQuantity);
		entitiesLayer.setHeight(sceneHeight);
		entitiesLayer.setWidth(sceneHeight);
		
		entitiesLayer.focusedProperty().addListener((observedValue, oldValue, newValue) -> {
			if(!oldValue && newValue) {
				InputConfig.setGoToInput();
			}
		});
		
		EventSystem.getIsPlayersTurnProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue && !oldValue) {
				redrawEntitiesLayer();
			}
		});
		
		gc = entitiesLayer.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(Font.font("Courier New", FontWeight.BLACK, tileSize));
		
		selectionLayer.setHeight(sceneHeight);
		selectionLayer.setWidth(sceneHeight);
		selectionLayer.focusedProperty().addListener((observable, oldValue, newValue) -> {
		    if(!oldValue && newValue) {
		    	selectedTile = Main.player.get(PositionComponent.class).getTile();
		    	redrawSelectionLayer();
		    }
		});
		sgc = selectionLayer.getGraphicsContext2D();
		
		sideBar.setPrefWidth(RenderSystem.getInstance().getSceneWidth() - RenderSystem.getInstance().getSceneHeight());
		
	    ResourceBar hpBar = new ResourceBar("Health", Color.FIREBRICK , PlayerInfo.CUR_HP);
	    resourceBars.getChildren().add(hpBar);
	    
	    console.setPrefWidth(RenderSystem.getInstance().getSceneWidth() - RenderSystem.getInstance().getSceneHeight());
	    Bindings.bindContent(console.getChildren(), Console.messages);
	    
	    clockLabel.setText(Clock.getHour());
	    clockLabel.textProperty().bind(Clock.hourProperty);
	    
	    InputConfig.setChangeListener((observedValue, oldValue, newValue) -> {
	    	if(!oldValue.equals(newValue)) {
	    		if(!newValue.equals("go to")) {
	    			selectionLayer.requestFocus();
	    		}else {
	    			entitiesLayer.requestFocus();
	    		}
	    	}
	    }); 
	    
	    redrawEntitiesLayer();
	}
	
	private void redrawEntitiesLayer() {
		PositionComponent pos00 = getPos00();
		Tile[][] map = Map.getSquareAreaAsArray(pos00, tileQuantity, tileQuantity);
		Set<Tile> visibleTiles = Main.player.get(VisionComponent.class).visionMap;
		
		float halfTile = tileSize>>1;
		
		gc.clearRect(0, 0, entitiesLayer.getWidth(), entitiesLayer.getHeight());
		sgc.clearRect(0, 0, selectionLayer.getWidth(), selectionLayer.getHeight());
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				Tile tile = map[i][j];
				if(visibleTiles.contains(tile)) {
					gc.setFill(tile.getBackColor());
					gc.fillRect(i*tileSize, j*tileSize, tileSize, tileSize); 
					gc.setFill(tile.getFrontColor());
					gc.fillText(tile.getASCII(), i*tileSize + halfTile, j*tileSize + halfTile);
				}else if(PlayerInfo.viewedTiles.contains(tile)) {
					gc.setFill(tile.getBackColor().darker());
					gc.fillRect(i*tileSize, j*tileSize, tileSize, tileSize);
					GraphicComponent terrainGC = tile.get(Type.TERRAIN).get(GraphicComponent.class);
					gc.setFill(terrainGC.color.darker());
					gc.fillText(terrainGC.ASCII, i*tileSize + halfTile, j*tileSize + halfTile);
					
				}
			}
		}
	}
	
	@FXML
	public void handleMouseMoved(MouseEvent e) {
		if(EventSystem.isPlayersTurn()) {
			sgc.clearRect(0, 0, selectionLayer.getWidth(), selectionLayer.getHeight());
			selectedTile = getTileUnderTheMouse(e.getX(), e.getY());
			redrawSelectionLayer();
    	}
	}
	
	@FXML
	public void handleMouseClicked(MouseEvent e) {
		if(EventSystem.isPlayersTurn()) {
			sgc.clearRect(0, 0, selectionLayer.getWidth(), selectionLayer.getHeight());
    		switch(e.getButton()) {
    		case MIDDLE:
    			break;
    		case PRIMARY:
    			executeAction();
    			break;
    		case SECONDARY:
    			break;
    		default:
    			break;
    		}
    	}
		e.consume();
	}

	@FXML
	public void handlePressedKeyOnEntityLayer(KeyEvent key) {
		if(!EventSystem.isPlayersTurn()) return;
		
		PositionComponent playerPos = Main.player.get(PositionComponent.class).clone();
		
		switch(key.getCode()) {
		case C:
			if(key.isShiftDown()) {
				RenderSystem.getInstance().changeScene("CraftMenu.fxml");
			}else {
				InputConfig.setCloseInput();
			}
			break;
		case E:
			InputConfig.setExamineInput();
			break;
		case F:
			InputConfig.setShootInput();
			break;
		case I:
			RenderSystem.getInstance().changeScene("Inventory.fxml");
			break;
		case J:
			InputConfig.setJumpInput();
			break;
		case K:
			InputConfig.setKickInput();
			break;
		case M:
//			GameScreen.getInstance().changePlayerView();
			break;
		case Q:
			MenuConfig.openQuaffMenu();
			break;
		case S:
			RenderSystem.getInstance().changeScene("SpellsMenu.fxml");
        	break;
		case T:
			MenuConfig.openThrowMenu();
			break;
		case W:
			if(key.isShiftDown()) {
				MenuConfig.openWearMenu();
			}else {
				MenuConfig.openWieldMenu();
			}
			break;
		case PAGE_UP:
			//subir consola
			break;
		case PAGE_DOWN:
			//bajar consola
			break;
		case SPACE:
			Map.getTile(0, 0, 0).get(Type.FEATURE).name = "table";
			break;
		case COMMA:
			PickUp.execute(Main.player);
			break;
		case LESS:
			if(key.isShiftDown()) {
				playerPos.coord[2]++;
				Effects.move(Main.player, playerPos);
				EndTurn.execute(Main.player, ActionType.WALK);
			}else {
				playerPos.coord[2]--;
				Effects.move(Main.player, playerPos);
				EndTurn.execute(Main.player, ActionType.WALK);
			}
			break;
		case NUMPAD1:
			Bump.execute(playerPos, Direction.SW);
			break;
		case NUMPAD2:
			Bump.execute(playerPos, Direction.S);
			break;
		case NUMPAD3:
			Bump.execute(playerPos, Direction.SE);
			break;
		case NUMPAD4:
			Bump.execute(playerPos, Direction.W);
			break;
		case NUMPAD5:
			EndTurn.execute(Main.player, ActionType.WAIT);
			break;
		case NUMPAD6:
			Bump.execute(playerPos, Direction.E);
			break;
		case NUMPAD7:
			Bump.execute(playerPos, Direction.NW);
			break;
		case NUMPAD8:
			Bump.execute(playerPos, Direction.N);
			break;
		case NUMPAD9:
			Bump.execute(playerPos, Direction.NE);
			break;
		case ESCAPE:
			System.exit(0);
			break;
		default:
			break;
		}
		key.consume();
	}
	
	@FXML
	public void handlePressedKeyOnSelectionLayer(KeyEvent key) {
		PositionComponent playerPos = Main.player.get(PositionComponent.class);
		Tile newSelectedTile = null;
		int maxDistance = InputConfig.getMaxDistance();
		
		KeyCode code = key.getCode();
		switch(key.getCode()) {
		case NUMPAD1:
			newSelectedTile = Map.getTile(selectedTile, Direction.SW);
			break;
		case NUMPAD2:
			newSelectedTile = Map.getTile(selectedTile, Direction.S);
			break;
		case NUMPAD3:
			newSelectedTile = Map.getTile(selectedTile, Direction.SE);
			break;
		case NUMPAD4:
			newSelectedTile = Map.getTile(selectedTile, Direction.W);
			break;
		case NUMPAD6:
			newSelectedTile = Map.getTile(selectedTile, Direction.E);
			break;
		case NUMPAD7:
			newSelectedTile = Map.getTile(selectedTile, Direction.NW);
			break;
		case NUMPAD8:
			newSelectedTile = Map.getTile(selectedTile, Direction.N);
			break;
		case NUMPAD9:
			newSelectedTile = Map.getTile(selectedTile, Direction.NE);
			break;
		case ESCAPE:
			sgc.clearRect(0, 0, selectionLayer.getWidth(), selectionLayer.getHeight());
			entitiesLayer.requestFocus();
			key.consume();
			return;
		case ENTER:
			sgc.clearRect(0, 0, selectionLayer.getWidth(), selectionLayer.getHeight());
			executeAction();
			key.consume();
			return;
		default:
			break;
		}
		
		if(code != KeyCode.ESCAPE && code != KeyCode.ENTER && maxDistance == 0) {
			selectedTile = newSelectedTile;
			executeAction();
			key.consume();
			return;
		}
		
		if(Map.getDistance(playerPos, newSelectedTile.getPos()) <= maxDistance) {
			selectedTile = newSelectedTile;
			redrawSelectionLayer();
		}
		
		key.consume();
	}
	
	private void executeAction() {
		switch(InputConfig.getMouseAction()) {
		case "cast":
			Spell castedSpell = Main.player.get(AbilitiesComponent.class).getSpell(InputConfig.getThrownItemName());
			castedSpell.cast(Main.player, selectedTile);
			break;
		case "close":
			if(selectedTile.has(Type.FEATURE) && selectedTile.get(Type.FEATURE).name.contains("open")) {
				Close.execute(Main.player, selectedTile);
			}
			break;
		case "examine":
			Examine.execute(selectedTile);
			break;
		case "go to":
			PositionComponent pos = Main.player.get(PositionComponent.class);
			Path path = AStar.findPath(pos, selectedTile.getPos(), Main.player);
			if(path.getLength() > 0) {
				Main.player.get(MovementComponent.class).path = path;
				FollowPath.execute(Main.player);
			}
			break;
		case "jump":
			Jump.execute(Main.player, selectedTile);
			break;
		case "kick":
			Kick.execute(Main.player, selectedTile);
			break;
		case "shoot":
			Shoot.execute(Main.player, selectedTile);
			break;
		case "throw":
			Throw.execute(Main.player, selectedTile, InputConfig.getThrownItemName());
			break;
		}
		entitiesLayer.requestFocus();
	}

	/**
	 * @return Devuelve la posición del @Tile que se dibuja en la esquina superior izquierda del canvas
	 */
	private PositionComponent getPos00() {
		PositionComponent pos00 = Main.player.get(PositionComponent.class).clone();
		int tileQuantity = (int) (entitiesLayer.getWidth()/tileSize);
	    pos00.coord[0] -= tileQuantity/2;
	    pos00.coord[1] -= tileQuantity/2;
	    
	    return pos00;
	}
	
	private Tile getTileUnderTheMouse(double x, double y) {
    	PositionComponent pos00 = getPos00();
    	pos00.coord[0] += (int) (x / tileSize);
    	pos00.coord[1] += (int) (y / tileSize);
    	
    	return pos00.getTile();
	}
	
	/**
	 * Dibuja el tile que este seleccionado o bajo el mouse, si se esta disparando, lanzando un item o algún otro tipo de proyectil o habilidad
	 * va a dibujar también el area que afecta y el camino que recorre
	 */
	private void redrawSelectionLayer() {
		sgc.clearRect(0, 0, selectionLayer.getWidth(), selectionLayer.getHeight());
		
		if(!InputConfig.getMouseAction().equals("go to"))
			drawSelectableArea();
		
		if(InputConfig.isProjectile()) {
			sgc.setStroke(Color.YELLOW);
			sgc.setGlobalAlpha(1);
			Map.getStraigthLine(Main.player.get(PositionComponent.class), selectedTile.getPos()).forEach(t -> drawSelectedTile(t));
		}
		
		int radius = InputConfig.getAffectedRadius();
		if(radius > 1) {
			sgc.setStroke(Color.WHITE);
			sgc.setGlobalAlpha(0.5f);
			Map.getCircundatingAreaAsSet(radius, selectedTile, true).forEach(t -> drawSelectedTile(t));
		}
		
		sgc.setStroke(Color.YELLOW);
		sgc.setGlobalAlpha(1);
		drawSelectedTile(selectedTile);
	}
	
	private void drawSelectableArea() {
		int maxDistance = InputConfig.getMaxDistance();
		Set<Tile> drawedTiles;
		if(maxDistance > 0) {
			drawedTiles = Map.getCircundatingAreaAsSet(maxDistance, Main.player.get(PositionComponent.class).getTile(), true);
		}
		else {
			drawedTiles = Map.getAdjacentTiles(Main.player.get(PositionComponent.class).getTile());
		}
		
		sgc.setStroke(Color.BLACK);
		for(Tile t : drawedTiles) {
			double[] coordInCanvas = getDiscretePosInCanvas(t.getPos());
			sgc.strokeRect(coordInCanvas[0], coordInCanvas[1], tileSize, tileSize);
		}
	}
	
	private void drawSelectedTile(Tile tile) {
		double[] coordInCanvas = getDiscretePosInCanvas(tile.getPos());
		sgc.strokeRect(coordInCanvas[0], coordInCanvas[1], tileSize, tileSize);
		sgc.strokeOval(coordInCanvas[0], coordInCanvas[1], tileSize, tileSize);
	}
	
	/**
	 * @return Devuelve un array con dos doubles x e y, ambos son múltiplo de tileSize y representan la posición
	 * 		   del canvas en el que se debe dibujar un @Tile
	 */
	private double[] getDiscretePosInCanvas(PositionComponent pos) {
		PositionComponent pos00 = getPos00();
		double x = (pos.coord[0] - pos00.coord[0])*tileSize;
		double y = (pos.coord[1] - pos00.coord[1])*tileSize;
		
		return new double[] {x, y};
	}
	
}
