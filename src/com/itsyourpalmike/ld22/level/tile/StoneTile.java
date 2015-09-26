package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class StoneTile extends Tile
{
	public StoneTile(int id)
	{
		super(id);
	}


	public void render(Screen screen, Level level, int x, int y)
	{
		int col =  Color.get(4, 4, 5, 5);
		int transitionColor =  Color.get(100, 4, 211, level.grassColor);
		
		boolean u = level.getTile(x, y-1) != this;
		boolean d = level.getTile(x, y+1) != this;
		boolean l = level.getTile(x-1, y) != this;
		boolean r = level.getTile(x+1, y) != this;
		
		boolean ul = level.getTile(x-1, y-1) != this;
		boolean dl = level.getTile(x-1, y+1) != this;
		boolean ur = level.getTile(x+1, y-1) != this;
		boolean dr = level.getTile(x+1, y+1) != this;
		
		if(!u && !l)
		{
			if(!ul)
				screen.render(x*16+0, y*16+0, 0, col, 0);
			else
				screen.render(x*16+0, y*16+0, 8 + 1 * 32, transitionColor, 0);
				
		}
		else
		{
			screen.render(x*16+0, y*16+0, (l?4:5) + (u?0:1) * 32, transitionColor, 0);
		}
		
		if(!u && !r)
		{
			if(!ur)
				screen.render(x*16+8, y*16+0, 1, col, 0);
			else
				screen.render(x*16+8, y*16+0, 7 + 1 * 32, transitionColor, 0);
		}
		else
		{
			screen.render(x*16+8, y*16+0, (r?6:5) + (u?0:1) * 32, transitionColor, 0);
		}
		
		if(!d && !l)
		{
			if(!dl)
				screen.render(x*16+0, y*16+8, 2, col, 0);
			else
				screen.render(x*16+0, y*16+8, 8 + 0 * 32, transitionColor, 0);
		}
		else
		{
			screen.render(x*16+0, y*16+8, (l?4:5) + (d?2:1) * 32, transitionColor, 0);
		}
		
		if(!d && !r)
		{
			if(!dr)
				screen.render(x*16+8, y*16+8, 3, col, 0);
			else
				screen.render(x*16+8, y*16+8, 7 + 0 * 32, transitionColor, 0);
		}
		else
		{
			screen.render(x*16+8, y*16+8, (r?6:5) + (d?2:1) * 32, transitionColor, 0);
		}
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e)
	{
		return false;
	}
}
