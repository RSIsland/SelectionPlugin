package com.ecconia.rsisland.plugin.selection;

import java.util.HashSet;
import java.util.Set;

import com.ecconia.rsisland.plugin.selection.api.Direction;
import com.ecconia.rsisland.plugin.selection.exceptions.ParseException;

public class Parsers
{
	public static Integer parseInt(String n)
	{
		try
		{
			return Integer.parseInt(n);
		}
		catch (NumberFormatException e)
		{
		}
		return null;
	}
	
	//TODO: Merge Directions
	//TODO: Proper exception
	public static Set<Direction> parseDirection(String input, Direction head) throws RuntimeException
	{
		Set<Direction> dirs = new HashSet<>();
		
		if(input.equals("a"))
		{
			for(Direction d : Direction.values())
			{
				dirs.add(d);
			}
			return dirs;
		}
		
		for(char c : input.toCharArray())
		{
			Direction dirForChar = Direction.getDirectionFromChar(c, head);
			if(dirForChar == null)
			{
				throw new ParseException("Char %v cannot be parsed to a direction.", c);
			}
			
			if(!dirs.add(dirForChar))
			{
				throw new ParseException("The direction %v for character %v is duplicated.", dirForChar, c);
			}
		}
		
		return dirs;
	}
}
