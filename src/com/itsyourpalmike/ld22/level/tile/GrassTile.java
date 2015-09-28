package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class GrassTile extends Tile
{
	public GrassTile(int id)
	{
		super(id);
		connectsToGrass = true;
	}


	public void render(Screen screen, Level level, int x, int y)
	{
		// This render creates smooth corners and shapes, so the world isn't obviously blocky
				int col =  Color.get(level.grassColor, level.grassColor, level.grassColor+111, level.grassColor+111);
				int transitionColor =  Color.get(level.grassColor-111, level.grassColor, level.grassColor+111, level.dirtColor);
				
				boolean u = !level.getTile(x, y-1).connectsToGrass;
				boolean d = !level.getTile(x, y+1).connectsToGrass;
				boolean l = !level.getTile(x-1, y).connectsToGrass;
				boolean r = !level.getTile(x+1, y).connectsToGrass;
				
				if(!u && !l)
				{
					screen.render(x*16+0, y*16+0, 0, col, 0);
				}
				else
				{
					screen.render(x*16+0, y*16+0, (l?11:12) + (u?0:1) * 32, transitionColor, 0);
				}
				
				if(!u && !r)
				{
						screen.render(x*16+8, y*16+0, 1, col, 0);
				}
				else
				{
					screen.render(x*16+8, y*16+0, (r?13:12) + (u?0:1) * 32, transitionColor, 0);
				}
				
				if(!d && !l)
				{
						screen.render(x*16+0, y*16+8, 2, col, 0);
				}
				else
				{
					screen.render(x*16+0, y*16+8, (l?11:12) + (d?2:1) * 32, transitionColor, 0);
				}
				
				if(!d && !r)
				{
						screen.render(x*16+8, y*16+8, 3, col, 0);
				}
				else
				{
					screen.render(x*16+8, y*16+8, (r?13:12) + (d?2:1) * 32, transitionColor, 0);
				}
	}
}
