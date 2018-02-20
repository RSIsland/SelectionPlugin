package com.ecconia.rsisland.plugin.selection.command.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.ecconia.rsisland.plugin.selection.Direction;
import com.ecconia.rsisland.plugin.selection.F;
import com.ecconia.rsisland.plugin.selection.Parsers;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.command.framework.Subcommand;
import com.ecconia.rsisland.plugin.selection.elements.SelPlayer;
import com.ecconia.rsisland.plugin.selection.elements.Selection;
import com.ecconia.rsisland.plugin.selection.exceptions.InvalidNameException;
import com.ecconia.rsisland.plugin.selection.exceptions.ParseException;

public class CommandMove extends Subcommand
{
	private SelectionPlugin plugin;
	
	public CommandMove(SelectionPlugin plugin)
	{
		super("move");
		this.plugin = plugin;
	}

	@Override
	public void exec(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		SelPlayer selPlayer = plugin.getPlayer(player);
		
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
				String dirsInput;
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
						dirsInput = args[0];
					}
				}
				else
				{
					dirsInput = args[1];
				}
				if(dirsInput.equals("a"))
				{
					F.e(player, "Moved in all directions, done. :/");
					return;
				}
				dirs = Parsers.parseDirection(dirsInput, head);
			}
			else
			{
				F.e(player, "Usage: /sel edit move <amount> [dirs] OR /sel edit move <name> <amount> <dirs>");
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
		
		for(Direction d : dirs)
		{
			Direction op = Direction.getOppositeDirection(d);
			if(dirs.contains(op))
			{
				F.e(player, "Direction %v and %v are opposite to each other.", d, op);
				return;
			}
		}
		
		if(amount < 0)
		{
			amount = -amount;
			dirs = Direction.invertDirections(dirs);
		}
		
		selection.move(dirs, amount);
		//TODO proper calling
		selPlayer.update(selection);
		
		//TODO: Details
		F.n(player, "Moved your selection.");
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
