package com.itsyourpalmike.ld22.screen;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;

public class TitleMenu extends Menu
{
	private int time = 0;
	private int selected = 0;
	
	private static final String[] options =
	{
			"Start game",
			"How to play",
			"About"
	};
	
	public TitleMenu()
	{
		
	}

	public void tick()
	{
		//if(input.menu.clicked) game.setMenu(null);
		
		if(input.up.clicked) selected--;
		if(input.down.clicked) selected++;
		
		int len = options.length;
		if(selected < 0) selected += len;
		if(selected >= len) selected -= len;
		
		if(input.attack.clicked)
		{
			if(selected == 0) game.setMenu(null);
		}
	}
	
	public void render(Screen screen)
	{
		screen.clear();
		for(int i = 0; i < 3; i++)
		{
			String msg = options[i];
			int col = Color.get(0, 444, 444, 444);
			if(i==selected)
			{
				msg = "> " + msg + " <";
				col = Color.get(0, 555, 555, 555);
			}
			Font.draw(msg, screen, (screen.w - msg.length()*8)/2, (9+i)*8, col);
		}
	}
}
