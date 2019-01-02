package persistency;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import application.Main;
import chunk.Chunk;
import components.PositionC;
import main.Type;
import map.Map;
import tile.Tile;
import world.WorldBuilder;

/**
 * TODO: guardar el heightMap del World y los lugares de las locations
 */
public class StateSaver implements Runnable{
	
	private static StateSaver instance;
	private ConcurrentHashMap <String, String> chunksToSave = new ConcurrentHashMap <>();
	
	public volatile Thread savingThread = new Thread(this);
	
	private StateSaver() {
		File saveFile = new File("../assets/Saves/" + WorldBuilder.getName() + ".db");
		if(!saveFile.exists()) { 
			createNewSaveFile();
		}
	}
	
	private Connection connect() {
		try {
			return DriverManager.getConnection("jdbc:sqlite:assets/Saves/" + WorldBuilder.getName() + ".db");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} 
    }
	
	private void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {}
	}
	
	private PreparedStatement createChunkSaveStatement(Connection con) {
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement("REPLACE INTO Chunks(ChunkCoord, Entities) VALUES(?, ?);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}
	
	private void closeStatement(PreparedStatement statement) {
		if(statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void createNewSaveFile() {
		try {
			File dbFile = new File("../assets/Saves/" + WorldBuilder.getName() + ".db");
			dbFile.createNewFile();
		} catch (IOException e1) {}

		Connection con = connect();
		try {
			PreparedStatement createWorldTable = con.prepareStatement("CREATE TABLE IF NOT EXISTS Chunks( " + 
																	"ChunkCoord TEXT PRIMARY KEY UNIQUE NOT NULL, " + 
																	"Entities  TEXT NOT NULL);");
			createWorldTable.execute();
			closeStatement(createWorldTable);
			PreparedStatement createPlayerTable = con.prepareStatement("CREATE TABLE IF NOT EXISTS Player( " +
																		"Position TEXT NOT NULL, " +
																		"HP TEXT, " +
																		"Stats TEXT NOT NULL, " +
																		"Equipment TEXT, " +
																		"Effects TEXT, " +
																		"Items TEXT);");
			createPlayerTable.execute();
			closeStatement(createPlayerTable);
			PreparedStatement createWorldInfoTable = con.prepareStatement("CREATE TABLE IF NOT EXISTS WorldInfo( " +
																		"Seed INTEGER NOT NULL, " +
																		"Items TEXT);");
			createWorldInfoTable.execute();
			closeStatement(createWorldInfoTable);
			close(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void save(String chunkPos, String entities) {
		System.out.println("saving " + chunkPos);
		Connection con = connect();
		boolean failed;
		do {
			failed = false;
			PreparedStatement statement = null;
			try {
				statement = createChunkSaveStatement(con);
				statement.setString(1, chunkPos);
				statement.setString(2, entities);
				statement.addBatch();
				statement.executeBatch();
				closeStatement(statement);
			} catch (SQLException e) {
				failed = true;
			}
		}while(failed);
		close(con);
	}
	
	public void save(Chunk chunk) {
		save(chunk.getPosAsString(), chunk.serialize());
	}
	
	public void saveGameState() {
		long tiempo = System.currentTimeMillis();
		savePlayerState();
		saveWorldState();
		System.out.println("save time " + (System.currentTimeMillis() - tiempo));
	}
	
	public void savePlayerState() {
//		Entity player = Main.PLAYER;
//		
//		Connection con = connect();
//		System.out.println("chunks en memoria " + Map.getChunksInMemory().values().size());
//		
//		String playerPos = Mappers.posMap.get(player).toString();
//		String playerHP = Mappers.healthMap.get(player).serialize();
//		String playerStats = Mappers.attMap.get(player).serialize();
//		String playerEquipment = Mappers.equipMap.get(player).serialize();
//		String playerEffects = Mappers.statusEffectsMap.get(player).serialize();
//		String playerItems = Mappers.inventoryMap.get(player).serialize();
//		
//		try {
//			PreparedStatement reset = con.prepareStatement("DELETE FROM Player");
//			reset.executeUpdate();
//			
//        	PreparedStatement pstmt = con.prepareStatement("REPLACE INTO Player(Position, HP, Stats, Equipment, Effects, Items) VALUES(?, ?, ?, ?, ?, ?)");
//        	pstmt.setString(1, playerPos);
//        	pstmt.setString(2, playerHP);
//        	pstmt.setString(3, playerStats);
//        	pstmt.setString(4, playerEquipment);
//        	pstmt.setString(5, playerEffects);
//        	pstmt.setString(6, playerItems);
//        	pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//   	    }
//		close(con);
	}
	
	public void saveWorldState() {
		Tile playerTile = Main.player.get(PositionC.class).getTile();
		playerTile.remove(Type.ACTOR);
		for(Chunk chunk : Map.getChunksInMemory().values()) {
			save(chunk);
		}
		playerTile.put(Main.player);
	}
	
	public void addChunkToSaveList(Chunk chunk) {
		String entities = chunk.serialize();
		chunk.dump();
		if(entities.length() > Chunk.SIZE*Chunk.SIZE) { // Si el chunk está vacío no se guarda
			String chunkCoord = chunk.getPosAsString();
			chunksToSave.put(chunkCoord, entities);
		}
	}

	@Override
	public void run() {
		while(savingThread.isAlive()) {
			
			Iterator<Entry<String, String>> iter = chunksToSave.entrySet().iterator();
			while(iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				save(entry.getKey(), entry.getValue());
				iter.remove();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
		
	}
	
	public static StateSaver getInstance() {
		if(instance == null){
			instance = new StateSaver();
		}
		return instance;
	}
	
}
