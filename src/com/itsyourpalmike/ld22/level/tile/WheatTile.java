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

public class WheatTile extends Tile
{
	public WheatTile(int id)
	{
		super(id);
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		int col = Color.get(level.dirtColor - 121, level.dirtColor - 11, level.dirtColor, 50);
		
		int age = level.getData(x, y);
		int icon = age / 10;
		if (icon >= 3)
		{
			col = Color.get(level.dirtColor - 121, level.dirtColor - 11, 50+(icon)*100, 40+(icon-3)*2*100);
			icon = 3;
		}

		screen.render(x * 16 + 0, y * 16 + 0, 4 + 3 * 32 + icon, col, 0);
		screen.render(x * 16 + 8, y * 16 + 0, 4 + 3 * 32 + icon, col, 0);
		screen.render(x * 16 + 0, y * 16 + 8, 4 + 3 * 32 + icon, col, 1);
		screen.render(x * 16 + 8, y * 16 + 8, 4 + 3 * 32 + icon, col, 1);
	}

	public void tick(Level level, int xt, int yt)
	{
		int age = level.getData(xt, yt);
		if (age < 50) level.setData(xt, yt, age + 1);
	}

	public void interact(Level level, int xt, int yt, Player player, Item item, int attackDir)
	{
		if (item instanceof ToolItem)
		{
			ToolItem tool = (ToolItem)item;
			if (tool.type == ToolType.shovel)
			{
				player.stamina -= 4 - tool.level;
				level.setTile(xt, yt, Tile.dirt, 0);
			}
		}
	}
	
	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{
		int age = level.getData(x, y);
		int count = random.nextInt(2);
		
		for (int i = 0; i < count; i++)
		{
			level.add(new ItemEntity(new ResourceItem(Resource.seeds), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
		}
		
		count = 0;
		if(age==50)
		{
			count = random.nextInt(3)+2;
			
		}
		else if(age >= 40)
		{
			count = random.nextInt(2)+1;
		}
		for (int i = 0; i < count; i++)
		{
			level.add(new ItemEntity(new ResourceItem(Resource.wheat), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
		}
		
		level.setTile(x, y, Tile.dirt, 0);
	}
}