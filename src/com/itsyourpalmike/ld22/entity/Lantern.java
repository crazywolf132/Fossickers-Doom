package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.crafting.Crafting;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.screen.CraftingMenu;

public class Lantern extends Furniture
{
	public Lantern()
	{
		super("Lantern");
		col = Color.get(-1, 000, 111, 555);
		sprite = 5;
		xr = 3;
		yr = 2;
	}

	public int getLightRadius()
	{
		return 8;
	}
	
	
}
