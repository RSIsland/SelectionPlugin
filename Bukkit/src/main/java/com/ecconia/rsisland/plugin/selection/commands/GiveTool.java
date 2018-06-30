package com.ecconia.rsisland.plugin.selection.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ecconia.rsisland.framework.cofami.Subcommand;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.interfaces.ItemManager;

public class GiveTool extends Subcommand
{
	public GiveTool()
	{
		super("tool");
		onlyPlayer();
	}

	@Override
	public void exec(CommandSender sender, String[] args)
	{
		checkPermission(sender);
		
		if(!(sender instanceof Player))
		{
			die("Only player can use this command, to get an item.");
		}
		
		String selectionName = args.length > 0 ? StringUtils.join(args, "") : "default";
		
		if(!selectionName.matches(SelectionPlugin.itemNameFormat))
		{
			die("Invalid name format %v.", selectionName);
		}
		
		ItemManager.giveItem((Player) sender, Material.BLAZE_ROD, selectionName);
		f.n(sender, "There ya go, for selection \"%v\".", selectionName);
	}
}
