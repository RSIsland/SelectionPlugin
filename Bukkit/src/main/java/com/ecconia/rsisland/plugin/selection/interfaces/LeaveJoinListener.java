package com.ecconia.rsisland.plugin.selection.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ecconia.rsisland.plugin.selection.SelectionPlugin;

public class LeaveJoinListener implements Listener
{
	private final SelectionPlugin plugin;
	
	public LeaveJoinListener(SelectionPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event)
	{
		plugin.updatePlayer(event.getPlayer().getUniqueId(), null);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		plugin.updatePlayer(player.getUniqueId(), player);
	}
}
