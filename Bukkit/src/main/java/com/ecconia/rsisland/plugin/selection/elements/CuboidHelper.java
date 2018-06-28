package com.ecconia.rsisland.plugin.selection.elements;

import com.ecconia.rsisland.framework.commonelements.Point;

public class CuboidHelper
{
	//Min point
	private Number xs;
	private Number ys;
	private Number zs;
	//Max point
	private Number xb;
	private Number yb;
	private Number zb;
	
	//First point
	private Number x1;
	private Number y1;
	private Number z1;
	//Second point
	private Number x2;
	private Number y2;
	private Number z2;
	
	public void resetBoth(Point p1, Point p2)
	{
		x1 = new Number(p1.getX());
		y1 = new Number(p1.getY());
		z1 = new Number(p1.getZ());
		
		x2 = new Number(p2.getX());
		y2 = new Number(p2.getY());
		z2 = new Number(p2.getZ());
		
		setMinMax();
	}
	
	public void resetFirst(Point p1)
	{
		x1 = new Number(p1.getX());
		y1 = new Number(p1.getY());
		z1 = new Number(p1.getZ());
		
		setMinMax();
	}
	
	public void resetSecond(Point p2)
	{
		x2 = new Number(p2.getX());
		y2 = new Number(p2.getY());
		z2 = new Number(p2.getZ());
		
		setMinMax();
	}

	private void setMinMax()
	{
		if(x1.g() > x2.g())
		{
			xb = x1;
			xs = x2;
		}
		else
		{
			xb = x2;
			xs = x1;
		}
		
		if(y1.g() > y2.g())
		{
			yb = y1;
			ys = y2;
		}
		else
		{
			yb = y2;
			ys = y1;
		}
		
		if(z1.g() > z2.g())
		{
			zb = z1;
			zs = z2;
		}
		else
		{
			zb = z2;
			zs = z1;
		}
	}
	
	//#########################################################################
	
	public void addMax(Point vec)
	{
		xb.add(vec.getX());
		yb.add(vec.getY());
		zb.add(vec.getZ());
	}

	public void addMin(Point vec)
	{
		xs.add(vec.getX());
		ys.add(vec.getY());
		zs.add(vec.getZ());
	}
	
	public void addBoth(Point vec)
	{
		x1.add(vec.getX());
		y1.add(vec.getY());
		z1.add(vec.getZ());
		
		x2.add(vec.getX());
		y2.add(vec.getY());
		z2.add(vec.getZ());
	}
	
	//#########################################################################
	
	public Point getFirstPoint()
	{
		return new Point(x1.g(), y1.g(), z1.g());
	}
	
	public Point getSecondPoint()
	{
		return new Point(x2.g(), y2.g(), z2.g());
	}
	
	public Point getMinPoint()
	{
		return new Point(xs.g(), ys.g(), zs.g());
	}
	
	public Point getMaxPoint()
	{
		return new Point(xb.g(), yb.g(), zb.g());
	}
	
	//#########################################################################
	
	private static class Number
	{
		private int i;
		
		private Number(int i)
		{
			this.i = i;
		}
		
		private int g()
		{
			return i;
		}
		
		private void add(int i)
		{
			this.i += i;
		}
	}
}
