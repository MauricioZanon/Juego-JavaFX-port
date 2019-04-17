package menus;

import java.util.HashMap;
import java.util.Map;

import actions.Examine;
import components.UsesC;
import components.UsesC.UseType;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.Entity;
import system.RenderSystem;

public class ExamineMenuController {

	@FXML public BorderPane root;
	@FXML public ListView<String> entitiesList;
    @FXML public ListView<String> actionList;
	public StackPane searchBar;
    
    private Map<String, Entity> usableEntities = new HashMap<>();

    public void initialize() {
    	Examine.examinedTile.getEntities(e -> e.has(UsesC.class)).forEach(e -> usableEntities.put(e.name, e));
    	usableEntities.keySet().forEach(n -> entitiesList.getItems().add(n));
    	
    	entitiesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			refreshActionList();
		});
    	
    	actionList.focusedProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue) {
    			actionList.getSelectionModel().select(0);
    		}
    	});
    	
    	searchBar = (StackPane) RenderSystem.getInstance().loadNode("SearchBar.fxml");
    	root.setBottom(searchBar);
    	
    }
    
    private void refreshActionList() {
    	actionList.getItems().clear();
    	Entity selectedEntity = usableEntities.get(entitiesList.getSelectionModel().getSelectedItem());
    	selectedEntity.get(UsesC.class).uses.forEach(u -> actionList.getItems().add(u.toString()));
    	
    }
    
    @FXML
    public void handlePressedKeyInEntitiesList(KeyEvent event) {
    	switch(event.getCode()) {
		case F:
			searchBar.requestFocus();
			break;
		case DOWN:
		case NUMPAD2:
			entitiesList.getSelectionModel().selectNext();
			break;
		case UP:
		case NUMPAD8:
			entitiesList.getSelectionModel().selectPrevious();
			break;
		case RIGHT:
		case NUMPAD6:
		case ENTER:
			actionList.requestFocus();
			break;
		case ESCAPE:
			RenderSystem.getInstance().closeSecondaryStage();
			break;
		default:
			break;
		}
		event.consume();
    }
    
    @FXML
    public void handleKeyPressedInActionList(KeyEvent event) {
    	switch(event.getCode()) {
		case DOWN:
		case NUMPAD2:
			actionList.getSelectionModel().selectNext();
			break;
		case UP:
		case NUMPAD8:
			actionList.getSelectionModel().selectPrevious();
			break;
		case LEFT:
		case NUMPAD4:
			entitiesList.requestFocus();
			break;
    	case ENTER:
    		Entity selectedEntity = usableEntities.get(entitiesList.getSelectionModel().getSelectedItem());
    		UseType selectedUse = UseType.valueOf(actionList.getSelectionModel().getSelectedItem());
    		Examine.useEntity(selectedEntity, selectedUse);
			RenderSystem.getInstance().closeSecondaryStage();
    		break;
    	case ESCAPE:
			RenderSystem.getInstance().closeSecondaryStage();
			break;
		default:
			break;
    	}
    	event.consume();

    }

    @FXML
    public void handlePressedKeyInItemList(KeyEvent event) {

    }

}