package com.itsyourpalmike.ld22.entity;

import java.util.List;
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

	public boolean blocks(Entity e)
	{
		return false;
	}

	public void hurt(Mob mob, int i, int attackDir)
	{
		
	}
	
	public boolean move(int xa, int ya)
	{
		if (xa != 0 || ya != 0)
		{
			boolean stopped = true;
			if (xa != 0 && move2(xa, 0)) stopped = false;
			if (ya != 0 && move2(0, ya)) stopped = false;
			return !stopped;
		}

		return true;
	}

	protected boolean move2(int xa, int ya)
	{
		if (xa != 0 && ya != 0) throw new IllegalArgumentException("Move2 can only mone alone one axis at time!");
		for (int c = 0; c < 4; c++)
		{
			int xt = ((x + xa) + (c % 2 * 2 - 1) * xr) >> 4;
			int yt = ((y + ya) + (c / 2 * 2 - 1) * yr) >> 4;
			if (!level.getTile(xt, yt).mayPass(level, xt, yt, this))
			{
				return false;
			}
		}
		
		List<Entity> wasInside = level.getEntities( x - xr, y - yr, x + xr, y + yr);
		List<Entity> isInside = level.getEntities( x + xa - xr, y + ya - yr, x + xa + xr, y + ya + yr);
		isInside.removeAll(wasInside);
		for(int i = 0; i < isInside.size(); i++)
		{
			Entity e = isInside.get(i);
			if(e == this) continue;
			if(e.blocks(this))
			{
				return false;
			}
		}

		x += xa;
		y += ya;
		return true;
	}
}
