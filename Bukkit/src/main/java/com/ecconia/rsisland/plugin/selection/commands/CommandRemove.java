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

public class CommandRemove extends Subcommand
{
	private final SelectionPlugin plugin;
	
	//TODO: remove multiple selections
	public CommandRemove(SelectionPlugin plugin)
	{
		super("remove");
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
			die("You do not have any selection.");
		}
		
		String selection;
		
		if(args.length == 1)
		{	
			selection = args[0];
		}
		else
		{
			selection = selPlayer.getLastSelectionName();
		}
		
		if(selection == null)
		{
			die("No latest selection. Add <selectionName> argument.");
		}
		
		selPlayer.remove(selection);
		f.n(sender, "Removed selection %v.", name);
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
