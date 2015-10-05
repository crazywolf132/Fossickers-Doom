package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.item.FurnitureItem;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.ToolItem;
import com.itsyourpalmike.ld22.item.ToolType;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.screen.ContainerMenu;

public class Chest extends Furniture
{
	public Inventory inventory = new Inventory();

	public Chest(int x, int y)
	{
		super("Chest", x, y);
		col = Color.get(-1, 110, 331, 552);
		sprite = 1;
		xr = 5;
		yr = 2;

		inventory.add(new ResourceItem(Resource.wheat, 64));
		inventory.add(new FurnitureItem(new Anvil(0, 0)));
		
		for (int i = 0; i < 5; i++)
		{
			inventory.add(new ToolItem(ToolType.pickaxe, i));
			inventory.add(new ToolItem(ToolType.hoe, i));
			inventory.add(new ToolItem(ToolType.shovel, i));
			inventory.add(new ToolItem(ToolType.sword, i));
			inventory.add(new ToolItem(ToolType.axe, i));
		}
	}

	public boolean use(Player player, int attackDir)
	{
		player.game.setMenu(new ContainerMenu(player, "Chest", inventory));
		return true;
	}
}
