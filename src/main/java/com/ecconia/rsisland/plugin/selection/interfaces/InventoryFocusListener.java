package com.ecconia.rsisland.plugin.selection.interfaces;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryFocusListener implements Listener
{
	@EventHandler
	public void onSlotChange(PlayerItemHeldEvent event)
	{
		ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
		String type = item == null ? "nothing" : item.getType().toString();
		event.getPlayer().sendMessage("Slot: " + type);
	}
	
	@EventHandler
	public void onSlotChange2(EntityPickupItemEvent event)
	{
		if(event.getEntityType() == EntityType.PLAYER)
		{
			Player player = (Player) event.getEntity();
			
			player.sendMessage("Pickup: " + event.getItem().getItemStack().getType());
		}
	}
	
	//Who cares what you dump
//	@EventHandler
//	public void onSlotChange3(PlayerDropItemEvent event)
//	{
//		Player player = event.getPlayer();
//		
//		player.sendMessage("Drop: " + event.getItemDrop().getItemStack().getType());
//	}
	
	@EventHandler
	public void onSlotChange4(PlayerChangedMainHandEvent event)
	{
		Player player = event.getPlayer();
		
		player.sendMessage("Main: " + event.getMainHand());
	}
	
//	@EventHandler
//	public void onSlotChange5( event)
//	{
//		Player player = event.getPlayer();
//		
//		player.sendMessage("Main: " + event.getMainHand());
//	}
}
