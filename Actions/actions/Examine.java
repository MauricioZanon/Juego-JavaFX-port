package actions;

import application.Main;
import gameScreen.InputConfig;
import main.Entity;
import main.Type;
import system.RenderSystem;
import tile.Tile;

/** Examina un tile adyacente y, de ser posible, usa la entidad encontrada */
public class Examine {
	
	public static Tile examinedTile = null;
	
	private Examine() {}
	
	//TODO elegir entidad si hay mas de una en el tile
	public static void execute(Tile tile) {
		Entity examinedFeature = tile.get(Type.FEATURE);
		if(examinedFeature != null) {
			switch(examinedFeature.TYPE) {
			case CONTAINER:
				examinedTile = tile;
				RenderSystem.getInstance().changeScene("ItemExchangeMenu.fxml");
				break;
			case DOOR:
				if(tile.has(Type.FEATURE) && tile.get(Type.FEATURE).name.contains("open")) {
					Close.execute(Main.player, tile);
				}
				break;
			default:
				break;
			}
			
		}
		InputConfig.setGoToInput();
	}

}
