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
import com.ecconia.rsisland.plugin.selection.Parsers;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.api.Direction;
import com.ecconia.rsisland.plugin.selection.elements.SelPlayer;
import com.ecconia.rsisland.plugin.selection.elements.Selection;

public class CommandMove extends Subcommand
{
	private SelectionPlugin plugin;
	
	public CommandMove(SelectionPlugin plugin)
	{
		super("move");
		this.plugin = plugin;
		onlyPlayer();
	}

	@Override
	public void exec(CommandSender sender, String[] args)
	{
		checkPermission(sender);
		
		Player player = getPlayer(sender);
		SelPlayer selPlayer = plugin.getIntPlayer(player);
		
		if(selPlayer == null)
		{
			die("Please make a selection first.");
		}
		
		int params = args.length;
		
		Integer amount = null;
		Set<Direction> dirs = null;
		Direction head = Direction.getDirectionFromHead(player.getLocation());
		String selectionName;
		
		if(params == 3)
		{
			selectionName = args[0];
			args[0] = args[1];
			args[1] = args[2];
			params = 2;
		}
		else
		{
			selectionName = selPlayer.getLastSelectionName();
			
			if(selectionName == null)
			{
				die("No latest used selection. Use: /sel use <name>");
			}
		}
		
		if(params == 1)
		{
			amount = Parsers.parseInt(args[0]);
			
			if(amount == null)
			{
				die("Could not parse amount %v.", args[0]);
			}
			
			dirs = Arrays.stream(new Direction[]{head}).collect(Collectors.toSet());
		}
		else if(params == 2)
		{
			String dirsInput = null;
			amount = Parsers.parseInt(args[0]);
			
			if(amount == null)
			{
				amount = Parsers.parseInt(args[1]);
				
				if(amount == null)
				{
					die("Neither %v nor %v can be parsed to an amount.", args[0], args[1]);
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
				die("Moved in all directions, done. :/");
			}
			
			dirs = Parsers.parseDirection(dirsInput, head);
		}
		else
		{
			die("Usage: /sel edit move <amount> [dirs] OR /sel edit move <name> <amount> <dirs>");
		}
		
		Selection selection = selPlayer.getSelection(selectionName);
		
		if(selection == null)
		{
			die("You do not have a selection %v.", selectionName);
		}
		
		for(Direction d : dirs)
		{
			Direction op = Direction.getOppositeDirection(d);
			
			if(dirs.contains(op))
			{
				die("Direction %v and %v are opposite to each other.", d, op);
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
		f.n(player, "Moved your selection.");
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
