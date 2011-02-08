package swin.rat.ui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import swin.rat.model.Patient;
import swin.rat.model.PrescribedTask;
import swin.rat.model.TaskFactory;
import swin.rat.model.TaskTemplate;
import swin.rat.ui.doctor.R;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

import com.thoughtworks.xstream.XStream;


/**
 * All central data needs to be located here. All objects of the "model" are accessible here.
 * Holds Global values.
 * 
 * @author scott
 *
 */
public class RatApplication extends Application
{
	static public final String FILENAME = "Patient";
	
	//////////////////////////// Patient Application Variables //////////////////////////////////// 
	public ArrayList< PrescribedTask > tasks;
	
	//////////////////////////// End Patient Application Variables ////////////////////////////////
	
	
	
	public Patient patient;			// Either new or found from allPatients
	
	
	public ArrayList<TaskTemplate> selectedTasks;	// Temporary assigned tasks 
	
	//
	//	The following variables are used in conjunction with the DisplayTaskActivity class. Values
	//	need to be set by the class that starts DisplayTaskActivity.
	//
	//	task, the first task to display
	//	bodyPartTasks, all tasks that should be looped through and displayed
	//
	public TaskTemplate task;				// To pass a selected task between activities
	public ArrayList<TaskTemplate> bodyPartTasks; 		// All body part tasks associated with task's body part
	public PrescribedTask prescribedTask;
	
	
	
	// Data that should be stored elsewhere
	public ArrayList<Patient> allPatients;
	
	
	public TaskFactory taskFactory;
	
	private XStream extreme; 			// Used to convert xml to POJO and back again
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		task = new TaskTemplate();
		bodyPartTasks = new ArrayList<TaskTemplate>();
		selectedTasks = new ArrayList<TaskTemplate>();
		prescribedTask = new PrescribedTask();
		//allTasks = new ArrayList<TaskTemplate>();
		taskFactory = new TaskFactory();
		try {
			taskFactory.loadTasks(new InputStreamReader(getResources().openRawResource(R.raw.tasks)));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		allPatients = new ArrayList<Patient>(0);
		
		
		
	}
	
		
	/**
	 *  Used to remove all data associated with a patient. Does not remove them from the system.
	 */
	public void clearPatient()
	{
		patient = new Patient();
		selectedTasks.clear();
	}
	
	public void savePatient()
	{
		FileOutputStream fos;
    	try
    	{
    		fos = openFileOutput(FILENAME, Context.MODE_APPEND);
    		fos.write(patient.toXmlString().getBytes());
    		fos.close();
    	}
    	catch( FileNotFoundException e)
    	{
    		Log.e("tag", "File Not Found");
    	}
    	catch( IOException e)
    	{
    		Log.e("tag", "IOException");
    	}
		
		
	}
	
	public Patient findPatient( String aName )
	{
		for( int i=0; i<allPatients.size(); i++ )
		{
			if( allPatients.get(i).name.equals(aName))
				return allPatients.get(i);
		}
		return null;
	}
	
	
	
}
