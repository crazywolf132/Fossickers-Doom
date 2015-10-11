package com.itsyourpalmike.ld22.item.resource;

import java.util.HashMap;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.level.tile.Tile;
import com.itsyourpalmike.ld22.sound.Sound;

public class Resource
{
	private static HashMap<String, Resource> resources = new HashMap<String, Resource>();
	
	public static void load(Resource resource)
	{
		resources.put(resource.name.toUpperCase(), resource);
	}
	
	public static Resource get(String name)
	{
		return resources.get(name.toUpperCase());
	}

	public final String name;
	public final int sprite;
	public final int color;

	public Resource(String name, int sprite, int color)
	{
		if (name.length() > 6) throw new RuntimeException("Resource name cannot be longer than six characters!");
		this.name = name;
		this.sprite = sprite;
		this.color = color;
	}

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir)
	{
		
		return false;
	}
}
