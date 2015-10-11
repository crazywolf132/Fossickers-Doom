package com.itsyourpalmike.ld22.crafting;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.resource.Resource;

public class ResourceRecipe extends Recipe
{
	private String resource;
	
	public ResourceRecipe(String resource)
	{
		super(null);
		this.resource = resource;
	}

	public void craft(Player player)
	{
		player.inventory.add(0, new ResourceItem(Resource.get(resource), 1));
	}
}
