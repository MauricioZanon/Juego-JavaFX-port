package entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import components.BackColorComponent;
import components.GraphicComponent;
import main.Entity;
import main.JavaFXThreadingRule;

public class EntityTests {
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Test
	public void addComponent() {
		Entity e = new Entity(null, 0);
		assertEquals("La entidad no se creó vacía", 0, e.getComponents().size());
		e.add(new GraphicComponent());
		assertEquals("No se agregó el component", 1, e.getComponents().size());
		e.add(new BackColorComponent());
		assertEquals("No se agregó el component", 2, e.getComponents().size());
	}

	@Test
	public void addRepeatedComponent() {
		Entity e = new Entity(null, 0);
		e.add(new GraphicComponent());
		e.add(new BackColorComponent());
		e.add(new BackColorComponent());
		assertEquals("Se agregó un component repetido", 2, e.getComponents().size());
	}
	
	@Test
	public void replaceOldComponent() {
		Entity e = new Entity(null, 0);
		GraphicComponent g = new GraphicComponent();
		g.ASCII = "a";
		e.add(g);
		assertEquals("No se agregó el component", "a", e.get(GraphicComponent.class).ASCII);
		GraphicComponent h = new GraphicComponent();
		h.ASCII = "b";
		e.add(h);
		assertEquals("No se reemplazó el component viejo", "b", e.get(GraphicComponent.class).ASCII);
	}
	
	@Test
	public void addNull() {
		Entity e = new Entity(null, 0);
		e.add(null);
		assertEquals("Se agregó un component null", 0, e.getComponents().size());
	}
	
	@Test
	public void hasComponent() {
		Entity e = new Entity(null, 0);
		e.add(new BackColorComponent());
		assertFalse("Se encontró un component que no debería estar", e.has(GraphicComponent.class));
		assertTrue("No se encontró un component que debería estar", e.has(BackColorComponent.class));
	}
	
	@Test
	public void getComponent() {
		Entity e = new Entity(null, 0);
		e.add(new GraphicComponent());
		e.add(new BackColorComponent());
		assertTrue("Se devolvió un component incorrecto", e.get(GraphicComponent.class).getClass() == GraphicComponent.class);
		assertTrue("Se devolvió un component incorrecto", e.get(BackColorComponent.class).getClass() == BackColorComponent.class);
	}
	
	@Test 
	public void getNotPressentComponent() {
		Entity e = new Entity(null, 0);
		assertEquals("Se devolvió un component incorrecto", null, e.get(GraphicComponent.class));
	}
	
	@Test
	public void removeNotPresentComponent() {
		Entity e = new Entity(null, 0);
		e.removeComponent(GraphicComponent.class);
		//TODO: agregar assert
	}
}
