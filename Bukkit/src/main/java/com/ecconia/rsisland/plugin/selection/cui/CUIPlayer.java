package com.ecconia.rsisland.plugin.selection.cui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.ecconia.rsisland.framework.commonelements.Cuboid;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.api.ISelection;
import com.ecconia.rsisland.plugin.selection.api.cui.CUIColor;
import com.ecconia.rsisland.plugin.selection.api.cui.CUICuboidConstruct;
import com.ecconia.rsisland.plugin.selection.elements.SelPlayer;

public class CUIPlayer
{
	private final CUIPacketSender builder;
	private final SelectionPlugin plugin;
	private final Player player;
	
	private Integer version;
	private boolean enabled;
	private boolean delayedVersion;
	
	private final Map<Plugin, List<UUID>> pluginSelections = new HashMap<>();
	private final Map<Object, CUISelection> selectionPluginRegistry = new HashMap<>();
	
	public CUIPlayer(SelectionPlugin plugin, Player player)
	{
		this.plugin = plugin;
		this.player = player;
		
		builder = new CUIPacketSender(plugin, player);
	}
	
	// Management #############################################################
	
	public void setVersion(Integer version)
	{
		if(this.version != null && !this.version.equals(version))
		{
			plugin.logger().error("Player %v send a different CUI version %v than the one he sent already %v, ask him!", player.getName(), version, this.version);
		}
		
		this.version = version;
		
		if(version > CUICore.requiredVersion)
		{
			plugin.logger().error("Player %v uses a newer CUI protocol version: %v. Please nag developer to update this plugin.", player.getName(), version);
		}
		
		if(delayedVersion)
		{
			checkIfOutdated();
			delayedVersion = false;
		}
	}
	
	private void checkIfOutdated()
	{
		if(version < CUICore.requiredVersion)
		{
			plugin.logger().f().e(player, "Your CUI is outdated, protocol version %v. Please update it, to enjoy multi selections on this server.", version);
			enabled = false;
		}
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
		
		if(enabled)
		{
			if(version == null)
			{
				delayedVersion = true;
			}
			else
			{
				checkIfOutdated();
			}
			
			getExistingSelections();
		}
		else
		{
			//Dump all stored "selections"
			selectionPluginRegistry.clear();
			pluginSelections.clear();
		}
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	private void getExistingSelections()
	{
		SelPlayer selPlayer = plugin.getIntPlayer(player);
		
		if(selPlayer != null)
		{
			for(ISelection sel : selPlayer.getSelections())
			{
				updateSelection(sel, sel.getCuboid());
			}
		}
	}
	
	// API ####################################################################
	
	public void replaceSelections(Plugin plugin, List<CUICuboidConstruct> selections)
	{
		List<UUID> pluginList = pluginSelections.get(plugin);
		
		if(pluginList != null)
		{
			for(UUID uuid : pluginList)
			{
				builder.deleteSelection(uuid);
			}
		}
		
		if(selections != null && !selections.isEmpty())
		{
			if(pluginList == null)
			{
				pluginList = new ArrayList<>();
				pluginSelections.put(plugin, pluginList);
			}
			
			for(CUICuboidConstruct selection : selections)
			{
				for(Cuboid cuboid : selection.getCuboids())
				{
					UUID uuid = UUID.randomUUID();
					pluginList.add(uuid);
					
					builder.createSelection(uuid);
					builder.setColor(selection.getColor());
					builder.setGrid(0.0);
					
					builder.setPoint(0, cuboid.getFirstPoint());
					builder.setPoint(1, cuboid.getSecondPoint());
				}
			}
		}
	}
	
	public boolean hasSelections(Plugin plugin)
	{
		List<UUID> pluginList = pluginSelections.get(plugin);
		
		return pluginList != null && !pluginList.isEmpty();
	}
	
	// SelectionPlugin ########################################################
	
	public void updateSelection(Object key, Cuboid cuboid)
	{
		CUISelection selectionData = selectionPluginRegistry.get(key);
		
		if(selectionData == null)
		{
			UUID uuid = UUID.randomUUID();
			CUIColor color = CUIColorPreset.randomCUIColor();
			
			selectionData = new CUISelection(uuid, color);
			selectionPluginRegistry.put(key, selectionData);
		}
		
		builder.createSelection(selectionData.getUuid());
		builder.setColor(selectionData.getColor());
		if(cuboid.getFirstPoint() != null) builder.setPoint(0, cuboid.getFirstPoint());
		if(cuboid.getSecondPoint() != null) builder.setPoint(1, cuboid.getSecondPoint());
	}
	
	public void destroySelection(Object key)
	{
		CUISelection selectionData = selectionPluginRegistry.get(key);
		
		if(selectionData != null)
		{
			builder.deleteSelection(selectionData.getUuid());
			selectionPluginRegistry.remove(key);
		}
	}
}
