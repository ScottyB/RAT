package swin.rat;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.net.Uri;
import android.util.Log;

public class PeopleHandler extends DefaultHandler 
{
	static final String	NAME = "name";
    static final String FIRST_NAME = "firstname";
    static final String SURNAME = "surname";
    static final String IMAGE = "image";
    static final String AGE = "age";
	
	
	private ArrayList<Person> people;
	private Person currentPerson;
	private StringBuilder builder;
	
	// Getter
	public ArrayList<Person> getPeople()
	{
		return this.people;
	}
	
	// Event handlers 
	
	@Override
	public void startDocument() throws SAXException
	{
		super.startDocument();
		people = new ArrayList<Person>();
		builder = new StringBuilder();
	}
		
	@Override
	public void characters( char[] ch, int start, int length) throws SAXException 
	{
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equalsIgnoreCase("person"))
        {
        	
        	this.currentPerson = new Person();
        }
     
    }
	
	
	// Called at </tag>
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException 
	{
		 super.endElement(uri, localName, name);
	        if (this.currentPerson != null)
	        {
	            if(localName.equalsIgnoreCase("surname"))
	            {
	            	currentPerson.setfSurname(builder.toString().trim());
	            } 
	            else if(localName.equalsIgnoreCase("firstname"))
	            {
	            	currentPerson.setfFirstName(builder.toString().trim());
	            }
	            else if (localName.equalsIgnoreCase("age"))
	            {
	            	currentPerson.setfAge(Integer.parseInt(builder.toString().trim()));
	            } 
	            else if (localName.equalsIgnoreCase("image"))
	            {
	            	String temp = builder.toString().trim();
	            	Log.e("tag", temp);
	            	
	            	currentPerson.setFsrcImage(Uri.parse(temp));
	            } 
	            else if ( localName.equalsIgnoreCase("person"))
	            {
	            	people.add(currentPerson);
	            	//name tag only
	            }
	            builder.setLength(0);    
	        }

		
	}

}
