package com.ecconia.rsisland.plugin.selection.cui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.ecconia.rsisland.plugin.selection.F;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.elements.Selection;

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
				//Some wrong format:
				F.e(plugin.getServer().getConsoleSender(), "Unknown CUI packet from %v: %v", player.getName(), message);
			}
		});
		
		//Check if WE is on the server, else catch the "/we cui" command silently.
		Plugin wePlugin = plugin.getServer().getPluginManager().getPlugin("WorldEdit");
		if(wePlugin == null)
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
//		if(cuiPlayers.remove(event.getPlayer()) != null)
//		{
//			F.n(plugin.getServer().getConsoleSender(), "Unregistered ", event.getPlayer().getName());
//		}
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
	
	public void createSelection(Player player, Selection selection)
	{
		if(hasCUI(player))
		{
//			F.n(player, "CUI: Created selection %v", selection.getName());
			cuiPlayers.get(player).createSelection(selection);
		}
	}
	
	public void destroySelection(Player player, Selection selection)
	{
		if(hasCUI(player))
		{
//			F.n(player, "CUI: Deleted selection %v", selection.getName());
			cuiPlayers.get(player).destroySelection(selection);
		}
	}
	
	public void updateSelection(Player player, Selection selection)
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
		F.n(player, "Enabling CUI. Warning incompatibility possible.");
	}
}