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
	private List<CraftOption> craftables = new ArrayList<CraftOption>();
	
	public CraftingMenu(Anvil anvil, Player player)
	{
		this.anvil = anvil;
		this.player = player;
		craftables.add(new CraftOption("Upgrade Anvil 1").addRequirement(Resource.wood, 16));
		craftables.add(new CraftOption("Upgrade Anvil 2"));
		craftables.add(new CraftOption("Upgrade Anvil 3"));
		craftables.add(new CraftOption("Upgrade Anvil 4"));
		craftables.add(new CraftOption("Upgrade Anvil 5"));
		craftables.add(new CraftOption("Upgrade Anvil 6"));
		craftables.add(new CraftOption("Upgrade Anvil 7"));
		craftables.add(new CraftOption("Upgrade Anvil 8"));
		craftables.add(new CraftOption("Upgrade Anvil 9"));
		craftables.add(new CraftOption("Upgrade Anvil 10"));
		craftables.add(new CraftOption("Upgrade Anvil 11"));
		craftables.add(new CraftOption("Upgrade Anvil 12"));
		craftables.add(new CraftOption("Upgrade Anvil 13"));
		
		for(int i = 0; i < craftables.size(); i++)
		{
			craftables.get(i).checkCanCraft(player);
		}
	}

	public void tick()
	{
		if(input.menu.clicked) game.setMenu(null);
		
		if(input.up.clicked) selected--;
		if(input.down.clicked) selected++;
		
		int len = craftables.size();
		if(len==0) selected = 0;
		if(selected < 0) selected += len;
		if(selected >= len) selected -= len;
	}
	
	public void render(Screen screen)
	{
		Font.renderFrame(screen, "crafting", 1, 1, 18, 11);
		
		renderItemList(screen, 1, 1, 18, 11, craftables, selected);
	}
}
