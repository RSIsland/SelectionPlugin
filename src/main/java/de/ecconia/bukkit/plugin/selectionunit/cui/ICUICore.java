package de.ecconia.bukkit.plugin.selectionunit.cui;

import org.bukkit.entity.Player;

import de.ecconia.bukkit.plugin.selectionunit.elements.Selection;

public interface ICUICore
{
	public void createSelection(Player player, Selection selection);
	
	public void destroySelection(Player player, Selection selection);
	
	public void updateSelection(Player player, Selection selection);
	
	public void forceEnable(Player player);
}
