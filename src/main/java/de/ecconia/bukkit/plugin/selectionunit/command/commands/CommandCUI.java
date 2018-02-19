package de.ecconia.bukkit.plugin.selectionunit.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ecconia.bukkit.plugin.selectionunit.command.framework.Subcommand;
import de.ecconia.bukkit.plugin.selectionunit.cui.ICUICore;

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
