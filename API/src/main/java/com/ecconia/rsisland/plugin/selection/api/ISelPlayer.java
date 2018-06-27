package com.ecconia.rsisland.plugin.selection.api;

import java.util.List;

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
	public void setCUIAreas(List<CUIArea> areas);
	
	/**
	 * 
	 * @return boolean - true if the user has a CUI which can be used.
	 */
	public boolean hasCUI();
}
