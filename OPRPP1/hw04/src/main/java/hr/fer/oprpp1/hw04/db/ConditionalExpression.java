package hr.fer.oprpp1.hw04.db;

public class ConditionalExpression {
	IFieldValueGetter getter;
	String literal;
	IComparisonOperator operator;
	
	public ConditionalExpression(IFieldValueGetter getter, String literal,  IComparisonOperator operator) {
		this.getter = getter;
		this.literal = literal;
		this.operator = operator;
	}
	
	public IFieldValueGetter getFieldGetter() {
		return getter;
	}
	
	public String getStringLiteral() {
		return literal;
	}
	
	public IComparisonOperator getComparisonOperator() {
		return operator;
	}
}
