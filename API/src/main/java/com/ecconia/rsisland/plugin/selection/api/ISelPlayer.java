package com.ecconia.rsisland.plugin.selection.api;

import java.util.List;

import org.bukkit.plugin.Plugin;

import com.ecconia.rsisland.plugin.selection.api.cui.CUICuboidConstruct;

public interface ISelPlayer
{
	/**
	 * Get the selection with name, else get the current selection.
	 * 
	 * @param name - selection name which will be returned if existing
	 * @return ISelection - selection with name else current selection, if both not set null
	 */
	public ISelection getSelectionOrCurrent(String name);
	
	/**
	 * Attempts to clear all visible CUI areas of a player and replace them with the list provided.
	 */
	public void setCUIAreas(Plugin plugin, List<CUICuboidConstruct> areas);
	
	/**
	 * @return boolean - Returns true if the player has visible elements form plugin.
	 */
	public boolean hasCuboidConstructs(Plugin plugin);
	
	/**
	 * 
	 * @return boolean - true if the user has a CUI which can be used.
	 */
	public boolean hasCUI();
}
