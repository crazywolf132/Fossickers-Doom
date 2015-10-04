package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.entity.Mob;
import com.itsyourpalmike.ld22.entity.particles.SmashParticle;
import com.itsyourpalmike.ld22.entity.particles.TextParticle;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.level.Level;

public class RockTile extends Tile
{
	public RockTile(int id)
	{
		super(id);
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		// This render creates smooth corners and shapes, so the world isn't obviously blocky
		int col = Color.get(444, 444, 333, 333);
		int transitionColor = Color.get(111, 444, 555, level.dirtColor);

		boolean u = level.getTile(x, y - 1) != this;
		boolean d = level.getTile(x, y + 1) != this;
		boolean l = level.getTile(x - 1, y) != this;
		boolean r = level.getTile(x + 1, y) != this;

		boolean ul = level.getTile(x - 1, y - 1) != this;
		boolean dl = level.getTile(x - 1, y + 1) != this;
		boolean ur = level.getTile(x + 1, y - 1) != this;
		boolean dr = level.getTile(x + 1, y + 1) != this;

		if (!u && !l)
		{
			if (!ul) screen.render(x * 16 + 0, y * 16 + 0, 0, col, 0);
			else screen.render(x * 16 + 0, y * 16 + 0, 7 + 0 * 32, transitionColor, 3);
		}
		else
		{
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 6 : 5) + (u ? 2 : 1) * 32, transitionColor, 3);
		}

		if (!u && !r)
		{
			if (!ur) screen.render(x * 16 + 8, y * 16 + 0, 1, col, 0);
			else screen.render(x * 16 + 8, y * 16 + 0, 8 + 0 * 32, transitionColor, 3);
		}
		else
		{
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 4 : 5) + (u ? 2 : 1) * 32, transitionColor, 3);
		}

		if (!d && !l)
		{
			if (!dl) screen.render(x * 16 + 0, y * 16 + 8, 2, col, 0);
			else screen.render(x * 16 + 0, y * 16 + 8, 7 + 1 * 32, transitionColor, 3);
		}
		else
		{
			screen.render(x * 16 + 0, y * 16 + 8, (l ? 6 : 5) + (d ? 0 : 1) * 32, transitionColor, 3);
		}

		if (!d && !r)
		{
			if (!dr) screen.render(x * 16 + 8, y * 16 + 8, 3, col, 0);
			else screen.render(x * 16 + 8, y * 16 + 8, 8 + 1 * 32, transitionColor, 3);
		}
		else
		{
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 4 : 5) + (d ? 0 : 1) * 32, transitionColor, 3);
		}
	}

	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{
		// We use the level data array to set rock's damage/life
		int damage = level.getData(x, y) + dmg;
		level.add(new SmashParticle(x * 16 + 8, y * 16 + 8));
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));
		
		if (damage >= 32)
		{
			int count = random.nextInt(4) + 1;
			
			for (int i = 0; i < count; i++)
				level.add(new ItemEntity(new ResourceItem(Resource.stone), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
			
			level.setTile(x, y, Tile.dirt, 0);
		}
		else
		{
			level.setData(x, y, damage);
		}
	}

	public boolean mayPass(Level level, int x, int y, Entity e)
	{
		return false;
	}
}