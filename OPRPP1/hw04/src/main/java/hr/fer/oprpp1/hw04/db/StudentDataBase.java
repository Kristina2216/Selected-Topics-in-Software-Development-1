package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDataBase {
	
	List<StudentRecord> records;
	Map<String, StudentRecord> index;
	
	public StudentDataBase(String[] rows) {
		records=new ArrayList<StudentRecord>();
		index=new HashMap<String, StudentRecord>();
		for(String row:rows) {
			String[] record = row.trim().split("\\s+");
			if(index.get(record[0])!=null) {
				System.err.println("Učenik s tim jmbagom već postoji!");
				System.exit(0);
			}
			if(record.length==5) {
				String lastName=record[1]+" "+record[2];
				record[1]=lastName;
				record[2]=record[3];
				record[3]=record[4];
			}
			if(Integer.parseInt(record[3])>5 || Integer.parseInt(record[3])<1){
				System.err.println("Ocijena mora biti u intervalu [1,5]!");
				System.exit(0);
			}
			StudentRecord studentRecord = new StudentRecord(record[2],record[1],record[0],Integer.parseInt(record[3]));
			records.add(studentRecord);
			index.put(record[0], studentRecord);
			
		}
	}
	
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> filtered = new ArrayList<StudentRecord>();
		for (StudentRecord record:records) {
			if (filter.accepts(record))
				filtered.add(record);
		}
		return filtered;
		
	}
}
