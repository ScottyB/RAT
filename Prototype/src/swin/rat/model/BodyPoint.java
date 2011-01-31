package swin.rat.model;

import java.util.ArrayList;
import java.util.Iterator;


import android.graphics.Point;

/**
 * A class to track body points and perform simple operations on them
 * @author Scott
 *
 */
public class BodyPoint extends Point 
{
	public String bodyPointName;
	
	public BodyPoint()
	{
		super();
		bodyPointName = "";
	}
	
	/**
	 * This method should be removed once application finished
	 * @param aName
	 */
	public BodyPoint(String aName)
	{
		super();
		bodyPointName = aName;
	}
	
	public BodyPoint(String aBody, int x, int y)
	{
		super(x,y);
		bodyPointName = aBody;
	}
	
	static public ArrayList<String> namesOfBodyPoints( ArrayList<BodyPoint> points)
	{
		ArrayList<String> temp = new ArrayList<String>(points.size());
		for( BodyPoint p : points)
		{
			temp.add(p.bodyPointName);
		}
		return temp;
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