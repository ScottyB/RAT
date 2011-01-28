package swin.rat.model;

public class PrescribedTask extends Task 
{
	public String notes;
	public Feedback feedback;
	
	public PrescribedTask()
	{
		super();
		feedback = new Feedback();
		notes = null;
	}
	
	
	public PrescribedTask( Task aTask )
	{
		super.frameTimes = aTask.frameTimes;
		super.freq = aTask.freq;
		super.holdTime = aTask.holdTime;
		super.longName = aTask.longName;
		super.shortName = aTask.longName;
		super.text = aTask.text;
		super.bodyZones = aTask.bodyZones;
		super.frames = aTask.frames;
		super.videoStream = aTask.videoStream;
		super.icon = aTask.icon;
		super.primaryImage = aTask.primaryImage;
		feedback = new Feedback();
		notes = null;
	}
	
	/**
	 * Change parameters if Activity is updated
	 * @param reps
	 * @param holdTime
	 * @param notes
	 */
	PrescribedTask( Integer reps, Double holdTime, String notes )
	{
		super();
		super.holdTime = holdTime;
		super.repetition = reps;
		this.notes = notes;
	}
	
}
