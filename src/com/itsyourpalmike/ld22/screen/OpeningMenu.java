package com.itsyourpalmike.ld22.screen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.sound.Sound;

public class OpeningMenu extends Menu
{

	String pluginsTXT = System.getenv("APPDATA") + "/.minicraft/curVer.txt";
	
	Game game;
	public OpeningMenu(Game game)
	{
		this.game = game;
		Sound.load("pluginsSelected", this.getClass().getResource("/pluginsSelected.wav"));
	}

	public void tick()
	{
		if (input.attack.clicked || input.menu.clicked)
		{

			Sound.play("pluginsSelected");
			
			URL website = null;
			try
			{
				website = new URL("http://knawledge.rocks/curVer.txt");
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
				int ver = Integer.parseInt(content);
				
				File file = new File(pluginsTXT);
				file.delete();
				
				if(Game.CURRENT_VERSION != ver)
				{
					
					game.setMenu(new UpdateMenu());
					System.out.println("NEEDS TO UPDATE");
				}
				else
				{
					game.setMenu(new FirstMenu(game));
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public void render(Screen screen)
	{
		screen.clear(0);

		Font.draw("CONTROLS:", screen, 0 * 8 + 4, 5 * 8, Color.get(0, 333, 333, 333));
		Font.draw("arrow keys,x,and c", screen, 0 * 8 + 4, 6 * 8, Color.get(0, 333, 333, 333));
		Font.draw("Press C to continue", screen, 0 * 8 + 4, 9 * 8, Color.get(0, 333, 333, 333));
	}
}
