package com.ecconia.rsisland.plugin.selection.command.commands;
//package de.ecconia.bukkit.plugin.selectionunit.command;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import org.bukkit.ChatColor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.bukkit.plugin.Plugin;
//
//import de.ecconia.bukkit.plugin.selectionunit.F;
//import de.ecconia.bukkit.plugin.selectionunit.Point;
//import de.ecconia.bukkit.plugin.selectionunit.SelectionUnit;
//import de.ecconia.bukkit.plugin.selectionunit.command.framework.SubCommand;
//import de.ecconia.bukkit.plugin.selectionunit.cui.CUIColor;
//import de.ecconia.bukkit.plugin.selectionunit.cui.CUISelection;
//
//public class TestSelections extends SubCommand
//{
//	private Map<Player, List<CUISelection>> selections = new HashMap<>();
//	private Plugin plugin;
//	
//	public TestSelections(Plugin plugin)
//	{
//		super("cui");
//		this.plugin = plugin;
//	}
//	
//	@Override
//	public void exec(CommandSender sender, String[] copyOfRange)
//	{
//		if(!(sender instanceof Player))
//		{
//			F.e(sender, "Only player can use this command, to test their CUI.");
//			return;
//		}
//		trigger((Player) sender);
//	}
//	
//	private void trigger(Player player)
//	{
//		List<CUISelection> userSelections = selections.remove(player);
//
//		if(userSelections != null)
//		{
//			for(CUISelection s : userSelections)
//			{
//				s.destroy();
//			}
//			return;
//		}
//		
//		userSelections = new ArrayList<>();
//		selections.put(player, userSelections);
//		
//		for(int i = 0; i < CUIColor.values().length; i++)
//		{
//			userSelections.add(newSelection(player, CUIColor.values()[i], 0, 100, (i+1)*6));
//		}
//		
//		return;
//	}
//	
//	private CUISelection newSelection(Player player, CUIColor color, int x, int y, int z)
//	{
////		CUISelection selection = new CUISelection(plugin, player, color);
////		selection.changePoint1(new Point(x-2, y-2, z-2));
////		selection.changePoint2(new Point(x+2, y+2, z+2));
////		selection.update();
////		return selection;
//	}
//	
//	@SuppressWarnings("unused")
//	private CUIColor randomColor() {
//	    int pick = new Random().nextInt(CUIColor.values().length);
//	    return CUIColor.values()[pick];
//	}
//}
