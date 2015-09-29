package com.itsyourpalmike.ld22.item;

import com.itsyourpalmike.ld22.gfx.Color;

public class Resource
{
	public static Resource wood = new Resource("Wood", 1+4*32, Color.get(-1, 200, 531, 430));
	public static Resource stone = new Resource("Stone", 2+4*32, Color.get(-1, 111, 333, 555));
	public static Resource flower = new Resource("Flower", 0+4*32, Color.get(-1, 10, 555, 440));
	
	public final String name;
	public final int sprite;
	public final int color;
	
	public Resource(String name, int sprite, int color)
	{
		this.name = name;
		this.sprite = sprite;
		this.color = color;
	}

}