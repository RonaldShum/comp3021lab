package base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.corba.se.spi.ior.TaggedProfileTemplate;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;




public class Folder implements Comparable<Folder>,Serializable{
	
	private ArrayList<Note> notes;
	private String name;
	private static final long serialVerionUID = 1L;
	
	public List<Note> searchNotes(String keywords){
		List<Note> fin = new ArrayList<Note>();
		
		keywords = keywords.toLowerCase();
		
		String[] key = keywords.split(" ");
		System.out.println(key.length);
		//check title only
		for(int i = 0; i < key.length;i++){
			System.out.println(key[i]);
			System.out.println("the number of i = " +i);
			
			if( (i+2) < key.length && key[i+1].equals("or") ){
				List<Note> temp = new ArrayList<>();
				System.out.println("here");
				
				//Add notes to temp1 or temp2 but not both
				for(Note j: notes){
					if(j.getTitle().toLowerCase().contains(key[i])|| j.getTitle().toLowerCase().contains(key[i+2])){
						temp.add(j);
					}else if(j instanceof TextNote && ((TextNote) j).getContent().contains(key[i])) {
						temp.add(j);
					}else if(j instanceof TextNote && ((TextNote) j).getContent().contains(key[i+2])){
						temp.add(j);
					}
				}
				//Add i 
				
				
				
				
				if(!fin.isEmpty() && !temp.isEmpty()){
					fin.retainAll(temp);
				}else if(!temp.isEmpty()){
					fin.addAll(temp);
				}
				i = i+2;
			}else{
				List<Note> temp = new ArrayList<Note>();
				//create a temp list that contains the keyword
				for(Note j: notes){
					System.out.println("main part of");
					if(j.getTitle().toLowerCase().contains(key[i])){
						System.out.println("title part ");
						temp.add(j);
					}else if(j instanceof TextNote && ((TextNote) j).getContent().toLowerCase().contains(key[i])){
						temp.add(j);
						System.out.println("content part");
					}
				}
				
				if(!fin.isEmpty() && !temp.isEmpty()){
					fin.retainAll(temp);
					
				}else if(!temp.isEmpty()){
					fin.addAll(temp);
				}
			}
			System.out.println("number of i in the end = " +i);
		}
		
		return fin;
		
		
	}
	
	public void sortNotes(){
		Collections.sort(notes);
	}
	
	public int compareTo(Folder o){
		return (this.name.compareTo(o.name));
	}
	
	public Folder(String name){
		this.name = name;
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note newNote){
		notes.add(newNote);
	}
	
	public String getName(){
		return name;
	}
	public ArrayList<Note> getNotes(){
		return notes;
	}
	
	public boolean removeNotes(String title){
		for(Note n: notes){
			if(n.getTitle().equals(title)){
				notes.remove(n);
				return true;
			}
		}
		return false;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Folder other = (Folder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		for(Note n : notes){
			if(n instanceof TextNote){
				nText++;
			}else{
				nImage++;
			}
		}
		return name +":"+nText+":"+nImage;
	}
	
	
	

}
