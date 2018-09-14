package Menus;

import java.util.Map.Entry;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.Entity;

/**
 * Muestra la descripción y los atributos del item seleccionado en @ItemList
 */
public class ItemDesc extends TextFlow{
	
	public static ItemDesc instance = null;
	
	private ItemDesc() {
		setMouseTransparent(true);
		setFocusTraversable(false);
		setPadding(new Insets(0, 0, 0, 10));
		setMaxWidth(450);
	}
	
	public void refresh() {
		getChildren().clear();
		
		Entity item = ItemList.getInstance().getSelectedItem();
		if(item != null) {
			Text name = new Text(StringUtils.toTitle(item.name) + "\n\n");
			name.setFill(Color.WHITE);
			name.setFont(Font.font("courier new", FontWeight.BLACK, 20));
			name.setUnderline(true);
			
			Text desc = new Text(item.description + "\n\n");
			desc.setFill(Color.WHITE);
			
			getChildren().addAll(name, desc);

			for(Entry<String, Float> att : item.getAttributes().entrySet()) {
				Text attName = new Text(att.getKey().toUpperCase() + ": ");
				attName.setFill(Color.LIGHTSTEELBLUE);
				Text attValue = new Text(att.getValue() + "\n");
				attValue.setFill(Color.WHITE);
				getChildren().addAll(attName, attValue);
			}
		}
	}
	
	public static ItemDesc getInstance() {
		if(instance == null) {
			instance = new ItemDesc();
		}
		return instance;
	}

}
