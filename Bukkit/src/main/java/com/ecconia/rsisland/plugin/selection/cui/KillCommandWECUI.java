package com.ecconia.rsisland.plugin.selection.cui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class KillCommandWECUI implements Listener
{
	@EventHandler
	public void onWECommand(PlayerCommandPreprocessEvent event)
	{
		if("/we cui".equals(event.getMessage()))
		{
			event.setCancelled(true);
		}
	}
}
