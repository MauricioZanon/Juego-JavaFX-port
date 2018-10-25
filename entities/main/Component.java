package main;

public abstract class Component implements Cloneable{
	
	@Override
	public abstract Component clone();
	
	public abstract String serialize();
	
}
