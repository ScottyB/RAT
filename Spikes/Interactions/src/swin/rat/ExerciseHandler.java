package swin.rat;

import java.net.URI;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.net.Uri;
import android.util.Log;

public class ExerciseHandler extends DefaultHandler
{
	/*
	 *  These Constants must match the values in the XML file 
	 */
	static final private String NAME_SHORT = "Short";
	static final private String NAME_LONG = "Long";
	static final private String BODYZONE = "BodyZone";
	static final private String ICON = "Icon";
	static final private String ICON2 = "PrimaryImage";
	static final private String FRAME = "Frame";
	static final private String VIDEO = "Videostream";
	static final private String TEXT = "text";
	static final private String REPS = "Reps";
	static final private String HOLD = "HoldTime";
	static final private String EXERCISE = "Exercise";
	
	// Attributes
	static final private String TIME = "time";
	
	private ArrayList<Exercise> exercises;
	private Exercise currentExercise;
	private StringBuilder builder;
	private Integer tempFrametime;
	private boolean isEmpty;
	
	public ArrayList<Exercise> getExercises()
	{
		return exercises;
	}
	
	@Override
	public void startDocument() throws SAXException
	{
		super.startDocument();
		exercises = new ArrayList<Exercise>();
		builder = new StringBuilder();
		
	}
		
	@Override
	public void characters( char[] ch, int start, int length) throws SAXException 
	{
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	
    }
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equalsIgnoreCase(EXERCISE))
        {
        	this.currentExercise = new Exercise();
        }
        if (localName.equalsIgnoreCase(FRAME))
        {
        	tempFrametime = Integer.parseInt(attributes.getValue(TIME));
        }
    }
	
	
	// Called at </tag>
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException 
	{
		
		 super.endElement(uri, localName, name);
	        if (this.currentExercise != null)
	        {
	            if(localName.equalsIgnoreCase(NAME_SHORT))
	            {
	            	currentExercise.setShortName(builder.toString().trim());
	            	
	            } 
	            else if(localName.equalsIgnoreCase(NAME_LONG))
	            {
	            	currentExercise.setLongName(builder.toString().trim());
	            }
	            else if (localName.equalsIgnoreCase(BODYZONE))
	            {
	            	currentExercise.addBodyZone(builder.toString().trim());
	            } 
	            else if (localName.equalsIgnoreCase(ICON))
	            {
	            	String temp = builder.toString().trim();
	            	currentExercise.setIcon(Uri.parse(temp));
	            	
	            }
	            else if (localName.equalsIgnoreCase(ICON2))
	            {
	            	String temp = builder.toString().trim();
	            	currentExercise.setPrimaryImage(Uri.parse(temp));
	            } 
	            else if ( localName.equalsIgnoreCase(FRAME))
	            {
	            	String temp = builder.toString().trim();
	            	currentExercise.addFrame(Uri.parse(temp), tempFrametime );
	            	
	            }
	            else if (localName.equalsIgnoreCase(VIDEO))
	            {
	            	String temp = builder.toString().trim();
	            	currentExercise.setVideoStream(URI.create(temp));
	            	
	            }
	            else if (localName.equalsIgnoreCase(TEXT))
	            {
	            	currentExercise.setText(builder.toString().trim());
	            	
	            }
	            else if (localName.equalsIgnoreCase(REPS))
	            {
	            	
	            	currentExercise.setRepetition(Integer.parseInt(builder.toString().trim()));
	                  	
	            	
	            }
	            else if (localName.equalsIgnoreCase(HOLD))
	            {
	            	currentExercise.setHoldTime(Double.parseDouble(builder.toString().trim()));
	            }
	            else if ( localName.equalsIgnoreCase(EXERCISE))
	            {
	            	exercises.add(currentExercise);
	            	//name tag only
	            }
	            builder.setLength(0);    
	        }

		
	}


}
