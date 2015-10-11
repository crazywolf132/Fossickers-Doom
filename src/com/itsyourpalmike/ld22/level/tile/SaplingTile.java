package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.Mob;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class SaplingTile extends Tile
{
	private String onType, growsTo;
	private int bgColor;
	
	public SaplingTile(String onType, String growsTo)
	{
		super();
		this.onType = onType;
		this.growsTo = growsTo;
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		connectsToSand = Tile.get(onType).connectsToSand;
		connectsToGrass = Tile.get(onType).connectsToGrass;
		connectsToWater = Tile.get(onType).connectsToWater;
		connectsToLava = Tile.get(onType).connectsToLava;
		
		Tile.get(onType).render(screen, level, x, y);
		int col = Color.get(10, 40, 50, -1);
		screen.render(x * 16 + 4, y * 16 + 4, 11 + 3 * 32, col, 0);
	}

	

	public void tick(Level level, int x, int y)
	{
		int age = level.getData(x, y) + 1;
		if(age > 100)
		{
			level.setTile(x, y, Tile.get(growsTo), 0);
		}
		else
		{
			level.setData(x, y, age);
		}
	}

	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{
		System.out.println("Planting sapling");
		level.setTile(x, y, Tile.get(onType), 0);
	}
}
