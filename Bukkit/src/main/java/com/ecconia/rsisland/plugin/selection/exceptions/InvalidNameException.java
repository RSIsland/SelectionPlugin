package com.ecconia.rsisland.plugin.selection.exceptions;

import com.ecconia.rsisland.framework.cofami.exceptions.CommandException;

@SuppressWarnings("serial")
public class InvalidNameException extends CommandException
{
	public InvalidNameException(String message, Object... args)
	{
		super(message, args);
	}
}
