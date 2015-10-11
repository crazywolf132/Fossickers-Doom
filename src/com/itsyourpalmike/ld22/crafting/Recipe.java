package com.itsyourpalmike.ld22.crafting;

import java.util.ArrayList;
import java.util.List;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.screen.ListItem;

public abstract class Recipe implements ListItem
{
	public class ItemTemplate
	{
		public String name;
		public int count;
		
		public ItemTemplate(String name, int count)
		{
			this.name = name;
			this.count = count;
		}
	}
	
	public List<ItemTemplate> costs = new ArrayList<ItemTemplate>();
	public boolean canCraft = false;
	public Item resultTemplate;

	public Recipe(Item resultTemplate)
	{
		this.resultTemplate = resultTemplate;
	}

	public Recipe addCost(String resource, int count)
	{
		costs.add(new ItemTemplate(resource, count));
		return this;
	}

	// Makes sure the player has the proper resourcse in the proper amounts
	public void checkCanCraft(Player player)
	{
		for (int i = 0; i < costs.size(); i++)
		{
			Item item = new ResourceItem(Resource.get(costs.get(i).name), costs.get(i).count);
			if (item instanceof ResourceItem)
			{
				ResourceItem ri = ((ResourceItem)item);
				if (!player.inventory.hasResources(ri.resource, ri.count))
				{
					canCraft = false;
					return;
				}
			}
		}

		canCraft = true;
		return;
	}

	public void renderInventory(Screen screen, int x, int y)
	{
		if(this.resultTemplate.sheet != null) screen.render(x, y, this.resultTemplate.getSprite(), this.resultTemplate.getColor(), 0, this.resultTemplate.sheet);
		else screen.render(x, y, this.resultTemplate.getSprite(), this.resultTemplate.getColor(), 0);
		Font.draw(this.resultTemplate.getName(), screen, x + 8, y, canCraft ? Color.get(-1, 555, 555, 555) : Color.get(-1, 222, 222, 222));
	}
	public abstract void craft(Player player);

	public void deductCost(Player player)
	{
		for (int i = 0; i < costs.size(); i++)
		{
			Item item = new ResourceItem(Resource.get(costs.get(i).name), costs.get(i).count);
			if (item instanceof ResourceItem)
			{
				ResourceItem ri = ((ResourceItem)item);
				player.inventory.removeResource(ri.resource, ri.count);
			}
		}
	}
}
