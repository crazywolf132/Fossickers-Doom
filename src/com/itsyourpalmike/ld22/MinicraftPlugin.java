package com.itsyourpalmike.ld22;

import net.xeoh.plugins.base.Plugin;

public interface MinicraftPlugin extends Plugin
{
	void onLoad(Game game);

	String getName();
	
	boolean autoEnabled();
}
