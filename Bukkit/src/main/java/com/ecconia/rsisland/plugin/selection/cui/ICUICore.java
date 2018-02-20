package com.ecconia.rsisland.plugin.selection.cui;

import org.bukkit.entity.Player;

import com.ecconia.rsisland.plugin.selection.elements.Selection;

public interface ICUICore
{
	public void createSelection(Player player, Selection selection);
	
	public void destroySelection(Player player, Selection selection);
	
	public void updateSelection(Player player, Selection selection);
	
	public void forceEnable(Player player);
}
