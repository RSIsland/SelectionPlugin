package com.ecconia.rsisland.plugin.selection.api;

import org.bukkit.entity.Player;

public interface SelectionAPI
{
	/**
	 * Get the player object of this plugin.
	 *  
	 * @param player - Bukkit Player object
	 * @return SelPlayer - a player object of this plugin
	 */
	public ISelPlayer getPlayer(Player player);
}
