package com.ecconia.rsisland.plugin.selection.api;

public class Point
{
	private int x;
	private int y;
	private int z;
	
	public Point(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getZ()
	{
		return z;
	}
	
	public Point mul(int amount)
	{
		return new Point(amount*x, amount*y, amount*z);
	}
	
	@Override
	public String toString()
	{
		return "x:" + x + " y:" + y + " z:" + z;
	}

	public Point add(Point other)
	{
		return new Point(x + other.getX(), y + other.getY(), z + other.getZ());
	}
}
