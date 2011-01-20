package swin.rat;

import java.net.URI;
import java.util.ArrayList;

import android.net.Uri;

/**
 * A class that holds data for a Android Exercise
 * @author scott
 *
 */
public class Exercise 
{
	private String fShortName;
	private String fLongName;
	private ArrayList<String> fBodyZones;
	private Uri fIcon;
	private Uri fPrimaryImage;
	private ArrayList<Uri> fAnimation;
	private ArrayList<Integer> fFrameTime;	// ms
	private URI fVideoStream;
	private String fText;
	private Integer fRepetition;
	private Double fHoldTime;
	
	Exercise()
	{
		fBodyZones = new ArrayList<String>();
		fAnimation = new ArrayList<Uri>();
		fFrameTime = new ArrayList<Integer>();
		
	}
	
	public String getShortName() {	return fShortName;	}
	public String getLongName() { 	return fLongName;	}
	public ArrayList<String> getBodyZones() { 	return fBodyZones; 	}
	public Uri getIcon() { return fIcon; 	}
	public Uri getPrimaryImage() { 	return fPrimaryImage;	}
	public ArrayList<Uri> getAnimation() {	return fAnimation;	}
	
	public Integer getFrameTime( int position ) 
	{ 
		return (fFrameTime.size() < position)? fFrameTime.get(position): -1; 
	}
	
	public URI getVideoStream() { return fVideoStream; 	}
	public String getText() { 	return fText;	}
	public Integer getRepetition() { 	return fRepetition; 	}
	public Double getHoldTime() { 	return fHoldTime; 	}
	
	
	public void setShortName(String fShortName) { 	this.fShortName = fShortName;	}
	public void setLongName(String fLongName) { this.fLongName = fLongName; }
	public void setIcon(Uri fIcon) { this.fIcon = fIcon;	}
	public void setPrimaryImage(Uri fPrimaryImage) { this.fPrimaryImage = fPrimaryImage; }
	public void setVideoStream(URI fVideoStream) {	this.fVideoStream = fVideoStream;	}
	public void setText(String fText) {	this.fText = fText;	}
	public void setRepetition(Integer fRepetion) { this.fRepetition = fRepetion;	}
	public void setHoldTime(Double fHoldTime) {	this.fHoldTime = fHoldTime;	}
	
	public void addFrame(Uri aFrame, Integer aTime ) 
	{ 	
		this.fAnimation.add( aFrame );
		this.fFrameTime.add( aTime );
	}
	
	public void addBodyZone( String aBodyZone ) 
	{	
		this.fBodyZones.add( aBodyZone );	
	}
	
	public boolean isForBodyPart( String aBodyZone )
	{
		for( String s:fBodyZones)
		{
			if( s.equals(aBodyZone))
				return true;
		}
		return false;
	}

}
