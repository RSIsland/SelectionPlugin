package com.ecconia.rsisland.plugin.selection.api;

import java.util.Set;

import org.bukkit.Location;

import com.ecconia.rsisland.framework.commonelements.Area;

//TODO: Descriptions
//TODO: Set booleans?
public interface ISelection
{
	/**
	 * Returns the first point, selected by the player.
	 * 
	 * @return location - the first point of the selection
	 */
	public Location getFirstPoint();

	/**
	 * Returns the second point, selected by the player.
	 * 
	 * @return location - the second point of the selection
	 */
	public Location getSecondPoint();
	
	/**
	 * Returns the min point, of the selection.
	 * The smaller x, y, z values of first and second point.
	 * 
	 * @return location - min point of the selection.
	 */
	public Location getMinPoint();
	
	/**
	 * Returns the max point, of the selection.
	 * The bigger x, y, z values of first and second point.
	 * 
	 * @return location - max point of the selection.
	 */
	public Location getMaxPoint();
	
	/**
	 * Return the Area, which provides world, min and amx postion.
	 * 
	 * @return area - the area of the selection
	 */
	Area getArea();

	//#########################################################################
	
	/**
	 * 
	 * @return string - name of the selection.
	 */
	public String getName();
		
	/**
	 * 
	 * @param point - first position of the selection.
	 */
	public boolean setFirstPoint(Location point);

	/**
	 * 
	 * @param point - second position of the selection.
	 */
	public boolean setSecondPoint(Location point);
	
	//#########################################################################
	
	/**
	 * 
	 * @return boolean - if no point is set.
	 */
	public boolean isEmpty();
	
	/**
	 * 
	 * @return boolean - if a point is not set.
	 */
	public boolean isIncomplete();
	
	//#########################################################################
	
	/**
	 * 
	 * @param dirs
	 * @param amount
	 */
	public void move(Set<Direction> dirs, int amount);
	
	/**
	 * 
	 * @param dirs
	 * @param amount
	 */
	public void expand(Set<Direction> dirs, int amount);
	
	/**
	 * 
	 * @param dirs
	 * @param amount
	 */
	public void shrink(Set<Direction> dirs, int amount);
}
