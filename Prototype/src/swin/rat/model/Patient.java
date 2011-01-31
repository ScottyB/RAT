package swin.rat.model;

import java.util.ArrayList;

public class Patient 
{
	private String name;
	private String phone;
	private String email;
	
	public ArrayList<Consultation> consultations;

	public Patient()
	{
		consultations = new ArrayList<Consultation>();
	}
	
	/**
	 * Constructor
	 * @param aName
	 * @param aPhone
	 * @param anEmail
	 */
	public Patient( String aName, String aPhone, String anEmail)
	{
		consultations = new ArrayList<Consultation>();
		name = aName;
		phone = aPhone;
		email = anEmail;
	}
	
	public Consultation newestConsultation()
	{
		if(consultations.size() > 0)
		{
			int last = consultations.size() -1;
			return consultations.get(last);
		}
		return null;
	}
	
}
