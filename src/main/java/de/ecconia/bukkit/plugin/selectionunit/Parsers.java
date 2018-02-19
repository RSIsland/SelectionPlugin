package de.ecconia.bukkit.plugin.selectionunit;

import java.util.HashSet;
import java.util.Set;

import de.ecconia.bukkit.plugin.selectionunit.exceptions.ParseException;

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
				throw new ParseException(F.e("Char %v cannot be parsed to a direction.", c));
			}
			
			if(!dirs.add(dirForChar))
			{
				throw new ParseException(F.e("The direction %v for character %v is duplicated.", dirForChar, c));
			}
		}
		
		return dirs;
	}
}
