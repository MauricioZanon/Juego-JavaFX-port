package components;

public abstract class Component implements Cloneable{

	
	/** Si esto es true entonces el componente no se copia al crearse una nueva entidad que lo use 
	 * TODO: cambiar nombre por algo mas explicativo
	 * */
	protected boolean isBase;
	
	@Override
	public abstract Component clone();
	
	public abstract String serialize();

	public boolean isBase() {
		return isBase;
	}
	
}
