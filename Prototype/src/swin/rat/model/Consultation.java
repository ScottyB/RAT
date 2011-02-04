package swin.rat.model;

import java.util.ArrayList;
import java.util.Calendar;

public class Consultation 
{
	private Calendar date;
	public ArrayList<PrescribedTask> tasks;
	private String notes;
	public ArrayList<BodyPoint> bodyPoints;
	
	
	
	public Consultation( Calendar date, ArrayList<BodyPoint> bodyPoints, String notes)
	{
		this.date = date;
		this.tasks = new ArrayList<PrescribedTask>();
		this.bodyPoints = new ArrayList<BodyPoint>();
		this.bodyPoints = bodyPoints;
		this.notes = notes;
	}
	
	@Override
	public String toString()
	{
		String temp;
		if( tasks.size() > 1)
		{
			temp = "Exercises";
		}
		else
		{
			temp = "Exercise";
				
		}
		String[] monthName = {"January", "February",
				            "March", "April", "May", "June", "July",
				            "August", "September", "October", "November",
				            "December"};
		return date.get(Calendar.DAY_OF_MONTH) +"-" + monthName[date.get(Calendar.MONTH)] +"-" +
			date.get(Calendar.YEAR) +", "+ tasks.size()+ " "+ temp;
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
	
	public void clearPoints()
	{
		bodyPoints.clear();
	}
	
	public ArrayList<BodyPoint> getPoints()
	{
		return bodyPoints;
	}
	
	/**
	 * Returns -1 if task cannont be found.
	 * @param aTask
	 * @return
	 */
	public int getTaskLocation( PrescribedTask aTask)
	{
		for(int i=0; i<tasks.size(); i++)
		{
			if( aTask.shortName.equals(tasks.get(i).shortName))
			{
				return i;
			}
				
		}
		return -1;
	}
}
