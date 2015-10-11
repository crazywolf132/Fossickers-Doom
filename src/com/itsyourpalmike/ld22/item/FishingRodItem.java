package com.itsyourpalmike.ld22.item;

import java.util.Random;

import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.level.tile.Tile;
import com.itsyourpalmike.ld22.plugin.UltimatePlugin;

// This class is used to create all tools in the game + keep track of their material type (Wood, Stone, Etc...)
public class FishingRodItem extends Item
{
	private Random random = new Random();
	
	public FishingRodItem()
	{
		super();
		sheet = UltimatePlugin.ultimateSheet;
	}

	public int getColor()
	{
		return Color.get(-1, 100, 321, 555);
	}

	public int getSprite()
	{
		return 0 + 0 * 32;
	}

	public void renderIcon(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0, UltimatePlugin.ultimateSheet);
	}

	public void renderInventory(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0, UltimatePlugin.ultimateSheet);
		Font.draw(this.getName(), screen, x + 8, y, Color.get(-1, 555, 555, 555));
	}

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir)
	{
		if (tile.id == Tile.get("water").id)
		{
			if (random.nextInt(50) != 0) return false;
			
			if (player.payStamina(3))
			{
				int count = 1;
				for (int i = 0; i < count; i++)
				{
					level.add(new ItemEntity(new ResourceItem(Resource.get("fish")), xt * 16 + random.nextInt(10) + 3, yt * 16 + random.nextInt(10) + 3));
				}
				return true;
			}
		}
		return false;
	}

	public String getName()
	{
		return "Fishn Rod";
	}
}