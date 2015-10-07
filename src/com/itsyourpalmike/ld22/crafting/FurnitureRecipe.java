package com.itsyourpalmike.ld22.crafting;

import com.itsyourpalmike.ld22.entity.Furniture;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.FurnitureItem;

public class FurnitureRecipe extends Recipe
{

	public FurnitureRecipe(Class<? extends Furniture> class1) throws InstantiationException, IllegalAccessException
	{
		super(new FurnitureItem(class1.newInstance()));
	}

	public void craft(Player player)
	{
		
	}

}
