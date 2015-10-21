package com.itsyourpalmike.ld22.crafting;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.resource.Resource;

public class ResourceRecipe extends Recipe
{
	private String resource;

	public ResourceRecipe(String resource)
	{
		super(new ResourceItem(Resource.get(resource)));
		this.resource = resource;
	}

	public void craft(Player player)
	{
		player.inventory.add(0, new ResourceItem(Resource.get(resource), 1));
	}
}
