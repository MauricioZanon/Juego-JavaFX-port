package components;

import java.util.HashSet;
import java.util.Set;

import RNG.RNG;
import factories.ItemFactory;
import main.Component;
import main.Entity;

public class DropComponent extends Component{
	
	/** Los ID de los items y su chance de dropear, 5000-10 dropea el item con ID 5000 el 10% de las veces */
	private String[] dropChances;
	
	public DropComponent(String[] dropChances) {
		this.dropChances = dropChances;
	}
	
	public Set<Entity> getDrops(){
		Set<Entity> items = new HashSet<>();
		
		for(int i = 0; i < dropChances.length; i++) {
			String[] chance = dropChances[i].split(".");
			if(RNG.nextInt(100) <= Integer.parseInt(chance[1])) {
				items.add(ItemFactory.createItem(Integer.parseInt(chance[0])));
			}
		}
		return items;
	}

	@Override
	public Component clone() {
		return new DropComponent(dropChances);
	}

	@Override
	public String serialize() {
		return "DRO " + String.join(".", dropChances);
	}

}
