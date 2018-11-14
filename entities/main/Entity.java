package main;

import java.util.EnumSet;
import java.util.HashMap;

import components.BodyComponent;

public class Entity implements Cloneable{
	
	private HashMap<Class<? extends Component>, Component> components = new HashMap<>();
	private HashMap<String, Float> attributes = new HashMap<>();
	private EnumSet<Flags> flags = EnumSet.noneOf(Flags.class);
	public final Type TYPE;
	public final int ID;
	public String name = "no name";
	public String description = "no desc";
	
	public Entity(Type type, int id, String name) {
		this.TYPE = type;
		this.ID = id;
		this.name = name;
	}
	
	public void addComponent(Component c) {
		if (c != null) {
			components.put(c.getClass(), c);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> T removeComponent (Class<T> componentClass) {
		return (T)components.remove(componentClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> T get(Class<T> componentClass) {
		return (T)components.get(componentClass);
	}

	public boolean has(Class<? extends Component> componentClass) {
		return components.containsKey(componentClass);
	}
	
	/**
	 * Devuelve el valor del atributo teniendo en cuenta los modificadores
	 */
	public float get(String att) {
		float value = attributes.containsKey(att) ? attributes.get(att) : 0;
		if(has(BodyComponent.class)) {
			for(Entity equipment : get(BodyComponent.class).getEquipment()) {
				value += equipment.getBase(att);
			}
		}
		return value;
	}
	
	/**
	 * Devuelve el valor del atributo sin tomar en cuenta los modificadores
	 */
	public float getBase(String att) {
		return attributes.containsKey(att) ? attributes.get(att) : 0;
	}
	
	/**
	 * Si este atributo ya existía se reemplaza su valor por value
	 */
	public void setAttribute(String name, float value) {
		attributes.put(name, value);
	}
	
	/**
	 *  Se le suma value al atributo, si no existe se agrega
	 */
	public void changeAttribute(String name, float value) {
		if(!attributes.containsKey(name)) {
			attributes.put(name, value);
		}
		else {
			attributes.put(name, attributes.get(name) + value);
		}
	}
	
	public HashMap<String, Float> getAttributes() {
		return attributes;
	}
	
	public EnumSet<Flags> getFlags() {
		return flags;
	}

	public void addFlag(Flags flag) {
		flags.add(flag);
	}
	
	public void removeFlag(Flags flag) {
		flags.remove(flag);
	}
	
	/**
	 * @param flag: el flag que se busca
	 * @return true si la entidad tiene el flag buscado
	 */
	public boolean is(Flags flag) {
		return flags.contains(flag);
	}

	public HashMap<Class<? extends Component>, Component> getComponents() {
		return components;
	}
	
	@Override //TODO test
	public Entity clone() {
		Entity e = new Entity(TYPE, ID, name);
		e.description = description;
		components.values().forEach(c -> e.addComponent(c.clone()));
		attributes.forEach((k, v) -> e.setAttribute(k, v));
		flags.forEach(f -> e.addFlag(f));
		
		return e;
	}
	
}
