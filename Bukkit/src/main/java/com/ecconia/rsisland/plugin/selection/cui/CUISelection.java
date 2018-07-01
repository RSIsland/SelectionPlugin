package com.ecconia.rsisland.plugin.selection.cui;

import java.util.UUID;

import com.ecconia.rsisland.plugin.selection.api.cui.CUIColor;

public class CUISelection
{
	private final UUID uuid;
	private final CUIColor color;
	
	public CUISelection(UUID uuid, CUIColor color)
	{
		this.color = color;
		this.uuid = uuid;
	}
	
	public UUID getUuid()
	{
		return uuid;
	}
	
	public CUIColor getColor()
	{
		return color;
	}
}
