package com.ecconia.rsisland.plugin.selection.elements;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import com.ecconia.rsisland.framework.commonelements.Cuboid;
import com.ecconia.rsisland.framework.commonelements.Point;
import com.ecconia.rsisland.plugin.selection.api.Direction;
import com.ecconia.rsisland.plugin.selection.api.ISelection;

public class Selection implements ISelection
{
	private String name;
	private World world;
	
	private CuboidHelper h;
	
	public Selection(String name)
	{
		this.name = name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return boolean - true if the other position also got changed.
	 */
	public boolean setFirstPoint(Location location)
	{
		boolean changedBoth = h == null || world != location.getWorld();
		
		if(changedBoth)
		{
			Point p = new Point(location);
			h.resetBoth(p, p);
			world = location.getWorld();
		}
		else
		{
			h.resetFirst(new Point(location));
		}
		
		return changedBoth;
	}
	
	/**
	 * @return boolean - true if the other position also got changed.
	 */
	public boolean setSecondPoint(Location location)
	{
		//If first initialisation or world changed
		boolean changedBoth = h == null || world != location.getWorld();
		
		if(changedBoth)
		{
			Point p = new Point(location);
			h.resetBoth(p, p);
			world = location.getWorld();
		}
		else
		{
			h.resetSecond(new Point(location));
		}
		
		return changedBoth;
	}
	
	//#########################################################################
	
	public void expand(Direction dir, int amount)
	{
		if(h == null)
		{
			throw new IllegalStateException("Selection without any point to modify.");
		}
		
		Point vec = dir.getDirectionVector().mul(amount);
		
		if(dir.isPositive())
		{
			h.addMax(vec);
		}
		else
		{
			h.addMin(vec);
		}
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
		if(h == null)
		{
			throw new IllegalStateException("Selection without any point to modify.");
		}
		
		Point vec = dir.getDirectionVector().mul(-i);
		
		if(dir.isPositive())
		{
			h.addMax(vec);
		}
		else
		{
			h.addMin(vec);
		}
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
	
	public void move(Set<Direction> dirs, int amount)
	{
		if(h == null)
		{
			throw new IllegalStateException("Selection without any point to modify.");
		}
		
		Point vec = new Point(0, 0, 0);
		
		for(Direction d : dirs)
		{
			vec = vec.add(d.getDirectionVector());
		}
		
		h.addBoth(vec.mul(amount));
	}

	//#########################################################################
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public World getWorld()
	{
		return world;
	}
	
	@Override
	public Point getFirstPoint()
	{
		if(h == null)
		{
			return null;
		}
		
		return h.getFirstPoint();
	}

	@Override
	public Point getSecondPoint()
	{
		if(h == null)
		{
			return null;
		}
		
		return h.getSecondPoint();
	}
	
	@Override
	public Location getFirstLocation()
	{
		if(h == null)
		{
			return null;
		}
		
		Point p = h.getFirstPoint();
		return new Location(world, p.getX(), p.getY(), p.getZ());
	}
	
	@Override
	public Location getSecondLocation()
	{
		if(h == null)
		{
			return null;
		}
		
		Point p = h.getSecondPoint();
		return new Location(world, p.getX(), p.getY(), p.getZ());
	}

	@Override
	public Point getMinPoint()
	{
		if(h == null)
		{
			return null;
		}
		
		return h.getMinPoint();
	}
	
	@Override
	public Point getMaxPoint()
	{
		if(h == null)
		{
			return null;
		}
		
		return h.getMaxPoint();
	}
	
	@Override
	public Location getMinLocation()
	{
		if(h == null)
		{
			return null;
		}
		
		Point p = h.getMinPoint();
		return new Location(world, p.getX(), p.getY(), p.getZ());
	}
	
	@Override
	public Location getMaxLocation()
	{
		if(h == null)
		{
			return null;
		}
		
		Point p = h.getMaxPoint();
		return new Location(world, p.getX(), p.getY(), p.getZ());
	}

	@Override
	public Cuboid getCuboid()
	{
		if(h == null)
		{
			return null;
		}
		
		return new Cuboid(h.getFirstPoint(), h.getSecondPoint());
	}
	
	@Override
	public Cuboid getMinMaxCuboid()
	{
		if(h == null)
		{
			return null;
		}
		
		return new Cuboid(h.getMinPoint(), h.getMaxPoint());
	}
}
