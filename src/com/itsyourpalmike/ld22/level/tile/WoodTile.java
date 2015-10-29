package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.entity.Mob;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.entity.particles.SmashParticle;
import com.itsyourpalmike.ld22.entity.particles.TextParticle;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.ToolItem;
import com.itsyourpalmike.ld22.item.ToolType;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.plugin.UltimatePlugin;

public class WoodTile extends Tile
{
	public WoodTile()
	{
		super();
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		// This render creates smooth corners and shapes, so the world isn't obviously blocky
		int col = Color.get(-1, 531, 430, 200);
		int transitionColor = Color.get(200, 531, 430, level.dirtColor);
		if(level.getData(x, y) == 10001)
			transitionColor = Color.get(200, 531, 430, level.sandColor);

		boolean u = level.getTile(x, y - 1) != this && !(level.getTile(x, y-1) instanceof StoneWallTile);
		boolean d = level.getTile(x, y + 1) != this && !(level.getTile(x, y+1) instanceof StoneWallTile);
		boolean l = level.getTile(x - 1, y) != this && !(level.getTile(x-1, y) instanceof StoneWallTile);
		boolean r = level.getTile(x + 1, y) != this  && !(level.getTile(x+1, y) instanceof StoneWallTile);

		boolean ul = level.getTile(x - 1, y - 1) != this && !(level.getTile(x - 1, y-1) instanceof StoneWallTile);
		boolean dl = level.getTile(x - 1, y + 1) != this && !(level.getTile(x-1, y+1) instanceof StoneWallTile);
		boolean ur = level.getTile(x + 1, y - 1) != this && !(level.getTile(x+1, y-1) instanceof StoneWallTile);
		boolean dr = level.getTile(x + 1, y + 1) != this && !(level.getTile(x+1, y+1) instanceof StoneWallTile);

		if (!u && !l)
		{
			if (!ul) screen.render(x * 16 + 0, y * 16 + 0, 0, col, 0, UltimatePlugin.icons2);
			else screen.render(x * 16 + 0, y * 16 + 0, 7 + 0 * 32, transitionColor, 3, UltimatePlugin.icons2);
		}
		else
		{
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 6 : 5) + (u ? 2 : 1) * 32, transitionColor, 3, UltimatePlugin.icons2);
		}

		if (!u && !r)
		{
			if (!ur) screen.render(x * 16 + 8, y * 16 + 0, 1, col, 0, UltimatePlugin.icons2);
			else screen.render(x * 16 + 8, y * 16 + 0, 8 + 0 * 32, transitionColor, 3, UltimatePlugin.icons2);
		}
		else
		{
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 4 : 5) + (u ? 2 : 1) * 32, transitionColor, 3, UltimatePlugin.icons2);
		}

		if (!d && !l)
		{
			if (!dl) screen.render(x * 16 + 0, y * 16 + 8, 2, col, 0, UltimatePlugin.icons2);
			else screen.render(x * 16 + 0, y * 16 + 8, 7 + 1 * 32, transitionColor, 3, UltimatePlugin.icons2);
		}
		else
		{
			screen.render(x * 16 + 0, y * 16 + 8, (l ? 6 : 5) + (d ? 0 : 1) * 32, transitionColor, 3, UltimatePlugin.icons2);
		}

		if (!d && !r)
		{
			if (!dr) screen.render(x * 16 + 8, y * 16 + 8, 3, col, 0, UltimatePlugin.icons2);
			else screen.render(x * 16 + 8, y * 16 + 8, 8 + 1 * 32, transitionColor, 3, UltimatePlugin.icons2);
		}
		else
		{
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 4 : 5) + (d ? 0 : 1) * 32, transitionColor, 3, UltimatePlugin.icons2);
		}
	}

	public void hurt(Level level, int x, int y, int dmg)
	{
		// We use the level data array to set rock's damage/life
		int damage = level.getData(x, y) + dmg;
		level.add(new SmashParticle(level, x * 16 + 8, y * 16 + 8));
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));

		if (damage >= 10)
		{
			level.add(new ItemEntity(new ResourceItem(Resource.get("wood")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));

			level.setTile(x, y, Tile.get("dirt"), 0);
		}
		else
		{
			level.setData(x, y, damage);
		}
	}

	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{
		hurt(level, x, y, dmg);
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir)
	{
		if (item instanceof ToolItem)
		{
			ToolItem tool = (ToolItem)item;
			if (tool.type == ToolType.axe)
			{
				if (player.payStamina(4 - tool.level))
				{
					hurt(level, xt, yt, random.nextInt(10) + ((tool.level) * 5 + 10));
					return true;
				}
			}
		}
		return false;
	}

	public boolean mayPass(Level level, int x, int y, Entity e)
	{
		return false;
	}

	public void tick(Level level, int xt, int yt)
	{
		int damage = level.getData(xt, yt);
		if (damage > 0) level.setData(xt, yt, damage - 1);
	}
}