package hr.fer.oprpp1.hw04.db;

public class ComparisonOperators implements IComparisonOperator {
	
	public static final IComparisonOperator LESS = (a,b)-> a.compareTo(b) < 0;
	public static final IComparisonOperator LESS_OR_EQUALS = (a,b)-> a.compareTo(b) <= 0;
	public static final IComparisonOperator GREATER = (a,b)-> a.compareTo(b) > 0;
	public static final IComparisonOperator GREATER_OR_EQUALS = (a,b)-> a.compareTo(b) >= 0;
	public static final IComparisonOperator EQUALS = (a,b)-> a.compareTo(b) == 0;
	public static final IComparisonOperator NOT_EQUALS = (a,b)-> a.compareTo(b) != 0;
	public static final IComparisonOperator LIKE = (a,b) -> {
		boolean found = false;
		if(b.contains("*")) {
			int firstAppear = b.indexOf('*');
			if((b.substring(firstAppear+1)).contains("*"))  
				throw new RuntimeException("Upit ne smije sadržavati dva znaka \"*\" !");
			if(b.charAt(0)=='*') {
				return a.contains(b.substring(1));
			}else if ((b.substring(b.length()-1)).charAt(0)=='*') {
				return a.contains(b.substring(0, b.length()-1));
			}else {
				int index = b.indexOf('*');
				String firstPart = b.substring(0,index);
				String secondPart = b.substring(index+1);
				int firstIndex = a.indexOf(firstPart);
				if(firstIndex==-1) return false;
				int secondIndex = a.indexOf(secondPart);
				if(secondIndex==-1 || secondIndex<(firstIndex+firstPart.length())) return false;
			}
			return true;
		}else
			return b.equals(a);
	};
	
	public boolean satisfied(String value1, String value2) {
		return this.satisfied(value1, value2);
	}


}
