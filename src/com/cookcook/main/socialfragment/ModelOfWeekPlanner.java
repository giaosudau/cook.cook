package com.cookcook.main.socialfragment;

public class ModelOfWeekPlanner {
	private String text;
	private int   week_id;
	
	public ModelOfWeekPlanner(String name, Integer pos)
	{
		text=name;
		week_id = pos;
	}
	public String getName()
	{
		return text;
	}
	public int getSelected()
	{
		return week_id;
	}

}
