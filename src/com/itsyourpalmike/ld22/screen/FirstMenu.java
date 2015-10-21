package com.itsyourpalmike.ld22.screen;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;

public class FirstMenu extends Menu
{
	private int selected = 0;
	private boolean[] toggles;
	private Game game;

	public FirstMenu(Game game)
	{
		this.game = game;
		toggles = new boolean[Game.plugins.size()];
		for (int i = 0; i < toggles.length; i++)
		{
			toggles[i] = true;
		}
	}

	public void tick()
	{
		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;

		int len = Game.plugins.size() + 1;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.attack.clicked || input.menu.clicked)
		{
			if (selected == toggles.length)
			{
				for (int i = toggles.length - 1; i >= 0; i--)
				{
					if (toggles[i] == false)
					{
						Game.plugins.remove(i);
					}
				}
				game.startGameForTheFirstTime();
			}
			else
			{
				toggles[selected] = !toggles[selected];
			}
		}
	}

	public void render(Screen screen)
	{
		screen.clear(0);

		for (int i = 0; i < toggles.length + 1; i++)
		{
			String msg = "";
			if (i == toggles.length)
			{
				int cnt = 0;
				for (int j = 0; j < toggles.length; j++)
				{
					if (toggles[j]) cnt++;
				}
				String s = (cnt == 0 || cnt > 1) ? "s" : "";
				msg = "Play With " + cnt + " plugin" + s;
				int col = Color.get(0, 333, 333, 333);

				if (i == selected)
				{
					msg = "> " + msg + " <";
					col = Color.get(0, 555, 555, 555);
				}

				Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, screen.h - 16, col);
			}
			else
			{
				msg = Game.plugins.get(i).getName();
				int col = 0;

				if (toggles[i] == true) col = Color.get(0, 040, 040, 040);
				else col = Color.get(0, 300, 300, 300);

				if (i == selected)
				{
					msg = "> " + msg + " <";
					if (toggles[i] == true) col = Color.get(0, 050, 050, 050);
					else col = Color.get(0, 500, 500, 500);
				}

				Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (4 + i) * 8, col);
			}
		}

		Font.draw("Disable", screen, (8 * 8) + (4 * 8), 8, Color.get(0, 500, 500, 500));
		Font.draw("and", screen, (8 * 8), 8, Color.get(0, 444, 444, 444));
		Font.draw("enable", screen, 8, 8, Color.get(0, 050, 050, 050));
		Font.draw("Minicraft plugins", screen, 12, 16, Color.get(0, 444, 444, 444));
	}
}
