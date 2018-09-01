package entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import factories.EntityFactory;
import main.Entity;
import main.JavaFXThreadingRule;
import main.Type;

public class FactoriesTests {
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	//TODO testead cada factory cuando se terminen de implementar
	
	@Test
	public void invalidId() {
		Entity e = EntityFactory.create(-1);
		assertEquals("Se crean entidades con id invalido", e, null);
	}
	
	@Test
	public void unassignedId() {
		Entity e = EntityFactory.create(999);
		assertEquals("Se crean entidades con id sin asignar", e, null);
	}
	
	@Test @Ignore //TODO sacar el ignore cuando se terminen de implementar las factories
	public void idBoundariesForEntityType() {
		Entity e = EntityFactory.create(0);
		assertTrue(e.getType() == Type.ACTOR);
		e = EntityFactory.create(1000);
		assertTrue(e.getType() == Type.TERRAIN);
		e = EntityFactory.create(2000);
		assertTrue(e.getType() == Type.FEATURE);
		e = EntityFactory.create(3000);
		assertTrue(e.getType() == Type.ITEM);
	}
	
}
