package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;

public class Anvil extends Furniture
{
	public Anvil(int x, int y)
	{
		super(x,y);
		col = Color.get(-1, 000, 111, 222);
		sprite = 0+8*32;
		xr = 3;
		yr = 2;
	}
}
