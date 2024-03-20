package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Node {
	ArrayIndexedCollection children;
	
	public void addChildNode(Node child) {
		if(this.numberOfChildren()==0) {
			this.children=new ArrayIndexedCollection();
		}
		children.add(child);
	}
	
	public int numberOfChildren() {
		if(children!=null)
			return children.size();
		else
			return 0;
	}
	public Node getChild(int index) {
		if(index>=this.numberOfChildren() || index<0)
			throw new  IndexOutOfBoundsException();
		return (Node) children.get(index);
	}
}
