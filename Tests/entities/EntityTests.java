package entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import components.BackColorComponent;
import components.GraphicComponent;
import main.Entity;

public class EntityTests {
	
	private Entity entity;
	
	@Before
	public void createTile() {
		entity = new Entity(null, 0, "test entity");
	}
	
	@Test
	public void addComponent() {
		assertEquals("La entidad no se creó vacía", 0, entity.getComponents().size());
		entity.addComponent(new GraphicComponent());
		assertEquals("No se agregó el component", 1, entity.getComponents().size());
		entity.addComponent(new BackColorComponent());
		assertEquals("No se agregó el component", 2, entity.getComponents().size());
	}

	@Test
	public void addRepeatedComponent() {
		entity.addComponent(new GraphicComponent());
		entity.addComponent(new BackColorComponent());
		entity.addComponent(new BackColorComponent());
		assertEquals("Se agregó un component repetido", 2, entity.getComponents().size());
	}
	
	@Test
	public void replaceOldComponent() {
		GraphicComponent g = new GraphicComponent();
		g.ASCII = "a";
		entity.addComponent(g);
		assertEquals("No se agregó el component", "a", entity.get(GraphicComponent.class).ASCII);
		GraphicComponent h = new GraphicComponent();
		h.ASCII = "b";
		entity.addComponent(h);
		assertEquals("No se reemplazó el component viejo", "b", entity.get(GraphicComponent.class).ASCII);
	}
	
	@Test
	public void addNull() {
		entity.addComponent(null);
		assertEquals("Se agregó un component null", 0, entity.getComponents().size());
	}
	
	@Test
	public void hasComponent() {
		entity.addComponent(new BackColorComponent());
		assertFalse("Se encontró un component que no debería estar", entity.has(GraphicComponent.class));
		assertTrue("No se encontró un component que debería estar", entity.has(BackColorComponent.class));
	}
	
	@Test
	public void getComponent() {
		entity.addComponent(new GraphicComponent());
		entity.addComponent(new BackColorComponent());
		assertTrue("Se devolvió un component incorrecto", entity.get(GraphicComponent.class).getClass() == GraphicComponent.class);
		assertTrue("Se devolvió un component incorrecto", entity.get(BackColorComponent.class).getClass() == BackColorComponent.class);
	}
	
	@Test 
	public void getNotPressentComponent() {
		assertEquals("Se devolvió un component incorrecto", null, entity.get(GraphicComponent.class));
	}
	
	@Test
	public void removeNotPresentComponent() {
		GraphicComponent c = entity.removeComponent(GraphicComponent.class);
		assertEquals(null, c);
	}
}
