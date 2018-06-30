package com.ecconia.rsisland.plugin.selection.api.cui;

import java.util.List;

import com.ecconia.rsisland.framework.commonelements.Cuboid;

public class CUICuboidConstruct
{
	private final List<Cuboid> cubiods; 
	
	private CUIColor color;
	
	public CUICuboidConstruct(List<Cuboid> rooms)
	{
		this.cubiods = rooms;
	}
	
	public void setColor(CUIColor color)
	{
		this.color = color;
	}
	
	public CUIColor getColor()
	{
		return color;
	}
	
	public List<Cuboid> getCuboids()
	{
		return cubiods;
	}
}
