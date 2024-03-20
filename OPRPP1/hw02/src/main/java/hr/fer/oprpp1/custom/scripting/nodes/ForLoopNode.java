package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class ForLoopNode extends Node{
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	public ForLoopNode(ElementVariable variable, Element start, Element end) {
		this.variable=variable;
		startExpression=start;
		endExpression=end;
		stepExpression=new ElementConstantInteger(1);
	}

	public ForLoopNode(ElementVariable variable, Element start, Element end, Element step) {
		if(variable==null || start==null||end==null) {
			throw new  NullPointerException();
		}
		this.variable=variable;
		startExpression=start;
		endExpression=end;
		stepExpression=step;
	}
	public ElementVariable getVariable() {
		return variable;
	}
	public Element getStartExpression() {
		return startExpression;
	}
	public Element getEndExpression() {
		return endExpression;
	}
	public Element getStepExpression() {
		return stepExpression;
	}
	@Override
	public String toString() {
		String d="{$FOR"+variable.asText()+" "+startExpression.asText()+" "+endExpression.asText()+" "+stepExpression.asText()+"$}";
		int i;
		for(i=0;i<this.children.size();i++) {
			d=d+children.get(i);
		}d=d+"{$END$}";
		return d;
	}
}
