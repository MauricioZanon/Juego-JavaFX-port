package main;

/**
 * El tipo de entidad
 * Cada tipo guarda un atributo (group) que es el tipo del cual desciende
 */
public enum Type {
	
	ACTOR(null),
		PLAYER(ACTOR),
	
	TERRAIN(null),
	
	ITEM(null),
		EQUIPMENT(ITEM),
			WEAPON(EQUIPMENT),
				SWORD(WEAPON),
				DAGGER(WEAPON),
				MACE(WEAPON),
				BOW(WEAPON),
			ARMOR(EQUIPMENT),
				HELMET(ARMOR),
				GLOVES(ARMOR),
				GREAVES(ARMOR),
				BOOTS(ARMOR),
				BREASTPLATE(ARMOR),
			CLOTHES(EQUIPMENT),
				HAT(CLOTHES),
				SHIRT(CLOTHES),
				PANTS(CLOTHES),
				SOCKS(CLOTHES),
			JEWELRY(EQUIPMENT),
				RING(JEWELRY),
				NECKLACE(JEWELRY),
			MUNITION(EQUIPMENT),
				ARROW(MUNITION),
		WAND(ITEM),
		BOOK(ITEM),
		TOOL(ITEM),
			KEY(TOOL),
			CONSUMABLE(ITEM),
			POTION(CONSUMABLE),
			SCROLL(CONSUMABLE),
			FOOD(CONSUMABLE),
			DRINK(CONSUMABLE),
		MATERIAL(ITEM),
		
	FEATURE(null),
		CONTAINER(FEATURE);
	
	private Type superType = null;
		
	private Type(Type group) {
	    this.superType = group;
	}
	
	public boolean is(Type other) {
	   if(this == other) return true;
	   else if(superType == null) return false;
	   else return superType.is(other);
	}
	
	public Type getSuperType() {
		return superType == null ? this : superType.getSuperType();
	}

}
