package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.relation.RoleUnresolvedList;

import org.hamcrest.core.IsInstanceOf;
import org.omg.PortableServer.POAPackage.AdapterAlreadyExists;

import com.sun.corba.se.spi.orbutil.fsm.Action;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.org.apache.bcel.internal.classfile.FieldOrMethod;

import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.java2d.windows.GDIRenderer;
import javafx.stage.FileChooser;

/**
 * 
 * NoteBook GUI with JAVAFX
 * 
 * COMP 3021
 * 
 * 
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 * 
	 * Combobox for selecting the folder
	 * 
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	/**
	 * 
	 * the stage of this display
	 */
	Stage stage;
	/**
	 * 
	 * current note selected by the user
	 */
	String currentNote = "";
	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		loadNoteBook();
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 * 
	 * @return
	 */
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load");
		buttonLoad.setPrefSize(100, 20);
		buttonLoad.setDisable(false);
		Button buttonSave = new Button("Save");
		buttonSave.setPrefSize(100, 20);
		buttonSave.setDisable(false);
		Label label = new Label("Search :");
		TextField textSearch = new TextField ();
		Button buttonSearch = new Button("Search");
		Button buttonClear = new Button("Clear Search");
		
		//buttonSearch setOnAction
		buttonSearch.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				currentSearch = textSearch.getText();
				//textAreaNote.setText("");
				//TODO
				updateListView();
				
			}
			
		});
		
		//buttonLoad setOnAction
		buttonLoad.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				
				
				//TODO load a NoteBook from file
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contains a NoteBook Object!");
				
				FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extensionFilter);
				
				File file = fileChooser.showOpenDialog(stage);
				
				if(file != null){
					
					//TODO
					noteBook = new NoteBook(file.getAbsolutePath());
					
					//update folderComboBox
					foldersComboBox.getItems().clear();
					for(Folder f: noteBook.getFolders()){
						foldersComboBox.getItems().add(f.getName());
					}
					
					
					
					
					
				}
				
			}
		});
		//buttonSave setOnAction
		buttonSave.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				//TODO save a NoteBook from file
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File To Save NoteBook Object!");
				
				FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extensionFilter);
				
				File file = fileChooser.showOpenDialog(stage);
				
				
				noteBook.save(file.getAbsolutePath());
				
				
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Successfully saved");
				alert.setContentText("You file has been saved to file " + file.getName() );
				alert.showAndWait().ifPresent(rs->{
					if(rs==ButtonType.OK){
						System.out.println("Press OK.");
					}
				});
			}
			
			
		});

		hbox.getChildren().addAll(buttonLoad, buttonSave,label,textSearch,buttonSearch,buttonClear);

		return hbox;
	}

	/**
	 * this create the section on the left
	 * 
	 * @return notebook
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes
		
		//foldersComboBox.getItems().addAll("FOLDER NAME 1", "FOLDER NAME 2", "FOLDER NAME 3");
		
		for(Folder f: noteBook.getFolders()){
			foldersComboBox.getItems().add(f.getName());
		}
		
		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if(ov==null||t1==null){
					return;
				}
				currentFolder = t1.toString();
				currentNote = "";
				// this contains the name of the folder selected
				// TODO update listview
				updateListView();

			}

		});

		foldersComboBox.setValue("-----");

		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote
				String content = "";
				for(Folder f: noteBook.getFolders()){
					if(f.getName().equals(currentFolder)){
						for(Note n:f.getNotes()){
							if((n instanceof TextNote)&&(n.getTitle().equals(title))){
								content = ((TextNote) n).getContent();
								currentNote = n.getTitle();
								System.out.println(currentNote);
							}
								
						}
					}
				}
				textAreaNote.setText(content);
				textAreaNote.setEditable(true);

			}
		});
		//addNoteButton
		Button buttonAddNote = new Button("Add a Note");
		buttonAddNote.setPrefSize(100, 20);
		
		//addFolderButton
		Button buttonAddFolder = new Button("Add a Folder");
		buttonAddFolder.setPrefSize(100, 20);
		vbox.getChildren().add(new Label("Choose folder: "));
		
		//buttonAddNote setOnAction
		buttonAddNote.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
			
				if(currentFolder.equals("-----")){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("Please choose a folder first!");
					alert.showAndWait().ifPresent(rs->{
						if(rs==ButtonType.OK){
							System.out.println("Press OK.");
						}
					});	
					
					return;
				}
				
				
				
				TextInputDialog dialog = new TextInputDialog("Add a Note");
				dialog.setTitle("Input");
				dialog.setHeaderText("Add a new note to current folder");
				dialog.setContentText("Please enter the name of your note");
				//get result
				Optional<String> result = dialog.showAndWait();
				if(result.isPresent()){
					//TODO
					noteBook.createTextNote(currentFolder, result.get());
					updateListView();
				}
			}
		});
		
		//buttonAddFolder setOnAction
		buttonAddFolder.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				TextInputDialog dialog = new TextInputDialog("Add a Folder");
				dialog.setTitle("Input");
				dialog.setHeaderText("Add a new folder for your notebook:");
				dialog.setContentText("Please enter the name you want to create");
				
				//Traditional way to get the respinse value.
				Optional<String> result = dialog.showAndWait();
				if(result.isPresent()){
					//TODO
					System.out.println(result.get());
					if(result.get().equals("")){
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setContentText("Please input an valid folder name");
						alert.showAndWait().ifPresent(rs->{
							if(rs==ButtonType.OK){
								System.out.println("Press OK.");
							}
						});	
						return;
					}
					//checkName
					for(Folder f : noteBook.getFolders()){
						if(f.getName().equals(result.get())){
						    Alert alert = new Alert(AlertType.WARNING);
						    alert.setTitle("Warning");
							alert.setContentText("Folder already exists!");
							alert.showAndWait().ifPresent(rs->{
								if(rs==ButtonType.OK){
									System.out.println("Press OK.");
								}
							});		
							return;
						}
					}
					
					//Add folder
					
					noteBook.getFolders().add(new Folder(result.get()));
					foldersComboBox.getItems().add(result.get());
				}
				
			}
		});
	
		
		//vbox.getChildren().add(foldersComboBox);
		//HBOX
		HBox comboButton = new HBox();
		comboButton.setSpacing(10);
		comboButton.getChildren().addAll(foldersComboBox,buttonAddFolder);
		vbox.getChildren().add(comboButton);
		//HBOX END
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		vbox.getChildren().add(buttonAddNote);

		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder
		if(currentSearch.equals("")){
		for(Folder f:noteBook.getFolders()){
			if(f.getName().equals(currentFolder)){
				for(Note n:f.getNotes()){
					if (n instanceof TextNote) {
						list.add(n.getTitle());
					}
				}
			}
		}
		}else{
			System.out.println(currentSearch);
			for(Folder f:noteBook.getFolders()){
				if(f.getName().equals(currentFolder)){
					for(Note n:f.searchNotes(currentSearch)){
						System.out.println(n.getTitle());
						if (n instanceof TextNote) {
							list.add(n.getTitle());
						}
					}
				}
			}
			
		}
		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(false);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		// 0 0 is the position in the grid
		grid.add(textAreaNote, 0, 1);
		
		//buttonSaveNote
		Button buttonSaveNote = new Button("Save Note");
		buttonSaveNote.setPrefSize(100, 20);
		//buttonDeleteNote
		Button buttonDeleteNote = new Button("Delete Note");
		buttonDeleteNote.setPrefSize(100, 20);
		//saveView
		ImageView saveView = new ImageView(new Image(new File("save.png").toURI().toString()));
		saveView.setFitHeight(18);
		saveView.setFitWidth(18);
		saveView.setPreserveRatio(true);
		//loadView
		ImageView loadView = new ImageView(new Image(new File("load.png").toURI().toString()));
		loadView.setFitHeight(18);
		loadView.setFitWidth(18);
		loadView.setPreserveRatio(true);
		//HBOX
		HBox h = new HBox();
		h.setSpacing(10);
		h.getChildren().addAll(saveView,buttonSaveNote,loadView,buttonDeleteNote);
		//addVBOX
		grid.add(h, 0, 0);
		//buttonDeleteNote setOnAction
		buttonDeleteNote.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event ){
				if(currentFolder.equals("-----")||currentNote.equals("")){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("Please select a folder and a note.");
					alert.showAndWait().ifPresent(rs->{
						if(rs==ButtonType.OK){
							System.out.println("Press OK.");
						}
					});	
					return;
				}else{
					for(Folder f:noteBook.getFolders()){
						if(f.getName().equals(currentFolder)){
							f.removeNotes(currentNote);
							updateListView();
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Successed");
							alert.setContentText("Your note has been successfully removed");
							alert.showAndWait().ifPresent(rs->{
								if(rs==ButtonType.OK){
									System.out.println("Press OK.");
								}
							});	
							
						}
					}
				}
				
			}
			
		});
		
		
		
		//buttonSaveNote setOnAction
		buttonSaveNote.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				if(currentFolder.equals("-----")||currentNote.equals("")){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("Please select a folder and a note.");
					alert.showAndWait().ifPresent(rs->{
						if(rs==ButtonType.OK){
							System.out.println("Press OK.");
						}
					});	
					return;
				}else{
					for(Folder f:noteBook.getFolders()){
						if(f.getName().equals(currentFolder)){
							for(Note n:f.getNotes()){
								System.out.println(n.getTitle());
								if (n instanceof TextNote && n.getTitle().equals(currentNote)) {
									((TextNote) n).changeContent(textAreaNote.getText());
								}
							}
						}
					}
					
				}
			}
		});
		

		return grid;
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called �he most shocking play in NFL history�� and the Washington Redskins dubbed the �hrowback Special��: the November 1985 play in which the Redskins�� Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award�inning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything�ntil it wasn�. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant� part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether� Daddy Was a Number Runner and Dorothy Allison� Bastard Out of Carolina, Jacqueline Woodson� Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood�he promise and peril of growing up�nd exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}
	private void loadNoteBook(File file){
		noteBook = new NoteBook(file.getAbsolutePath());
	}

}
