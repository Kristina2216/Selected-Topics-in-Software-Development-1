package hr.fer.oprpp1.custom.scripting.elems;

public class ElementVariable extends Element{
	private String name;

	public ElementVariable(String s) {
		name=s;
	}
	public String getName() {
		return name;
	}
	@Override
	public String asText() {
		return name;
	}

}
