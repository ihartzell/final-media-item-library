import javafx.collections.ObservableList;	// What all these imports are for is importing classes and methods from classes within the Java library.
import java.util.Optional;				// Import has to do with the dialog boxes.
import javafx.application.Application;	// This import is necessary for every javafx App.
import javafx.event.ActionEvent;		// Necessary for actions events such as clicking a button.
import javafx.event.EventHandler;		// Necessary for tackling what needs to occur when the user actually clicks a button.
import javafx.geometry.Insets;
import javafx.scene.Scene;						// The Scene Class is Necessary for storing the panes in the scene and the scene in the stage.
import javafx.scene.control.Alert;				// This is used for my error boxes.
import javafx.scene.control.Alert.AlertType;	// This is also used for my error boxes, it's essentially the AlertType of ERROR.
import javafx.scene.control.Button;		// This Class is Necessary for creating buttons.
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ListView;	// The ListView class is what allows for the overall GUI, where a user can click on items.
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;				// This class is essential as it's the stage of the GUI.

public class LibraryGUI extends Application				
{					
	//Declaration for my Library object.												
	Library libraryFieldObject = new Library();	
	
	// Declaration for myListView as a view of media item objects.
	ListView <MediaItem> myListView = new ListView <MediaItem>();		
		
	// This is where I launch the app.
	public static void main(String[] args)				
	{
		Application.launch(args);
	}
	
	// This is where I start the setup for the theater.
	public void start(Stage primaryStage) 				
	{
		// I call my refreshViewList method to essentially make no nulls or booleans appear.
		refreshViewList();
		
		// I'm making my border pane object and my grid pane object.
		BorderPane personalBorderPane = new BorderPane();	
		GridPane personalGridPane = new GridPane();			
		
		// I set the width and height for my list view.
		// I set the style of the list view to size 16 pixels of type Lucida Calligraphy.
		myListView.setPrefSize(500, 500);
		myListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		myListView.setStyle("-fx-font-size: 16px; -fx-font-family: 'Lucida Calligraphy'");
		
		// The list view is stored in the center of the border pane.
		// The grid pane is in the bottom of the border pane, pane inside a pane essentially.
		personalBorderPane.setCenter(myListView);			
		personalBorderPane.setBottom(personalGridPane);		
		
		// setPadding is for the thickness of the buttons
		// setVgap and setHgap are for setting the gap between the buttons.
		personalGridPane.setPadding(new Insets(10,10,10,10));	
		personalGridPane.setVgap(10);							
		personalGridPane.setHgap(10);							
		
		// I'm making four inner class handler objects to handle
		// the events from clicking the different buttons.
		AddHandler handlerForAdd = new AddHandler();						
		CheckOutHandler handlerForCheckOut = new CheckOutHandler();			
		CheckInHandler handlerForCheckIn = new CheckInHandler();
		DeleteHandler handlerForDelete = new DeleteHandler();
		
		
		// I'm making my add button.
		// I set the size of the button in the pane
		// I add the add button to the grid pane at column 0 row 0
		// the I call for the setOnAction method which has the handler
		// as the argument.
		Button add = new Button("Add");													
		add.setPrefSize(175, 25);																				
		personalGridPane.add(add, 0, 0);												
		add.setOnAction(handlerForAdd);								
		
		Button checkOut = new Button ("Check Out");
		checkOut.setPrefSize(175, 25);
		checkOut.setDisable(true);
		personalGridPane.add(checkOut, 1, 0);
		checkOut.setOnAction(handlerForCheckOut);
		
		Button checkIn = new Button("Check In");
		checkIn.setPrefSize(175, 25);
		checkIn.setDisable(true);
		personalGridPane.add(checkIn, 2, 0);
		checkIn.setOnAction(handlerForCheckIn);
		
		// Delete, Check Out, and Check in, follow the same pattern
		// as Add except that they're initially disabled until
		// the user clicks on something in the list view.
		Button delete = new Button("Delete");											
		delete.setPrefSize(175, 25);
		delete.setDisable(true);
		personalGridPane.add(delete, 3, 0);
		delete.setOnAction(handlerForDelete);
		
		// Here I'm making an observable list that contains media item objects.
		// I store in the dimmingButtons variable what ever the user
		// clicks on.
		// Note that getSelectionModel().getSelectedItems() those methods are for getting
		// what the user clicks on. The user can selected multiple items with the getSelectedItems method.
		// The first if statement says that if the size of the dimmingButtons variable is < 0
		// Then I disable all buttons except for Add.
		// Other wise checkOut checkIn and delete are visible, so when
		// the user clicks on an item in the list view the buttons are visible.
		myListView.setOnMouseReleased((event) ->{
			ObservableList<MediaItem> dimmingButtons = myListView.getSelectionModel().getSelectedItems();
			if(dimmingButtons.size() <= 0)
			{
				checkOut.setDisable(true);
				checkIn.setDisable(true);
				delete.setDisable(true);
			}
			else
			{
				checkOut.setDisable(false);
				checkIn.setDisable(false);
				delete.setDisable(false);
			}
		});
		// Here I'm putting my pane with dimensions of 750x750 in the scene
		Scene scene = new Scene (personalBorderPane,750,750);		
		
		// I'm setting the title of my stage.
		// I'm setting the scene in the stage.
		// Then I'm showing the whole stage or theater.
		// I make it so the stage is unsizable by the user.
		// I then in the event of closing the program and save the information to
		// my text file.
		primaryStage.setTitle("My Personal Library");				
		primaryStage.setScene(scene);								
		primaryStage.show();										
		primaryStage.setResizable(false);							
		primaryStage.setOnCloseRequest((event) ->{
			libraryFieldObject.save();
		});
		
	}	// End of the main class.
	
	// This method basically makes it so that every time it's called
	// it removes booleans and nulls in the GUI so it looks pretty.
	// The ObservableList is a type of array list that essentially
	// allows listeners to listen for this list so it works better with GUI's.
	// I clear the guiArrayList so it can start from scratch.
	// I then cycle through the array list of media items
	// and then add to the guiArrayList the media item.
	public void refreshViewList()											
    {																		
        ObservableList<MediaItem> guiArrayList = myListView.getItems();		
        																	
        guiArrayList.clear();												
        																	 
        for (MediaItem mediaItem : libraryFieldObject.listOfMediaItems)			
        {																		
           																		
        	guiArrayList.add(mediaItem);
        }
    }
	
	// This method tackles all the dialog box stuff.
	public String requestString(String request)
	{
	        //Create new empty dialog.
	        TextInputDialog dialogBox = new TextInputDialog();
	       
	        //Set title.
	        dialogBox.setTitle("Library Manager");
	       
	        //Set Context from input string
	        dialogBox.setContentText(request);
	       
	        // No header
	        dialogBox.setHeaderText("");
	       
	        //Get Result, show the dialog box and wait.
	        Optional<String> result = dialogBox.showAndWait();
	       
	        //If result, then return
	        if(result.isPresent())
	        {
	        	return result.get();
	        }
	           
	        else
	        {
	        	 return "";
	        }    
	}
	
	// This method is used for my try catches regarding writing to a file and reading from a file.
	// This is also used for my parse method in the MediaItems class for the IndexOutOfBounds exception.
	// The IndexOutOfBounds exception is for the case where the formatting of the string has been corrupted.
	
	public static void displayError(Exception ex, String errorDescription)
	{
		Alert displayErrorDialog = new Alert(AlertType.ERROR);
		displayErrorDialog.setTitle("Error");
		displayErrorDialog.setContentText(errorDescription);
	}
	
	// My Handler for the Add button which handles what I want to happen which the user clicks Add.
	class AddHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			// I'm calling for the requestString method with the string text as the argument.
			// I then store that into title.
			// format works the same way.
			String title = requestString("Title? (Example, Toy Story)");
			String format = requestString ("Format? (Dvd,Blue Ray ect..)");
			
			// If the title and format equal a blank string then I return nothing.
			if(title.equals("") && !format.equals(""))
			{
				return;
			}
			// Otherwise I add use the addNewItem method associated with the library object to have the title and format display and then
			// I refresh the view.
			else
			{
				libraryFieldObject.addNewItem(title, format);
				refreshViewList();
			}
		}
	}
	
	// My Check Out handler which handles what I want to happen when the user clicks Check Out.
	class CheckOutHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			// Very similar process as with the Add handler.
			// The difference is I cycle through the list view array list.
			// I the use my library object to call the markItemOnLoan method
			// which essentially will mark an item on loan.
			String name = requestString("Name? (Example, Joe, Mark)");
			String date = requestString("Date? (Example, April 2nd 2016)");
			
			 for (MediaItem mediaItem : myListView.getSelectionModel().getSelectedItems())
	         {
				 try
				 {
					 libraryFieldObject.markItemOnLoan(mediaItem, name, date);
				 }
				 // I caught the RuntimeException that was thrown from my if statment within the MediaItem class of
				 // the markOnLoan method.
				 // I create an errorBox object.
				 // I set it's title.
				 // I remove the format because I didn't like how it looked.
				 // Then I sent a useful message back to the user.
				 catch (RuntimeException re)
				 {
					 Alert errorBox = new Alert(AlertType.ERROR);
					 errorBox.setTitle("Invalid media entry!");
					 errorBox.setHeaderText("");
					 errorBox.setContentText("This media item is already on loan. You attempted to loan"
					 		+ " an item that is already loaned out. Try picking an item in the library not current loaned out.");
					 errorBox.showAndWait();
				 } 
	         }
			refreshViewList();
		}
	}
	
	// My Check In handler which handles what I want to happen when the user clicks Check in.
	class CheckInHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			// This cycles through the list view and calls for the 
			// markItemReturned method which basically removes all the on loaned stuff.
			for(MediaItem mediaItem: myListView.getSelectionModel().getSelectedItems())
			{
				try
				{
					libraryFieldObject.markItemReturned(mediaItem);
				}
				catch (RuntimeException re)
				{
					// I caught the RuntimeException that was thrown by my if statement within the MediaItem class in my
					// markReturned method.
					// I then set the title of the error box as well as
					// the header and the content message.
					Alert errorBoxForCheckIn = new Alert(AlertType.ERROR);
					errorBoxForCheckIn.setTitle("Checked In Message");
					errorBoxForCheckIn.setHeaderText("");
					errorBoxForCheckIn.setContentText("This item is already checked in. If you wish to check something in,"
													+ " try clicking on an item that is loaned out to someone.");
					errorBoxForCheckIn.showAndWait();
				}
			}
			refreshViewList();
		}
	}
	
	// My Check In handler which handles what I want to happen when the user clicks delete.
	class DeleteHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			// Cycles through the list view and deletes the item the user selects.
			for(MediaItem mediaItem: myListView.getSelectionModel().getSelectedItems())
			{
				libraryFieldObject.delete(mediaItem);
			}
			refreshViewList();
		}
	}
	
}
