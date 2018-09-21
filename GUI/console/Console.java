package console;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Console extends ScrollPane{
	
	private static Console instance = null;
	private TextFlow console = new TextFlow();
	
	private Console() {
		setFitToWidth(true);
		setContent(console);
		setStyle("-fx-background: #000000; "
				+ "-fx-font-weight: BOLD ");
		setMinHeight(400);
		setMaxHeight(400);
		setFocusTraversable(false);
	}
	
	/**
	 * Agrega un mensaje a la consola
	 * @param t El texto que se quiere agregar, debe estar separado con - para cada parte del texto
	 * @param colors Los colores que se usan, se usan en orden para cada parte del texto
	 */
	public void addMessage(String t, Color... colors) {
		Platform.runLater(() -> {
			String[] splittedText = t.split("-");
			for(int i = 0; i < splittedText.length; i++) {
				console.getChildren().add(createText(splittedText[i], colors[i]));
			}
		});
	}
	
	public void addMessage(String text) {
		Platform.runLater(() -> console.getChildren().add(createText(text, Color.WHITE)));
		
	}
	
	private Text createText(String message, Color color) {
		Text text = new Text(message);
		text.setFill(color);
		text.setOnMouseEntered(e -> {
			//TODO si es el nombre de una entidad aparece un text flotante con su descripcion
		});
		return text;
	}
	
	//FIXME al scrollear despues de haber llegado a un limite la consola queda quieta pero el valor sigue cambiando, 
	// y cuando se quiere volver a scrollear para el otro lado hay que mantener el boton un rato
	public void scroll(int value) {
//		setScrollTop(getScrollTop() + value);
	}
	
	public static Console getInstance() {
		if(instance == null) {
			instance = new Console();
		}
		return instance;
	}

}
