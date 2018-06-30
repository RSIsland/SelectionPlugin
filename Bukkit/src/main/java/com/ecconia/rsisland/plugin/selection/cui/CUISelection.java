package com.ecconia.rsisland.plugin.selection.cui;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.ecconia.rsisland.plugin.selection.api.ISelection;
import com.ecconia.rsisland.plugin.selection.api.cui.CUIColor;

public class CUISelection
{
	private final CUIPacketSender builder;
	private final UUID uuid;
	private CUIColor color;
	
	private final ISelection selection;
	
	public CUISelection(Plugin plugin, Player player, CUIColor color, ISelection selection)
	{
		uuid = UUID.randomUUID();
		builder = new CUIPacketSender(plugin, player);
		this.selection = selection;
		this.color = color;
	}
	
	public void setColor(CUIColor color)
	{
		this.color = color;
	}
	
	/**
	 * Call to set this selection as the current one on client side.
	 */
	public void update()
	{
		builder.createSelection(uuid);
		builder.setColor(color);
		if(selection.getFirstPoint() != null) builder.setPoint(0, selection.getFirstPoint());
		if(selection.getSecondPoint() != null) builder.setPoint(1, selection.getSecondPoint());
	}
	
	/**
	 * Remove the selection from the client.
	 */
	public void destroy()
	{
		builder.deleteSelection(uuid);
	}
	
	public boolean isVisible()
	{
		//TODO add show/hide command
		return true;
	}
}
