package de.ecconia.bukkit.plugin.selectionunit.elements;

import java.util.Set;

import org.bukkit.Location;

import de.ecconia.bukkit.plugin.selectionunit.Direction;
import de.ecconia.bukkit.plugin.selectionunit.Point;

public class Selection
{
	private String name;
	
	private Location pos1;
	private Location pos2;
	
	private ModificationWrapper wrapper;
	
	public Selection(String name)
	{
		this.name = name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return boolean - False if the other position also got changed.
	 */
	public boolean setPosFirst(Location location)
	{
		boolean noChangesOther = true;
		pos1 = location.clone();
		
		if(pos2 == null || (pos2 != null && !pos2.getWorld().equals(pos1.getWorld())))
		{
			pos2 = pos1.clone();
			noChangesOther = false;
		}
		
		refreshMinMax();
		return noChangesOther;
	}

	/**
	 * @return boolean - False if the other position also got changed.
	 */
	public boolean setPosSecond(Location location)
	{
		boolean noChangesOther = true;
		pos2 = location.clone();
		
		if(pos1 == null || (pos1 != null && !pos1.getWorld().equals(pos2.getWorld())))
		{
			pos1 = pos2.clone();
			noChangesOther = false;
		}
		
		refreshMinMax();
		return noChangesOther;
	}
	
	//#########################################################################
	
	public boolean isIncomplete()
	{
		return pos1 == null || pos2 == null;
	}
	
	public boolean isNotSet()
	{
		return pos1 == null && pos2 == null;
	}

	public String getName()
	{
		return name;
	}

	public Location getPosFirst()
	{
		return pos1.clone();
	}
	
	public Location getPosSecond()
	{
		return pos2.clone();
	}
	
	public Location getMin()
	{
		return wrapper.getMin();
	}
	
	public Location getMax()
	{
		return wrapper.getMax();
	}
	
	//#########################################################################
	
	private void refreshMinMax()
	{
		wrapper = new ModificationWrapper(pos1, pos2);
	}
	
	public void expand(Direction dir, int amount)
	{
		Point vec = dir.getDirectionVector().mul(amount);
		
		if(dir.isPositive())
		{
			wrapper.addMax(vec);
		}
		else
		{
			wrapper.addMin(vec);
		}
		refreshMinMax();
	}

	//TODO dirset, to fuse directions.
	public void expand(Set<Direction> dirSet, int amount)
	{
		for(Direction d : dirSet)
		{
			expand(d, amount);
		}
	}

	public void shrink(Direction dir, int i)
	{
		Point vec = dir.getDirectionVector().mul(-i);
		
		if(dir.isPositive())
		{
			wrapper.addMax(vec);
		}
		else
		{
			wrapper.addMin(vec);
		}
		refreshMinMax();
	}

	//TODO: fix bug, when shrinking a 2*2 when minimum, that points are swapped.
	//TODO dirset, to fuse directions.
	public void shrink(Set<Direction> dirSet, int amount)
	{
		for(Direction d : dirSet)
		{
			shrink(d, amount);
		}
	}
	
	public void move(Set<Direction> dirs, Integer amount)
	{
		Point vec = new Point(0, 0, 0);
		
		for(Direction d : dirs)
		{
			vec = vec.add(d.getDirectionVector());
		}
		
		wrapper.addBoth(vec.mul(amount));
		refreshMinMax();
	}
	
	private class ModificationWrapper
	{
		private final Location p1;
		private final Location p2;
		
		private final boolean x1;
		private final boolean y1;
		private final boolean z1;
		
		public ModificationWrapper(Location p1, Location p2)
		{
			this.p1 = p1;
			this.p2 = p2;
			
			x1 = p1.getBlockX() > p2.getBlockX();
			y1 = p1.getBlockY() > p2.getBlockY();
			z1 = p1.getBlockZ() > p2.getBlockZ();
		}
		
		private int getBigX()
		{
			return x1 ? p1.getBlockX() : p2.getBlockX();
		}
		
		private int getBigY()
		{
			return y1 ? p1.getBlockY() : p2.getBlockY();
		}
		
		private int getBigZ()
		{
			return z1 ? p1.getBlockZ() : p2.getBlockZ();
		}
		
		private int getMinX()
		{
			return x1 ? p2.getBlockX() : p1.getBlockX();
		}
		
		private int getMinY()
		{
			return y1 ? p2.getBlockY() : p1.getBlockY();
		}
		
		private int getMinZ()
		{
			return z1 ? p2.getBlockZ() : p1.getBlockZ();
		}
		
		private void setBigX(int n)
		{
			if(x1) p1.setX(n);
			else   p2.setX(n);
		}
		
		private void setBigY(int n)
		{
			if(y1) p1.setY(n);
			else   p2.setY(n);
		}
		
		private void setBigZ(int n)
		{
			if(z1) p1.setZ(n);
			else   p2.setZ(n);
		}
		
		private void setMinX(int n)
		{
			if(x1) p2.setX(n);
			else   p1.setX(n);
		}
		
		private void setMinY(int n)
		{
			if(y1) p2.setY(n);
			else   p1.setY(n);
		}
		
		private void setMinZ(int n)
		{
			if(z1) p2.setZ(n);
			else   p1.setZ(n);
		}
		
		public void addMax(Point vec)
		{
			setBigX(getBigX() + vec.getX());
			setBigY(getBigY() + vec.getY());
			setBigZ(getBigZ() + vec.getZ());
		}

		public void addMin(Point vec)
		{
			setMinX(getMinX() + vec.getX());
			setMinY(getMinY() + vec.getY());
			setMinZ(getMinZ() + vec.getZ());
		}
		
		public void addBoth(Point vec)
		{
			p2.setX(p2.getBlockX() + vec.getX());
			p2.setY(p2.getBlockY() + vec.getY());
			p2.setZ(p2.getBlockZ() + vec.getZ());
			
			p1.setX(p1.getBlockX() + vec.getX());
			p1.setY(p1.getBlockY() + vec.getY());
			p1.setZ(p1.getBlockZ() + vec.getZ());
		}
		
		public Location getMin()
		{
			return new Location(p1.getWorld(), getMinX(), getMinY(), getMinZ());
		}
		
		public Location getMax()
		{
			return new Location(p1.getWorld(), getBigX(), getBigY(), getBigZ());
		}
	}
}
