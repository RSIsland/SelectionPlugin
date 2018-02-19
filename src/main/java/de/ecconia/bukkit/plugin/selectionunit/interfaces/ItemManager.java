package de.ecconia.bukkit.plugin.selectionunit.interfaces;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.ecconia.bukkit.plugin.selectionunit.F;
import de.ecconia.bukkit.plugin.selectionunit.SelectionPlugin;
import de.ecconia.bukkit.plugin.selectionunit.exceptions.InvalidNameException;

public class ItemManager
{
	public static void giveItem(Player player, Material mat, String name)
	{
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(SelectionPlugin.itemName + name);
		item.setItemMeta(meta);
		
		player.getInventory().addItem(item);
	}
	
	public static String checkPlayerHandForTool(Player player)
	{
		//Ignore OFF_HAND since it would make not much sense to put a tool there.
		ItemStack item = player.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		if(meta != null)
		{
			String name = meta.getDisplayName();
			
			if(name.startsWith(SelectionPlugin.itemName))
			{
				name = name.substring(SelectionPlugin.itemName.length());
				if(name.matches(SelectionPlugin.itemNameFormat))
				{
					return name;
				}
				throw new InvalidNameException(F.e("Invalid name \"%v\", please destroy item.", name));
			}
		}
		
		return null;
	}
}
