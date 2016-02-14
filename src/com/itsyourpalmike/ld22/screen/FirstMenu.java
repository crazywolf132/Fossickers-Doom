package com.itsyourpalmike.ld22.screen;

import java.util.ArrayList;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.plugin.DownloadPlugins;
import com.itsyourpalmike.ld22.sound.Sound;

public class FirstMenu extends Menu
{
	private Game game;
	private int selected = 0;
	private ArrayList<PluginMenuItem> plugins = new ArrayList<PluginMenuItem>();

	public FirstMenu(Game game)
	{
		Sound.load("pluginsSelected", this.getClass().getResource("/pluginsSelected.wav"));
		this.game = game;
		for (int i = 0; i < Game.plugins.size(); i++)
		{
			plugins.add(new PluginMenuItem(Game.plugins.get(i)));
		}
		
		plugins.add(new PluginMenuItem(new DownloadPlugins()));
	}

	public void tick()
	{

		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;

		int len = plugins.size() + 1;
		if (len == 0) selected = 0;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.attack.clicked || input.menu.clicked)
		{
			if (selected == plugins.size())
			{
				// Remove the disabled plugins from the Game.plugins array list before running game
				for (int i = plugins.size()-2; i >= 0; i--)
				{
					if (!plugins.get(i).enabled)
					{
						Game.plugins.remove(i);
					}
				}
				Sound.play("pluginsSelected");
				game.startGameForTheFirstTime();
			}
			else if (selected == plugins.size() - 1)
			{
				System.out.println("DOWNLOADING");
				Sound.play("pluginsSelected");
				game.deletePlugins();
				game.setMenu(new DownloadMenu(game));
			}
			else
			{
				plugins.get(selected).enabled = !plugins.get(selected).enabled;
			}
		}

	}

	public void render(Screen screen)
	{
		screen.clear(0);

		Font.draw("Disable", screen, (8 * 8) + (4 * 8), 8, Color.get(0, 500, 500, 500));
		Font.draw("and", screen, (8 * 8), 8, Color.get(0, 444, 444, 444));
		Font.draw("enable", screen, 8, 8, Color.get(0, 050, 050, 050));
		Font.draw("Minicraft plugins", screen, 12, 16, Color.get(0, 444, 444, 444));

		renderPluginList(screen, 1, 3, 12, 12, plugins, selected);

		String msg;
		int cnt = 0;
		for (int j = 0; j < plugins.size(); j++)
		{
			if (plugins.get(j).enabled) cnt++;
		}
		if(cnt > 99) cnt = 99;
		String s = (cnt == 0 || cnt > 1) ? "s" : "";
		msg = "Play With " + cnt + "plugin" + s;
		int col = Color.get(0, 333, 333, 333);

		if (selected == plugins.size())
		{
			msg = "" + msg + "";
			col = Color.get(0, 555, 555, 555);
		}

		Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, screen.h - 16, col);

		if (selected == plugins.size())
		{
			Font.draw("" + cnt, screen, ((screen.w - msg.length() * 8) / 2) + 10*8, screen.h - 16, Color.get(0, 050, 050, 050));
		}
		else
		{
			Font.draw("" + cnt, screen, ((screen.w - msg.length() * 8) / 2) + 10*8, screen.h - 16, Color.get(0, 040, 040, 040));
		}
	}
}
