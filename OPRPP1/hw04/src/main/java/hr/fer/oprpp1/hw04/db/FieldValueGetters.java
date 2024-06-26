package hr.fer.oprpp1.hw04.db;

public class FieldValueGetters implements IFieldValueGetter {
	
	public static final IFieldValueGetter FIRST_NAME = (s)-> s.firstName; 
	public static final IFieldValueGetter LAST_NAME = (s)-> s.lastName; 
	public static final IFieldValueGetter JMBAG = (s)-> s.jmbag; 
	
	public String get(StudentRecord record) {
		return this.get(record);
	}
}
