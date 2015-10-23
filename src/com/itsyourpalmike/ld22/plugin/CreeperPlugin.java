package com.itsyourpalmike.ld22.plugin;

import javax.imageio.ImageIO;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.MinicraftPlugin;
import com.itsyourpalmike.ld22.entity.Creeper;
import com.itsyourpalmike.ld22.gfx.SpriteSheet;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.sound.Sound;

public class CreeperPlugin implements MinicraftPlugin
{
	public static SpriteSheet creeperSheet;

	public void onLoad(Game game)
	{
		try
		{
			creeperSheet = new SpriteSheet(ImageIO.read(CreeperPlugin.class.getResource("/creeper.png")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Sound.load("fuse", this.getClass().getResource("/fuse.wav"));
		Level.addMobToLevelSpawner(Creeper.class);
	}

	public String getName()
	{
		return "Creeper Plugin";
	}

	@Override
	public boolean autoEnabled()
	{
		// TODO Auto-generated method stub
		return true;
	}
}
