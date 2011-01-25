package swin.rat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Application;


/**
 * Needs to be accessed in manifest file <application android:name=".MyApp"
 * @author scott
 *
 */
public class AcessObject extends Application
{
	private Exercise storedObject;	// Used to pass an exercise between DisplayExercise and SelectionActivity
			// Used to indicate whether or not SelectionActivity should add storedObject
	private ArrayList<Exercise> bodyPartExercises;
	private ArrayList<Exercise> selectedExercises;
	
	private ArrayList<Exercise> allExercises;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		bodyPartExercises = new ArrayList<Exercise>();
		selectedExercises = new ArrayList<Exercise>();
		allExercises = new ArrayList<Exercise>();
		loadExercises();
		
	}
	
	public void setSelectedExercises( ArrayList<Exercise> exercises )
	{
		selectedExercises = exercises;
	}
	
	public ArrayList<Exercise> getSelectedExercises()
	{
		return selectedExercises;
	}
		
	
	public void setExercise( Exercise aObject )
	{
		storedObject = aObject;
	}

	public Exercise getExercise()
	{
		return storedObject;
	}
	
	public void setBodyExercises( ArrayList<Exercise> exercises )
	{
		bodyPartExercises = exercises;
	}
	
	public ArrayList<Exercise> getBodyExercises()
	{
		return bodyPartExercises;
	}
	
	private void loadExercises()
	{
		SAXParserFactory saxf = SAXParserFactory.newInstance();
		try {
			SAXParser saxp = saxf.newSAXParser();
			XMLReader xmlr = saxp.getXMLReader();
			ExerciseHandler handler = new ExerciseHandler();
			xmlr.setContentHandler(handler);
			InputStream in = this.getResources().openRawResource(R.raw.exercises);
			InputSource inputsource = new InputSource(in);
			xmlr.parse(inputsource);
			allExercises = (ArrayList<Exercise>)handler.getExercises();
			
		} catch (ParserConfigurationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Exercise> getExercises()
	{
		return allExercises;
	}
	
	
	
}
