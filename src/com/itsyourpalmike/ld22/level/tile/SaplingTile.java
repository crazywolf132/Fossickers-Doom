package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.Mob;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class SaplingTile extends Tile
{
	private Tile onType, growsTo;
	private int bgColor;
	
	public SaplingTile(int id, Tile onType, Tile growsTo)
	{
		super(id);
		this.onType = onType;
		this.growsTo = growsTo;
		
		connectsToSand = onType.connectsToSand;
		connectsToGrass = onType.connectsToGrass;
		connectsToWater = onType.connectsToWater;
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		onType.render(screen, level, x, y);
		int col = Color.get(10, 40, 50, -1);
		screen.render(x * 16 + 4, y * 16 + 4, 11 + 3 * 32, col, 0);
	}

	

	public void tick(Level level, int x, int y)
	{
		int age = level.getData(x, y) + 1;
		if(age > 100)
		{
			level.setTile(x, y, growsTo, 0);
		}
		else
		{
			level.setData(x, y, age);
		}
	}

	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{
		level.setTile(x, y, onType, 0);
	}
}
