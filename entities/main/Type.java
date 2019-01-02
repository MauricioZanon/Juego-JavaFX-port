package main;

/**
 * El tipo de entidad
 * Cada tipo guarda un atributo (group) que es el tipo del cual desciende
 */
public enum Type {
	
	ACTOR(null, false),
		PLAYER(ACTOR, false),
	
	TERRAIN(null, false),
	
	ITEM(null, true),
		EQUIPMENT(ITEM, true),
			WEAPON(EQUIPMENT, true),
				AXE(WEAPON, true),
				BOW(WEAPON, true),
				DAGGER(WEAPON, true),
				MACE(WEAPON, true),
				SWORD(WEAPON, true),
			ARMOR(EQUIPMENT, true),
				HELMET(ARMOR, true),
				GLOVES(ARMOR, true),
				GREAVES(ARMOR, true),
				BOOTS(ARMOR, true),
				BREASTPLATE(ARMOR, true),
			CLOTHES(EQUIPMENT, true),
				HAT(CLOTHES, true),
				SHIRT(CLOTHES, true),
				PANTS(CLOTHES, true),
				SOCKS(CLOTHES, true),
			JEWELRY(EQUIPMENT, true),
				RING(JEWELRY, true),
				NECKLACE(JEWELRY, true),
			MUNITION(EQUIPMENT, true),
				ARROW(MUNITION, true),
		WAND(ITEM, true),
		BOOK(ITEM, true),
		TOOL(ITEM, true),
			KEY(TOOL, true),
			CONSUMABLE(ITEM, true),
			POTION(CONSUMABLE, true),
			SCROLL(CONSUMABLE, true),
			FOOD(CONSUMABLE, true),
			DRINK(CONSUMABLE, true),
		MATERIAL(ITEM, true),
		
	FEATURE(null, false),
		DOOR(FEATURE, false),
		CONTAINER(FEATURE, false),
	
	TRAP(null, false);
	
	private Type superType = null;
	private boolean isTileStackable;
		
	private Type(Type group, boolean isTileStackable) {
	    this.superType = group;
	    this.isTileStackable = isTileStackable;
	}
	
	public boolean is(Type other) {
	   if(this == other) return true;
	   else if(superType == null) return false;
	   else return superType.is(other);
	}
	
	public Type getSuperType() {
		return superType == null ? this : superType.getSuperType();
	}

	public boolean isTileStackable() {
		return isTileStackable;
	}

}
