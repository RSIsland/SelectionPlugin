package com.ecconia.rsisland.plugin.selection.api.cui;

public class CUIColor
{
	private final String bounds;
	private final String grid;
	private final String point1;
	private final String point2;
	
	public static CUIColor validateColor(String bounds, String grid, String point1, String point2)
	{
		if( !bounds.matches("#[0-9a-fA-F]{6,8}") ||
			!grid.matches("#[0-9a-fA-F]{6,8}") ||
			!point1.matches("#[0-9a-fA-F]{6,8}") ||
			!point2.matches("#[0-9a-fA-F]{6,8}") )
		{
			return null;
		}
		
		return new CUIColor(bounds, grid, point1, point2);
	}
	
	public CUIColor(String bounds, String grid, String point1, String point2)
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
