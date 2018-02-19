package de.ecconia.bukkit.plugin.selectionunit.command.commands;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import de.ecconia.bukkit.plugin.selectionunit.F;
import de.ecconia.bukkit.plugin.selectionunit.SelectionPlugin;
import de.ecconia.bukkit.plugin.selectionunit.command.framework.Subcommand;
import de.ecconia.bukkit.plugin.selectionunit.elements.SelPlayer;
import de.ecconia.bukkit.plugin.selectionunit.exceptions.InvalidNameException;

public class CommandRemove extends Subcommand
{
	private final SelectionPlugin plugin;
	
	//TODO: remove multiple selections
	public CommandRemove(SelectionPlugin plugin)
	{
		super("remove");
		
		this.plugin = plugin;
	}

	@Override
	public void exec(CommandSender sender, String[] args)
	{
		SelPlayer player = plugin.getPlayer((Player) sender);
		
		String selection = null;
		
		if(args.length == 1)
		{	
			selection = args[0];
		}
		else
		{
			try
			{
				selection = player.getLastSelectionName();
			}
			catch (InvalidNameException e)
			{
				sender.sendMessage(e.getMessage());
				return;
			}
		}
		
		if(selection == null)
		{
			F.e(sender, "No latest selection. Add <selectionName> argument.");
			return;
		}
		
		player.remove(selection);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args)
	{
		if (args.length == 1)
		{
			SelPlayer player = plugin.getPlayer((Player) sender);
			List<String> selections = player.getSelectionNames().stream().filter(name -> StringUtil.startsWithIgnoreCase(name, args[0])).collect(Collectors.toList());
			
			return selections;
		}
		return Collections.emptyList();
	}
}
