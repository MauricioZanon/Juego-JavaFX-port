package gameScreen;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

//TODO cambiar nombre
public class Console{
	
	public static ObservableList<Text> messages = FXCollections.<Text>observableArrayList(); //TODO cambiar nombre
	
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
		text.setFill(color);
		text.setOnMouseEntered(e -> {
			//TODO si es el nombre de una entidad aparece un text flotante con su descripcion
		});
		return text;
	}

}
