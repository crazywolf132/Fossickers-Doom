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

public class BetterFlowerTile extends GrassTile
{
	int type = -1;
	int flowerColor;
	public BetterFlowerTile()
	{
		super();
		connectsToGrass = true;
		 type = -1;
	}
	
	public BetterFlowerTile(int type)
	{
		super();
		connectsToGrass = true;
		this.type = type;
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		super.render(screen, level, x, y); // Render the grass
		int data = level.getData(x, y);
		int shape = (data / 16) % 2;
		flowerColor = data%16;
		if(type != -1) flowerColor = type;
		

		int flowerCol = 0;
		if(flowerColor == 0) flowerCol = Color.get(10, level.grassColor, 555, 440);
		if(flowerColor == 1) flowerCol = Color.get(10, level.grassColor, 511, 400);
		if(flowerColor == 2) flowerCol = Color.get(10, level.grassColor, 115, 445);
		if(flowerColor == 3) flowerCol = Color.get(10, level.grassColor, 111, 000);

		// Unique flower shapes (TL, TR, BL, BR)
		if (shape == 0) screen.render(x * 16 + 0, y * 16 + 0, 1 + 1 * 32, flowerCol, 0);
		if (shape == 1) screen.render(x * 16 + 8, y * 16 + 0, 1 + 1 * 32, flowerCol, 0);
		if (shape == 1) screen.render(x * 16 + 0, y * 16 + 8, 1 + 1 * 32, flowerCol, 0);
		if (shape == 3) screen.render(x * 16 + 8, y * 16 + 8, 1 + 1 * 32, flowerCol, 0);
	}

	// If we hurt a flower with a bare hand, 2 flowers pop out???
	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{
		int data = level.getData(x, y);
		int shape = (data / 16) % 2;
		flowerColor = data%16;
		if(type != -1) flowerColor = type;
		
		int count = random.nextInt(2) + 1;
		for (int i = 0; i < count; i++)
		{
			if(flowerColor == 0)
	level.add(new ItemEntity(new ResourceItem(Resource.get("daisy")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
			if(flowerColor == 1)
	level.add(new ItemEntity(new ResourceItem(Resource.get("rose")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
			if(flowerColor == 2)
	level.add(new ItemEntity(new ResourceItem(Resource.get("salvia")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
			if(flowerColor == 3)
	level.add(new ItemEntity(new ResourceItem(Resource.get("b.rose")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
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
					
					int data = level.getData(x, y);
					int shape = (data / 16) % 2;
					flowerColor = data%16;
					if(type != -1) flowerColor = type;
					
					if(flowerColor == 0)
						level.add(new ItemEntity(new ResourceItem(Resource.get("daisy")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
								if(flowerColor == 1)
						level.add(new ItemEntity(new ResourceItem(Resource.get("rose")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
								if(flowerColor == 2)
						level.add(new ItemEntity(new ResourceItem(Resource.get("salvia")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
								if(flowerColor == 3)
						level.add(new ItemEntity(new ResourceItem(Resource.get("b.rose")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
					return true;
				}
			}
		}
		return false;
	}
}
