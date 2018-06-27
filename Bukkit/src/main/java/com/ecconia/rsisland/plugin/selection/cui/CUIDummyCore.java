package com.ecconia.rsisland.plugin.selection.cui;

import java.util.List;

import org.bukkit.entity.Player;

import com.ecconia.rsisland.plugin.selection.api.CUIArea;
import com.ecconia.rsisland.plugin.selection.elements.Selection;

public class CUIDummyCore implements ICUICore
{
	@Override
	public void createSelection(Player player, Selection selection)
	{
		//Do nothing
	}

	@Override
	public void destroySelection(Player player, Selection selection)
	{
		//Do nothing
	}

	@Override
	public void updateSelection(Player player, Selection selection)
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
	public void replaceSelections(Player player, List<CUIArea> areas)
	{
		//Do nothing
	}
}
