package swin.rat.model;

import java.util.ArrayList;
import java.util.Date;

public class Consultation 
{
	private Date date;
	public ArrayList<PrescribedTask> tasks;
	private String notes;
	private ArrayList<BodyPoint> bodyPoints;
	
	
	
	public Consultation( Date date, ArrayList<BodyPoint> bodyPoints, String notes)
	{
		this.date = date;
		this.tasks = new ArrayList<PrescribedTask>();
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
	
	public boolean addBodyPoint( BodyPoint point )
	{
		
		for( int i=0; i<bodyPoints.size();i++)
		{
			if( bodyPoints.get(i).bodyPointName == point.bodyPointName)
				return false;
		}
		if( bodyPoints.contains(point))
		{
			return false;
		}
		else
		{
			bodyPoints.add(point);
			return true;
		}
	}
	
	public ArrayList<BodyPoint> getPoints()
	{
		return bodyPoints;
	}
}
