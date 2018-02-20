package com.ecconia.rsisland.plugin.selection.command.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ecconia.rsisland.plugin.selection.F;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.command.framework.Subcommand;
import com.ecconia.rsisland.plugin.selection.interfaces.ItemManager;

public class GiveTool extends Subcommand
{
	public GiveTool()
	{
		super("tool");
	}

	@Override
	public void exec(CommandSender sender, String[] args)
	{
		if(!(sender instanceof Player))
		{
			F.e(sender, "Only player can use this command, to get an item.");
			return;
		}
		
		String selectionName = args.length > 0 ? StringUtils.join(args, "") : "default";
		
		if(!selectionName.matches(SelectionPlugin.itemNameFormat))
		{
			F.e(sender, "Invalid name format %v.", selectionName);
			return;
		}
		
		ItemManager.giveItem((Player) sender, Material.BLAZE_ROD, selectionName);
		F.n(sender, "There ya go, for selection \"%v\".", selectionName);
	}
}
