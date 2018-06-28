package com.ecconia.rsisland.plugin.selection.api;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import com.ecconia.rsisland.framework.commonelements.Cuboid;
import com.ecconia.rsisland.framework.commonelements.Point;

//TODO: Set booleans? <- Wat?
public interface ISelection
{
	// GETTERS ################################################################
	
	/**
	 * @return String - name of the selection.
	 */
	public String getName();
	
	/**
	 * @return World - the World this selection is located in or null if unset
	 */
	public World getWorld();
	
	/**
	 * Returns the first point, selected by the player.
	 * 
	 * @return Point - the first point of the selection
	 */
	public Point getFirstPoint();
	
	/**
	 * Returns the second point, selected by the player.
	 * 
	 * @return Point - the first point of the selection
	 */
	public Point getSecondPoint();
	
	/**
	 * Returns the first point, selected by the player.
	 * 
	 * @return Location - the first point of the selection
	 */
	public Location getFirstLocation();
	
	/**
	 * Returns the second point, selected by the player.
	 * 
	 * @return Location - the first point of the selection
	 */
	public Location getSecondLocation();
	
	/**
	 * Returns the min point, of the selection.
	 * The smaller x, y, z values of first and second point.
	 * 
	 * @return Point - min point of the selection or null if unset
	 */
	public Point getMinPoint();
	
	/**
	 * Returns the max point, of the selection.
	 * The bigger x, y, z values of first and second point.
	 * 
	 * @return Point - max point of the selection or null if unset
	 */
	public Point getMaxPoint();
	
	/**
	 * Returns the min point, of the selection.
	 * The smaller x, y, z values of first and second point.
	 * 
	 * @return Location - min point of the selection or null if unset
	 */
	public Location getMinLocation();
	
	/**
	 * Returns the max point, of the selection.
	 * The bigger x, y, z values of first and second point.
	 * 
	 * @return Location - max point of the selection or null if unset
	 */
	public Location getMaxLocation();

	/**
	 * @return Cuboid - the Cuboid of this Selection or null if unset
	 */
	public Cuboid getCuboid();
	
	/**
	 * @return Cuboid - the Cuboid of this Selection (first point = min; second point = max) or null if unset
	 */
	public Cuboid getMinMaxCuboid();
	
	// SETTERS ################################################################
	
	/**
	 * @param point - first position of the selection.
	 */
	public boolean setFirstPoint(Location point);
	
	/**
	 * @param point - second position of the selection.
	 */
	public boolean setSecondPoint(Location point);
	
	// MODIFIERS ##############################################################
	
	/**
	 * @param dirs
	 * @param amount
	 */
	public void move(Set<Direction> dirs, int amount);
	
	/**
	 * @param dirs
	 * @param amount
	 */
	public void expand(Set<Direction> dirs, int amount);
	
	/**
	 * @param dirs
	 * @param amount
	 */
	public void shrink(Set<Direction> dirs, int amount);
}
