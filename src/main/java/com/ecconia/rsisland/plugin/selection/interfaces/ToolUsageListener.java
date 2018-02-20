package com.ecconia.rsisland.plugin.selection.interfaces;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.ecconia.rsisland.plugin.selection.Direction;
import com.ecconia.rsisland.plugin.selection.F;
import com.ecconia.rsisland.plugin.selection.Hand;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;

public class ToolUsageListener implements Listener
{
	private final SelectionPlugin plugin; 
	
	public ToolUsageListener(SelectionPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void interactionListener(PlayerInteractEvent event)
	{
		ItemStack item = event.getItem();
		if(item != null)
		{
			String name = item.getItemMeta().getDisplayName();
			if(name != null && name.startsWith(SelectionPlugin.itemName))
			{
				event.setCancelled(true);
				name = name.substring(SelectionPlugin.itemName.length());
				Player player = event.getPlayer();
				
				if(name.matches(SelectionPlugin.itemNameFormat))
				{
					Block block = event.getClickedBlock();
					if(block == null)
					{
						Hand hand = event.getAction() == Action.LEFT_CLICK_AIR ? Hand.LEFT : Hand.RIGHT;
						Direction dir = Direction.getDirectionFromHead(player.getLocation());
						
						plugin.actionToolClickAir(player, name, dir, hand);
					}
					else
					{
						Hand hand = event.getAction() == Action.LEFT_CLICK_BLOCK ? Hand.LEFT : Hand.RIGHT;
	//					Location target = block.getLocation();
	//					if(hand.selAtBlock())
	//					{
	//						BlockFace face = event.getBlockFace();
	//						target = block.getLocation().add(face.getModX(), face.getModY(), face.getModZ());
	//					}
						
						plugin.actionToolClickBlock(player, name, hand, block.getLocation(), event.getBlockFace());
					}
				}
				else
				{
					F.e(player, "Invalid selection tool name: \"%v\"", name);
				}
			}
		}
	}
}
