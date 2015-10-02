package com.itsyourpalmike.ld22.screen;

import java.util.ArrayList;
import java.util.List;

import com.itsyourpalmike.ld22.entity.Anvil;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.item.Resource;
import com.itsyourpalmike.ld22.item.ResourceItem;

public class CraftingMenu extends Menu
{
	private Player player;
	private int selected = 0;
	private Anvil anvil;
	private List<Item> craftables = new ArrayList<Item>();
	
	public CraftingMenu(Anvil anvil, Player player)
	{
		this.anvil = anvil;
		this.player = player;
		craftables.add(new ResourceItem(Resource.stone));
	}

	public void tick()
	{
		if(input.menu.clicked) game.setMenu(null);
		
		if(input.up.clicked) selected--;
		if(input.down.clicked) selected++;
		
		int len = craftables.size();
		if(selected < 0) selected += len;
		if(selected >= len) selected -= len;
	}
	
	public void render(Screen screen)
	{
		Font.renderFrame(screen, "crafting", 1, 1, 12, 12);
		for (int i = 0; i < craftables.size(); i++)
		{
			craftables.get(i).renderInventory(screen, 8*2, (i + 2) * 8);
		}
		
		int yy = selected + 2;
		Font.draw(">", screen, 1*8, yy*8, Color.get(5, 555, 555, 555));
		Font.draw("<", screen, 12*8, yy*8, Color.get(5, 555, 555, 555));
	}
}
