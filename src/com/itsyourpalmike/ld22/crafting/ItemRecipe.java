package com.itsyourpalmike.ld22.crafting;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.plugin.UltimatePlugin;

public class ItemRecipe extends Recipe
{
	private Class<? extends Item> clazz;
	
	public ItemRecipe(Class<? extends Item> clazz) throws InstantiationException, IllegalAccessException
	{
		super(clazz.newInstance());
		this.clazz = clazz;
	}

	public void craft(Player player)
	{
		try
		{
			player.inventory.add(0, clazz.newInstance());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	
}
