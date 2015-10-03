package com.itsyourpalmike.ld22.screen;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;

public class InventoryMenu extends Menu
{
	private Player player;
	private int selected = 0;
	
	public InventoryMenu(Player player)
	{
		this.player = player;
	}

	public void tick()
	{
		if(input.menu.clicked) game.setMenu(null);
		
		if(input.up.clicked) selected--;
		if(input.down.clicked) selected++;
		
		int len = player.inventory.items.size();
		if(len==0) selected = 0;
		if(selected < 0) selected += len;
		if(selected >= len) selected -= len;
	}
	
	public void render(Screen screen)
	{
		Font.renderFrame(screen, "inventory", 1, 1, 12, 11);
		renderItemList(screen, 1, 1, 12, 11, player.inventory.items, selected);
	}
}
