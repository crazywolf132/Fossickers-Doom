package com.itsyourpalmike.ld22.crafting;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.item.ToolItem;
import com.itsyourpalmike.ld22.item.ToolType;

public class ToolRecipe extends Recipe
{
	private ToolType type;
	private int level;

	public ToolRecipe(ToolType type, int level)
	{
		super(new ToolItem(type, level));
		this.type = type;
		this.level = level;
	}

	public void craft(Player player)
	{
		player.inventory.add(0, new ToolItem(type, level));
	}

}
