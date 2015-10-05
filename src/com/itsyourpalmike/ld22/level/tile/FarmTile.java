package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.ToolItem;
import com.itsyourpalmike.ld22.item.ToolType;
import com.itsyourpalmike.ld22.level.Level;

public class FarmTile extends Tile
{
	public FarmTile(int id)
	{
		super(id);
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		int col = Color.get(level.dirtColor-121, level.dirtColor-11, level.dirtColor, level.dirtColor + 111);

		screen.render(x * 16 + 0, y * 16 + 0, 2 + 32, col, 1);
		screen.render(x * 16 + 8, y * 16 + 0,  2 + 32, col, 0);
		screen.render(x * 16 + 0, y * 16 + 8,  2 + 32, col, 0);
		screen.render(x * 16 + 8, y * 16 + 8,  2 + 32, col, 1);
	}

	public void interact(Level level, int xt, int yt, Player player, Item item, int attackDir)
	{
		if (item instanceof ToolItem)
		{
			ToolItem tool = (ToolItem)item;
			if (tool.type == ToolType.shovel)
			{
				player.stamina-=4-tool.level;
				level.setTile(xt, yt, Tile.dirt, 0);
			}
		}
	}
}
