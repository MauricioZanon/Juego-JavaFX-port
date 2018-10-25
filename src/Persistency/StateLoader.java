package Persistency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import application.Main;
import chunk.Chunk;
import chunk.EmptyChunk;
import components.HealthComponent;
import components.PositionComponent;
import main.Entity;
import player.PlayerBuilder;
import world.WorldBuilder;

public class StateLoader {
	
	private static StateLoader instance = null;
	
	private StateLoader() {}
	
	private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:assets/Saves/world.db");
        } catch (SQLException e) {
            System.out.println("load connection failed " + e.getMessage());
        } 
        return conn;
    }
	
	private void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {}
	}
	
	public Chunk loadChunk(String chunkPos) {
		Connection con = connect();
		Chunk chunk = null;
		try {
			PreparedStatement statement = con.prepareStatement("SELECT Entities FROM Chunks WHERE ChunkCoord='" + chunkPos + "'");
			ResultSet rs = statement.executeQuery();
			chunk = new Chunk(chunkPos, rs.getString("Entities"));
			statement.close();
			rs.close();
			close(con);
			System.out.println("loading " + chunkPos);
			return chunk;
		} catch (SQLException e) {}
		
		int[] coord = Arrays.stream(chunkPos.split(":")).mapToInt(Integer::parseInt).toArray();
		if(coord[2] == 0) {
			return WorldBuilder.createOverworldChunk(coord[0], coord[1]);
		}else {
			return new EmptyChunk(coord[0], coord[1], coord[2]);
		}
	}
	
	public void loadPlayer() {
		Entity player = PlayerBuilder.createBasePlayer();
		
		Connection con = connect();
		try {
			PreparedStatement statement = con.prepareStatement("SELECT * FROM Player");
			ResultSet rs = statement.executeQuery();
			statement.close();
			
			String position = rs.getString("Position");
			PositionComponent pos = new PositionComponent();
			pos.coord = Arrays.stream(position.split(":")).mapToInt(Integer::parseInt).toArray();
			player.addComponent(pos);
			
			String[] hp = rs.getString("HP").split("-");
			HealthComponent hpComp = player.get(HealthComponent.class);
			hpComp.maxHP = Float.parseFloat(hp[0]);
			hpComp.curHP = Float.parseFloat(hp[1]);
			
			String[] stats = rs.getString("Stats").split("/");
			for(int i = 0; i < stats.length; i++) {
				String stat[] = stats[i].split("-");
				player.setAttribute(stat[0], Float.parseFloat(stat[1]));
			}
			rs.close();
			close(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Main.player = player;
	}
	
	public static StateLoader getInstance() {
		if(instance == null) {
			instance = new StateLoader();
		}
		return instance;
	}
	
}
