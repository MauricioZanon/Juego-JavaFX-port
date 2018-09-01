package main;

import java.util.HashMap;

/** Una entidad es todo lo que aparece en el juego y puede interactuar con el jugador*/
public class Entity {
	
	private final HashMap<Class<? extends Component>, Component> components = new HashMap<>();
	private Type type = null;
	public int id = 0;
	
	public Entity(Type type, int id) {
		this.type = type;
		this.id = id;
	}
	
	public Entity() {} // TODO: borrar cuando se dejen de usar los XML en las factories
	
	public void add(Component c) {
		if (c != null) {
			components.put(c.getClass(), c);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> T get(Class<T> componentClass) {
		return (T)components.get(componentClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> T removeComponent (Class<T> componentClass) {
		return (T)components.remove(componentClass);
	}
	
	public boolean has(Class<? extends Component> componentClass) {
		return components.containsKey(componentClass);
	}

	public HashMap<Class<? extends Component>, Component> getComponents() {
		return components;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
