package com.itsyourpalmike.ld22.screen;

import java.util.ArrayList;
import java.util.List;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.resource.Resource;

public class CraftOption implements ListItem
{
	private String msg;
	private List<ResourceItem> requirements = new ArrayList<ResourceItem>();
	public boolean canCraft;

	public CraftOption(String msg)
	{
		this.msg = msg;
	}

	public void renderInventory(Screen screen, int x, int y)
	{
		if(!canCraft)
		{
			Font.draw(msg, screen, x, y, Color.get(-1, 333, 333, 333));
		}
		else
		{
			Font.draw(msg, screen, x, y, Color.get(-1, 555, 555, 555));
		}
	}

	public CraftOption addRequirement(Resource resource, int count)
	{
		requirements.add(new ResourceItem(resource, count));
		return this;
	}

	public void checkCanCraft(Player player)
	{
		for (int i = 0; i < requirements.size(); i++)
		{
			ResourceItem ri = requirements.get(i);
			if (!player.inventory.hasResources(ri.resource, ri.count))
			{
				canCraft = false;
				return;
			}
		}
		
		canCraft = true;
		return;
	}
}
