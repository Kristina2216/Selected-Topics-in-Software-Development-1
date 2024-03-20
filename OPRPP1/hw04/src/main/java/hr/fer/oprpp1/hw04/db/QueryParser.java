package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;


public class QueryParser {
	List<ConditionalExpression> queries;
	
	public QueryParser(String query) {
		queries=read(query);
		QueryFilter filter = new QueryFilter(queries);
	}
	
	
	public List<ConditionalExpression> read(String query) {
		IFieldValueGetter getter=null;
		IComparisonOperator operator=null;
		String literal="";
		char[] data=new char[query.length()];
		for(int i=0;i<query.length();i++)
			data[i]=query.charAt(i);
		List<ConditionalExpression> ret=new ArrayList<ConditionalExpression>(); 
		int currentIndex=0;
		while(true) {
			if(currentIndex>=data.length) return ret;
			char c=data[currentIndex];
			if(c == ' ') {
				currentIndex++;
				continue;
			}else if (c=='\"') {
				currentIndex++;
				c=data[currentIndex];
				while(c!='\"') {
					literal+=String.valueOf(c);
					currentIndex++;
					c=data[currentIndex];
				}
				ConditionalExpression expression = new ConditionalExpression(getter, literal, operator);
				//System.out.println(literal);
				literal="";
				ret.add(expression);
			}else if((""+c+data[currentIndex+1]+data[currentIndex+2]+data[currentIndex+3]).toLowerCase().equals("and")){
					currentIndex+=3;
					continue;
			}else if(c=='q') {
				if(!("q"+data[currentIndex+1]+data[currentIndex+2]+data[currentIndex+3]+data[currentIndex+4]).equals("query"))
					throw new RuntimeException("Neispravan upit");
				currentIndex+=6;
				continue;
			}else if(c=='l') {
				if(!("l"+data[currentIndex+1]+data[currentIndex+2]
					+data[currentIndex+3]+data[currentIndex+4]
					+data[currentIndex+5]+data[currentIndex+6]
							+data[currentIndex+7]).equals("lastName"))
					throw new RuntimeException("Neispravan upit");
				getter=FieldValueGetters.LAST_NAME;
				//System.out.println("lastName");
				currentIndex+=8;
				continue;
			}else if (c=='f') {
				if(!("f"+data[currentIndex+1]+data[currentIndex+2]
						+data[currentIndex+3]+data[currentIndex+4]
						+data[currentIndex+5]+data[currentIndex+6]
						+data[currentIndex+7]+data[currentIndex+8]).equals("firstName"))
						throw new RuntimeException("Neispravan upit");
				getter=FieldValueGetters.FIRST_NAME;
				//System.out.println("firstName");
				currentIndex+=9;
				continue;
			}else if (c=='j') {
				if(!("j"+data[currentIndex+1]+data[currentIndex+2]
						+data[currentIndex+3]+data[currentIndex+4]).equals("jmbag"))
					throw new RuntimeException("Neispravan upit!");
				getter=FieldValueGetters.JMBAG;
				//System.out.println("jmbag");
				currentIndex+=5;
				continue;
			}else if (c=='<') {
				currentIndex++;
				c=data[currentIndex];
				if(c == '=') {
					operator = ComparisonOperators.LESS_OR_EQUALS;
					//System.out.println("less or equals");
				}else {
					operator = ComparisonOperators.LESS;
					System.out.println("less");
					continue;
				}
			}else if (c=='>') {
				currentIndex++;
				c=data[currentIndex];
				if(c == '=') {
					operator = ComparisonOperators.GREATER_OR_EQUALS;
					//System.out.println("greater or equals");
				}else {
					operator = ComparisonOperators.GREATER;
					//System.out.println("greater");
					continue;
				}
			}else if(c=='=') {
				operator= ComparisonOperators.EQUALS;
				//System.out.println("equals");
			}else if(c=='L') {
				operator= ComparisonOperators.LIKE;
				//System.out.println("like");
			}else if(c=='!') {
				currentIndex+=1;
				operator = ComparisonOperators.NOT_EQUALS;
				//System.out.println("not equals");
			}
			currentIndex++;
		}
	}
	
	public boolean isDirectQuery() {
		return(queries.size()==1 &&
				queries.get(0).getComparisonOperator()==ComparisonOperators.EQUALS
				&& queries.get(0).getFieldGetter()==FieldValueGetters.JMBAG);
	}
	
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException();
		}
		return(queries.get(0).getStringLiteral());
	}
	
	List<ConditionalExpression> getQuery(){
		return queries;
	}
	
	public static void main(String[] args) {
		QueryParser qp2 = new QueryParser("query jmbag=\"0123456789\"");
		System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
		System.out.println(qp2.getQueriedJMBAG()); // would throw!
		System.out.println("size: " + qp2.getQuery().size()); // 2
	}
}
