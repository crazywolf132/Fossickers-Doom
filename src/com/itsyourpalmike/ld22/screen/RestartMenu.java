package com.itsyourpalmike.ld22.screen;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;

public class RestartMenu extends Menu
{

	public RestartMenu()
	{
	}

	public void tick()
	{
		
	}

	public void render(Screen screen)
	{
		screen.clear(0);

		Font.draw("CLOSE AND", screen, 4 * 8 + 4, 1 * 8, Color.get(0, 555, 555, 555));
		Font.draw("REOPEN TO", screen, 0 * 8 + 4, 3 * 8, Color.get(0, 333, 333, 333));
		Font.draw("USE PLUGINS", screen, 0 * 8 + 4, 4 * 8, Color.get(0, 333, 333, 333));
	}
}
