package components;

import main.Component;

public class DescriptionComponent extends Component{
	
	public String name = "no name";

	@Override
	public DescriptionComponent clone() {
		DescriptionComponent dc = new DescriptionComponent();
		dc.name = name;
		return dc;
	}

}
