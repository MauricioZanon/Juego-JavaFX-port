package entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import factories.EntityFactory;
import main.Entity;
import main.Type;

public class FactoriesTests {
	
	//TODO testear cada factory cuando se terminen de implementar
	
	@Test
	public void invalidIdTest() {
		ExpectedException exception = ExpectedException.none();
		exception.expect(ArrayIndexOutOfBoundsException.class);
		Entity e = EntityFactory.create(-1);
		assertEquals("Se crean entidades con id invalido", null, e);
	}
	
	@Test
	public void unassignedIdTest() {
		ExpectedException exception = ExpectedException.none();
		exception.expect(ArrayIndexOutOfBoundsException.class);
		Entity e = EntityFactory.create(999);
		assertEquals("Se crean entidades con id sin asignar", null, e);
	}

	@Test
	public void idBoundariesForEntityType() {
		Entity e = EntityFactory.create(0);
		assertTrue(e.type.is(Type.ACTOR));
		e = EntityFactory.create(1000);
		assertTrue(e.type.is(Type.TERRAIN));
		e = EntityFactory.create(2000);
		assertTrue(e.type.is(Type.FEATURE));
		e = EntityFactory.create(3000);
		assertTrue(e.type.is(Type.ITEM));
	}
	
}
