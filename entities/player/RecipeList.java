package player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Type;

public class RecipeList {
	
	public static HashMap<String, List<Recipe>> recipes = new HashMap<>();
	
	private static Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:assets/Data/Entities.db");
		} catch (SQLException e) {
			System.out.println("load connection failed " + e.getMessage());
		} 
		return conn;
    }
	
	private static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {}
	}
	
	private static ResultSet executeQuery(String query, Connection con) {
		try {
			return con.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void loadRecipes() {
		Connection con = connect();
		ResultSet RS = executeQuery("SELECT * FROM Recipes;", con);
		try {
			while(RS.next()) {
				String itemType = RS.getString(3);
				String recipeCategory = getRecipeCategory(itemType);
				
				if(!recipes.containsKey(recipeCategory)) {
					recipes.put(recipeCategory, new ArrayList<>());
				}
				
				recipes.get(recipeCategory).add(new Recipe(RS.getString(2), 
														RS.getString(4), 
														RS.getString(5), 
														RS.getString(6), 
														RS.getString(7), 
														RS.getInt(8), 
														RS.getInt(9)));
			}
			
			RS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close(con);
	}
	
	private static String getRecipeCategory(String itemType) {
		Type type = Type.valueOf(itemType);
		
		if(type.is(Type.WEAPON)) {
			return "Weapon";
		}
		if(type.is(Type.ARMOR)) {
			return "Armor";
		}
		if(type.is(Type.CLOTHES)) {
			return "Clothes";
		}
//		if(type.is(Type.FOOD) || type.is(Type.DRINK)) {
//			return "Food";
//		}
		if(type.is(Type.JEWELRY)) {
			return "Jewelry";
		}
		return "Misc";
	}
	
}
