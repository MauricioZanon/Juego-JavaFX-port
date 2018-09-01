package factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.sqlite.SQLiteConfig;

import components.BackColorComponent;
import components.GraphicComponent;
import components.HealthComponent;
import components.MovementComponent;
import components.MovementComponent.MovementType;
import components.TransitableComponent;
import javafx.scene.paint.Color;
import main.Entity;
import main.Flags;
import main.Type;

public abstract class EntityFactory{
	
	public static void initialize(){
		Connection con = connect();
		ResultSet entitiesRS = executeQuery("SELECT * FROM Entities;", con);
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
				
				String name = entity.name;
				switch(entity.TYPE.getSuperType()) {
				case ACTOR:
					NPCFactory.NPCs.put(name, entity);
					break;
				case FEATURE:
					FeatureFactory.features.put(name, entity);
					break;
				case ITEM:
					Type type = entity.TYPE;
					if(type.is(Type.WEAPON)) {
						ItemFactory.weapons.put(name, entity);
					}else if(type.is(Type.ARMOR)) {
						ItemFactory.armors.put(name, entity);
					}else if (type.is(Type.POTION)) {
						ItemFactory.potions.put(name, entity);
					}else if(type.is(Type.TOOL)) {
						ItemFactory.tools.put(name, entity);
					}
					break;
				case TERRAIN:
					TerrainFactory.terrainPool.put(name, entity);
					break;
				default:
					System.out.println("tipo de entidad no identificado " + entity.TYPE);
					break;
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(con);
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
		
		String attributes = rs.getString("Attributes");
		if(attributes != null) {
			String [] attArray = attributes.split(" ");
			for(int i = 0; i < attArray.length; i++) {
				String[] att = attArray[i].split(":");
				e.setAttribute(att[0], Float.parseFloat(att[1]));
			}
		}
		return e;
	}
	
	private static void addGraphicComponent(Entity e, ResultSet RS) throws SQLException {
		String ASCII = RS.getString("ASCII");
		String frontColor = RS.getString("frontColor");
		String backColor = RS.getString("backColor");
		
		GraphicComponent comp = new GraphicComponent();
		comp.ASCII = ASCII;
		if(frontColor != null) {
			double[] frontArray = Arrays.stream(frontColor.split(" ")).mapToDouble(Double::parseDouble).toArray();
			comp.color = new Color(frontArray[0], frontArray[1], frontArray[02], frontArray[3]);
		}
		e.addComponent(comp);
		if(backColor != null) {
			BackColorComponent backComp = new BackColorComponent();
			double[] backArray = Arrays.stream(backColor.split(" ")).mapToDouble(Double::parseDouble).toArray();
			backComp.color = new Color(backArray[0], backArray[1], backArray[02], backArray[3]);
			e.addComponent(backComp);
		}
	}
	
	private static void addHealthComponent(Entity e, ResultSet RS) throws SQLException {
		HealthComponent comp = new HealthComponent();
		comp.maxHP = RS.getFloat("maxHP");
		comp.curHP = comp.maxHP;
		comp.HPreg = RS.getFloat("HPreg");
		e.addComponent(comp);
	}
	
	private static void addMovementComponent(Entity e, ResultSet RS) throws SQLException {
		MovementComponent comp = new MovementComponent();
		comp.movementType = MovementType.valueOf(RS.getString("movementType"));
		e.addComponent(comp);
	}
	
	private static void addTransitableComponent(Entity e, ResultSet RS) throws SQLException {
		TransitableComponent comp = new TransitableComponent();
		comp.transitable = RS.getBoolean("isTransitable");
		e.addComponent(comp);
	}
	
}