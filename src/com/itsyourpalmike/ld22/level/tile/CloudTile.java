package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class CloudTile extends Tile
{
	public CloudTile(int id)
	{
		super(id);
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		// This render creates smooth corners and shapes, so the world isn't obviously blocky
		int col = Color.get(444, 444, 555, 555);
		int transitionColor = Color.get(333, 444, 555, -1);

		boolean u = level.getTile(x, y - 1) == Tile.infiniteFall;
		boolean d = level.getTile(x, y + 1) == Tile.infiniteFall;
		boolean l = level.getTile(x - 1, y) == Tile.infiniteFall;
		boolean r = level.getTile(x + 1, y) == Tile.infiniteFall;

		boolean ul = level.getTile(x - 1, y - 1) == Tile.infiniteFall;
		boolean dl = level.getTile(x - 1, y + 1) == Tile.infiniteFall;
		boolean ur = level.getTile(x + 1, y - 1) == Tile.infiniteFall;
		boolean dr = level.getTile(x + 1, y + 1) == Tile.infiniteFall;

		if (!u && !l)
		{
			if (!ul) screen.render(x * 16 + 0, y * 16 + 0, 17, col, 0);
			else screen.render(x * 16 + 0, y * 16 + 0, 7 + 0 * 32, transitionColor, 3);
		}
		else
		{
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 6 : 5) + (u ? 2 : 1) * 32, transitionColor, 3);
		}

		if (!u && !r)
		{
			if (!ur) screen.render(x * 16 + 8, y * 16 + 0, 18, col, 0);
			else screen.render(x * 16 + 8, y * 16 + 0, 8 + 0 * 32, transitionColor, 3);
		}
		else
		{
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 4 : 5) + (u ? 2 : 1) * 32, transitionColor, 3);
		}

		if (!d && !l)
		{
			if (!dl) screen.render(x * 16 + 0, y * 16 + 8, 20, col, 0);
			else screen.render(x * 16 + 0, y * 16 + 8, 7 + 1 * 32, transitionColor, 3);
		}
		else
		{
			screen.render(x * 16 + 0, y * 16 + 8, (l ? 6 : 5) + (d ? 0 : 1) * 32, transitionColor, 3);
		}

		if (!d && !r)
		{
			if (!dr) screen.render(x * 16 + 8, y * 16 + 8, 19, col, 0);
			else screen.render(x * 16 + 8, y * 16 + 8, 8 + 1 * 32, transitionColor, 3);
		}
		else
		{
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 4 : 5) + (d ? 0 : 1) * 32, transitionColor, 3);
		}
	}

	/*public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir)
	{
		if (item instanceof ToolItem)
		{
			ToolItem tool = (ToolItem)item;
			if (tool.type == ToolType.pickaxe)
			{
				if (player.payStamina(4 - tool.level))
				{
					hurt(level, xt, yt, random.nextInt(10) + ((tool.level) * 5 + 10));
					return true;
				}
			}
		}
		return false;
	}*/

	public boolean mayPass(Level level, int x, int y, Entity e)
	{
		return true;
	}
}