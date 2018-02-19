package de.ecconia.bukkit.plugin.selectionunit.cui;

public enum CUIPackets
{
	SELECTION	("s"),
	POINT		("p"),
	COLOR		("col"),
	
	GRID		("grid"),

	POINT2D		("p2"),
	ELLIPSOID	("e"),
	CYLINDER	("cyl"),
	MINMAX		("mm"),
	UPDATE		("u"),
	POLYGON		("poly"),
	;
	
	private final String name;
	
	CUIPackets(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
}
