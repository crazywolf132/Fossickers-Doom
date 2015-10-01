package com.itsyourpalmike.ld22.entity;

import java.util.ArrayList;
import java.util.List;

import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.Resource;
import com.itsyourpalmike.ld22.item.ResourceItem;

public class Inventory
{
	public List<Item> items = new ArrayList<Item>();

	public void add(Item item)
	{
		if (item instanceof ResourceItem)
		{
			ResourceItem toTake = (ResourceItem)item;
			ResourceItem has = findResource(toTake.resource);
			if (has == null)
			{
				items.add(toTake);
			}
			else
			{
				has.count += toTake.count;
			}
		}
	}

	private ResourceItem findResource(Resource resource)
	{
		for (int i = 0; i < items.size(); i++)
		{
			if (items.get(i) instanceof ResourceItem)
			{
				ResourceItem has = (ResourceItem)items.get(i);
				if (has.resource == resource) return has;
			}
		}
		return null;
	}

	public boolean hasResources(Resource r, int count)
	{
		ResourceItem ri = findResource(r);
		if (ri == null) return false;
		return ri.count >= count;
	}

	public boolean removeResource(Resource r, int count)
	{
		ResourceItem ri = findResource(r);
		if (ri == null) return false;
		if (ri.count < count) return false;
		ri.count -= count;
		if (ri.count <= 0) items.remove(ri);
		return true;
	}

}
