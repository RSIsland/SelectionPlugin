package com.ecconia.rsisland.plugin.selection.command.commands;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.ecconia.rsisland.plugin.selection.F;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.command.framework.Subcommand;
import com.ecconia.rsisland.plugin.selection.elements.SelPlayer;

public class CommandUse extends Subcommand
{
	private SelectionPlugin plugin;
	
	public CommandUse(SelectionPlugin plugin)	{
		super("use");
		this.plugin = plugin;
	}

	@Override
	public void exec(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		SelPlayer selPlayer = plugin.getIntPlayer(player);
		
		if(args.length == 1)
		{
			selPlayer.use(args[0]);
		}
		else
		{
			F.e(player, "Usage: /sel use <selectionName>");
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args)
	{
		if (args.length == 1)
		{
			SelPlayer player = plugin.getIntPlayer((Player) sender);
			List<String> selections = player.getSelectionNames().stream().filter(name -> StringUtil.startsWithIgnoreCase(name, args[0])).collect(Collectors.toList());
			
			return selections;
		}
		return Collections.emptyList();
	}
}
