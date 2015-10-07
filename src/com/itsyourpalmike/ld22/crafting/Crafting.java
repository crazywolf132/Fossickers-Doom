package com.itsyourpalmike.ld22.crafting;

import java.util.ArrayList;
import java.util.List;

import com.itsyourpalmike.ld22.entity.Furnace;
import com.itsyourpalmike.ld22.entity.Oven;
import com.itsyourpalmike.ld22.item.resource.Resource;

public class Crafting
{
	public static final List<Recipe> anvilRecipes = new ArrayList<Recipe>();
	public static final List<Recipe> chestRecipes = new ArrayList<Recipe>();
	public static final List<Recipe> ovenRecipes = new ArrayList<Recipe>();
	public static final List<Recipe> furnaceRecipes = new ArrayList<Recipe>();
	public static final List<Recipe> workbenchRecipes = new ArrayList<Recipe>();

	static
	{
		try
		{
			workbenchRecipes.add(new FurnitureRecipe(Oven.class).addCost(Resource.stone, 16));
			workbenchRecipes.add(new FurnitureRecipe(Furnace.class).addCost(Resource.stone, 20));

			ovenRecipes.add(new ResourceRecipe(Resource.bread).addCost(Resource.wheat, 4));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
