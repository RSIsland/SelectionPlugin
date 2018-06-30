package com.ecconia.rsisland.plugin.selection.exceptions;

import com.ecconia.rsisland.framework.cofami.exceptions.CommandException;

@SuppressWarnings("serial")
public class ParseException extends CommandException
{
	public ParseException(String message, Object... args)
	{
		super(message, args);
	}
}
