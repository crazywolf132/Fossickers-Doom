package com.itsyourpalmike.ld22.plugin;

import com.itsyourpalmike.ld22.Game;

import net.xeoh.plugins.base.Plugin;

public interface MinicraftPlugin extends Plugin
{
	void onLoad(Game game);

	String getName();
}
