package entities;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Ignore;
import org.junit.Test;

import components.BackColorComponent;
import components.GraphicComponent;
import components.HealthComponent;
import components.MovementComponent;
import components.MovementComponent.MovementType;
import components.PositionComponent;
import javafx.scene.paint.Color;

public class ComponentsTests {
	
	@Test
	public void cloneBackColorComponent() {
		BackColorComponent bc = new BackColorComponent();
		bc.colors[0] = Color.rgb(255, 0, 0);
		BackColorComponent bcClone = bc.clone();
		assertEquals("El color de fondo no se clono", bc.colors[0], bcClone.colors[0]);
		
		bc.colors[0] = Color.rgb(0, 255, 0);
		assertNotEquals("El color en el BackColorComponent original es el mismo que en el clon", bc.colors[0], bcClone.colors[0]);
	}
	
	@Test @Ignore
	public void cloneContainerComponent() {
		//TODO:
	}
	
	@Test
	public void cloneGraphicComponent() {
		GraphicComponent gc = new GraphicComponent();
		gc.ASCII = "@";
		gc.color = Color.rgb(255, 0, 0);
		GraphicComponent gcClone = gc.clone();
		assertEquals("Se clono mal el GraphicComponent", gc.ASCII, gcClone.ASCII);
		assertEquals("Se clono mal el GraphicComponent", gc.color, gcClone.color);
		
		gc.ASCII = "g";
		assertNotEquals("El GraphicComponent original comparte valores con el clone", gc.ASCII, gcClone.ASCII);

		gc.color = Color.rgb(0, 255, 0);
		assertNotEquals("El GraphicComponent original comparte valores con el clone", gc.color, gcClone.color);
	}
	
	@Test
	public void cloneHealthComponent() {
		HealthComponent hc = new HealthComponent();
		hc.curHP = 1;
		hc.maxHP = 10;
		hc.HPreg = 0.1f;
		HealthComponent hcClone = hc.clone();
		assertEquals("El HealthComponent se clono mal", hc.curHP, hcClone.curHP, 0.1f);
		assertEquals("El HealthComponent se clono mal", hc.maxHP, hcClone.maxHP, 0.1f);
		assertEquals("El HealthComponent se clono mal", hc.HPreg, hcClone.HPreg, 0.001f);
		
		hc.curHP = 2;
		assertNotEquals("El HealthComponent original comparte valores con el clon", hc.curHP, hcClone.curHP);
		hc.maxHP = 20;
		assertNotEquals("El HealthComponent original comparte valores con el clon", hc.maxHP, hcClone.maxHP);
		hc.HPreg = 0.2f;
		assertNotEquals("El HealthComponent original comparte valores con el clon", hc.HPreg, hcClone.HPreg);
	}
	
	@Test
	public void cloneMovementComponent() {
		//TODO: test path
		MovementComponent mc = new MovementComponent();
		mc.movementType = MovementType.WALK;
		MovementComponent mcClone = mc.clone();
		assertEquals("El MovementComponent se clono mal", mc.movementType, mcClone.movementType);
		
		mc.movementType = MovementType.FLY;
		assertNotEquals("El MovementComponent original comparte valores con el clon", mc.movementType, mcClone.movementType);
	}
	
	@Test
	public void clonePositionComponent() {
		PositionComponent pc = new PositionComponent();
		pc.coord = new int[] {1, 2, 3};
		PositionComponent pcClone = pc.clone();
		assertArrayEquals("El PositionComponent se clono mal", pc.coord, pcClone.coord);
		
		pc.coord = new int[] {4, 5, 6};
		assertThat("El PositionComponent original comparte valores con el clon", pc.coord, IsNot.not(IsEqual.equalTo(pcClone.coord)));
	}
	
	@Test @Ignore
	public void cloneTimedComponent() {
		//TODO
	}
	
//	@Test
//	public void cloneTransitableComponent() {
//		TransitableComponent tc = new TransitableComponent();
//		tc.transitable = !tc.transitable;
//		TransitableComponent tcClone = tc.clone();
//		assertEquals("El TransitableComponent se clono mal", tc.transitable, tcClone.transitable);
//		
//		tc.transitable = !tc.transitable;
//		assertNotEquals("El TransitableComponent original comparte valores con el clon", tc.transitable, tcClone.transitable);
//	}
//	
//	@Test
//	public void cloneTranslucentComponent() {
//		TranslucentComponent tc = new TranslucentComponent();
//		tc.translucent = !tc.translucent;
//		TranslucentComponent tcClone = tc.clone();
//		assertEquals("El TranslucentComponent se clono mal", tc.translucent, tcClone.translucent);
//		
//		tc.translucent = !tc.translucent;
//		assertNotEquals("El TranslucentComponent original comparte valores con el clon", tc.translucent, tcClone.translucent);
//	}
	
	@Test
	public void positionComponentGettersTest() {
		PositionComponent pos = new PositionComponent();
		pos.coord = new int[] {0,0,0};
		assertEquals(0, pos.getGx());
		assertEquals(0, pos.getGy());
		
		pos.coord = new int[] {-1, -1, 0};
		assertEquals(-1, pos.getGx());
		assertEquals(-1, pos.getGy());

		pos.coord = new int[] {-150, -350, 0};
		assertEquals(-2, pos.getGx());
		assertEquals(-4, pos.getGy());
	}
}
