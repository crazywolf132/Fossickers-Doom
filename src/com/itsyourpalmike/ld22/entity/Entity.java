package com.itsyourpalmike.ld22.entity;

import java.util.List;
import java.util.Random;

import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.level.tile.Tile;

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

	public void hurt(Mob mob, int dmg, int attackDir)
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
		int xt0 = ((x + xa) - xr) >> 4;
		int yt0 = ((y + ya) - yr) >> 4;
		int xt1 = ((x + xa) + xr) >> 4;
		int yt1 = ((y + ya) + yr) >> 4;
		
		boolean blocked = false;
		for(int yt = yt0; yt<=yt1;yt++)
		{
			for(int xt = xt0; xt<=xt1;xt++)
			{
					level.getTile(xt, yt).bumpedInto(level, xt, yt, this);
					if (!level.getTile(xt, yt).mayPass(level, xt, yt, this))
					{
						blocked = true;
						return false;
					}
			}
		}
		if(blocked) return false;
	
		
		List<Entity> wasInside = level.getEntities( x - xr, y - yr, x + xr, y + yr);
		List<Entity> isInside = level.getEntities( x + xa - xr, y + ya - yr, x + xa + xr, y + ya + yr);
		isInside.removeAll(wasInside);
		for(int i = 0; i < isInside.size(); i++)
		{
			Entity e = isInside.get(i);
			if(e == this) continue;
			e.touchedBy(this);
		}
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

	protected void touchedBy(Entity entity)
	{
		
	}

	public boolean isBlockableBy(Mob mob)
	{
		
		return true;
	}

	public void touchItem(ItemEntity itemEntity)
	{
		
	}

	public void hurt(Tile tile, int x, int y, int dmg)
	{
		
	}

	public boolean canSwim()
	{
		return false;
	}
}
