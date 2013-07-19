package com.cookcook.main.socialfragment;

public class Model {
	private String text;
	private boolean selected;
	
	public Model(String name)
	{
		text=name;
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
