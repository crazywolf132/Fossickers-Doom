package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.entity.Mob;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.ToolItem;
import com.itsyourpalmike.ld22.item.ToolType;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.level.Level;

public class SandTile extends Tile
{
	public SandTile()
	{
		super();
		connectsToSand = true;
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		// This render creates smooth corners and shapes, so the world isn't obviously blocky
		int col = Color.get(level.sandColor + 2, level.sandColor, level.sandColor - 110, level.sandColor - 110);
		int transitionColor = Color.get(level.sandColor - 110, level.sandColor, level.sandColor - 110, level.dirtColor);

		boolean u = !level.getTile(x, y - 1).connectsToSand;
		boolean d = !level.getTile(x, y + 1).connectsToSand;
		boolean l = !level.getTile(x - 1, y).connectsToSand;
		boolean r = !level.getTile(x + 1, y).connectsToSand;

		boolean steppedOn = level.getData(x, y) > 0;

		if (!u && !l)
		{
			if (!steppedOn) screen.render(x * 16 + 0, y * 16 + 0, 0, col, 0);
			else screen.render(x * 16 + 0, y * 16 + 0, 3 + 1 * 32, col, 0);
		}
		else
		{
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 11 : 12) + (u ? 0 : 1) * 32, transitionColor, 0);
		}

		if (!u && !r)
		{
			screen.render(x * 16 + 8, y * 16 + 0, 1, col, 0);
		}
		else
		{
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 13 : 12) + (u ? 0 : 1) * 32, transitionColor, 0);
		}

		if (!d && !l)
		{
			screen.render(x * 16 + 0, y * 16 + 8, 2, col, 0);
		}
		else
		{
			screen.render(x * 16 + 0, y * 16 + 8, (l ? 11 : 12) + (d ? 2 : 1) * 32, transitionColor, 0);
		}

		if (!d && !r)
		{
			if (!steppedOn) screen.render(x * 16 + 8, y * 16 + 8, 3, col, 0);
			else screen.render(x * 16 + 8, y * 16 + 8, 3 + 1 * 32, col, 0);
		}
		else
		{
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 13 : 12) + (d ? 2 : 1) * 32, transitionColor, 0);
		}
	}

	// Make footprints in sand!!!
	/////////////////////////////////////////////////////////////////
	public void tick(Level level, int x, int y)
	{
		int d = level.getData(x, y);
		if (d > 0) level.setData(x, y, d - 1);
	}

	public void steppedOn(Level level, int x, int y, Entity entity)
	{
		if (entity instanceof Mob)
		{
			level.setData(x, y, 10);
		}
	}
	/////////////////////////////////////////////////////////////////

	// If a player digs sand with a shovel, make a hole!!! - DUH!!!
	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir)
	{
		if (item instanceof ToolItem)
		{
			ToolItem tool = (ToolItem)item;
			if (tool.type == ToolType.shovel)
			{
				if (player.payStamina(4 - tool.level))
				{
					level.setTile(xt, yt, Tile.get("hole"), 0);
					level.add(new ItemEntity(new ResourceItem(Resource.get("Sand")), xt * 16 + random.nextInt(10) + 3, yt * 16 + random.nextInt(10) + 3));
					return true;
				}
			}
		}
		return false;
	}
}
