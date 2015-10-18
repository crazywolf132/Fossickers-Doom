package com.itsyourpalmike.ld22.item.resource;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.SpriteSheet;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.level.tile.Tile;

public class DyeResource extends Resource
{
	public int mainColor;

	public DyeResource(String name, int sprite, int mainC)
	{
		super(name, sprite, Color.get(-1, mainC, mainC, mainC), null);
		mainColor = mainC;
	}

	public DyeResource(String name, int sprite, int mainC, SpriteSheet sheet)
	{
		super(name, sprite, Color.get(-1, mainC, mainC, mainC), sheet);
		mainColor = mainC;
	}

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir)
	{
		if (player.shirtColor != mainColor)
		{
			player.shirtColor = mainColor;
			return true;
		}
		return false;
	}

}
