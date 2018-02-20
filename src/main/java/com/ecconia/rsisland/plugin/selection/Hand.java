package com.ecconia.rsisland.plugin.selection;

public enum Hand
{
	LEFT,
	RIGHT,
	;
	
	public boolean grows()
	{
		return this == Hand.LEFT;
	}
	
	public boolean shrinks()
	{
		return !grows();
	}
	
	public boolean selAtBlock()
	{
		return this == Hand.RIGHT;
	}
	
	public boolean selBlock()
	{
		return !grows();
	}

	public boolean isFirstPos()
	{
		return this == Hand.LEFT;
	}
	
	public boolean isSecondPos()
	{
		return !isFirstPos();
	}
}
