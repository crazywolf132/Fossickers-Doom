package com.itsyourpalmike.ld22.level.tile;

import java.util.Random;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.Mob;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class Tile
{
	protected final Random random = new Random();
	public static Tile[] tiles = new Tile[256];
	public static Tile grass = new GrassTile(0);
	public static Tile rock = new RockTile(1);
	public static Tile water = new WaterTile(2);
	public static Tile flower = new FlowerTile(3);
	public static Tile tree = new TreeTile(4);
	public static Tile dirt = new DirtTile(5);
	public static Tile sand = new SandTile(6);
	
	public final byte id;
	public boolean isGrassy = false;
	
	public Tile(int id)
	{
		this.id = (byte)id;
		if(tiles[id]!=null) throw new RuntimeException("Duplicate tile ids!");
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
}
