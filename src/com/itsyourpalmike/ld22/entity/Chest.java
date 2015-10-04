package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.item.ToolItem;
import com.itsyourpalmike.ld22.item.ToolType;
import com.itsyourpalmike.ld22.screen.ContainerMenu;

public class Chest extends Furniture
{
	public Inventory inventory = new Inventory();

	public Chest(int x, int y)
	{
		super(x, y);
		col = Color.get(-1, 110, 331, 552);
		sprite = 2 + 8 * 32;
		xr = 5;
		yr = 2;

		for (int i = 0; i < 5; i++)
		{
			inventory.add(new ToolItem(ToolType.pickaxe, i));
			inventory.add(new ToolItem(ToolType.hoe, i));
			inventory.add(new ToolItem(ToolType.shovel, i));
			inventory.add(new ToolItem(ToolType.sword, i));
			inventory.add(new ToolItem(ToolType.pickaxe, i));
		}
	}

	public boolean use(Player player, int attackDir)
	{
		player.game.setMenu(new ContainerMenu(player, "Chest", inventory));
		return true;
	}
}
