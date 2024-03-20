package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentDB {
	
	public static class RecordFormatter {
		
		List<StudentRecord> records;
		boolean direct;
		
		public RecordFormatter (List<StudentRecord> records, boolean direct) {
			this.records=records;
			this.direct=direct;
		}
		
		public List<String> format() {
			List<String> ret=new ArrayList<String>();
			int biggestLastName=0;
			int biggestFirstName=0;
			int size=records.size();
			if(size==0) {
				ret.add("Records selected: "+size);
				return ret;
			}
			if(direct==true)
				ret.add("Using index for record retrieval.");
			for(StudentRecord m: records) {
				if(m.lastName.length()>biggestLastName)
					biggestLastName=m.lastName.length();
				if(m.firstName.length()>biggestFirstName)
					biggestFirstName=m.firstName.length();
			}
			ret.add("+"+new String(new char[12]).replace("\0", "=")
					+"+"+new String(new char[biggestLastName+2]).replace("\0", "=")
					+"+"+new String(new char[biggestFirstName+2]).replace("\0", "=")
					+"+"+new String(new char[3]).replace("\0", "=")+"+");
			for(StudentRecord m: records) {
				ret.add("| "+m.jmbag+" | "+
						m.lastName+new String(new char[biggestLastName-m.lastName.length()]).replace("\0", " ")+" | "+
						m.firstName+new String(new char[biggestFirstName-m.firstName.length()]).replace("\0", " ")+" | "+
						m.finalGrade+ " |");
			}
			ret.add( "+"+new String(new char[12]).replace("\0", "=")
					+"+"+new String(new char[biggestLastName+2]).replace("\0", "=")
					+"+"+new String(new char[biggestFirstName+2]).replace("\0", "=")
					+"+"+new String(new char[3]).replace("\0", "=")+"+"
					+"\nRecords selected: "+size);
			return ret;
		}
		
	}
	
public static void main(String[] args) throws IOException {
	List<String> lines=null;
	lines = Files.readAllLines(
	Paths.get("./database.txt"),
		StandardCharsets.UTF_8);
	StudentDataBase base = new StudentDataBase(lines.stream()
	                .toArray(String[]::new));
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String input="";
	while(true){
		input = br.readLine();
		List<String> output=new ArrayList<String>();
		if(input.equals("exit")) break;
		QueryParser p=new QueryParser(input);
		if(p.isDirectQuery()) {
			 StudentRecord r = base.forJMBAG(p.getQueriedJMBAG());
			 List<StudentRecord> list=new ArrayList<StudentRecord>();
			 list.add(r);
			 RecordFormatter formater= new RecordFormatter(list, true);
			 output=formater.format();
		} else {
			List<StudentRecord> list=new ArrayList<StudentRecord>();
			 for(StudentRecord r : base.filter(new QueryFilter(p.getQuery()))) {
				 list.add(r);
			 }RecordFormatter formater= new RecordFormatter(list, false);
			 output=formater.format();
		}
		output.forEach(System.out::println);
	};
	System.out.println("Goodbye!");
}


}
