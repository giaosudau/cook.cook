package com.cookcook.main.my_recipe;

public class Model_Comments {
	private String name;
	private String comment;
	private String time;
	
	public Model_Comments(String name, String comment, String time)
	{
		this.name=name;
		this.comment=comment;
		this.time=time;
	}
	public String getName()
	{
		return name;
	}
	public String getComment()
	{
		return comment;
	}
	public String getTime()
	{
		return time;
	}

}
