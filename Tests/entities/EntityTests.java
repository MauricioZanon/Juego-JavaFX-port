//package entities;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import componentsomponents.GraphicC;
//import main.Entity;
//
//public class EntityTests {
//	
//	private Entity entity;
//	
//	@Before
//	public void createEntity() {
//		entity = new Entity(null, 0, "test entity");
//	}
//	
////	@Test
////	public void addComponent() {
////		assertEquals("La entidad no se creó vacía", 0, entity.getComponents().size());
////		entity.addComponent(new GraphicC());
////		assertEquals("No se agregó el component", 1, entity.getComponents().size());
////		entity.addComponent(new BackColorC(Color.WHITE));
////		assertEquals("No se agregó el component", 2, entity.getComponents().size());
////	}
////
////	@Test
////	public void addRepeatedComponent() {
////		entity.addComponent(new GraphicC());
////		entity.addComponent(new BackColorC(Color.WHITE));
////		entity.addComponent(new BackColorC(Color.BLACK));
////		assertEquals("Se agregó un component repetido", 2, entity.getComponents().size());
////	}
//	
//	@Test
//	public void replaceOldComponent() {
//		GraphicC g = new GraphicC();
//		g.ASCII = "a";
//		entity.addComponent(g);
//		assertEquals("No se agregó el component", "a", entity.get(GraphicC.class).ASCII);
//		GraphicC h = new GraphicC();
//		h.ASCII = "b";
//		entity.addComponent(h);
//		assertEquals("No se reemplazó el component viejo", "b", entity.get(GraphicC.class).ASCII);
//	}
//	
//	@Test
//	public void addNull() {
//		entity.addComponent(null);
//		assertEquals("Se agregó un component null", 0, entity.getComponents().size());
//	}
//	
////	@Test
////	public void hasComponent() {
////		entity.addComponent(new BackColorC(Color.WHITE));
////		assertFalse("Se encontró un component que no debería estar", entity.has(GraphicC.class));
////		assertTrue("No se encontró un component que debería estar", entity.has(BackColorC.class));
////	}
////	
////	@Test
////	public void getComponent() {
////		entity.addComponent(new GraphicC());
////		entity.addComponent(new BackColorC(Color.WHITE));
////		assertTrue("Se devolvió un component incorrecto", entity.get(GraphicC.class).getClass() == GraphicC.class);
////		assertTrue("Se devolvió un component incorrecto", entity.get(BackColorC.class).getClass() == BackColorC.class);
////	}
//	
//	@Test 
//	public void getNotPressentComponent() {
//		assertEquals("Se devolvió un component incorrecto", null, entity.get(GraphicC.class));
//	}
//	
//	@Test
//	public void removeNotPresentComponent() {
//		GraphicC c = entity.removeComponent(GraphicC.class);
//		assertEquals(null, c);
//	}
//}
