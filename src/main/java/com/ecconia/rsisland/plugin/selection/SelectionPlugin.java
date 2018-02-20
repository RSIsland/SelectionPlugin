package com.ecconia.rsisland.plugin.selection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.ecconia.rsisland.plugin.selection.command.commands.CommandCUI;
import com.ecconia.rsisland.plugin.selection.command.commands.CommandExpand;
import com.ecconia.rsisland.plugin.selection.command.commands.CommandMove;
import com.ecconia.rsisland.plugin.selection.command.commands.CommandRemove;
import com.ecconia.rsisland.plugin.selection.command.commands.CommandShrink;
import com.ecconia.rsisland.plugin.selection.command.commands.CommandUse;
import com.ecconia.rsisland.plugin.selection.command.commands.GiveTool;
import com.ecconia.rsisland.plugin.selection.command.framework.CommandGroup;
import com.ecconia.rsisland.plugin.selection.command.framework.CommandHandler;
import com.ecconia.rsisland.plugin.selection.command.framework.Subcommand;
import com.ecconia.rsisland.plugin.selection.cui.CUICore;
import com.ecconia.rsisland.plugin.selection.cui.ICUICore;
import com.ecconia.rsisland.plugin.selection.elements.SelPlayer;
import com.ecconia.rsisland.plugin.selection.interfaces.LeaveJoinListener;
import com.ecconia.rsisland.plugin.selection.interfaces.ToolUsageListener;

public class SelectionPlugin extends JavaPlugin
{
	public static final String prefix = ChatColor.WHITE + "[" + ChatColor.GOLD + "Select" + ChatColor.WHITE + "] ";
	public static final String itemName = "SelectionTool: ";
	public static final String itemNameFormat = "[a-zA-Z0-9:-_]+";
	
	private Map<UUID, SelPlayer> players = new HashMap<>();
	private ICUICore cuiCore;
	
	@Override
	public void onEnable()
	{
		//Handles all CUI stuff, not calling this Constructor -> CUI disabled.
//		cuiCore = true ? new CUICore(this) : new CUIDummyCore();
		cuiCore = new CUICore(this);
		
		getServer().getPluginManager().registerEvents(new ToolUsageListener(this), this);
		getServer().getPluginManager().registerEvents(new LeaveJoinListener(this), this);
		
//		getServer().getPluginManager().registerEvents(new InventoryFocusListener(), this);
		
		initCommands();
	}
	
	private void initCommands()
	{
		getCommand("sel").setExecutor(new CommandHandler(this, new Subcommand[] {
			new GiveTool(),
			new CommandGroup("manage", new Subcommand[] {
				new CommandRemove(this),
			}),
			new CommandGroup("edit", new Subcommand[] {
				new CommandExpand(this),
				new CommandShrink(this),
				new CommandMove(this),
			}),
			new CommandCUI(cuiCore),
			new CommandUse(this),
		}));
	}

	public void actionToolClickAir(Player player, String name, Direction dir, Hand hand)
	{
		SelPlayer splayer = getPlayer(player);
		
		splayer.actionToolClickAir(name, dir, hand);
	}

	public void actionToolClickBlock(Player player, String name, Hand hand, Location location, BlockFace blockFace)
	{
		//player.sendMessage(prefix + ChatColor.GRAY + "You set the " + ChatColor.GOLD + (hand.isFirstPos() ? "first" : "second") + ChatColor.GRAY + " position of your selection " + ChatColor.GOLD + name + ChatColor.GRAY + " to " + ChatColor.GOLD + target.getBlockX() + ", " + target.getBlockY() + ", " + target.getBlockZ() + ChatColor.GRAY + ".");
		
		SelPlayer splayer = getPlayer(player);
		
		splayer.actionToolClickBlock(name, hand, location, blockFace);
	}
	
	public SelPlayer getPlayer(Player player)
	{
		SelPlayer selPlayer = players.get(player.getUniqueId());
		if(selPlayer == null)
		{
			selPlayer = new SelPlayer(player, cuiCore);
			players.put(player.getUniqueId(), selPlayer);
		}
		return selPlayer;
	}
}
