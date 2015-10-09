package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.ToolItem;
import com.itsyourpalmike.ld22.item.ToolType;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.level.Level;

public class InfiniteFallTile extends Tile
{
	public InfiniteFallTile(int id)
	{
		super(id);
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		
	}

	public void tick(Level level, int xt, int yt)
	{
		
	}

	public boolean mayPass(Level level, int x, int y, Entity e)
	{
		return false;
	}
}
