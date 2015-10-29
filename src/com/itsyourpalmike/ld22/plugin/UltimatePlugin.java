package com.itsyourpalmike.ld22.plugin;

import javax.imageio.ImageIO;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.MinicraftPlugin;
import com.itsyourpalmike.ld22.crafting.Crafting;
import com.itsyourpalmike.ld22.crafting.ItemRecipe;
import com.itsyourpalmike.ld22.crafting.ResourceRecipe;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.SpriteSheet;
import com.itsyourpalmike.ld22.item.Bow;
import com.itsyourpalmike.ld22.item.FishingRod;
import com.itsyourpalmike.ld22.item.resource.DyeResource;
import com.itsyourpalmike.ld22.item.resource.FoodResource;
import com.itsyourpalmike.ld22.item.resource.PlantableResource;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.level.tile.BetterFlowerTile;
import com.itsyourpalmike.ld22.level.tile.StoneWallTile;
import com.itsyourpalmike.ld22.level.tile.Tile;
import com.itsyourpalmike.ld22.level.tile.WoodTile;

public class UltimatePlugin implements MinicraftPlugin
{
	public static SpriteSheet ultimateSheet;
	public static SpriteSheet icons2;
	public static SpriteSheet icons3;

	public void onLoad(Game game)
	{
		Tile.load("flower", new BetterFlowerTile());

		Tile.load("daisy", new BetterFlowerTile(0));
		Tile.load("rose", new BetterFlowerTile(1));
		Tile.load("salvia", new BetterFlowerTile(2));
		Tile.load("b.rose", new BetterFlowerTile(3));
		Tile.load("woodtile", new WoodTile());
		Tile.load("stonewalltile", new StoneWallTile());

		Resource.load(new PlantableResource("Wood", 1 + 4 * 32, Color.get(-1, 200, 531, 430), "woodtile", "grass", "dirt"));
		Resource.load(new PlantableResource("Stone", 2 + 4 * 32, Color.get(-1, 111, 333, 555), "stonewalltile", "grass", "dirt"));

		Resource.load(new PlantableResource("daisy", 0 + 4 * 32, Color.get(-1, 10, 555, 440), "daisy", "grass"));
		Resource.load(new PlantableResource("rose", 0 + 4 * 32, Color.get(-1, 10, 511, 400), "rose", "grass"));
		Resource.load(new PlantableResource("salvia", 0 + 4 * 32, Color.get(-1, 10, 115, 445), "salvia", "grass"));
		Resource.load(new PlantableResource("b.rose", 0 + 4 * 32, Color.get(-1, 10, 111, 000), "b.rose", "grass"));

		Resource.load(new DyeResource("white", 2 + 4 * 32, 555));
		Resource.load(new DyeResource("red", 2 + 4 * 32, 400));
		Resource.load(new DyeResource("blue", 2 + 4 * 32, 115));
		Resource.load(new DyeResource("black", 2 + 4 * 32, 111));
		Resource.load(new DyeResource("green", 2 + 4 * 32, 40));
		Resource.load(new DyeResource("pink", 2 + 4 * 32, 522));
		Resource.load(new DyeResource("purple", 2 + 4 * 32, 313));
		Resource.load(new DyeResource("yellow", 2 + 4 * 32, 440));
		Resource.load(new DyeResource("orange", 2 + 4 * 32, 520));
		Resource.load(new DyeResource("gray", 2 + 4 * 32, 222));

		try
		{
			ultimateSheet = new SpriteSheet(ImageIO.read(this.getClass().getResource("/ultimate.png")));
			icons2 = new SpriteSheet(ImageIO.read(this.getClass().getResource("/icons2.png")));
			icons3 = new SpriteSheet(ImageIO.read(this.getClass().getResource("/icons3.png")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Resource.load(new Resource("star", 35, Color.get(-1, 110, 440, 440), ultimateSheet));
		Resource.load(new Resource("R.fish", 32, Color.get(-1, 0, 135, 245), ultimateSheet));
		Resource.load(new FoodResource("C.FISH", 32, Color.get(-1, 0, 511, 410), ultimateSheet, 2, 5));
		Resource.load(new Resource("Arrow", 33, Color.get(-1, 555, 555, 321), ultimateSheet));
		Resource.load(new FoodResource("g.apple", 34, Color.get(-1, 110, 440, 553), ultimateSheet, 5, 4));

		try
		{
			Crafting.workbenchRecipes.add(new ItemRecipe(FishingRod.class).addCost("wood", 5).addCost("cloth", 5));
			Crafting.workbenchRecipes.add(new ItemRecipe(Bow.class).addCost("wood", 5).addCost("cloth", 5));
			Crafting.workbenchRecipes.add(new ResourceRecipe("Arrow").addCost("wood", 1).addCost("stone", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("g.apple").addCost("apple", 1).addCost("gold", 1));

			Crafting.workbenchRecipes.add(new ResourceRecipe("white").addCost("daisy", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("red").addCost("rose", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("blue").addCost("salvia", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("black").addCost("b.rose", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("green").addCost("cactus", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("pink").addCost("red", 1).addCost("white", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("purple").addCost("red", 1).addCost("blue", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("yellow").addCost("star", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("orange").addCost("red", 1).addCost("yellow", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("gray").addCost("white", 1).addCost("black", 1));

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

	@Override
	public boolean autoEnabled()
	{
		// TODO Auto-generated method stub
		return true;
	}
}
