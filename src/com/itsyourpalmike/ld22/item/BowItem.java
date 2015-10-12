package com.itsyourpalmike.ld22.item;

import com.itsyourpalmike.ld22.entity.ArrowEntity;
import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.level.tile.Tile;
import com.itsyourpalmike.ld22.plugin.UltimatePlugin;

// This class is used to create all tools in the game + keep track of their material type (Wood, Stone, Etc...)
public class BowItem extends Item
{

	public BowItem()
	{
		super();
		sheet = UltimatePlugin.ultimateSheet;
	}

	public int getColor()
	{
		return Color.get(-1, 100, 321, 555);
	}

	public int getSprite()
	{
		return 1 + 0 * 32;
	}

	public void renderIcon(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0, UltimatePlugin.ultimateSheet);
	}

	public boolean canAttack()
	{
		return false;
	}

	public boolean interact(Player player, Entity entity, int attackDir)
	{
		if (player.payStamina(1) && player.inventory.hasResources(Resource.get("arrow"), 1))
		{
			player.level.add(new ArrowEntity(player));
			player.inventory.removeResource(Resource.get("arrow"), 1);
			return true;
		}
		return false;
	}

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir)
	{
		if (player.payStamina(1) && player.inventory.hasResources(Resource.get("arrow"), 1))
		{
			player.level.add(new ArrowEntity(player));
			player.inventory.removeResource(Resource.get("arrow"), 1);
			return true;
		}
		return false;
	}

	public void renderInventory(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0, UltimatePlugin.ultimateSheet);
		Font.draw(this.getName(), screen, x + 8, y, Color.get(-1, 555, 555, 555));
	}

	public String getName()
	{
		return "bow";
	}
}