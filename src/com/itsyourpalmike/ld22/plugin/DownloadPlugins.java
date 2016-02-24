package com.itsyourpalmike.ld22.plugin;

import javax.imageio.ImageIO;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.MinicraftPlugin;
import com.itsyourpalmike.ld22.gfx.SpriteSheet;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.sound.Sound;

public class DownloadPlugins implements MinicraftPlugin
{

	public void onLoad(Game game)
	{
		
	}

	public String getName()
	{
		return "GETMORE PLUGINS";
	}

	@Override
	public boolean autoEnabled()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
