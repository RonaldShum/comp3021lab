package base;

import java.io.Serializable;
import java.sql.Date;

public class Note implements Comparable<Note>,Serializable{
	private Date date;
	private String title;
	private static final long serialVersionUID = 1L;
	
	public int compareTo(Note o){
		long date1 = this.date.getTime();
		long date2 = o.date.getTime();
		
		if(date1 > date2){
			return -1;
		}else if(date1 < date2){
			return 1;
		}else{
			return 0;
		}
	}
		
	public Note(String title){
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}
	
	public String toString(){
		return date.toString() + "\t" + title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public boolean equals(Note another){
		return (this.title.equals(another.title));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Note other = (Note) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
	
	

}
