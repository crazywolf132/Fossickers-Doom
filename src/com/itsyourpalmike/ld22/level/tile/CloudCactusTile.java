package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.AirWizard;
import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.Mob;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.entity.particles.SmashParticle;
import com.itsyourpalmike.ld22.entity.particles.TextParticle;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.ToolItem;
import com.itsyourpalmike.ld22.item.ToolType;
import com.itsyourpalmike.ld22.level.Level;

public class CloudCactusTile extends Tile
{
	public CloudCactusTile()
	{
		super();
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		int color = Color.get(444, 111, 333, 555);
		screen.render(x * 16 + 0, y * 16 + 0, 17 + 1 * 32, color, 0);
		screen.render(x * 16 + 8, y * 16 + 0, 18 + 1 * 32, color, 0);
		screen.render(x * 16 + 0, y * 16 + 8, 17 + 2 * 32, color, 0);
		screen.render(x * 16 + 8, y * 16 + 8, 18 + 2 * 32, color, 0);
	}

	public boolean mayPass(Level level, int x, int y, Entity e)
	{
		if (e instanceof AirWizard) return true;
		return false;
	}

	public void hurt(Level level, int x, int y, int dmg)
	{
		// We use the level data array to set rock's damage/life
		int damage = level.getData(x, y) + 1;
		level.add(new SmashParticle(level, x * 16 + 8, y * 16 + 8));
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));

		if (dmg > 0)
		{
			if (damage >= 10)
			{
				level.setTile(x, y, Tile.get("cloud"), 0);
			}
			else
			{
				level.setData(x, y, damage);
			}
		}
	}

	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{
		hurt(level, x, y, 0);
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir)
	{
		if (item instanceof ToolItem)
		{
			ToolItem tool = (ToolItem)item;
			if (tool.type == ToolType.pickaxe)
			{
				if (player.payStamina(6 - tool.level))
				{
					hurt(level, xt, yt, 1);
					return true;
				}
			}
		}
		return false;
	}

	// If something bumps into a cactus, hurt that something!!! - DUH!!!
	public void bumpedInto(Level level, int x, int y, Entity entity)
	{
		if (entity instanceof AirWizard) return;
		entity.hurt(this, x, y, 3);
	}
}
