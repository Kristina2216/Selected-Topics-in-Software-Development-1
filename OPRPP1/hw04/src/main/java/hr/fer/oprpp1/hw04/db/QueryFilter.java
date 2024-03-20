package hr.fer.oprpp1.hw04.db;

import java.util.List;

public class QueryFilter implements IFilter {
	
	List<ConditionalExpression> queries;
	
	public QueryFilter(List<ConditionalExpression> list) {
		queries=list;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression e:queries) {
			if(
				!((e.getComparisonOperator()).satisfied((e.getFieldGetter()).get(record),e.getStringLiteral()))
				)
				return false;
		}
		return true;
	}
}
