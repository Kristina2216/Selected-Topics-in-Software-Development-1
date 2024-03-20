package hr.fer.oprpp1.custom.scripting.nodes;

public class DocumentNode extends Node{
	
	@Override
	public String toString() {
		String d="";
		int i;
		for(i=0;i<this.children.size();i++) {
			d=d+children.get(i);
		}return d;
	}
	@Override
	public boolean equals(Object other) {
		return(this.toString().equals(other.toString()));
	}
}
