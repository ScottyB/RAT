package swin.rat.model;

// Feedback provided by the patient on a prescribed activity
public class Feedback 
{
	public boolean wasFun;
	public Integer reps;
	public Integer freq;
	
	public Feedback()
	{
		reps = null;
		freq = null;
	}
	
	public Feedback( boolean isFun, Integer reps, Integer freq )
	{
		wasFun = isFun;
		this.reps = reps;
		this.freq = freq;
	}
	
	public boolean hasData()
	{
		if( reps == null || freq == null)
			return false;
		else
			return true;
	}
	
}
