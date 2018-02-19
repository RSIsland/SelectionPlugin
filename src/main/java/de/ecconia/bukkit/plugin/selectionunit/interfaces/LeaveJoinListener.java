package de.ecconia.bukkit.plugin.selectionunit.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.ecconia.bukkit.plugin.selectionunit.SelectionPlugin;
import de.ecconia.bukkit.plugin.selectionunit.elements.SelPlayer;

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
		Player player = event.getPlayer();
		SelPlayer sel = plugin.getPlayer(player);
		sel.updatePlayer(null);
	}
	
	@EventHandler
	public void onLeave(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		SelPlayer sel = plugin.getPlayer(player);
		sel.updatePlayer(player);
	}
}
