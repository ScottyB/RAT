package swin.rat;

import java.util.ArrayList;

public class Animal 
{
	private String name;
	public Integer numberOfLegs;
	public boolean canFly;
	private boolean onFarm;
	ArrayList<Integer> types;
	
	Animal()
	{
		name = "";
		numberOfLegs = null;
		canFly = false;
		onFarm = false;
		ArrayList<Integer> types = new ArrayList<Integer>();
	}
	
	Animal(String aName, Integer numOfLegs, boolean flyable, boolean onFarm)
	{
		name = aName;
		numberOfLegs = new Integer(numOfLegs);
		canFly = flyable;
		this.onFarm = onFarm;
		
		
	}

	
}
