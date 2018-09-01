package system;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class RenderSystem {
	
	private static Stage primaryStage;
	
	public static void initialize(Stage ps) {
		primaryStage = ps;
	}
	
	public static void changeScene(Scene scene) {
		primaryStage.setScene(scene);
	}
	
	public static void render() {
		primaryStage.show();
	}
	
	public static double getStageHeight() {
		return primaryStage.getHeight();
	}

	public static double getStageWidth() {
		return primaryStage.getWidth();
	}

}
