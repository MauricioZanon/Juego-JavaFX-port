package components;

import java.util.HashSet;
import java.util.Set;

import RNG.RNG;
import factories.ItemFactory;
import main.Entity;

public class DropC extends Component{
	
	/** Los ID de los items y su chance de dropear, 2.6-5000-10 dropea de 2 a 6 unidades del item con ID 5000 el 10% de las veces */
	private String[] onDeathDrops;
	private String[] onBreakDrops;
	private String[] onButcherDrops;
	
	public DropC(String[] onDeathDrops, String[] onBreakDrops, String[] onButcherDrops) {
		this.onDeathDrops = onDeathDrops;
		this.onBreakDrops = onBreakDrops;
		this.onButcherDrops = onButcherDrops;
		isBase = true;
	}

	public Set<Entity> getOnDeathItems(){
		return getItems(onDeathDrops);
	}
	
	public Set<Entity> getOnBreakItems(){
		return getItems(onBreakDrops);
	}
	
	public Set<Entity> getOnButcherItems(){
		return getItems(onButcherDrops);
	}
	
	private Set<Entity> getItems(String[] drops){
		Set<Entity> items = new HashSet<>();
		for(int i = 0; i < drops.length; i++) {
			String[] dropInfo = drops[i].split("-");
			int quantity = readQuantity(dropInfo[0]);
			if(RNG.nextInt(100) <= Integer.parseInt(dropInfo[2])) {
				int itemID = Integer.parseInt(dropInfo[1]);
				for(int j = 0; j < quantity; j++) {
					items.add(ItemFactory.createItem(itemID));
				}
			}
		}
		return items;
	}
	
	private int readQuantity(String string) {
		String[] quantitiesString = string.split("\\.");
		if(quantitiesString.length == 1) {
			return Integer.parseInt(quantitiesString[0]);
		}else {
			return RNG.nextInt(Integer.parseInt(quantitiesString[0]), Integer.parseInt(quantitiesString[1]));
		}
	}
	

	@Override
	public Component clone() {
		return new DropC(onDeathDrops, onBreakDrops, onButcherDrops);
	}

	@Override
	public String serialize() {
		return "";
	}

}
