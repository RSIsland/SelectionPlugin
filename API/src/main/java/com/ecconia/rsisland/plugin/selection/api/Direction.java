package com.ecconia.rsisland.plugin.selection.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;

public enum Direction
{
	NORTH	('n', 0, 0,-1),
	EAST	('e', 1, 0, 0),
	SOUTH	('s', 0, 0, 1),
	WEST	('w',-1, 0, 0),
	UP		('u', 0, 1, 0),
	DOWN	('d', 0,-1, 0),
	;
	
	private static Map<Character, Direction> directionsByChar = new HashMap<>(); 
	
	private char directionChar;
	private Point directionVector;
	private boolean positive;
	
	private Direction(char directionChar, int x, int y, int z)
	{
		this.directionChar = directionChar;
		directionVector = new Point(x, y, z);
		positive = !(x < 0 || y < 0 || z < 0);
	}
	
	public Point getDirectionVector()
	{
		return directionVector;
	}
	
	public char getDirectionChar()
	{
		return directionChar;
	}
	
	public boolean isPositive()
	{
		return positive;
	}
	
	static
	{
		for(Direction d : values())
		{
			directionsByChar.put(d.getDirectionChar(), d);
		}
    }
	
	//TODO left right forward backward support
	private static Direction getDirectionByChar(char directionChar)
	{
		return directionsByChar.get(directionChar);
	}
	
	public static Direction getDirectionFromHead(Location location)
	{
		float pitch = location.getPitch();
		if(pitch > 70) //Original 45
		{
			return Direction.DOWN;
		}
		else if(pitch < -70) //Original -45
		{
			return Direction.UP;
		}
		
		float yaw = location.getYaw();
		if(yaw < 0)
		{
			yaw += 360;
		}
		
		if(yaw < 45)
		{
			return Direction.SOUTH;
		}
		else if(yaw < 135)
		{
			return Direction.WEST;
		}
		else if(yaw < 225)
		{
			return Direction.NORTH;
		}
		else if(yaw < 315)
		{
			return Direction.EAST;
		}
		
		return Direction.SOUTH;
	}

	public static Direction getDirectionFromChar(char c, Direction head)
	{
		Direction dir = getDirectionByChar(c);
		if(dir != null)
		{
			return dir;
		}
		switch(c)
		{
		case 'l':
			return getLeftDirection(head);
		case 'r':
			return getRightDirection(head);
		case 'b':
			return getOppositeDirection(head);
		case 'f':
			return head;
		}
		return null;
	}
	
	private static Direction getLeftDirection(Direction dir)
	{
		switch (dir)
		{
		case NORTH:
			return Direction.WEST;
		case EAST:
			return Direction.NORTH;
		case SOUTH:
			return Direction.EAST;
		case WEST:
			return Direction.SOUTH;
		default:
			return null;
		}
	}
	
	public static Direction getOppositeDirection(Direction dir)
	{
		switch (dir)
		{
		case NORTH:
			return Direction.SOUTH;
		case EAST:
			return Direction.WEST;
		case SOUTH:
			return Direction.NORTH;
		case WEST:
			return Direction.EAST;
		case UP:
			return Direction.DOWN;
		case DOWN:
			return Direction.UP;
		}
		return null;
	}
	
	private static Direction getRightDirection(Direction dir)
	{
		switch (dir)
		{
		case NORTH:
			return Direction.EAST;
		case EAST:
			return Direction.SOUTH;
		case SOUTH:
			return Direction.WEST;
		case WEST:
			return Direction.NORTH;
		default:
			return null;
		}
	}

	public static Set<Direction> invertDirections(Set<Direction> dirs)
	{
		Set<Direction> ret = new HashSet<>();
		for(Direction dir : dirs)
		{
			ret.add(getOppositeDirection(dir));
		}
		return ret;
	}
}
