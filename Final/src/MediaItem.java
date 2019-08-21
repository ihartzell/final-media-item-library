public class MediaItem 
{
	// Declarations of private fields.
	// These variables are private to this class and other
	// classes can only have access through the public getters and setters.
	private String title;
	private String format;
	private boolean onLoan;
	private String loanedTo;
	private String dateLoaned;
	
	// Constructor to initialize fields.
	MediaItem()	
	{
		title = null;
		format = null;
		loanedTo = null;
		dateLoaned = null;
		onLoan = false;
	}
	
	// Constructor to initialize the title and format of this media item.
	MediaItem(String title, String format)	
	{
		onLoan = false;
		this.title = title;
		this.format = format;
	}
	
	// Getters and setters till markOnLoan method.
	public String getTitle() 	
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getFormat() 
	{
		return format;
	}

	public void setFormat(String format) 
	{
		this.format = format;
	}

	public boolean isOnLoan() 
	{
		return onLoan;
	}

	public void setOnLoan(boolean onLoan) 
	{
		this.onLoan = onLoan;
	}

	public String getLoanedTo() 
	{
		return loanedTo;
	}

	public void setLoanedTo(String loanedTo) 
	{
		this.loanedTo = loanedTo;
	}

	public String getDateLoaned() 						
	{
		return dateLoaned;
	}

	public void setDateLoaned(String dateLoaned) 			
	{
		this.dateLoaned = dateLoaned;
	}
	
	// This method will mark on loan what the user tells it to.
	// onLoan starts as false, so if it's true then first if statement.
	// Other wise onLoan would be true and it would be loaned to the name
	// and the date loaned would be the date.
	void markOnLoan(String name, String date)			
	{	
		// I throw a RunTimeException, which is a fundamental class in Java like the String class.
		// The in the handler for Check out is where I have my try catch block.
		if (onLoan == true)								
		{
			throw new RuntimeException();
		}
		else
		{
			onLoan = true;
			loanedTo = name;
			dateLoaned = date;
		}
	}
	
	// Sets onLoan to false
	void markReturned()	
	{
		 //If onLoan is already false then it's not loaned to anyone.
		// I throw the RuntimeException and catch that in my Check In handler class.
		if (onLoan == false)	
		{
			throw new RuntimeException();
		}
		onLoan = false;
	}
	
	// This parse method has to do strictly with the files.
	
	public static MediaItem parse(String symbolString)	
	{
		// I use the split method to split instances of the ~ sign in the file
		// so I can store what's there in each aspect of the mediaItemObject
		// such as title, format ect.
		MediaItem mediaItemObject = new MediaItem();
        String[] stringArray = symbolString.split("~");	// This line splits the input string for each "~" and stores them in the array of strings.
        
        try
        {
        	mediaItemObject.title = stringArray[0];
        	mediaItemObject.format = stringArray[1];
        	mediaItemObject.onLoan = Boolean.parseBoolean(stringArray[2]);
        	mediaItemObject.loanedTo = stringArray[3];
        	mediaItemObject.dateLoaned = stringArray[4];
        }
        
        catch (IndexOutOfBoundsException ex)
        {
        	LibraryGUI.displayError(ex, " The formatting for the text field is corrupted!");
        }
        
        return mediaItemObject;
	}
	
	// This makes a string format of the media items.
	// %1$s means title, %2$s means format this is the syntax
	// for using the format method from the String class.
	// Essentially what's happening is the title, format, loanedTo, and dateLoaned,
	// are being reformatted so they look pretty in the GUI without the booleans and nulls.
	@Override
	public String toString() 	
	{
		//if onLoan is already false the it's loaned to whoever the user specifies and when.
		// Other wise if onLoan is true for example then I just reformat the title and string.
		// \t makes tabs bettwen everything in the list view.
		if(onLoan)
        {
            return String.format("%1$s \t(%2$s) \t[Loaned to %3$s on %4$s]", title, format, loanedTo, dateLoaned);
        }
		else
	    {
			return String.format("%1$s \t(%2$s)", title, format);
	    }
	}
	
	public String toFileString()
    {
        return title + "~" + format + "~" + onLoan + "~" + loanedTo + "~" + dateLoaned;
    }        
}

