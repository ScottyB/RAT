package swin.rat.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class TaskFactory 
{
	public List<TaskTemplate> tasks;
	private XStream extreme;
	
	public TaskFactory()
	{
		tasks = new ArrayList<TaskTemplate>();
		extreme = new XStream( new DomDriver());
		extreme.alias("task", TaskTemplate.class);
		extreme.alias("frame", String.class);
		extreme.alias("bodyZone", String.class);
        extreme.alias("frameTime", Integer.class);
	}
	
	@SuppressWarnings("unused")
	public void loadTasks( InputStreamReader in) throws IOException, ClassNotFoundException
	{
		ObjectInputStream oIn = extreme.createObjectInputStream(in);; 
		try{
			
			for(;;)
				tasks.add((TaskTemplate)oIn.readObject());
			
		}
		catch ( EOFException e)
		{
			// When end of file
			oIn.close();
		}
		
		
	}
	
	static public PrescribedTask getPrescribedTask( TaskTemplate aTask, Integer reps, Integer freq )
	{
		PrescribedTask temp = new PrescribedTask();
		temp.frameTimes = aTask.frameTimes;
		temp.freq = freq;
		temp.repetition = reps;
		temp.holdTime = aTask.holdTime;
		temp.longName = aTask.longName;
		temp.shortName = aTask.longName;
		temp.text = aTask.text;
		temp.bodyZones = aTask.bodyZones;
		temp.frames = aTask.frames;
		temp.videoStream = aTask.videoStream;
		temp.icon = aTask.icon;
		temp.primaryImage = aTask.primaryImage;
		temp.feedback = new Feedback();
		return temp;
		
	}
}
