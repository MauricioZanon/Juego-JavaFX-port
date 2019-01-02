package factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.sqlite.SQLiteConfig;

import components.BackColorC;
import components.ContainerC;
import components.DropC;
import components.GraphicC;
import components.HealthC;
import components.LightSourceC;
import components.MovementC;
import components.MovementC.MovementType;
import components.TransitableC;
import javafx.scene.paint.Color;
import main.Att;
import main.Entity;
import main.Flags;
import main.Type;

public abstract class EntityFactory{
	
	public static Entity create(int ID) {
		if(ID < 1000) {
			return NPCFactory.createNPC(ID);
		}else if(ID < 2000) {
			return TerrainFactory.get(ID);
		}else if (ID < 3000) {
			return FeatureFactory.createFeature(ID);
		}else {
			return ItemFactory.createItem(ID);
		}
	}
	
	public static void loadEntities(){
		Connection con = connect();
		ResultSet entitiesRS = executeQuery("SELECT * FROM BasicData;", con);
		try {
			while(entitiesRS.next()) {
				int ID = entitiesRS.getInt("ID");
				Entity entity = createBaseEntity(entitiesRS);
				
				ResultSet RS = executeQuery("SELECT * FROM GraphicComponents WHERE ID='" + ID + "'", con);
				if(!RS.isClosed()) {
					addGraphicComponent(entity, RS);
				}
				
				RS = executeQuery("SELECT * FROM HealthComponents WHERE ID='" + ID + "'", con);
				if(!RS.isClosed()) {
					addHealthComponent(entity, RS);
				}
				
				RS = executeQuery("SELECT * FROM MovementComponents WHERE ID='" + ID + "'", con);
				if(!RS.isClosed()) {
					addMovementComponent(entity, RS);
				}
				
				RS = executeQuery("SELECT * FROM TransitableComponents WHERE ID='" + ID + "'", con);
				if(!RS.isClosed()) {
					addTransitableComponent(entity, RS);
				}
				
				RS = executeQuery("SELECT * FROM DropComponents WHERE ID='" + ID + "'", con);
				if(!RS.isClosed()) {
					addDropComponent(entity, RS);
				}
				RS.close();
				
				if(entity.TYPE == Type.CONTAINER) {
					entity.addComponent(new ContainerC());
				}
				
				String name = entity.name;
				switch(entity.TYPE.getSuperType()) {
				case ACTOR:
					NPCFactory.NPCsByID.add(entity.ID, entity);
					NPCFactory.NPCs.put(name, entity);
					break;
				case FEATURE:
					FeatureFactory.featuresByID.add(entity.ID-2000, entity);
					FeatureFactory.features.put(name, entity);
					break;
				case ITEM:
					Type type = entity.TYPE;
					if(type.is(Type.WEAPON)) {
						ItemFactory.weaponsByID.add(entity.ID-4000, entity);
						ItemFactory.weapons.put(name, entity);
					}else if(type.is(Type.ARMOR)) {
						ItemFactory.armorsByID.add(entity.ID-3000, entity);
						ItemFactory.armors.put(name, entity);
					}else if (type.is(Type.POTION)) {
						ItemFactory.potionsByID.add(entity.ID-5000, entity);
						ItemFactory.potions.put(name, entity);
					}else if(type.is(Type.TOOL) || type.is(Type.MUNITION)) {
						ItemFactory.toolsByID.add(entity.ID-6000, entity);
						ItemFactory.tools.put(name, entity);
					}else if(type.is(Type.MATERIAL)) {
						ItemFactory.materialsByID.add(entity.ID-7000, entity);
						ItemFactory.materials.put(name, entity);
					}
					break;
				case TERRAIN:
					TerrainFactory.terrainsByID.add(entity.ID-1000, entity);
					TerrainFactory.terrainPool.put(name, entity);
					break;
				default:
					System.out.println("tipo de entidad no identificado " + entity.TYPE);
					break;
					
				}
			}
			close(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static ResultSet executeQuery(String query, Connection con) {
		try {
			return con.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static Connection connect() {
		Connection conn = null;
		try {
			SQLiteConfig config = new SQLiteConfig();  
			config.enforceForeignKeys(true);
			conn = DriverManager.getConnection("jdbc:sqlite:assets/Data/Entities.db", config.toProperties());
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
	
	private static Entity createBaseEntity(ResultSet rs) throws SQLException {
		int ID = rs.getInt("ID");
		String name = rs.getString("Name");
		Type type = Type.valueOf(rs.getString("Type"));
		Entity e = new Entity(type, ID, name);
		
		e.description = rs.getString("Description");
		
		String flagsString = rs.getString("Flags");
		if(flagsString != null) {
			String[] flagsArray = flagsString.split(" ");
			for(int i = 0; i < flagsArray.length; i++) {
				e.addFlag(Flags.valueOf(flagsArray[i]));
			}
		}
		
		if(e.is(Flags.LIGHT_SOURCE)) {
			e.addComponent(new LightSourceC());
		}
		
		String attributes = rs.getString("Attributes");
		if(attributes != null) {
			String [] attArray = attributes.split(" ");
			for(int i = 0; i < attArray.length; i++) {
				String[] att = attArray[i].split(":");
				e.setAttribute(Att.valueOf(att[0]), Float.parseFloat(att[1]));
			}
		}
		return e;
	}
	
	private static void addGraphicComponent(Entity e, ResultSet RS) throws SQLException {
		String ASCII = RS.getString("ASCII");
		String frontColor = RS.getString("frontColor");
		
		GraphicC comp = new GraphicC();
		comp.ASCII = ASCII;
		if(frontColor != null) {
			double[] frontArray = Arrays.stream(frontColor.split(" ")).mapToDouble(Double::parseDouble).toArray();
			comp.color = new Color(frontArray[0], frontArray[1], frontArray[02], frontArray[3]);
		}
		e.addComponent(comp);

		String backColor = RS.getString("backColor");
		if(backColor != null) {
			double[] backArray = Arrays.stream(backColor.split(" ")).mapToDouble(Double::parseDouble).toArray();
			Color baseColor = new Color(backArray[0], backArray[1], backArray[02], backArray[3]);
			e.addComponent(new BackColorC(baseColor));
		}
	}
	
	private static void addHealthComponent(Entity e, ResultSet RS) throws SQLException {
		HealthC comp = new HealthC();
		comp.setMaxHP(RS.getFloat("maxHP"));
		comp.setCurHP(RS.getFloat("maxHP"));
		comp.setHPreg(RS.getFloat("HPreg"));
		e.addComponent(comp);
	}
	
	private static void addMovementComponent(Entity e, ResultSet RS) throws SQLException {
		MovementC comp = new MovementC();
		comp.movementType = MovementType.valueOf(RS.getString("movementType"));
		e.addComponent(comp);
	}
	
	private static void addTransitableComponent(Entity e, ResultSet RS) throws SQLException {
		TransitableC comp = new TransitableC();
		String acceptedMovString = RS.getString("acceptedMovement");
		if(!acceptedMovString.equals("")) {
			String[] movTypes = acceptedMovString.split(" ");
			for(int i = 0; i < movTypes.length; i++) {
				String[] mov = movTypes[i].split("-");
				comp.add(MovementType.valueOf(mov[0]), Float.parseFloat(mov[1]));
			}
		}
		e.addComponent(comp);
	}
	
	private static void addDropComponent(Entity e, ResultSet RS) throws SQLException {
		String onDeathItems = RS.getString("On death");
		if(onDeathItems == null) onDeathItems = "";
		
		String onBreakItems = RS.getString("On break");
		if(onBreakItems == null) onBreakItems = "";
		
		String onButcherItems = RS.getString("On butcher");
		if(onButcherItems == null) onButcherItems = "";
		
		e.addComponent(new DropC(onDeathItems.split(" "), onBreakItems.split(" "), onButcherItems.split(" ")));
	}
	
}