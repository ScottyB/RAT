package swin.rat.model;

import android.graphics.Point;

/**
 * A class to track body points and perform simple operations on them
 * @author Scott
 *
 */
public class BodyPoint extends Point 
{
	private String mBodyPointName;
	
	public BodyPoint()
	{
		super();
		mBodyPointName = "";
	}
	
	public BodyPoint(String aBody, int x, int y)
	{
		super(x,y);
		mBodyPointName = aBody;
	}
	
	public String getName()
	{
		return mBodyPointName;
	}
	
	public static double distanceFrom(int aX, int aY, Point aPoint )
	{
		double x = aX;
		double y = aY;
		double deltaX = Math.pow((x-aPoint.x), 2);
		double deltaY = Math.pow((y-aPoint.y), 2);
		return truncate(Math.sqrt(deltaX+ deltaY));
	}
	
	public static double truncate(double x)
	{
	    long y=(long)(x*100);
	    return (double)y/100;
	}
	
}