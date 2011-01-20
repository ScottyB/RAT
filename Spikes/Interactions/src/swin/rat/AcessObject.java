package swin.rat;

import java.util.ArrayList;

import android.app.Application;


/**
 * Needs to be accessed in manifest file <application android:name=".MyApp"
 * @author scott
 *
 */
public class AcessObject extends Application
{
	private Exercise storedObject;	// Used to pass an exercise between DisplayExercise and SelectionActivity
	private boolean toAdd;			// Used to indicate whether or not SelectionActivity should add storedObject
	private ArrayList<Exercise> bodyPartExercises;
	
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		bodyPartExercises = new ArrayList<Exercise>();
		
	}
	
	public void setToAdd( boolean add )
	{
		toAdd = add;
	}
	
	public boolean getToAdd()
	{
		return toAdd;
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
}
