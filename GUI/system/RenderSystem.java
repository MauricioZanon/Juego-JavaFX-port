package system;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RenderSystem {
	
	private static RenderSystem instance = null;
	private Stage primaryStage;
	
	private FXMLLoader loader;
	
	private RenderSystem() {}
	
	public void initialize(Stage ps) {
		primaryStage = ps;
	}
	
	public void changeScene(String fxmlFileName) {
		FXMLLoader newLoader = new FXMLLoader(getClass().getResource("../FXML/"+ fxmlFileName));
		Scene scene = null;
		try {
			scene = new Scene(newLoader.load());
		} catch (IOException e) {
			System.out.println("no se encontro " + fxmlFileName + ". Cuando pasa esto casi siempre es porque no se puso"
					+ " bien la direcion del controller en el FXML o porque hay un error en el metodo initialize del controller,"
					+ " en vez de tirar escepcion en ese metodo tira IOException aca");
		}
		if(scene != null) {
			loader = newLoader;
			primaryStage.setScene(scene);
			scene.getRoot().requestFocus();
		}
	}
	
	public Object getController() {
		return loader.getController();
	}
	
	public double getSceneHeight() {
		return primaryStage.getScene().getHeight();
	}

	public double getSceneWidth() {
		return primaryStage.getScene().getWidth();
	}

	public static RenderSystem getInstance() {
		if(instance == null) {
			instance = new RenderSystem();
		}
		return instance;
	}
	
}
