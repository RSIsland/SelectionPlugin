package com.ecconia.rsisland.plugin.selection.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ecconia.rsisland.plugin.selection.command.framework.Subcommand;
import com.ecconia.rsisland.plugin.selection.cui.ICUICore;

public class CommandCUI extends Subcommand
{
	private final ICUICore cui;
	
	public CommandCUI(ICUICore cui)
	{
		super("cui");
		this.cui = cui;
	}

	@Override
	public void exec(CommandSender sender, String[] args)
	{
		cui.forceEnable((Player) sender);
	}
}
