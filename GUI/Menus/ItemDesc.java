package Menus;

import java.util.Map.Entry;

import javafx.scene.control.TextArea;
import main.Entity;

/**
 * Muestra la descripción y los atributos del item seleccionado en @ItemList
 */
public class ItemDesc extends TextArea{
	
	public static ItemDesc instance = null;
	
	private ItemDesc() {
		setMouseTransparent(true);
		setFocusTraversable(false);
		
		setStyle("-fx-background-color: black, black, black, brown;");
		setWrapText(true);
	}
	
	public void refresh() {
		Entity item = ItemList.getInstance().getSelectedItem();
		StringBuilder sb = new StringBuilder();
		if(item != null) {
			sb.append(item.name);
			sb.append("\n\n");
			sb.append(item.description);
			sb.append("\n\n");
			
			for(Entry<String, Float> att : item.getAttributes().entrySet()) {
				sb.append(att.getKey().toUpperCase());
				sb.append(": ");
				sb.append(att.getValue());
				sb.append("\n");
			}
		}
		setText(sb.toString());
	}
	
	public static ItemDesc getInstance() {
		if(instance == null) {
			instance = new ItemDesc();
		}
		return instance;
	}

}
