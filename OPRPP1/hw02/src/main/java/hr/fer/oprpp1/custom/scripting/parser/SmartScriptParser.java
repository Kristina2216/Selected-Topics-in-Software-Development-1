package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

public class SmartScriptParser {
	private DocumentNode doc;
	private SmartScriptLexer lex;
	private String body;
	
	public SmartScriptParser(String docBody) {
		body=docBody;
		doc=new DocumentNode();
		lex=new SmartScriptLexer(docBody);
	}
		
	public void parse() {
		String docBody=this.body;
		ObjectStack s=new ObjectStack();
		s.push(doc);
		Token t=lex.nextToken();
		Node parent;
		while(t.getType()!=TokenType.EOF) {
			Node child;
			if(lex.state==LexerState.TEXT && t.getType()==TokenType.STRING) {
				parent=(Node)s.peek();
				child=new TextNode((String)t.getValue());
				parent.addChildNode(child);
			}else if(t.getType()==TokenType.ECHOTAG) {
				parent=(Node)s.peek();
				Element[] el=new Element[docBody.length()];
				int i=0;
				t=lex.nextToken();
				while(t.getType()!=TokenType.ENDTAG) {
					if (t.getType()==TokenType.DOUBLE)
						el[i]=new ElementConstantDouble(Double.parseDouble(String.valueOf(t.getValue())));
					else if (t.getType()==TokenType.INTEGER)
						el[i]=new ElementConstantInteger(Integer.parseInt(String.valueOf(t.getValue())));
					else if (t.getType()==TokenType.STRING)
						el[i]=new ElementString(String.valueOf(t.getValue()));
					else if (t.getType()==TokenType.OPERATOR)
						el[i]=new ElementOperator(String.valueOf(t.getValue()));
					else if (t.getType()==TokenType.VARIABLE)
						el[i]=new ElementVariable(String.valueOf(t.getValue()));
					else if (t.getType()==TokenType.FUNCTION)
						el[i]=new ElementFunction(String.valueOf(t.getValue()));
					t=lex.nextToken();
					i++;
				}
				child=new EchoNode(el);
				parent.addChildNode(child);
			}else if(t.getType()==TokenType.FORTAG) {
				Element[] el=new Element[4];
				parent=(Node)s.peek();
				t=lex.nextToken();
				if(t.getType()!=TokenType.VARIABLE)
					throw new SmartScriptParserException("First element must be a variable!");
				el[0]=new ElementVariable((String)t.getValue());
				t=lex.nextToken();
				if(t.getType()==TokenType.INTEGER)
					el[1]=new ElementConstantInteger(Integer.parseInt(String.valueOf(t.getValue())));
				else if(t.getType()==TokenType.DOUBLE)
					el[1]=new ElementConstantDouble(Double.parseDouble(String.valueOf(t.getValue())));
				else if(t.getType()==TokenType.STRING)
					el[1]=new ElementString(String.valueOf(t.getValue()));
				else if(t.getType()==TokenType.VARIABLE)
					el[1]=new ElementVariable(String.valueOf(t.getValue()));
				else
					throw new SmartScriptParserException("Element must be a type of String/Variable/Integer/Double!");
				t=lex.nextToken();
				if(t.getType()==TokenType.INTEGER)
					el[2]=new ElementConstantInteger(Integer.parseInt(String.valueOf(t.getValue())));
				else if(t.getType()==TokenType.DOUBLE)
					el[2]=new ElementConstantDouble(Double.parseDouble(String.valueOf(t.getValue())));
				else if(t.getType()==TokenType.STRING)
					el[2]=new ElementString(String.valueOf(t.getValue()));
				else if(t.getType()==TokenType.VARIABLE)
					el[2]=new ElementVariable(String.valueOf(t.getValue()));
				else
					throw new SmartScriptParserException("Element must be a type of String/Variable/Integer/Double!");
				t=lex.nextToken();
				if(t.getType()==TokenType.ENDTAG) {
					child=new ForLoopNode((ElementVariable)el[0],el[1],el[2]);
				}else if(t.getType()!=TokenType.STRING && t.getType()!=TokenType.VARIABLE&&t.getType()!=TokenType.DOUBLE&&t.getType()!=TokenType.INTEGER)
					throw new SmartScriptParserException();
				else{ 
					if(t.getType()==TokenType.INTEGER)
						el[3]=new ElementConstantInteger(Integer.parseInt(String.valueOf(t.getValue())));
					else if(t.getType()==TokenType.DOUBLE)
						el[3]=new ElementConstantDouble(Double.parseDouble(String.valueOf(t.getValue())));
					else if(t.getType()==TokenType.STRING)
						el[3]=new ElementString(String.valueOf(t.getValue()));
					else if(t.getType()==TokenType.VARIABLE)
						el[3]=new ElementVariable(String.valueOf(t.getValue()));
					else
						throw new SmartScriptParserException("Element must be a type of String/Variable/Integer/Double!");
					child=new ForLoopNode((ElementVariable)el[0],el[1],el[2],el[3]);
				}
				t=lex.nextToken();
					if(t.getType()!=TokenType.ENDTAG)
						throw new SmartScriptParserException("Too many arguments!");
				parent.addChildNode(child);
				s.push(child);
			}else if(t.getType()==TokenType.CLOSETAG) {
				s.pop();
			}
			t=lex.nextToken();
			
		}if(s.size()==0)
			throw new SmartScriptParserException("Too many end tags!");
	}
	
	public DocumentNode getDocumentBody() {
		this.parse();
		return doc;
	}
	
}
