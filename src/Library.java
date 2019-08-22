import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;	
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Library 		
{
	// All this constructor does is call for the open method which reads in data from a file
	// which I can then create a Library object from this constructor.
	public Library()												
	{
		open();
	}
	
	// I'm declaring an infinite number, or array list of media item objects of type MediaItem
	// This way I don't have to deal with all kinds of string annoyances.
	ArrayList <MediaItem> listOfMediaItems = new ArrayList < >();		
	
	// In this method I create an object from the media item constructor
	// so I can then add that to the array list of media items.
	public void addNewItem(String title, String format)				
	{
		MediaItem itemObj = new MediaItem(title,format);				
		listOfMediaItems.add(itemObj);									
	}
	
	// I pass into this method an object of type MediaItem so I can then use that object to easily call
	// for my markOnLoan method.
	public void markItemOnLoan(MediaItem mediaItem, String name, String date)
	{
	    
		mediaItem.markOnLoan(name, date);
	}
	
	// Again I pass into this method a MediaItem object named mediaItem so I can call for the markReturned method.
	public void markItemReturned(MediaItem mediaItem)
	{
		mediaItem.markReturned();
	}
	
	// Similar process as with the previous two method but in here I just remove what ever MediaItem object is in the array list of media items.
	public void delete(MediaItem mediaItem)
	{
		listOfMediaItems.remove(mediaItem);
	}
	
	// This method writes information to a library.txt file.
	public void save()	
	{
		// File Class is used to store the name of the file as an object.
		File libraryFile = new File("library.txt");
		
        try 
        {
        	// PrintWriter class is used to create an object to write to the file.
        	PrintWriter fileOutput = new PrintWriter(libraryFile);
        	
        	// I cycle through the array list and the output to the file
        	// Every aspect of the media item including booleans and nulls with ~'s in between.
        	for(MediaItem mediaItem: listOfMediaItems)	
        	{											
        		fileOutput.println(mediaItem.toFileString());
        	}
        	
        	// Have to close the file.
        	fileOutput.close();						
        } 
        
        // I'm calling for the displayError method in the LibraryGUI class.
        // I then call for it's arguments which are an exception, and a string message,
        // which can be whatever I want. I do the same thing for all file exceptions and
        // the parse method in the MediaItem class.
        catch (FileNotFoundException ex) 
        {
        	LibraryGUI.displayError(ex, "The file was unable to be found.");
        }   
	}
	
	// In this method I then read in the data that is in the file.
	public void open()		
	{
		// I clear the array list.
		listOfMediaItems.clear();
		try
		{
			// Very similar process to the save method.
			// I'm creating a FileReader object which is the same name of the file.
			FileReader fileRead = new FileReader("library.txt");
			try
			{
				// This essentially reads text from a character input stream.
				// This objects makes it so it'll read appropriately the lines of media items.
				BufferedReader bufferer = new BufferedReader(fileRead);		
				
				String str;													
				
				while((str = bufferer.readLine()) != null)
				{
					if(!str.equalsIgnoreCase(""))
					{
						MediaItem mediaItemObject = MediaItem.parse(str);	
						listOfMediaItems.add(mediaItemObject);				
					}														
				}
				// I have the close the bufferer similarly to closing of a file.
				bufferer.close();
			}
			catch (IOException ex)
			{
				LibraryGUI.displayError(ex, "There was an issue reading in the file.");
			}
		
		}
		catch (FileNotFoundException ex)
		{
			LibraryGUI.displayError(ex, "The file couldn't be found.");
		}
	}
	
}

