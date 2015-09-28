package com.itsyourpalmike.ld22.level.tile;

import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.entity.Mob;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Resource;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.level.Level;

public class FlowerTile extends GrassTile
{
	public FlowerTile(int id)
	{
		super(id);
		tiles[id] = this;
		connectsToGrass = true;
	}
	
	public void render(Screen screen, Level level, int x, int y)
	{
		super.render(screen, level, x, y); // Render the grass
		int data = level.getData(x, y);
		//int color = data % 16;
		int shape = (data/16)%2;
		
		// Unique flower colors
		int flowerCol = Color.get(10, level.grassColor, 555, 440);
		/*if(color == 0) flowerCol =  Color.get(10,  level.grassColor, 555, 440);
		if(color == 1) flowerCol =  Color.get(10,  level.grassColor, 533, 400);
		if(color == 2) flowerCol =  Color.get(10,  level.grassColor, 115, 445);
		if(color == 3) flowerCol =  Color.get(10,  level.grassColor, 111, 000);*/
		
		// Unique flower shapes (TL, TR, BL, BR)
		if(shape==0) screen.render(x*16+0, y*16+0, 1+1*32, flowerCol, 0);
		if(shape==1) screen.render(x*16+8, y*16+0, 1+1*32, flowerCol, 0);
		if(shape==1) screen.render(x*16+0, y*16+8, 1+1*32, flowerCol, 0);
		if(shape==3) screen.render(x*16+8, y*16+8, 1+1*32, flowerCol, 0);
	}
	
	// If we hurt flowers they dissapear
	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{
		level.setTile(x,y, Tile.grass, 0);
		level.add(new ItemEntity(new ResourceItem(Resource.flower), x * 16 + random.nextInt(10)+3, y * 16  + random.nextInt(10)+3));
		level.add(new ItemEntity(new ResourceItem(Resource.flower), x * 16 + random.nextInt(10)+3, y * 16  + random.nextInt(10)+3));
	}
}
