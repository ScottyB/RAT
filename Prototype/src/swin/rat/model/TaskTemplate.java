package swin.rat.model;

import java.util.ArrayList;

import android.net.Uri;

public class TaskTemplate
{
	public String shortName;
	public String longName;
	protected ArrayList<String> bodyZones;
	protected String icon;				// Uri
	protected String primaryImage;	// Uri
	protected ArrayList<String> frames;
	public ArrayList<Integer> frameTimes;	// ms
	protected String videoStream;
	
	// Description of Activity needs to be tightened up a little bit
	public String text;
	public Integer repetition;
	public Double holdTime;
	public Integer freq;
	
	public TaskTemplate()
	{
		bodyZones = new ArrayList<String>();
		frames = new ArrayList<String>();
		frameTimes = new ArrayList<Integer>();
		shortName = null;
		longName = null;
		icon = null;
		primaryImage = null;
		videoStream = null;
		
	}
	
	public Integer getFrameTime( int position ) 
	{ 
		return (frameTimes.size() < position)? frameTimes.get(position): -1; 
	}
	
	public Uri getIcon()
	{
		return Uri.parse(icon);
	}
	
	public Uri getPrimaryImage()
	{
		return Uri.parse(primaryImage);
	}
	
	public void addFrame(Uri aFrame, Integer aTime ) 
	{ 	
		this.frames.add( aFrame.toString() );
		this.frameTimes.add( aTime );
	}
	
	public void addBodyZone( String aBodyZone ) 
	{	
		this.bodyZones.add( aBodyZone );	
	}
	
	public boolean isForBodyPart( String aBodyZone )
	{
		for( String s:bodyZones)
		{
			if( s.equals(aBodyZone))
				return true;
		}
		return false;
	}
	
	public ArrayList<Uri> getFrames()
	{
		ArrayList<Uri> temp = new ArrayList<Uri>(frames.size());
		for( int i=0; i<frames.size(); i++)
		{
			temp.add(Uri.parse(frames.get(i)));
		}
		return temp;
	}
	
}
