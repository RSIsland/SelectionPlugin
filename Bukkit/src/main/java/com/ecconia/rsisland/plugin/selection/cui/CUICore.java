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

import com.ecconia.rsisland.framework.commonelements.Cuboid;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.api.cui.CUICuboidConstruct;

public class CUICore implements Listener, ICUICore
{
	public static final String channel = "WECUI";
	public static final int requiredVersion = 4;
	
	private final SelectionPlugin plugin;
	private final Map<Player, CUIPlayer> cuiPlayers = new HashMap<>();
	
	//TODO: Make this Multiworld compatible, it will break.
	public CUICore(SelectionPlugin plugin)
	{
		this.plugin = plugin;
		
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
			getOrCreateCUIPlayer(event.getPlayer()).setEnabled(true);
		}
	}
	
	@EventHandler
	public void onUnregistration(PlayerUnregisterChannelEvent event)
	{
		if(event.getChannel().equals(channel))
		{
			getOrCreateCUIPlayer(event.getPlayer()).setEnabled(false);
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event)
	{
		//When a player leaves his CUI resets, the whole object can be dumped.
		cuiPlayers.remove(event.getPlayer());
	}
	
	// Internal ###############################################################
	
	@Override
	public void forceEnable(Player player)
	{
		CUIPlayer cuiPlayer = getOrCreateCUIPlayer(player);
		cuiPlayer.setEnabled(true);
	}
	
	@Override
	public void destroySelection(Player player, Object key)
	{
		CUIPlayer cuiPlayer = cuiPlayers.get(player);
		
		if(cuiPlayer != null && cuiPlayer.isEnabled())
		{
			cuiPlayer.destroySelection(key);
		}
	}
	
	@Override
	public void updateSelection(Player player, Object key, Cuboid cuboid)
	{
		CUIPlayer cuiPlayer = cuiPlayers.get(player);
		
		if(cuiPlayer != null && cuiPlayer.isEnabled())
		{
			cuiPlayer.updateSelection(key, cuboid);
		}
	}
	
	// API-Triggered ##########################################################
	
	@Override
	public boolean cuiEnabled(Player player)
	{
		CUIPlayer cuiPlayer = cuiPlayers.get(player);
		
		return cuiPlayer != null && cuiPlayer.isEnabled();
	}
	
	@Override
	public void replaceSelections(Player player, Plugin plugin, List<CUICuboidConstruct> areas)
	{
		CUIPlayer cuiPlayer = cuiPlayers.get(player);
		
		if(cuiPlayer != null && cuiPlayer.isEnabled())
		{
			cuiPlayer.replaceSelections(plugin, areas);
		}
	}
	
	@Override
	public boolean hasSelections(Player player, Plugin plugin)
	{
		CUIPlayer cuiPlayer = cuiPlayers.get(player);
		
		if(cuiPlayer != null && cuiPlayer.isEnabled())
		{
			return cuiPlayer.hasSelections(plugin);
		}
		
		return false;
	}
}
