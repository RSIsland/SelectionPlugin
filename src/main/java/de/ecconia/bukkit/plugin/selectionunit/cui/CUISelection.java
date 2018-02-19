package de.ecconia.bukkit.plugin.selectionunit.cui;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.ecconia.bukkit.plugin.selectionunit.elements.Selection;

public class CUISelection
{
	private final CUIPacketSender builder;
	private final UUID uuid;
	private CUIColor color;
	
	private final Selection selection;
	
	public CUISelection(Plugin plugin, Player player, CUIColor color, Selection selection)
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
		if(selection.getPosFirst() != null) builder.setPoint(0, selection.getPosFirst());
		if(selection.getPosSecond() != null) builder.setPoint(1, selection.getPosSecond());
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
