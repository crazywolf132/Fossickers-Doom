package com.itsyourpalmike.ld22.item;

import java.util.Random;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.Furniture;
import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.level.tile.Tile;

// This class is used to create all tools in the game + keep track of their material type (Wood, Stone, Etc...)
public class FurnitureItem extends Item
{
	public Furniture furniture;
	public boolean placed = false;

	public FurnitureItem(Furniture furniture)
	{
		this.furniture = furniture;
	}

	public int getColor()
	{
		return furniture.col;
	}

	public int getSprite()
	{
		return furniture.sprite+10*32;
	}

	public void renderIcon(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0);
	}

	public void renderInventory(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0);
		Font.draw(furniture.name, screen, x + 8, y, Color.get(-1, 555, 555, 555));
	}
	
	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir)
	{
		if(tile.mayPass(level, xt, yt, furniture))
		{
			furniture.x = xt*16+8;
			furniture.y = yt*16+8;
			level.add(furniture);
			placed = true;
			return true;
		}
		return false;

	}
	
	public boolean isDepleted()
	{
		return placed;
	}

	public void onTake(ItemEntity itemEntity)
	{
		
	}
	public boolean canAttack()
	{
		return false;
	}
}
