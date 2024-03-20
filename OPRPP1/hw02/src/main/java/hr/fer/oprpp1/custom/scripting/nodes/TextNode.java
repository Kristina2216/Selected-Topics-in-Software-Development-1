package hr.fer.oprpp1.custom.scripting.nodes;

public class TextNode extends Node{
	String text;
	
	public TextNode(String t) {
		text=t;
	}
	public String getText() {
		return text;
	}
	@Override
	public String toString() {
		return text;
	}

}
