package com.itsyourpalmike.ld22.level.tile;

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
import com.itsyourpalmike.ld22.plugin.UltimatePlugin;

public class MushroomTile extends GrassTile
{
	public MushroomTile()
	{
		super();
		connectsToGrass = true;
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		super.render(screen, level, x, y); // Render the grass
		int data = level.getData(x, y);
		int shape = (data / 16) % 2;
		//shape = 0;

		int flowerCol = Color.get(533, level.grassColor, 500, 220);

		// Unique flower shapes (TL, TR, BL, BR)
		if (shape == 0) screen.render(x * 16 + 0, y * 16 + 0, 4 + 0 * 32, flowerCol, 0, UltimatePlugin.ultimateSheet);
		if (shape == 1) screen.render(x * 16 + 8, y * 16 + 0, 4 + 0 * 32, flowerCol, 0, UltimatePlugin.ultimateSheet);
		if (shape == 2) screen.render(x * 16 + 0, y * 16 + 8, 4 + 0 * 32, flowerCol, 0, UltimatePlugin.ultimateSheet);
		if (shape == 3) screen.render(x * 16 + 8, y * 16 + 8, 4 + 0 * 32, flowerCol, 0, UltimatePlugin.ultimateSheet);
	}

	// If we hurt a flower with a bare hand, 2 flowers pop out???
	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{
		int count = 1;
		for (int i = 0; i < count; i++)
		{
			level.add(new ItemEntity(new ResourceItem(Resource.get("shroom")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
		}
		level.setTile(x, y, Tile.get("grass"), 0);
	}

	// If we hurt a flower with a shovel, 2 flowers pop out??? + the grass is turned to dirt!
	public boolean interact(Level level, int x, int y, Player player, Item item, int attackDir)
	{
		if (item instanceof ToolItem)
		{
			ToolItem tool = (ToolItem)item;
			if (tool.type == ToolType.shovel)
			{
				if (player.payStamina(4 - tool.level))
				{
					level.setTile(x, y, Tile.get("dirt"), 0);
					level.add(new ItemEntity(new ResourceItem(Resource.get("shroom")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
					return true;
				}
			}
		}
		return false;
	}
}
