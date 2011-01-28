package swin.rat.model;

import java.util.ArrayList;
import java.util.Date;

public class Consultation 
{
	private Date date;
	private ArrayList<PrescribedTask> tasks;
	private String notes;
	private ArrayList<BodyPoint> bodyPoints;
	
	Consultation( Date date, ArrayList<PrescribedTask> tasks, 
						ArrayList<BodyPoint> bodyPoints, String notes)
	{
		this.date = date;
		this.tasks = new ArrayList<PrescribedTask>();
		this.tasks = tasks;
		this.bodyPoints = new ArrayList<BodyPoint>();
		this.bodyPoints = bodyPoints;
		this.notes = notes;
	}
	
	public boolean hasActivities()
	{
		return (tasks.size() > 0)?true:false;
	}
	
	public void addActivity( PrescribedTask activity )
	{
		this.tasks.add(activity);
	}
	
}
