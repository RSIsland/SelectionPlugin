package de.ecconia.bukkit.plugin.selectionunit.command.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ecconia.bukkit.plugin.selectionunit.F;
import de.ecconia.bukkit.plugin.selectionunit.SelectionPlugin;
import de.ecconia.bukkit.plugin.selectionunit.command.framework.Subcommand;
import de.ecconia.bukkit.plugin.selectionunit.interfaces.ItemManager;

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
