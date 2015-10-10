package com.itsyourpalmike.ld22;

import net.xeoh.plugins.base.Plugin;

public interface MinicraftPlugin extends Plugin
{
	int g = 4;
	
	void onLoad(Game game);
	
	public default void ocws()
	{
		
	}
}
