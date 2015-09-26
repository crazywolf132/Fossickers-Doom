package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class GrassTile extends Tile
{
	public GrassTile(int id)
	{
		super(id);
	}


	public void render(Screen screen, Level level, int x, int y)
	{
		int col =  Color.get(level.grassColor,  level.grassColor, level.grassColor+111, level.grassColor+111);
		screen.render(x*16+0, y*16+0, 0, col, 0);
		screen.render(x*16+8, y*16+0, 1, col, 0);
		screen.render(x*16+0, y*16+8, 2, col, 0);
		screen.render(x*16+8, y*16+8, 3, col, 0);
	}
}
