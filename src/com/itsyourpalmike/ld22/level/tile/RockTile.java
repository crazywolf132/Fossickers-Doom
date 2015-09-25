package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class RockTile extends Tile
{
	public RockTile(int id)
	{
		super(id);
	}


	public void render(Screen screen, Level level, int x, int y)
	{
		int rc1 = 11;
		int rc2 = 333;
		int rc3 = 555;
		screen.render(x*16+0, y*16+0, 32, Color.get(rc1,  level.grassColor, rc2, rc3), 0);
		screen.render(x*16+8, y*16+0, 32, Color.get(rc1,  level.grassColor, rc2, rc3), 0);
		screen.render(x*16+0, y*16+8, 32, Color.get(rc1,  level.grassColor, rc2, rc3), 0);
		screen.render(x*16+8, y*16+8, 32, Color.get(rc1,  level.grassColor, rc2, rc3), 0);
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e)
	{
		return false;
	}
}
