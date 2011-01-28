package swin.rat;

import java.net.URI;
import java.util.ArrayList;

import android.net.Uri;

public class Task
{
	public String shortName;
	private String longName;
	private ArrayList<String> bodyZones;
	private String icon;
	private String primaryImage;
	private ArrayList<String> frames;
	private ArrayList<Integer> frameTimes;	// ms
	private String videoStream;
	
	// Description of Activity needs to be tightened up a little bit
	public String text;
	public Integer repetition;
	public Double holdTime;
	
	public Task()
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
	
//	public void addFrame(Uri aFrame, Integer aTime ) 
//	{ 	
//		this.frames.add( aFrame );
//		this.frameTimes.add( aTime );
//	}
	
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

	
}
