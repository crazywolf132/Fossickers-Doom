package com.itsyourpalmike.ld22.item;

import com.itsyourpalmike.ld22.entity.ItemEntity;
import com.itsyourpalmike.ld22.entity.particles.TextParticle;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;

public class ResourceItem extends Item
{
	public Resource resource;
	public int count = 1;
	
	// An item that is a resource (wood, stone, etc...)
	public ResourceItem(Resource resource)
	{
		this.resource = resource;
	}
	
	public ResourceItem(Resource resource, int count)
	{
		this.resource = resource;
		this.count = count;
	}
	
	public int getColor()
	{
		return resource.color;
	}
	
	public int getSprite()
	{
		
		return resource.sprite;
	}
	
	public void renderInventory(Screen screen, int x, int y)
	{
		screen.render(x, y, resource.sprite, resource.color, 0);
		Font.draw(resource.name, screen, x+32, y, Color.get(-1, 555, 555, 555));
		int cc = count;
		if(cc>999) cc = 999;
		Font.draw(""+cc, screen, x+8, y, Color.get(-1, 444, 444, 444));
	}

	public void onTake(ItemEntity itemEntity)
	{
		//itemEntity.level.add(new TextParticle("+"+resource.name, itemEntity.x, itemEntity.y, Color.get(-1, 555, 555, 555)));
	}
}
