package com.itsyourpalmike.ld22.screen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.GameLauncher;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.plugin.DownloadPlugins;
import com.itsyourpalmike.ld22.sound.Sound;

public class DownloadMenu extends Menu
{
	private Game game;
	private int selected = 0;
	private ArrayList<DownloadablePlugin> plugins = new ArrayList<DownloadablePlugin>();
	String savePath = System.getenv("APPDATA") + "/.minicraft/tmp/";
	String pluginsTXT = System.getenv("APPDATA") + "/.minicraft/plugins.txt";

	public DownloadMenu(Game game)
	{
		Sound.load("pluginsSelected", this.getClass().getResource("/pluginsSelected.wav"));
		this.game = game;
		for (int i = 0; i < Game.plugins.size(); i++)
		{
			//plugins.add(new PluginMenuItem(Game.plugins.get(i)));
		}
		
		
		
		URL website = null;
		try
		{
			website = new URL("http://knawledge.rocks/plugins.txt");
		}
		catch (MalformedURLException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try (InputStream in = website.openStream()) {
		    try
			{
				Files.copy(in, Paths.get(pluginsTXT), StandardCopyOption.REPLACE_EXISTING);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String content = null;
		try
		{
			content = new String(Files.readAllBytes(Paths.get(pluginsTXT)));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] parts = content.split(",");
		File file = new File(pluginsTXT);
		file.delete();
		
		for(int i = 0; i < parts.length; i+=3)
		{
			plugins.add(new DownloadablePlugin(parts[i], parts[i+1], parts[i+2]));
		}
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
				for (int i = plugins.size()-1; i >= 0; i--)
				{
					if (!plugins.get(i).enabled)
					{
						plugins.remove(i);
					}
				}
				Sound.play("pluginsSelected");
				
				File file = new File(savePath);
				if (!file.exists())
				{
					file.mkdir();
				}
				
				for(int i = 0; i < plugins.size(); i++)
				{
					System.out.println(plugins.get(i).getName() + " " + plugins.get(i).getFilePath());
					URL website = null;
					try
					{
						website = new URL(plugins.get(i).getFilePath());
					}
					catch (MalformedURLException e2)
					{
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try (InputStream in = website.openStream()) {
					    try
						{
							Files.copy(in, Paths.get(savePath + plugins.get(i).getSimpleFileName()) , StandardCopyOption.REPLACE_EXISTING);
							in.close();
						}
						catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				game.setMenu(new RestartMenu());
				
				//game.init();
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

		Font.draw("deselect", screen, (8 * 8) + (4 * 8), 8, Color.get(0, 500, 500, 500));
		Font.draw("and", screen, (8 * 8), 8, Color.get(0, 444, 444, 444));
		Font.draw("select", screen, 8, 8, Color.get(0, 050, 050, 050));
		Font.draw("Minicraft plugins", screen, 12, 16, Color.get(0, 444, 444, 444));

		renderPluginList(screen, 1, 3, 12, 12, selected, plugins);

		String msg;
		int cnt = 0;
		for (int j = 0; j < plugins.size(); j++)
		{
			if (plugins.get(j).enabled) cnt++;
		}
		if(cnt > 99) cnt = 99;
		String s = (cnt == 0 || cnt > 1) ? "s" : "";
		msg = "Download " + cnt + "plugin" + s;
		int col = Color.get(0, 333, 333, 333);

		if (selected == plugins.size())
		{
			msg = "" + msg + "";
			col = Color.get(0, 555, 555, 555);
		}

		Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, screen.h - 16, col);

		if (selected == plugins.size())
		{
			Font.draw("" + cnt, screen, ((screen.w - msg.length() * 8) / 2) + 9*8, screen.h - 16, Color.get(0, 050, 050, 050));
		}
		else
		{
			Font.draw("" + cnt, screen, ((screen.w - msg.length() * 8) / 2) + 9*8, screen.h - 16, Color.get(0, 040, 040, 040));
		}
	}
}
