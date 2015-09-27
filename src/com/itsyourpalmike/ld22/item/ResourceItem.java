package com.itsyourpalmike.ld22.item;

public class ResourceItem extends Item
{
	public Resource resource;
	
	// An item that is a resource (wood, stone, etc...)
	public ResourceItem(Resource resource)
	{
		this.resource = resource;
	}
	
	public int getColor()
	{
		return resource.color;
	}
	
	public int getSprite()
	{
		
		return resource.sprite;
	}
}
