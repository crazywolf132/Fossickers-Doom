package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.ToolItem;
import com.itsyourpalmike.ld22.item.ToolType;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.level.Level;

public class GrassTile extends Tile
{
	public GrassTile(int id)
	{
		super(id);
		connectsToGrass = true;
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		// This render creates smooth corners and shapes, so the world isn't obviously blocky
		int col = Color.get(level.grassColor, level.grassColor, level.grassColor + 111, level.grassColor + 111);
		int transitionColor = Color.get(level.grassColor - 111, level.grassColor, level.grassColor + 111, level.dirtColor);

		boolean u = !level.getTile(x, y - 1).connectsToGrass;
		boolean d = !level.getTile(x, y + 1).connectsToGrass;
		boolean l = !level.getTile(x - 1, y).connectsToGrass;
		boolean r = !level.getTile(x + 1, y).connectsToGrass;

		if (!u && !l)
		{
			screen.render(x * 16 + 0, y * 16 + 0, 0, col, 0);
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
			screen.render(x * 16 + 8, y * 16 + 8, 3, col, 0);
		}
		else
		{
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 13 : 12) + (d ? 2 : 1) * 32, transitionColor, 0);
		}
	}

	// Spread grass onto adjacent dirt tiles
	public void tick(Level level, int xt, int yt)
	{
		if (random.nextInt(10) != 0) return; // Growing offset delay thing-a-ma-bob
		
		int xn = xt;
		int yn = yt;

		if (random.nextBoolean()) xn += random.nextInt(2) * 2 - 1;
		else yn += random.nextInt(2) * 2 - 1;

		if (level.getTile(xn, yn) == Tile.dirt)
		{
			level.setTile(xn, yn, this, 0);
		}
	}

	// Turn grass to dirt when a shovel is used - AKA the player is digging
	public void interact(Level level, int xt, int yt, Player player, Item item, int attackDir)
	{
		if (item instanceof ToolItem)
		{
			ToolItem tool = (ToolItem)item;
			if (tool.type == ToolType.shovel)
			{
				player.stamina-=4-tool.level;
				level.setTile(xt, yt, Tile.dirt, 0);
				if(random.nextInt(5) == 0)
				{
					level.add(new ItemEntity(new ResourceItem(Resource.seeds), xt * 16 + random.nextInt(10) + 3, yt * 16 + random.nextInt(10) + 3));	
				}
			}
			if (tool.type == ToolType.hoe)
			{
				player.stamina-=4-tool.level;
				level.setTile(xt, yt, Tile.farmland, 0);
			}
		}
	}
}
