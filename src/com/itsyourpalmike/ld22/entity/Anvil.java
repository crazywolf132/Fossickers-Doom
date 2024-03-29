package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.crafting.Crafting;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.screen.CraftingMenu;

public class Anvil extends Furniture
{
	public Anvil()
	{
		super("Anvil");
		col = Color.get(-1, 000, 111, 222);
		sprite = 0;
		xr = 3;
		yr = 2;
	}

	public boolean use(Player player, int attackDir)
	{
		player.game.setMenu(new CraftingMenu(Crafting.anvilRecipes, player));
		return true;
	}
}
