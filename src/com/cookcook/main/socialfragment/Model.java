package com.cookcook.main.socialfragment;

public class Model {
	private String text;
	private boolean selected;
	
	public Model(String name, Integer check)
	{
		text=name;
		if (check ==1)
			selected=true;
		else
			selected=false;
	}
	public String getName()
	{
		return text;
	}
	public boolean getSelected()
	{
		return selected;
	}
	public void setSelected(boolean selected)
	{
		this.selected=selected;
	}
	public boolean isSelected()
	{
		return selected;
	}

}
