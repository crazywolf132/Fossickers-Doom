package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.crafting.Crafting;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.screen.CraftingMenu;

public class Torch extends Furniture
{
	public Torch()
	{
		super("Torch");
		col = Color.get(-1, 000, 332, 442);
		sprite = 5;
		xr = 3;
		yr = 2;
	}

	public boolean use(Player player, int attackDir)
	{
		player.game.setMenu(new CraftingMenu(Crafting.ovenRecipes, player));
		return true;
	}

	public int getLightRadius()
	{
		return 8;
	}
	
	
}
