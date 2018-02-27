package com.ecconia.rsisland.plugin.selection.commands;

import org.bukkit.command.CommandSender;

import com.ecconia.rsisland.framework.cofami.Subcommand;
import com.ecconia.rsisland.plugin.selection.cui.ICUICore;

public class CommandCUI extends Subcommand
{
	private final ICUICore cui;
	
	public CommandCUI(ICUICore cui)
	{
		super("cui");
		this.cui = cui;
		onlyPlayer();
	}

	@Override
	public void exec(CommandSender sender, String[] args)
	{
		checkPermission(sender);
		
		cui.forceEnable(getPlayer(sender));
	}
}
