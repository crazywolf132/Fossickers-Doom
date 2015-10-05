package com.itsyourpalmike.ld22.item;

import java.util.Random;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;

// This class is used to create all tools in the game + keep track of their material type (Wood, Stone, Etc...)
public class PowerGloveItem extends Item
{
	public int getColor()
	{
		return Color.get(-1, 100, 320, 430);
	}

	public int getSprite()
	{
		return 7 + 4 * 32;
	}

	public void renderIcon(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0);
	}

	public void renderInventory(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0);
		Font.draw("Power glove", screen, x + 8, y, Color.get(-1, 555, 555, 555));
	}

	public void onTake(ItemEntity itemEntity)
	{
		
	}
}
