package com.ecconia.rsisland.plugin.selection.cui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.api.ISelection;
import com.ecconia.rsisland.plugin.selection.api.cui.CUICuboidConstruct;

public class CUICore implements Listener, ICUICore
{
	private SelectionPlugin plugin;
	
	public static final String channel = "WECUI";
	public static final int requiredVersion = 4;

	private Map<Player, CUIPlayer> cuiPlayers;
	
	public CUICore(SelectionPlugin plugin)
	{
		this.plugin = plugin;
		
		cuiPlayers = new HashMap<>();
		
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, channel);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
		//TODO: Should this plugin listen to inputs, only the version will be provided probably.
		//Expose that this server provides CUI.
		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, channel, new PluginMessageListener()
		{
			@Override
			public void onPluginMessageReceived(String channel, Player player, byte[] byteMessage)
			{
				String message = new String(byteMessage);
				
				if(message.startsWith("v|"))
				{
					try
					{
						String versionString = message.substring(2);
						int version = Integer.parseInt(versionString);
						
						getOrCreateCUIPlayer(player).setVersion(version);
						
						return;
					}
					catch (NumberFormatException e)
					{
					}
				}
				//Some unknown format:
				plugin.logger().error("Unknown CUI packet from %v: %v. Please report to the plugin author.", player.getName(), message);
			}
		});
		
		//Check if WE is on the server, else catch the "/we cui" command silently.
		if(plugin.getServer().getPluginManager().getPlugin("WorldEdit") == null)
		{
			plugin.getServer().getPluginManager().registerEvents(new KillCommandWECUI(), plugin);
		}
	}
	
	private CUIPlayer getOrCreateCUIPlayer(Player player)
	{
		CUIPlayer cuiPlayer = cuiPlayers.get(player);
		if(cuiPlayer == null)
		{
			cuiPlayer = new CUIPlayer(plugin, player);
			cuiPlayers.put(player, cuiPlayer);
		}
		return cuiPlayer;
	}
	
	@EventHandler
	public void onRegistration(PlayerRegisterChannelEvent event)
	{
		if (event.getChannel().equals(channel))
		{
			Player player = event.getPlayer();
			CUIPlayer cuiPlayer = getOrCreateCUIPlayer(player);
			cuiPlayer.setEnabled(true);
		}
	}
	
	@EventHandler
	public void onUnregistration(PlayerUnregisterChannelEvent event)
	{
		if(event.getChannel().equals(channel))
		{
			Player player = event.getPlayer();
			CUIPlayer cuiPlayer = getOrCreateCUIPlayer(player);
			cuiPlayer.setEnabled(false);
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event)
	{
		cuiPlayers.remove(event.getPlayer());
	}
	
	//#########################################################################
	
	public boolean hasCUI(Player player)
	{
		CUIPlayer cuiPlayer = cuiPlayers.get(player);
		if(cuiPlayer != null)
		{
			return cuiPlayer.isEnabled();
		}
		return false;
	}
	
	@Override
	public void createSelection(Player player, ISelection selection)
	{
		if(hasCUI(player))
		{
//			F.n(player, "CUI: Created selection %v", selection.getName());
			cuiPlayers.get(player).createSelection(selection);
		}
	}
	
	@Override
	public void destroySelection(Player player, ISelection selection)
	{
		if(hasCUI(player))
		{
//			F.n(player, "CUI: Deleted selection %v", selection.getName());
			cuiPlayers.get(player).destroySelection(selection);
		}
	}
	
	@Override
	public void updateSelection(Player player, ISelection selection)
	{
		if(hasCUI(player))
		{
//			F.n(player, "CUI: Updated selection %v", selection.getName());
			cuiPlayers.get(player).updateSelection(selection);
		}
	}
	
	@Override
	public void forceEnable(Player player)
	{
		CUIPlayer cuiPlayer = getOrCreateCUIPlayer(player);
		cuiPlayer.setEnabled(true);
	}
	
	@Override
	public boolean cuiEnabled(Player player)
	{
		return hasCUI(player);
	}
	
	@Override
	public void replaceSelections(Player player, Plugin plugin, List<CUICuboidConstruct> areas)
	{
		if(hasCUI(player))
		{
			cuiPlayers.get(player).replaceSelections(areas);
		}
	}
	
	@Override
	public boolean hasSelections(Player player, Plugin plugin)
	{
		return false;
	}
}
