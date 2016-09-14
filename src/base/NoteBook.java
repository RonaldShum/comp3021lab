package base;

import java.util.ArrayList;

import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;

public class NoteBook {
	
	private ArrayList<Folder> folders;
	
	public NoteBook(){
		folders = new ArrayList<Folder>();
	}
	
	public boolean createTextNote(String folderName,String title){
		TextNote note = new TextNote(title);
		return insertNote(folderName,note);
	}
	public boolean createImageNote(String folderName,String title){
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}
	public ArrayList<Folder> getFolders(){
		return folders;
	}
	private boolean insertNote(String folderName,Note note){
		for(Folder f : folders){
			if(f.getName().equals(folderName)){
				for(Note n: f.getNotes()){
					if(n.equals(note)){
						System.out.println("Creating note "+note.getTitle()+" under folder "+folderName+" failed");
						return false;
					}
				}
				f.addNote(note);
				return true;
			}
		}
		Folder newFolder = new Folder(folderName);
		newFolder.addNote(note);
		folders.add(newFolder);
		return true;
		
		
	}

}
