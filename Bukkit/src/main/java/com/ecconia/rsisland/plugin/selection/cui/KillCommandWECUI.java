package com.ecconia.rsisland.plugin.selection.cui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class KillCommandWECUI implements Listener
{
	@EventHandler
	public void onWECommand(PlayerCommandPreprocessEvent event)
	{
		/**
		 * Maybe forward these commands to the CUI-Core
		 * /we cui - request update
		 * //sel - request deleting of we selection 
		 */
		//Cancel WE commands used by CUI - They won't be used by this plugin
		if("/we cui".equals(event.getMessage()) || "//sel".equals(event.getMessage()))
		{
			event.setCancelled(true);
		}
	}
}
