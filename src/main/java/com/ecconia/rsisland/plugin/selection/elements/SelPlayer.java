package com.ecconia.rsisland.plugin.selection.elements;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.ecconia.rsisland.plugin.selection.Direction;
import com.ecconia.rsisland.plugin.selection.F;
import com.ecconia.rsisland.plugin.selection.Hand;
import com.ecconia.rsisland.plugin.selection.Point;
import com.ecconia.rsisland.plugin.selection.api.ISelPlayer;
import com.ecconia.rsisland.plugin.selection.api.ISelection;
import com.ecconia.rsisland.plugin.selection.cui.ICUICore;
import com.ecconia.rsisland.plugin.selection.interfaces.ItemManager;

public class SelPlayer implements ISelPlayer
{
	private Player player;
	
	private Map<String, Selection> selections;
	private ICUICore cui;
	
	private Selection lastEditedSelection;
	
	public SelPlayer(Player player, ICUICore cuiCore)
	{
		selections = new HashMap<>();
		
		this.player = player;
		this.cui = cuiCore;
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
			F.n(player, "New selection %v created.", name);
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
			F.n(player, "First and second point set for selection %v.", name);
		}
		else
		{
			F.n(player, (hand.isFirstPos() ? "First" : "Second") + " point set for selection %v.", name);
		}
		
		cui.updateSelection(player, sel);
	}
	
	public Selection getSelection(String name)
	{
		return selections.get(name);
	}
	
	public Selection generateSelection(String name)
	{
		Selection sel = new Selection(name);
		selections.put(name, sel);
		
		cui.createSelection(player, sel);
		
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
			F.e(player, "You do not have a selection %v.", name);
			return;
		}
		
		if(lastEditedSelection == selection)
		{
			lastEditedSelection = null;
		}
		
		cui.destroySelection(player, selection);
		selections.remove(name);
		F.n(player, "Removed selection %v.", name);
	}

	//TODO direction string
	public void actionToolClickAir(String name, Direction dir, Hand hand)
	{
		Selection sel = getSelection(name);
		if(sel == null)
		{
			F.e(player, "Selection %v does not exist. Changes not possible.", name);
			return;
		}
		
		lastEditedSelection = sel;
		
		if(sel.isEmpty())
		{
			F.e(player, "Selection %v is empty. Changes not possible.", name);
			return;
		}
		
		if(hand.grows())
		{
			sel.expand(dir, 1);
			F.n(player, "Expanded selection %v by 1.", sel.getName());
		}
		else
		{
			boolean shrinkedMax = false;
			
			Point vector = dir.getDirectionVector();
			if(vector.getX() != 0)
			{
				shrinkedMax = sel.getFirstPoint().getBlockX() == sel.getSecondPoint().getBlockX();
			}
			else if(vector.getY() != 0)
			{
				shrinkedMax = sel.getFirstPoint().getBlockY() == sel.getSecondPoint().getBlockY();
			}
			else if(vector.getZ() != 0)
			{
				shrinkedMax = sel.getFirstPoint().getBlockZ() == sel.getSecondPoint().getBlockZ();
			}
			
			if(shrinkedMax)
			{
				F.n(player, "Cannot shrink selection %v anymore.", sel.getName());
				return;
			}
			
			sel.shrink(dir, 1);
			F.n(player, "Shrinked selection %v by 1.", sel.getName());
		}
		
		cui.updateSelection(player, sel);
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
		cui.updateSelection(player, selection);
	}
	

	public void use(String name)
	{
		Selection selection = getSelection(name);
		if(selection == null)
		{
			F.e(player, "You do not have a selection %v.", name);
			return;
		}
		
		lastEditedSelection = selection;
		
		F.n(player, "Set %v active.", name);
	}

	
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
}
