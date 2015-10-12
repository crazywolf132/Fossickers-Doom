package com.itsyourpalmike.ld22.plugin;

import javax.imageio.ImageIO;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.crafting.Crafting;
import com.itsyourpalmike.ld22.crafting.ItemRecipe;
import com.itsyourpalmike.ld22.crafting.ResourceRecipe;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.SpriteSheet;
import com.itsyourpalmike.ld22.item.FishingRodItem;
import com.itsyourpalmike.ld22.item.resource.FoodResource;
import com.itsyourpalmike.ld22.item.resource.Resource;

public class UltimatePlugin implements MinicraftPlugin
{
	public static SpriteSheet ultimateSheet;

	public void onLoad(Game game)
	{
		try
		{
			ultimateSheet = new SpriteSheet(ImageIO.read(this.getClass().getResource("/ultimate.png")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Resource.load(new Resource("R.fish", 32, Color.get(-1, 0, 135, 245), ultimateSheet));
		Resource.load(new FoodResource("C.FISH", 32, Color.get(-1, 0, 511, 410), ultimateSheet, 2, 5));
		Resource.load(new Resource("Arrow", 33, Color.get(-1, 555, 555, 321), ultimateSheet));
		
		try
		{
			Crafting.workbenchRecipes.add(new ItemRecipe(FishingRodItem.class).addCost("wood", 5).addCost("cloth", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("Arrow").addCost("wood", 1).addCost("stone", 1));
			Crafting.ovenRecipes.add(new ResourceRecipe("C.FISH").addCost("r.fish", 1).addCost("coal", 1));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getName()
	{
		return "Ultimate Plugin";
	}
}
