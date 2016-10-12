package base;

import java.io.File;
import java.io.Serializable;

public class ImageNote extends Note implements Serializable{
	private File images;
	private static final long serialVerionUID =1L;
	
	public ImageNote(String title){
		super(title);
	}
}
