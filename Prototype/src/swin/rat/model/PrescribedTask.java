package swin.rat.model;

public class PrescribedTask extends TaskTemplate 
{
	
	public Feedback feedback;
	public boolean isCompleted;
	
	public PrescribedTask()
	{
		super();
		feedback = new Feedback();
		isCompleted = false;
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
		feedback = new Feedback();
		isCompleted = false;
	}
	
}
