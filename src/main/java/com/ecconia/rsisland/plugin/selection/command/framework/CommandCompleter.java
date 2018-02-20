package com.ecconia.rsisland.plugin.selection.command.framework;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface CommandCompleter
{
	List<String> onTabComplete(CommandSender sender, String[] args);
}
