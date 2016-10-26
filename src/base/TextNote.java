package base;

import java.io.Serializable;
import java.util.HashMap;



//import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;



public class TextNote extends Note implements Serializable{
	private String content;
	private static final long serialVersionUID = 1L;
	
	public TextNote(String title){
		super(title);
		content="";
	}
	
	public TextNote(String title,String content){
		super(title);
		this.content = content;
	}
	
	public TextNote(java.io.File f){
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	
	private String getTextFromFile(String absolutePath){
		String result = "";
		BufferedReader br = null;
		
		try{
			String sCurrentLine;
			br = new  BufferedReader(new FileReader(absolutePath));
			
			while((sCurrentLine = br.readLine()) !=null){
				result = result+sCurrentLine;
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(br != null)br.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
		
	}
	
	public void exportTextToFile(String pathFolder){
		File file = new File(pathFolder + this.getTitle().replaceAll(" ", "_")+ ".txt");
		try{
			System.out.println(file.getAbsolutePath());
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//buggy function
	public Character countLetters(){
		HashMap<Character,Integer> count = new HashMap<Character,Integer>();
		String a = this.getTitle() + this.getContent();
		int b = 0;
		Character r = ' ';
		for (int i = 0; i < a.length(); i++) {
			Character c = a.charAt(i);
			if (c <= 'Z' && c >= 'A' || c <= 'z' && c >= 'a') {
				if (!count.containsKey(c)) {
					count.put(c, 1);
					if (count.get(c) > b) {
						b = count.get(c);
						r = c;
					}
				} else {
					count.put(c, count.get(c) + 1);
					if (count.get(c) > b) {
						b = count.get(c);
						r = c;
					}
				}
			}
		}
		return r;
	}
	
	public void changeContent(String change){
		content = new String(change);
	}
	
	public String getContent(){
		return content;
	}
}
