package base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import sun.security.util.PropertyExpander.ExpandException;



public class NoteBook implements Serializable {
	
	private ArrayList<Folder> folders;
	private static final long serialVersionUID = 1L;
	
	public NoteBook(){
		folders = new ArrayList<Folder>();
	}
	
	public NoteBook(String file){
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try{
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			NoteBook n = (NoteBook) in.readObject();
			this.folders = new ArrayList<Folder>(n.folders);
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean save(String file){
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try{
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Note> searchNotes(String keywords){
		List<Note> fin = new ArrayList<Note>();
		for(Folder i : folders){
			
			fin.addAll(i.searchNotes(keywords));
		}
		return fin;
	}
	
	public void sortFolders(){
		for(Folder i: folders){
			i.sortNotes();
		}
		Collections.sort(folders);
	}
	public boolean createTextNote(String folderName,String title,String content){
		TextNote note = new TextNote(title,content);
		return insertNote(folderName,note);
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
