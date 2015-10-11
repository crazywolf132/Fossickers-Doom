package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.AirWizard;
import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class InfiniteFallTile extends Tile
{
	public InfiniteFallTile()
	{
		super();
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		
	}

	public void tick(Level level, int xt, int yt)
	{
		
	}

	public boolean mayPass(Level level, int x, int y, Entity e)
	{
		if(e instanceof AirWizard) return true;
		return false;
	}
}
