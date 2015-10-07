package com.itsyourpalmike.ld22.crafting;

import com.itsyourpalmike.ld22.entity.Furniture;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.FurnitureItem;

public class FurnitureRecipe extends Recipe
{
	private Class<? extends Furniture> clazz;
	
	public FurnitureRecipe(Class<? extends Furniture> clazz) throws InstantiationException, IllegalAccessException
	{
		super(new FurnitureItem(clazz.newInstance()));
		this.clazz = clazz;
	}

	public void craft(Player player)
	{
		try
		{
			player.inventory.add(0, new FurnitureItem(clazz.newInstance()));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}
