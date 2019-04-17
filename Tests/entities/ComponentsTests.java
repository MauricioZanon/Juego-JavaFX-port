package entities;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Ignore;
import org.junit.Test;

import components.HealthC;
import components.MovementC;
import components.MovementC.MovementType;
import components.PositionC;

public class ComponentsTests {
	
	@Test @Ignore
	public void cloneContainerComponent() {
		//TODO:
	}
	
	@Test
	public void cloneHealthComponent() {
		HealthC hc = new HealthC();
		hc.setCurHP(1);
		hc.setMaxHP(10);
		hc.setHPreg(0.1f);
		HealthC hcClone = hc.clone();
		assertEquals("El HealthComponent se clono mal, curHP", hc.getCurHP(), hcClone.getCurHP(), 0.1f);
		assertEquals("El HealthComponent se clono mal, maxHP", hc.getMaxHP(), hcClone.getMaxHP(), 0.1f);
		assertEquals("El HealthComponent se clono mal, HPreg", hc.getHPreg(), hcClone.getHPreg(), 0.001f);
		
		hc.setCurHP(2);
		assertNotEquals("El HealthComponent original comparte curHP con el clon", hc.getCurHP(), hcClone.getCurHP());
		hc.setMaxHP(20);
		assertNotEquals("El HealthComponent original comparte maxHP con el clon", hc.getMaxHP(), hcClone.getMaxHP());
		hc.setHPreg(0.2f);
		assertNotEquals("El HealthComponent original comparte HPreg con el clon", hc.getHPreg(), hcClone.getHPreg());
	}
	
	@Test
	public void cloneMovementComponent() {
		//TODO: test path
		MovementC mc = new MovementC();
		mc.movementType = MovementType.WALK;
		MovementC mcClone = mc.clone();
		assertEquals("El MovementComponent se clono mal", mc.movementType, mcClone.movementType);
		
		mc.movementType = MovementType.FLY;
		assertNotEquals("El MovementComponent original comparte valores con el clon", mc.movementType, mcClone.movementType);
	}
	
	@Test
	public void clonePositionComponent() {
		PositionC pc = new PositionC();
		pc.coord = new int[] {1, 2, 3};
		PositionC pcClone = pc.clone();
		assertArrayEquals("El PositionComponent se clono mal", pc.coord, pcClone.coord);
		
		pc.coord = new int[] {4, 5, 6};
		assertNotEquals("El PositionComponent original comparte valores con el clon", pc.coord, pcClone.coord);
	}
	
//	@Test
//	public void cloneAIComponent() {
//		AIC ai = new AIC();
//		ai.changeBeh(new AttackingState(null, null));
//		ai.changeBeh(new PlayerState(null));
//		ai.changeBeh(new IdleState(null));
//		
//		AIC ai2 = ai.clone();
//		
//		assertTrue("No se copiaron los behaviours", ai2.getBeh() != null);
//		
//		while(ai.getBeh() != null && ai2.getBeh() != null) {
//			assertEquals("Los behaviours no se copiaron en el orden correcto", ai.getBeh().getClass(), ai2.getBeh().getClass());
//			ai.resumePreviousBeh();
//			ai2.resumePreviousBeh();
//		}
//		
//		assertTrue("Los AIComponents no tienen la misma cantidad de bahaviours", ai.getBeh() == null && ai2.getBeh() == null);
//	}
	
	@Test
	public void positionComponentGettersTest() {
		PositionC pos = new PositionC();
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
