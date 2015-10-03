package com.itsyourpalmike.ld22.item;

import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.entity.particles.TextParticle;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;

public class ToolItem extends Item
{
	public static final int MAX_LEVEL = 5;
	public static final String[] LEVEL_NAMES =
	{
		"Wood", "Rock", "Iron", "Gold", "Gem"
	};
	public static final int[] LEVEL_COLORS =
	{
		Color.get(-1, 100, 321, 431),
		Color.get(-1, 100, 321, 111),
		Color.get(-1, 100, 321, 555),
		Color.get(-1, 100, 321, 550),
		Color.get(-1, 100, 321, 055)
	};
	
	public ToolType type;
	public int level = 0;
	
	public ToolItem(ToolType type, int level)
	{
		this.type = type;
		this.level = level;
	}
	
	public int getColor()
	{
		return LEVEL_COLORS[level];
	}
	
	public int getSprite()
	{
		
		return type.sprite + 5 * 32;
	}
	
	public void renderInventory(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0);
		Font.draw(LEVEL_NAMES[level] + " " +type.name, screen, x+8, y, Color.get(-1, 555, 555, 555));
	}

	public void onTake(ItemEntity itemEntity)
	{
		//itemEntity.level.add(new TextParticle("+"+resource.name, itemEntity.x, itemEntity.y, Color.get(-1, 555, 555, 555)));
	}
}
