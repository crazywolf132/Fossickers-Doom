package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;

public class Chest extends Furniture
{
	public Inventory inventory;
	
	public Chest(int x, int y)
	{
		super(x,y);
		col = Color.get(-1, 110, 331, 552);
		sprite = 2+8*32;
		xr = 5;
		yr = 2;
	}
}
