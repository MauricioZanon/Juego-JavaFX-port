package actions;

import application.Main;
import gameScreen.InputConfig;
import main.Type;
import system.RenderSystem;
import tile.Tile;

/** Examina un tile adyacente y, de ser posible, usa la entidad encontrada */
public class Examine {
	
	public static Tile examinedTile = null;
	
	private Examine() {}
	
	//TODO elegir entidad si hay mas de una en el tile
	public static void execute(Tile tile) {
		if(tile.has(Type.CONTAINER)) {
			examinedTile = tile;
			RenderSystem.getInstance().changeScene("ItemExchangeMenu.fxml");
		}
		else if(tile.has(Type.FEATURE) && tile.get(Type.FEATURE).name.contains("open")) {
			Close.execute(Main.player, tile);
		}
		
		InputConfig.setGoToInput();
	}

}
