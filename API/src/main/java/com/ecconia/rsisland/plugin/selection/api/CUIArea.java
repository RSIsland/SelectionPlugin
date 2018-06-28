package com.ecconia.rsisland.plugin.selection.api;

import java.util.List;

import com.ecconia.rsisland.framework.commonelements.Cuboid;

public class CUIArea
{
	private final List<Cuboid> cubiods; 
	
	private Color color;
	
	public CUIArea(List<Cuboid> rooms)
	{
		this.cubiods = rooms;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public List<Cuboid> getCuboids()
	{
		return cubiods;
	}
	
	//TODO: Outsource
	public static class Color
	{
		private final String bounds;
		private final String grid;
		private final String point1;
		private final String point2;
		
		public static Color validateColor(String bounds, String grid, String point1, String point2)
		{
			if( !bounds.matches("#[0-9a-fA-F]{6,8}") ||
				!grid.matches("#[0-9a-fA-F]{6,8}") ||
				!point1.matches("#[0-9a-fA-F]{6,8}") ||
				!point2.matches("#[0-9a-fA-F]{6,8}") )
			{
				throw new MalformedColorException();
			}
			
			return new Color(bounds, grid, point1, point2);
		}
		
		public Color(String bounds, String grid, String point1, String point2)
		{
			this.bounds = bounds;
			this.grid = grid;
			this.point1 = point1;
			this.point2 = point2;
		}
		
		public String getBounds()
		{
			return bounds;
		}
		
		public String getGrid()
		{
			return grid;
		}
		
		public String getPoint1()
		{
			return point1;
		}
		
		public String getPoint2()
		{
			return point2;
		}
	}
	
	@SuppressWarnings("serial")
	public static class MalformedColorException extends RuntimeException
	{
	}
}
