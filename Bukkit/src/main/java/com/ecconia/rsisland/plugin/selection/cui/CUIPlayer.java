package com.ecconia.rsisland.plugin.selection.cui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.ecconia.rsisland.framework.commonelements.Cuboid;
import com.ecconia.rsisland.plugin.selection.F;
import com.ecconia.rsisland.plugin.selection.SelectionPlugin;
import com.ecconia.rsisland.plugin.selection.api.ISelection;
import com.ecconia.rsisland.plugin.selection.api.cui.CUICuboidConstruct;
import com.ecconia.rsisland.plugin.selection.elements.SelPlayer;

public class CUIPlayer
{
	private Integer version;
	private boolean enabled;
	
	private boolean delayedVersion;
	
	private Map<ISelection, CUISelection> selections = new HashMap<>();
	
	private SelectionPlugin plugin;
	private Player player;
	
	public CUIPlayer(SelectionPlugin plugin, Player player)
	{
		this.plugin = plugin;
		this.player = player;
	}
	
	public void setVersion(Integer version)
	{
		if(this.version != null && !this.version.equals(version))
		{
			F.e(player.getServer().getConsoleSender(), "Player %v send a different CUI version %v than the one he sent already %v, ask him!", player.getName(), version, this.version);
		}
		this.version = version;
		
		if(version > CUICore.requiredVersion)
		{
			F.e(player.getServer().getConsoleSender(), "Player %v uses a newer CUI protocol version: %v. Please nag developer to update this plugin.", player.getName(), version);
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
		
		if(selPlayer != null)
		{
			for(ISelection sel : selPlayer.getSelections())
			{
				createSelection(sel);
				updateSelection(sel);
			}
		}
	}
	
	//#########################################################################
	
	public void createSelection(ISelection selection)
	{
		getOrCreateCUISelection(selection);
	}

	public void destroySelection(ISelection selection)
	{
		CUISelection sel = selections.get(selection);
		
		if(sel != null)
		{
			sel.destroy();
			selections.remove(selection);
		}
	}

	public void updateSelection(ISelection selection)
	{
		CUISelection sel = getOrCreateCUISelection(selection);
		if(sel.isVisible())
		{
			sel.update();
		}
	}
	
	private CUISelection getOrCreateCUISelection(ISelection selection)
	{
		CUISelection sel = selections.get(selection);
		if(sel == null)
		{
			sel = new CUISelection(plugin, player, CUIColorPreset.randomCUIColor(), selection);
			selections.put(selection, sel);
		}
		return sel;
	}
	
	public void replaceSelections(List<CUICuboidConstruct> selections)
	{
		for(CUISelection selection : this.selections.values())
		{
			selection.destroy();
		}
		
		this.selections.clear();
		
		for(CUICuboidConstruct selection : selections)
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
