package main;

//TODO: Test
public enum Flags {
	
	/** Un item con este flag tiene efectos adversos al ser usado */
	CURSED,
	/** Se puede agarrar y ser arrastrado */
	DRAGGABLE,
	/** Se puede tomar */
	DRINKABLE,
	/** Se puede comer */
	EDIBLE,
	/** Si se lanza (con throw) se rompe */
	FRAGILE,
	/** Baja un nivel */
	GOES_DOWN,
	/** Sube un nivel */
	GOES_UP,
	/** No se lo puede destruir */
	INDESTRUCTIBLE,
	/** Puede prenderse fuego */
	INFLAMMABLE,
	/** Emite luz */
	LIGHT_SOURCE,
	/** Bloquea la visión */
	OPAQUE,
	/** Se puede levantar y guardar en el inventario */
	PICKUPABLE,
	/** Se puede usar como ropa o armadura */
	WEARABLE,

}
