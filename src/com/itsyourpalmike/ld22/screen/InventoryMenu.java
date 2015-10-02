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
		if(selected < 0) selected += len;
		if(selected >= len) selected -= len;
	}
	
	public void render(Screen screen)
	{
		Font.renderFrame(screen, "inventory", 1, 1, 12, 12);
		for (int i = 0; i < player.inventory.items.size(); i++)
		{
			player.inventory.items.get(i).renderInventory(screen, 8*2, (i + 2) * 8);
		}
		
		int yy = selected + 2;
		Font.draw(">", screen, 1*8, yy*8, Color.get(5, 555, 555, 555));
		Font.draw("<", screen, 12*8, yy*8, Color.get(5, 555, 555, 555));
	}
}
