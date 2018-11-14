package player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeList {
	
	public static HashMap<String, List<Recipe>> recipes = new HashMap<>();
	
	private static Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:assets/Data/Recipes.db");
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
		ResultSet tablesRS = executeQuery("SELECT * FROM sqlite_master WHERE type='table';", con);
		try {
			while(tablesRS.next()) {
				String tableName = tablesRS.getString(2);
				recipes.put(tableName, new ArrayList<>());
				ResultSet recipesRS = executeQuery("SELECT * from '" + tableName + "';", con);
				while(recipesRS.next()) {
					recipes.get(tableName).add(new Recipe(recipesRS.getString(1), 
															recipesRS.getString(2), 
															recipesRS.getString(3), 
															recipesRS.getString(4), 
															recipesRS.getString(5), 
															recipesRS.getInt(6), 
															recipesRS.getInt(7)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close(con);
	}
	
}
