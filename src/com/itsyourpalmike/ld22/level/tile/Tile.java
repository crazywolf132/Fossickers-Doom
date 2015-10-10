package com.itsyourpalmike.ld22.level.tile;

import java.util.HashMap;
import java.util.Random;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.Mob;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.sound.Sound;

public class Tile
{
	public static int tickCount = 0;
	protected final Random random = new Random();

	private static HashMap<String, Tile> tileCollection = new HashMap<String, Tile>();
	public static Tile[] tiles = new Tile[256];
	public static Tile blank = new DirtTile(100);

	public final byte id;
	public boolean connectsToGrass = false;
	public boolean connectsToSand = false;
	public boolean connectsToWater = false;
	public boolean connectsToLava = false;
	
	public static void load(String name, Tile tile)
	{
		tileCollection.put(name, tile);
	}
	
	public static Tile get(String name)
	{
		return tileCollection.get(name);
	}

	public Tile(int id)
	{
		this.id = (byte)id;
		if (tiles[id] != null) throw new RuntimeException("Duplicate tile IDs!");
		tiles[id] = this;
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		
	}

	public boolean mayPass(Level level, int x, int y, Entity e)
	{
		return true;
	}

	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{

	}

	public void bumpedInto(Level level, int x, int y, Entity entity)
	{

	}

	public void tick(Level level, int xt, int yt)
	{

	}

	public void steppedOn(Level level, int xt, int yt, Entity entity)
	{

	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir)
	{
		return false;
	}

	public boolean use(Level level, int xt, int yt, Player player, int attackDir)
	{
		return false;
	}

	public boolean connectsToLiquid()
	{
		return connectsToWater || connectsToLava;
	}

	public int getLightRadius(Level level, int x, int y)
	{
		return 0;
	}
}
