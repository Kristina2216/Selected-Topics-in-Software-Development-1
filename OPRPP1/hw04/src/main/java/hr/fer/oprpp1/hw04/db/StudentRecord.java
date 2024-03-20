package hr.fer.oprpp1.hw04.db;

public class StudentRecord {
	public String firstName;
	public String lastName;
	public String jmbag;
	public int finalGrade;
	
	public StudentRecord(String firstName, String lastName, String jmbag, int finalGrade) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.jmbag = jmbag;
		this.finalGrade = finalGrade;
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return firstName+" "+lastName+" "+jmbag+" "+finalGrade;
	}

}
