package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.crafting.Crafting;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.screen.CraftingMenu;

public class Oven extends Furniture
{
	public Oven()
	{
		super("Oven");
		col = Color.get(-1, 000, 332, 552);
		sprite = 2;
		xr = 3;
		yr = 2;
	}

	public boolean use(Player player, int attackDir)
	{
		player.game.setMenu(new CraftingMenu(Crafting.ovenRecipes, player));
		return true;
	}
}
