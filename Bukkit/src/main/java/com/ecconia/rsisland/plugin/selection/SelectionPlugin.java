package com.ecconia.rsisland.plugin.selection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.ecconia.rsisland.framework.cofami.CommandHandler;
import com.ecconia.rsisland.framework.cofami.Feedback;
import com.ecconia.rsisland.framework.cofami.GroupSubcommand;
import com.ecconia.rsisland.plugin.selection.api.ISelPlayer;
import com.ecconia.rsisland.plugin.selection.api.SelectionAPI;
import com.ecconia.rsisland.plugin.selection.commands.CommandCUI;
import com.ecconia.rsisland.plugin.selection.commands.CommandExpand;
import com.ecconia.rsisland.plugin.selection.commands.CommandMove;
import com.ecconia.rsisland.plugin.selection.commands.CommandRemove;
import com.ecconia.rsisland.plugin.selection.commands.CommandShrink;
import com.ecconia.rsisland.plugin.selection.commands.CommandUse;
import com.ecconia.rsisland.plugin.selection.commands.GiveTool;
import com.ecconia.rsisland.plugin.selection.cui.CUICore;
import com.ecconia.rsisland.plugin.selection.cui.ICUICore;
import com.ecconia.rsisland.plugin.selection.elements.SelPlayer;
import com.ecconia.rsisland.plugin.selection.interfaces.LeaveJoinListener;
import com.ecconia.rsisland.plugin.selection.interfaces.ToolUsageListener;

public class SelectionPlugin extends JavaPlugin implements SelectionAPI
{
	public static final String itemName = "SelectionTool: ";
	public static final String itemNameFormat = "[a-zA-Z0-9:-_]+";
	
	private Map<UUID, SelPlayer> players = new HashMap<>();
	private ICUICore cuiCore;
	
	private FormattedLogger logger;
	
	@Override
	public void onLoad()
	{
		//Register API
		getServer().getServicesManager().register(SelectionAPI.class, this, this, ServicePriority.Normal);
	}
	
	@Override
	public void onEnable()
	{
		//Handles all CUI stuff, not calling this Constructor -> CUI disabled.
//		cuiCore = true ? new CUICore(this) : new CUIDummyCore();
		cuiCore = new CUICore(this);
		
		logger = new FormattedLogger(new Feedback(Feedback.simplePrefix(ChatColor.WHITE, ChatColor.GOLD, "Select")), getServer().getConsoleSender());
		
		getServer().getPluginManager().registerEvents(new ToolUsageListener(this), this);
		getServer().getPluginManager().registerEvents(new LeaveJoinListener(this), this);
		
//		getServer().getPluginManager().registerEvents(new InventoryFocusListener(), this);
		
		initCommands();
	}
	
	private void initCommands()
	{
		new CommandHandler(this, logger.f(), new GroupSubcommand("sel"
			,new GiveTool()
			,new GroupSubcommand("manage"
				,new CommandRemove(this))
			,new GroupSubcommand("edit"
				,new CommandExpand(this)
				,new CommandShrink(this)
				,new CommandMove(this))
			,new CommandCUI(cuiCore)
			,new CommandUse(this)
		));
	}
	
	public SelPlayer getOrCreateIntPlayer(Player player)
	{
		SelPlayer selPlayer = players.get(player.getUniqueId());
		
		if(selPlayer == null)
		{
			selPlayer = new SelPlayer(player, cuiCore, logger.f());
			players.put(player.getUniqueId(), selPlayer);
		}
		
		return selPlayer;
	}
	
	public SelPlayer getIntPlayer(Player player)
	{
		return players.get(player.getUniqueId());
	}
	
	public FormattedLogger logger()
	{
		return logger;
	}
	
	// API ####################################################################
	
	@Override
	public ISelPlayer getPlayer(Player player)
	{
		return (ISelPlayer) getOrCreateIntPlayer(player);
	}
}
