package com.ecconia.rsisland.plugin.selection.cui;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.ecconia.rsisland.framework.commonelements.Cuboid;
import com.ecconia.rsisland.plugin.selection.api.cui.CUICuboidConstruct;

public interface ICUICore
{
	// Internal-Triggered #####################################################
	
	public void destroySelection(Player player, Object key);
	
	public void updateSelection(Player player, Object key, Cuboid cuboid);
	
	public void forceEnable(Player player);
	
	// API-Triggered ##########################################################
	
	public boolean cuiEnabled(Player player);
	
	public void replaceSelections(Player player, Plugin plugin, List<CUICuboidConstruct> areas);
	
	public boolean hasSelections(Player player, Plugin plugin);
}
