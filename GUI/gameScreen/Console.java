package gameScreen;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Console{
	
	public static ObservableList<Text> messages = FXCollections.<Text>observableArrayList();
	
	private Console() {}
	
	/**
	 * Agrega un mensaje a la consola
	 * @param text El texto que se quiere agregar, debe estar separado con - para cada parte del texto
	 * @param colors Los colores que se usan, se usan en orden para cada parte del texto
	 */
	public static void addMessage(String text, Color... colors) {
		String[] splittedText = text.split("-");
		for(int i = splittedText.length - 1; i >= 0; i--) {
			addToMessageList(createTextNode(splittedText[i], colors[i]));
		}
	}
	
	public static void addMessage(String text) {
		addToMessageList(createTextNode(text, Color.WHITE));
	}
	
	private static void addToMessageList(Text textNode) {
		Platform.runLater(() -> messages.add(0, textNode));
	}
	
	private static Text createTextNode(String message, Color color) {
		Text text = new Text(message);
		text.setFont(Font.font("courier new", FontWeight.BLACK, 14));
		text.setFill(color);
		text.setOnMouseEntered(e -> {
			//TODO si es el nombre de una entidad aparece un texto flotante con su descripcion
		});
		return text;
	}

}
