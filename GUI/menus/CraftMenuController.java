package menus;

import java.util.ArrayList;
import java.util.List;

import actions.Craft;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import player.Recipe;
import player.RecipeList;
import system.RenderSystem;
import text.StringUtils;

public class CraftMenuController {

    @FXML private HBox categories;
    @FXML private ListView<Text> craftablesList;
    @FXML private TextFlow description;
    @FXML private TextField searchField;
    @FXML private TextFlow requirements;
    
    private List<Text> availableCategories = new ArrayList<>();
    private List<Recipe> shownRecipes;
    private int categoryIndex = 0;

    public void initialize() {
    	Text weaponsText = new Text("Weapons");
    	weaponsText.setFont(Font.font("courier new", FontWeight.BLACK, 22));
    	weaponsText.setFill(Color.WHITE);
    	availableCategories.add(weaponsText);
    	
    	Text armorText = new Text("Armor");
    	armorText.setFont(Font.font("courier new", FontWeight.BLACK, 22));
    	armorText.setFill(Color.WHITE);
    	availableCategories.add(armorText);
    	
    	Text clothesText = new Text("Clothes");
    	clothesText.setFont(Font.font("courier new", FontWeight.BLACK, 22));
    	clothesText.setFill(Color.WHITE);
    	availableCategories.add(clothesText);
    	
    	Text jewelsText = new Text("Jewels");
    	jewelsText.setFont(Font.font("courier new", FontWeight.BLACK, 22));
    	jewelsText.setFill(Color.WHITE);
    	availableCategories.add(jewelsText);
    	
    	Text foodText = new Text("Food");
    	foodText.setFont(Font.font("courier new", FontWeight.BLACK, 22));
    	foodText.setFill(Color.WHITE);
    	availableCategories.add(foodText);
    	
    	Text magicItemsText = new Text("Magic items");
    	magicItemsText.setFont(Font.font("courier new", FontWeight.BLACK, 22));
    	magicItemsText.setFill(Color.WHITE);
    	availableCategories.add(magicItemsText);
    	
    	Text miscText = new Text("Misc");
    	miscText.setFont(Font.font("courier new", FontWeight.BLACK, 22));
    	miscText.setFill(Color.WHITE);
    	availableCategories.add(miscText);
    	
    	categories.getChildren().addAll(availableCategories);
    	
    	changeSelectedCategory(0);
    }
    
    @FXML
    void handlePressedKey(KeyEvent event) {
    	switch(event.getCode()) {
    	case ESCAPE:
    		RenderSystem.getInstance().closeSecondaryStage();
    		break;
    	case LEFT:
    	case NUMPAD4:
    		changeSelectedCategory(-1);
    		refreshRequirements();
    		break;
    	case RIGHT:
    	case NUMPAD6:
    		changeSelectedCategory(1);
    		refreshRequirements();
    		break;
    	case DOWN:
    	case NUMPAD2:
			craftablesList.getSelectionModel().selectNext();
			refreshRequirements();
    		break;
    	case UP:
    	case NUMPAD8:
			craftablesList.getSelectionModel().selectPrevious();
			refreshRequirements();
    		break;
    	case ENTER:
    		Recipe selectedRecipe = shownRecipes.get(craftablesList.getSelectionModel().getSelectedIndex());
    		if(selectedRecipe.isCraftable()) {
    			Craft.execute(selectedRecipe);
    			RenderSystem.getInstance().closeSecondaryStage();
    		}
    		break;
    	default:
    		break;
    	}
    	
    	event.consume();
    }

	private void changeSelectedCategory(int variation) {
    	Text selectedCategory = availableCategories.get(categoryIndex);
    	selectedCategory.setFill(Color.WHITE);
    	
    	categoryIndex += variation;
    	if(categoryIndex < 0) categoryIndex = availableCategories.size()-1;
    	else if(categoryIndex > availableCategories.size()-1) categoryIndex = 0;
    	
    	selectedCategory = availableCategories.get(categoryIndex);
    	selectedCategory.setFill(Color.YELLOW);
    	
    	refreshCraftablesList(selectedCategory.getText());
    }
    
    private void refreshCraftablesList(String category) {
    	craftablesList.getItems().clear();
    	shownRecipes = RecipeList.recipes.get(category);
    	
    	if(shownRecipes != null) {
    		for(Recipe r : shownRecipes) {
    			Text text = new Text(r.name);
    			if(r.isCraftable()) {
    				text.setFill(Color.WHITE);
    			}else {
    				text.setFill(Color.GREY);
    			}
    			craftablesList.getItems().add(text);
    		}
    	}
    	
    }
    
    private void refreshRequirements() {
    	requirements.getChildren().clear();

		if(craftablesList.getSelectionModel().getSelectedIndex() < 0) // Si no hay nada seleccionado...
			return;
		
    	Recipe selectedRecipe = shownRecipes.get(craftablesList.getSelectionModel().getSelectedIndex());
    	
    	Text recipeName = new Text(StringUtils.toTitle(selectedRecipe.name) + "\n\n");
    	recipeName.setFont(Font.font("courier new", FontWeight.BLACK, 20));
    	recipeName.setUnderline(true);
    	recipeName.setFill(Color.WHITE);
    	requirements.getChildren().add(recipeName);
    	
    	Text materialsTitle = new Text("Materials\n");
    	materialsTitle.setFont(Font.font("courier new", FontWeight.BOLD, 16));
    	materialsTitle.setUnderline(true);
    	materialsTitle.setFill(Color.AQUAMARINE);
    	requirements.getChildren().add(materialsTitle);
    	
    	String[] materials = selectedRecipe.materials.split("&");
    	for(int i = 0; i < materials.length; i++) {
    		Text t = new Text(materials[i].replace("|", " or ") + "\n");
    		t.setFont(Font.font("courier new", FontWeight.BOLD, 14));
    		t.setFill(Color.WHITE);
    		requirements.getChildren().add(t);
    	}
    	
    	Text toolsTitle = new Text("\nTools\n");
    	toolsTitle.setFont(Font.font("courier new", FontWeight.BOLD, 16));
    	toolsTitle.setUnderline(true);
    	toolsTitle.setFill(Color.AQUAMARINE);
    	requirements.getChildren().add(toolsTitle);
    	
    	String[] tools = selectedRecipe.tools.split("-");
    	for(int i = 0; i < tools.length; i++) {
    		Text t = new Text(tools[i] + "\n");
    		t.setFont(Font.font("courier new", FontWeight.BOLD, 14));
    		t.setFill(Color.WHITE);
    		requirements.getChildren().add(t);
    	}
    	
    	Text workStationsTitle = new Text("\nWork stations\n");
    	workStationsTitle.setFont(Font.font("courier new", FontWeight.BOLD, 16));
    	workStationsTitle.setUnderline(true);
    	workStationsTitle.setFill(Color.AQUAMARINE);
    	requirements.getChildren().add(workStationsTitle);
    	
    	String[] workStations = selectedRecipe.workStations.split("-");
    	for(int i = 0; i < workStations.length; i++) {
    		Text t = new Text(workStations[i] + "\n");
    		t.setFont(Font.font("courier new", FontWeight.BOLD, 14));
    		t.setFill(Color.WHITE);
    		requirements.getChildren().add(t);
    	}
    	
    	Text skillsTitle = new Text("\nSkills\n");
    	skillsTitle.setFont(Font.font("courier new", FontWeight.BOLD, 16));
    	skillsTitle.setUnderline(true);
    	skillsTitle.setFill(Color.AQUAMARINE);
    	requirements.getChildren().add(skillsTitle);
    	
    	String[] skills = selectedRecipe.skills.split(" ");
    	for(int i = 0; i < skills.length; i++) {
    		Text t = new Text(StringUtils.toTitle(skills[i]) + "\n");
    		t.setFont(Font.font("courier new", FontWeight.BOLD, 14));
    		t.setFill(Color.WHITE);
    		requirements.getChildren().add(t);
    	}
    	
    }

}
