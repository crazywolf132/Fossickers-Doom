package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.screen.ContainerMenu;

public class Chest extends Furniture
{
	public Inventory inventory = new Inventory();

	public Chest()
	{
		super("Chest");
		col = Color.get(-1, 110, 331, 552);
		sprite = 1;
		xr = 5;
		yr = 2;
	}

	public boolean use(Player player, int attackDir)
	{
		player.game.setMenu(new ContainerMenu(player, "Chest", inventory));
		return true;
	}
}
