package com.ecconia.rsisland.plugin.selection.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.ecconia.rsisland.framework.cofami.Subcommand;
import com.ecconia.rsisland.plugin.selection.F;
import com.ecconia.rsisland.plugin.selection.Parsers;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.api.Direction;
import com.ecconia.rsisland.plugin.selection.elements.SelPlayer;
import com.ecconia.rsisland.plugin.selection.elements.Selection;
import com.ecconia.rsisland.plugin.selection.exceptions.InvalidNameException;
import com.ecconia.rsisland.plugin.selection.exceptions.ParseException;

public class CommandExpand extends Subcommand
{
	private SelectionPlugin plugin;
	
	public CommandExpand(SelectionPlugin plugin)
	{
		super("expand");
		this.plugin = plugin;
		onlyPlayer();
	}

	@Override
	public void exec(CommandSender sender, String[] args)
	{
		checkPermission(sender);
		
		Player player = getPlayer(sender);
		SelPlayer selPlayer = plugin.getIntPlayer(player);
		
		int params = args.length;
		
		Integer amount = null;
		Set<Direction> dirs = null;
		Direction head = Direction.getDirectionFromHead(player.getLocation());
		String selectionName;
		
		try
		{
			if(params == 3)
			{
				selectionName = args[0];
				args[0] = args[1];
				args[1] = args[2];
				params = 2;
			}
			else
			{
				try
				{
					selectionName = selPlayer.getLastSelectionName();
				}
				catch (InvalidNameException e)
				{
					player.sendMessage(e.getMessage());
					return;
				}
				if(selectionName == null)
				{
					F.e(player, "No latest used selection. Use: /sel use <name>");
					return;
				}
			}
			
			if(params == 1)
			{
				amount = Parsers.parseInt(args[0]);
				if(amount == null)
				{
					F.e(player, "Could not parse amount %v.", args[0]);
					return;
				}
				dirs = Arrays.stream(new Direction[]{head}).collect(Collectors.toSet());
			}
			else if(params == 2)
			{
				amount = Parsers.parseInt(args[0]);
				if(amount == null)
				{
					amount = Parsers.parseInt(args[1]);
					if(amount == null)
					{
						F.e(player, "Neither %v nor %v can be parsed to an amount.", args[0], args[1]);
						return;
					}
					else
					{
						dirs = Parsers.parseDirection(args[0], head);
					}
				}
				else
				{
					dirs = Parsers.parseDirection(args[1], head);
				}
			}
			else
			{
				F.e(player, "Usage: /sel edit expand <amount> [dirs] OR /sel edit expand <name> <amount> <dirs>");
				return;
			}
		}
		catch (ParseException e)
		{
			player.sendMessage(e.getMessage());
			return;
		}
		
		Selection selection = selPlayer.getSelection(selectionName);
		if(selection == null)
		{
			F.e(player, "You do not have a selection %v.", selectionName);
			return;
		}
		
		if(amount < 0)
		{
			amount = -amount;
			dirs = Direction.invertDirections(dirs);
			
			selection.shrink(dirs, amount);
			//TODO: Details
			F.n(player, "Shrinked your selection.");
		}
		else
		{
			selection.expand(dirs, amount);
			//TODO: Details
			F.n(player, "Expanded your selection.");
		}
		
		//TODO proper calling, should be in the changing methods included
		selPlayer.update(selection);
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
