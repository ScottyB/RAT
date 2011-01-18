package swin.rat;

import java.net.URI;

import android.net.Uri;

// A boring person class for testing Plain Old Java Object (POJO)
public class Person 
{
	private String fFirstName;
	private String fSurname;
	private Integer fAge;
	private Uri fsrcImage;
	
	public Person()
	{
		fFirstName = "";
		fSurname = "";
		fAge = null;
		fsrcImage = null;
	}
	
	
	public Person(String fFirstName, String fSurname, Integer fAge,	Uri fsrcImage) 
	{
		super();
		this.fFirstName = fFirstName;
		this.fSurname = fSurname;
		this.fAge = fAge;
		this.fsrcImage = fsrcImage;
	}
	
	// Getter and Setters
	public String getfFirstName() {	return fFirstName;	}
	public void setfFirstName(String fFirstName) {	this.fFirstName = fFirstName;	}
	public String getfSurname() {	return fSurname;	}
	public void setfSurname(String fSurname) {	this.fSurname = fSurname;	}
	public Integer getfAge() {		return fAge;	}
	public void setfAge(Integer fAge) {		this.fAge = fAge;	}
	public Uri getFsrcImage() {		return fsrcImage;	}
	public void setFsrcImage(Uri fsrcImage) {		this.fsrcImage = fsrcImage;	}
}
