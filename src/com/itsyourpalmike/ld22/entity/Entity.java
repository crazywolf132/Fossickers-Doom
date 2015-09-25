package com.itsyourpalmike.ld22.entity;

import java.util.Random;

import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class Entity
{
	protected final Random random = new Random();
	public int x, y;
	public int xr = 6;
	public int yr = 6;
	public boolean removed;
	public Level level;
	
	public void render(Screen screen)
	{
		
	}
	
	public void tick()
	{
		
	}
	
	public void remove()
	{
		removed = true;
	}

	public final void init(Level level)
	{
		this.level = level;
	}

	public boolean intersects(int x0, int y0, int x1, int y1)
	{
		return !(x+xr<x0 || y+yr < y0 || x-xr>x1 || y-yr>y1);
	}

	public boolean blocks(Mob mob)
	{
		return false;
	}

	public void hurt(Mob mob, int i, int attackDir)
	{
		
	}
}
