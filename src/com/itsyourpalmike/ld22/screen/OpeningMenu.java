package com.itsyourpalmike.ld22.screen;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.sound.Sound;

public class OpeningMenu extends Menu
{
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
			game.setMenu(new FirstMenu(game));
			Sound.play("pluginsSelected");
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
