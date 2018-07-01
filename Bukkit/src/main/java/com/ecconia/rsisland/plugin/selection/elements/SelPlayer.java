package com.ecconia.rsisland.plugin.selection.elements;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.ecconia.rsisland.framework.cofami.Feedback;
import com.ecconia.rsisland.framework.cofami.exceptions.CommandException;
import com.ecconia.rsisland.framework.commonelements.Point;
import com.ecconia.rsisland.plugin.selection.Hand;
import com.ecconia.rsisland.plugin.selection.api.Direction;
import com.ecconia.rsisland.plugin.selection.api.ISelPlayer;
import com.ecconia.rsisland.plugin.selection.api.ISelection;
import com.ecconia.rsisland.plugin.selection.api.cui.CUICuboidConstruct;
import com.ecconia.rsisland.plugin.selection.cui.ICUICore;
import com.ecconia.rsisland.plugin.selection.interfaces.ItemManager;

public class SelPlayer implements ISelPlayer
{
	private Player player;
	private final Feedback f;
	
	private Map<String, Selection> selections = new HashMap<>();
	private ICUICore cui;
	
	private Selection lastEditedSelection;
	
	public SelPlayer(Player player, ICUICore cuiCore, Feedback f)
	{
		this.player = player;
		this.cui = cuiCore;
		this.f = f;
	}
	
	public void updatePlayer(Player player)
	{
		this.player = player;
	}
	
	public void actionToolClickBlock(String name, Hand hand, Location location, BlockFace blockFace)
	{
		Selection sel = getSelection(name);
		if(sel == null)
		{
			sel = generateSelection(name);
			f.n(player, "New selection %v created.", name);
		}
		
		lastEditedSelection = sel;
		
		boolean overwroteBoth;
		if(hand.isFirstPos())
		{
			overwroteBoth = sel.setFirstPoint(location);
		}
		else
		{
			overwroteBoth = sel.setSecondPoint(location);
		}
		
		if(overwroteBoth)
		{
			f.n(player, "First and second point set for selection %v.", name);
		}
		else
		{
			f.n(player, (hand.isFirstPos() ? "First" : "Second") + " point set for selection %v.", name);
		}
		
		cui.updateSelection(player, sel, sel.getCuboid());
	}
	
	public Selection getSelection(String name)
	{
		return selections.get(name);
	}
	
	public Selection generateSelection(String name)
	{
		Selection sel = new Selection(name);
		selections.put(name, sel);
		
		return sel;
	}
	
	public Set<String> getSelectionNames()
	{
		return selections.keySet();
	}
	
	//TODO API firendly
	public void remove(String name)
	{
		Selection selection = getSelection(name);
		
		if(selection == null)
		{
			throw new CommandException("You do not have a selection %v.", name);
		}
		
		if(lastEditedSelection == selection)
		{
			lastEditedSelection = null;
		}
		
		cui.destroySelection(player, selection);
		selections.remove(name);
	}
	
	//TODO direction string
	public void actionToolClickAir(String name, Direction dir, Hand hand)
	{
		Selection sel = getSelection(name);
		if(sel == null)
		{
			f.e(player, "Selection %v does not exist. Changes not possible.", name);
			return;
		}
		
		lastEditedSelection = sel;
		
		if(sel.getWorld() == null)
		{
			f.e(player, "Selection %v is empty. Changes not possible.", name);
			return;
		}
		
		if(hand.grows())
		{
			sel.expand(dir, 1);
			f.n(player, "Expanded selection %v by 1.", sel.getName());
		}
		else
		{
			boolean shrinkedMax = false;
			
			Point vector = dir.getDirectionVector();
			
			if(vector.getX() != 0)
			{
				shrinkedMax = sel.getFirstPoint().getX() == sel.getSecondPoint().getX();
			}
			else if(vector.getY() != 0)
			{
				shrinkedMax = sel.getFirstPoint().getY() == sel.getSecondPoint().getY();
			}
			else if(vector.getZ() != 0)
			{
				shrinkedMax = sel.getFirstPoint().getZ() == sel.getSecondPoint().getZ();
			}
			
			if(shrinkedMax)
			{
				f.n(player, "Cannot shrink selection %v anymore.", sel.getName());
				return;
			}
			
			sel.shrink(dir, 1);
			f.n(player, "Shrinked selection %v by 1.", sel.getName());
		}
		
		cui.updateSelection(player, sel, sel.getCuboid());
	}
	
	public Collection<Selection> getSelections()
	{
		return selections.values();
	}
	
	/**
	 * Warning: throws InvalidNameException
	 * @return
	 */
	public String getLastSelectionName()
	{
		String name = ItemManager.checkPlayerHandForTool(player);
		
		if(name != null)
		{
			return name;
		}
		
		if(lastEditedSelection != null)
		{
			return lastEditedSelection.getName();
		}
		
		return null;
	}
	
	public void update(Selection selection)
	{
		//TODO: OHNONONOO this is a cancer call! Put it somewhere else!!!
		lastEditedSelection = selection;
		cui.updateSelection(player, selection, selection.getCuboid());
	}
	
	public void use(String name)
	{
		Selection selection = getSelection(name);
		
		if(selection == null)
		{
			new CommandException("You do not have a selection %v.", name);
		}
		
		lastEditedSelection = selection;
	}
	
	// API ####################################################################
	
	@Override
	public ISelection getSelectionOrCurrent(String name)
	{
		Selection selection = getSelection(name);
		
		if(selection == null)
		{
			selection = lastEditedSelection;
		}
		
		return (ISelection) selection;
	}
	
	// CUI-API ################################################################
	
	@Override
	public boolean hasCUI()
	{
		return cui.cuiEnabled(player);
	}
	
	@Override
	public void setCUIAreas(Plugin plugin, List<CUICuboidConstruct> areas)
	{
		cui.replaceSelections(player, plugin, areas);
	}
	
	@Override
	public boolean hasCuboidConstructs(Plugin plugin)
	{
		return cui.hasSelections(player, plugin);
	}
}
