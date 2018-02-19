package de.ecconia.bukkit.plugin.selectionunit.command.framework;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface CommandCompleter
{
	List<String> onTabComplete(CommandSender sender, String[] args);
}
