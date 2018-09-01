package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import Chunk.Chunk;
import Chunk.EmptyChunk;
import world.WorldBuilder;

public class StateLoader {
	
	private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:assets/Saves/world.db");
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
	
	public static Chunk load(String chunkPos) {
		Connection con = connect();
		Chunk chunk = null;
		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT Entities FROM Chunks WHERE ChunkCoord='" + chunkPos + "'");
			ResultSet rs = pstmt.executeQuery();
			chunk = new Chunk(chunkPos, rs.getString("Entities"));
			close(con);
			return chunk;
		} catch (SQLException e) {close(con);}
		int[] coord = Arrays.stream(chunkPos.split(":")).mapToInt(Integer::parseInt).toArray();
		if(coord[2] == 0) {
			close(con);
			return WorldBuilder.createOverworldChunk(coord[0], coord[1]);
		}else {
			close(con);
			return new EmptyChunk(coord[0], coord[1], coord[2]);
		}
	}
	
	public static void loadPlayer() {
//		Entity player = PlayerBuilder.createBasePlayer();
//		
//		Connection con = connect();
//		try {
//			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Player");
//			ResultSet rs = pstmt.executeQuery();
//			
//			String position = rs.getString("Position");
//			PositionComponent pos = Juego.ENGINE.createComponent(PositionComponent.class);
//			pos.coord = Arrays.stream(position.split(":")).mapToInt(Integer::parseInt).toArray();
//			player.add(pos);
//			
//			String[] hp = rs.getString("HP").split("-");
//			Mappers.healthMap.get(player).maxHP = Float.parseFloat(hp[0]);
//			Mappers.healthMap.get(player).curHP = Float.parseFloat(hp[1]);
//			
//			AttributeComponent att = Mappers.attMap.get(player);
//			String[] stats = rs.getString("Stats").split("/");
//			for(int i = 0; i < stats.length; i++) {
//				String stat[] = stats[i].split("-");
//				att.set(stat[0], Float.parseFloat(stat[1]));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		close(con);
//		
//		Juego.player = player;
	}
	
}
