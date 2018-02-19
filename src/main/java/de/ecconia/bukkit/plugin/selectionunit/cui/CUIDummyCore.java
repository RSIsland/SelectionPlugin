package de.ecconia.bukkit.plugin.selectionunit.cui;

import org.bukkit.entity.Player;

import de.ecconia.bukkit.plugin.selectionunit.elements.Selection;

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
}
