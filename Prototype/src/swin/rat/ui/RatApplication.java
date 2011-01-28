package swin.rat.ui;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import swin.rat.model.Patient;
import swin.rat.model.PrescribedTask;
import swin.rat.model.Task;
import swin.rat.ui.doctor.R;
import android.app.Application;
import android.net.Uri;
import android.util.Log;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


/**
 * All central data needs to be located here. All objects of the "model" are accessible here.
 * Holds Global values.
 * 
 * @author scott
 *
 */
public class RatApplication extends Application
{
	//////////////////////////// Patient Application Variables //////////////////////////////////// 
	public ArrayList< PrescribedTask > tasks;
	
	//////////////////////////// End Patient Application Variables ////////////////////////////////
	
	
	
	public Patient patient;			// Either new or found from allPatients
	
	public ArrayList<String> bodyPartNames; 	// Names of the temporarily selected body parts
	public ArrayList<Task> selectedTasks;	// Temporary assigned tasks 
	
	//
	//	The following variables are used in conjunction with the DisplayTaskActivity class. Values
	//	need to be set by the class that starts DisplayTaskActivity.
	//
	//	task, the first task to display
	//	bodyPartTasks, all tasks that should be looped through and displayed
	//
	public Task task;				// To pass a selected task between activities
	public ArrayList<Task> bodyPartTasks; 		// All body part tasks associated with task's body part
	
	
	
	// Data that should be stored elsewhere
	private ArrayList<Patient> allPatients;
	public ArrayList<Task> allTasks;
	
	private XStream extreme; 			// Used to convert xml to POJO and back again
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		task = new Task();
		bodyPartTasks = new ArrayList<Task>();
		selectedTasks = new ArrayList<Task>();
		allTasks = new ArrayList<Task>();
		allPatients = new ArrayList<Patient>();
		patient = new Patient();
		extreme = new XStream( new DomDriver());
		bodyPartNames = new ArrayList<String>();
		
		extreme.alias("task", Task.class);
		extreme.alias("frame", String.class);
		extreme.alias("bodyZone", String.class);
        extreme.alias("frameTime", Integer.class);
		
        loadTasks();
		
	}
	
	// Loads all the tasks from file and stores them
	private void loadTasks()
	{
		ObjectInputStream oIn = null; 
		InputStreamReader in = new InputStreamReader(getResources().openRawResource(R.raw.tasks));
		
		try 
		{
			oIn = extreme.createObjectInputStream(in);
					
			for(;;)
				allTasks.add((Task)oIn.readObject());
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch ( EOFException e)
		{
			// When end of file
			try 
			{
				oIn.close();
			} catch (IOException e1) 
			{
				
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	

	
	
	
}
