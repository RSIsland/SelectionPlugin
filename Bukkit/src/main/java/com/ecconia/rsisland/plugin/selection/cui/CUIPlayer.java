package com.ecconia.rsisland.plugin.selection.cui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.ecconia.rsisland.framework.commonelements.Cuboid;
import com.ecconia.rsisland.plugin.selection.F;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.api.CUIArea;
import com.ecconia.rsisland.plugin.selection.elements.SelPlayer;
import com.ecconia.rsisland.plugin.selection.elements.Selection;

public class CUIPlayer
{
	private Integer version;
	private boolean enabled;
	
	private boolean delayedVersion;
	
	private Map<Selection, CUISelection> selections;
	
	//TODO: Add set of all cui-selections
	
	private SelectionPlugin plugin;
	private Player player;
	
	public CUIPlayer(SelectionPlugin plugin, Player player)
	{
		this.plugin = plugin;
		this.player = player;
		
		selections = new HashMap<>();
	}
	
	public void setVersion(Integer version)
	{
		if(this.version != null && !this.version.equals(version))
		{
			F.e(player.getServer().getConsoleSender(), "Player %v send a different version %v than the one he sent before %v, nag him!", player.getName(), version, this.version);
		}
		this.version = version;
		
		if(version > CUICore.requiredVersion)
		{
			F.e(player.getServer().getConsoleSender(), "Player %v uses a newer CUI protocol version: %v Please nag developer to update this plugin.", player.getName(), version);
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
			F.e(player, "Your CUI is outdated, protocol version %v. Please update it, to enjoy multi selections on this server.", version);
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
			selections.clear();
		}
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	private void getExistingSelections()
	{
		SelPlayer selPlayer = plugin.getIntPlayer(player);
		for(Selection sel : selPlayer.getSelections())
		{
			createSelection(sel);
			updateSelection(sel);
		}
	}

	//#########################################################################
	
	public void createSelection(Selection selection)
	{
		getOrCreateCUISelection(selection);
	}

	public void destroySelection(Selection selection)
	{
		CUISelection sel = selections.get(selection);
		
		if(sel != null)
		{
			sel.destroy();
			selections.remove(selection);
		}
	}

	public void updateSelection(Selection selection)
	{
		CUISelection sel = getOrCreateCUISelection(selection);
		if(sel.isVisible())
		{
			sel.update();
		}
	}
	
	private CUISelection getOrCreateCUISelection(Selection selection)
	{
		CUISelection sel = selections.get(selection);
		if(sel == null)
		{
			sel = new CUISelection(plugin, player, CUIColor.randomCUIColor(), selection);
			selections.put(selection, sel);
		}
		return sel;
	}
	
	public void replaceSelections(List<CUIArea> selections)
	{
		for(CUISelection selection : this.selections.values())
		{
			selection.destroy();
		}
		
		this.selections.clear();
		
		for(CUIArea selection : selections)
		{
			for(Cuboid cuboid : selection.getCuboids())
			{
				CUIPacketSender builder = new CUIPacketSender(plugin, player);
				
				builder.createSelection(UUID.randomUUID());
				builder.setColor(selection.getColor());
				builder.setGrid(0.0);
				
				builder.setPoint(0, cuboid.getFirstPoint());
				builder.setPoint(1, cuboid.getSecondPoint());
			}
		}
	}
}
