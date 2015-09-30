package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;

public class Chest extends Furniture
{
	public Chest(int x, int y)
	{
		super(x,y);
		col = Color.get(-1, 110, 331, 552);
		sprite = 2+8*32;
		xr = 3;
		yr = 2;
	}
}
