package hr.fer.oprpp1.custom.scripting.elems;

public class ElementString extends Element{
	private String value;
	
	public ElementString(String s) {
		value=s;
	}
	public String getValue() {
		return value;
	}
	@Override
	public String asText() {
		return value;
	}

}
