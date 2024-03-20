package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class EchoNode extends Node{
	private Element[] elements;
	
	public EchoNode(Element[] el) {
		elements=new Element[el.length];
		int i;
		for(i=0;i<el.length;i++) {
			elements[i]=el[i];
			i++;
		}
	}
	
	public Element[] getElements() {
		return elements;
	}
	@Override
	public String toString() {
		String d="{$=";
		int i;
		for(i=0;i<elements.length;i++) {
			if(elements[i]!=null)
				d=d+ elements[i].asText();
				i++;
		}d=d+"$}";
		return d;
	}
}
