package de.ecconia.bukkit.plugin.selectionunit.cui;

import java.util.Random;

public enum CUIColor
{
	BLACK	("#ffffff", "#000000", "#ff00ff", "#00ffff"),
	BLUED	("#5555FF", "#0000AA", "#ff00ff", "#00ffff"),
	GREEND	("#55FF55", "#00AA00", "#ff00ff", "#00ffff"),
	AQUAD	("#55FFFF", "#00AAAA", "#ff00ff", "#00ffff"),
	REDD	("#FF5555", "#AA0000", "#ff00ff", "#00ffff"),
	PURPLED	("#FF55FF", "#AA00AA", "#ff00ff", "#00ffff"),
	GOLD	("#FFFF55", "#FFAA00", "#ff00ff", "#00ffff"),
	GRAY	("#555555", "#AAAAAA", "#ff00ff", "#00ffff"),
	GRAYD	("#AAAAAA", "#555555", "#ff00ff", "#00ffff"),
	BLUE	("#0000AA", "#5555FF", "#ff00ff", "#00ffff"),
	GREEN	("#00AA00", "#55FF55", "#ff00ff", "#00ffff"),
	AQUA	("#00AAAA", "#55FFFF", "#ff00ff", "#00ffff"),
	RED		("#AA0000", "#FF5555", "#ff00ff", "#00ffff"),
	PURPLE	("#AA00AA", "#FF55FF", "#ff00ff", "#00ffff"),
	YELLOW	("#FFAA00", "#FFFF55", "#ff00ff", "#00ffff"),
	WHITE	("#000000", "#FFFFFF", "#ff00ff", "#00ffff"),
	;
	private final String bounds;
	private final String grid;
	private final String point1;
	private final String point2;
	
	CUIColor(String bounds, String grid, String point1, String point2)
	{
		this.bounds = bounds;
		this.grid = grid;
		this.point1 = point1;
		this.point2 = point2;
	}
	
	public String getBounds()
	{
		return bounds;
	}
	
	public String getGrid()
	{
		return grid;
	}
	
	public String getPoint1()
	{
		return point1;
	}
	
	public String getPoint2()
	{
		return point2;
	}
	
	public static CUIColor randomCUIColor()
	{
		Random r = new Random();
		return values()[r.nextInt(values().length)];
	}
	
	public static String randomColor()
	{
		Random r = new Random();
		StringBuilder sb = new StringBuilder("#");
		sb.append(Integer.toString(r.nextInt(255*255*255), 16));
		return sb.toString();
	}
}
