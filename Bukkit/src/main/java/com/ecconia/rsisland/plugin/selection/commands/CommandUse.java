package com.ecconia.rsisland.plugin.selection.commands;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.ecconia.rsisland.framework.cofami.Subcommand;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.elements.SelPlayer;

public class CommandUse extends Subcommand
{
	private SelectionPlugin plugin;
	
	public CommandUse(SelectionPlugin plugin)
	{
		super("use");
		this.plugin = plugin;
		onlyPlayer();
	}

	@Override
	public void exec(CommandSender sender, String[] args)
	{
		checkPermission(sender);
		
		SelPlayer selPlayer = plugin.getIntPlayer(getPlayer(sender));
		
		if(selPlayer == null)
		{
			die("Please make a selection first.");
		}
		
		if(args.length == 1)
		{
			selPlayer.use(args[0]);
			f.n(sender, "Set %v active.", args[0]);
		}
		else
		{
			f.e(sender, "Usage: /sel use <selectionName>");
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args)
	{
		if(args.length == 1)
		{
			SelPlayer player = plugin.getIntPlayer((Player) sender);
			
			if(player == null)
			{
				return Collections.emptyList();
			}
			
			return player.getSelectionNames().stream().filter(name -> StringUtil.startsWithIgnoreCase(name, args[0])).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
}
