package com.ecconia.rsisland.plugin.selection.cui;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.ecconia.rsisland.plugin.selection.api.ISelection;
import com.ecconia.rsisland.plugin.selection.api.cui.CUICuboidConstruct;

public interface ICUICore
{
	// Internal-Triggered #####################################################
	//Default to the SelectionPlugin plugin
	
	public void createSelection(Player player, ISelection selection);
	
	public void destroySelection(Player player, ISelection selection);
	
	public void updateSelection(Player player, ISelection selection);
	
	public void forceEnable(Player player);
	
	// Both-Triggered #########################################################
	
	public boolean cuiEnabled(Player player);
	
	// API-Triggered ##########################################################
	
	public void replaceSelections(Player player, Plugin plugin, List<CUICuboidConstruct> areas);
	
	public boolean hasSelections(Player player, Plugin plugin);
}
