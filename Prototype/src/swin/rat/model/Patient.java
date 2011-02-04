package swin.rat.model;

import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Patient 
{
	public String name;
	public String phone;
	public String email;
	
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
	
	public String toXmlString()
	{
		XStream stream = new XStream( new DomDriver());
		stream.alias("patient", Patient.class);
		stream.alias("consultations", Consultation.class);
		return stream.toXML(this);
	}
	
}
