package com.ecconia.rsisland.plugin.selection.cui;

import java.nio.charset.Charset;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CUIPacketSender
{
	private final Plugin plugin;
	private final Player player;
	
	public CUIPacketSender(Plugin plugin, Player player)
	{
		this.plugin = plugin;
		this.player = player;
	}

	public void createSelection(UUID uuid)
	{
		sendMessage(newMessage(true, CUIPackets.SELECTION, "cuboid", uuid));
	}
	
	public void deleteSelection(UUID uuid)
	{
		sendMessage(newMessage(true, CUIPackets.SELECTION, "clear", uuid));
	}
	
	public void setColor(CUIColor color)
	{
		sendMessage(newMessage(true, CUIPackets.COLOR, color.getBounds(), color.getGrid(), color.getPoint1(), color.getPoint2()));
	}
	
	public void setPoint(int which, Location location)
	{
		sendMessage(newMessage(true, CUIPackets.POINT, which, location.getBlockX(), location.getBlockY(), location.getBlockZ(), ""));
	}
	
	private void sendMessage(String message)
	{
		player.sendPluginMessage(plugin, CUICore.channel, message.getBytes(Charset.forName("UTF-8")));
	}

	private String newMessage(boolean multi, CUIPackets type, Object... arguments)
	{
		StringBuilder message = new StringBuilder();
		if(multi)
		{
			message.append('+');
		}
		message.append(type.getName());
		
		for(Object i : arguments)
		{
			message.append('|').append(i);
		}
		
		return message.toString();
	}

	public void setGrid(int i)
	{
		sendMessage(newMessage(true, CUIPackets.GRID, i));
	}
}
