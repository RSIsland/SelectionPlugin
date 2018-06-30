package com.ecconia.rsisland.plugin.selection.cui;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.ecconia.rsisland.plugin.selection.api.ISelection;
import com.ecconia.rsisland.plugin.selection.api.cui.CUICuboidConstruct;

public class CUIDummyCore implements ICUICore
{
	@Override
	public void createSelection(Player player, ISelection selection)
	{
		//Do nothing
	}
	
	@Override
	public void destroySelection(Player player, ISelection selection)
	{
		//Do nothing
	}
	
	@Override
	public void updateSelection(Player player, ISelection selection)
	{
		//Do nothing
	}
	
	@Override
	public void forceEnable(Player player)
	{
		//Do nothing
	}
	
	@Override
	public boolean cuiEnabled(Player player)
	{
		return false;
	}
	
	@Override
	public void replaceSelections(Player player, Plugin plugin, List<CUICuboidConstruct> areas)
	{
		//Do nothing
	}
	
	@Override
	public boolean hasSelections(Player player, Plugin plugin)
	{
		return false;
	}
}
