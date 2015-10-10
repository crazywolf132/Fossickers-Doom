package com.itsyourpalmike.ld22;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.gfx.SpriteSheet;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.level.tile.Tile;
import com.itsyourpalmike.ld22.screen.DeadMenu;
import com.itsyourpalmike.ld22.screen.LevelTransitionMenu;
import com.itsyourpalmike.ld22.screen.Menu;
import com.itsyourpalmike.ld22.screen.TitleMenu;
import com.itsyourpalmike.ld22.screen.WonMenu;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;

	// Game constants
	public static final String NAME = "Minicraft: Ultimate Edition";
	public static final int HEIGHT = 120;
	public static final int WIDTH = 160;
	public static final int SCALE = 3;

	// Important game variables
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private int[] colors = new int[256];
	private boolean running = false;
	public int tickCount = 0;
	public int gameTime = 0;

	// Important game objects
	private InputHandler input = new InputHandler(this);
	private Screen screen;
	private Screen lightScreen;
	private Level level;
	private Level[] levels = new Level[5];
	private int currentLevel = 3;
	public Player player;
	public Menu menu;
	private int playerDeadTime;
	private int pendingLevelChange;
	private Random random = new Random();
	private int wonTimer = 0;
	public boolean hasWon = false;

	public void setMenu(Menu menu)
	{
		this.menu = menu;
		if (menu != null) menu.init(this, input);
	}

	public void start()
	{
		running = true;
		new Thread(this).start();
	}

	public void stop()
	{
		running = false;
	}

	public void run()
	{
		init();

		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60.0;
		long lastTimer1 = System.currentTimeMillis();

		// Game Loop
		while (running)
		{
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (unprocessed >= 1)
			{
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			if (shouldRender)
			{
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000)
			{
				lastTimer1 += 1000;
			}
		}
	}

	public void resetGame()
	{
		playerDeadTime = 0;
		wonTimer = 0;
		gameTime = 0;
		hasWon = false;

		levels = new Level[5];
		currentLevel = 3;

		levels[4] = new Level(128, 128, 1, null);
		levels[3] = new Level(128, 128, 0, levels[4]);
		levels[2] = new Level(128, 128, -1, levels[3]);
		levels[1] = new Level(128, 128, -2, levels[2]);
		levels[0] = new Level(128, 128, -3, levels[1]);

		level = levels[currentLevel];

		// Creating the player and validating start position
		player = new Player(this, input);
		player.findStartPos(level);
		level.add(player);

		for(int i = 0; i < 5; i++)
		{
			levels[i].trySpawn(5000);
		}
	}

	private void init()
	{

		// Setting up colors
		int pp = 0;
		for (int r = 0; r < 6; r++)
		{
			for (int g = 0; g < 6; g++)
			{
				for (int b = 0; b < 6; b++)
				{
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					int mid = (rr * 30 + gg * 59 + bb * 11) / 100;

					int r1 = ((rr + mid * 1) / 2) * 230 / 255 + 10;
					int g1 = ((gg + mid * 1) / 2) * 230 / 255 + 10;
					int b1 = ((bb + mid * 1) / 2) * 230 / 255 + 10;
					colors[pp++] = r1 << 16 | g1 << 8 | b1;
				}
			}
		}

		// Creating the screen using a new SpriteSheet
		try
		{
			screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/icons.png"))));
			lightScreen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/icons.png"))));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		resetGame();

		// Displays the Main Menu Screen
		setMenu(new TitleMenu());
	}

	public void tick()
	{
		tickCount++;
		if (!hasFocus()) // If we don't have focus release the keys, otherwise input can get stuck
		{
			input.releaseAll();
		}
		else
		{
			if (!player.removed && !hasWon) gameTime++;

			input.tick();

			if (menu != null)
			{
				menu.tick();
			}
			else
			{
				if (player.removed)
				{
					playerDeadTime++;
					if (playerDeadTime > 60)
					{
						setMenu(new DeadMenu());
					}
				}
				else
				{
					if (pendingLevelChange != 0)
					{
						setMenu(new LevelTransitionMenu(pendingLevelChange));
						pendingLevelChange = 0;
					}
				}
				if(wonTimer>0)
				{
					if(--wonTimer == 0)
					{
						setMenu(new WonMenu());
					}
				}
				level.tick();
				Tile.tickCount++;
			}

		}

	}

	public void changeLevel(int dir)
	{
		level.remove(player);
		currentLevel += dir;
		level = levels[currentLevel];
		player.x = (player.x >> 4) * 16 + 8;
		player.y = (player.y >> 4) * 16 + 8;
		level.add(player);
	}

	public void render()
	{
		// Setting up the BufferStrategy
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		// Rendering the background tiles w proper offsets
		int xScroll = player.x - screen.w / 2;
		int yScroll = player.y - (screen.h - 8) / 2;
		if (xScroll < 16) xScroll = 16;
		if (yScroll < 16) yScroll = 16;
		if (xScroll > level.w * 16 - screen.w) xScroll = level.w * 16 - screen.w - 16;
		if (yScroll > level.h * 16 - screen.h) yScroll = level.h * 16 - screen.h - 16;

		if (currentLevel > 3)
		{
			int col = Color.get(20, 20, 121, 121);
			for (int y = 0; y < 14; y++)
			{
				for (int x = 0; x < 24; x++)
				{
					screen.render(x * 8 - ((xScroll / 4) & 7), y * 8 - ((yScroll / 4) & 7), 0, col, 0);
				}
			}
		}

		level.renderBackground(screen, xScroll, yScroll);
		level.renderSprites(screen, xScroll, yScroll);

		if (currentLevel < 3)
		{
			lightScreen.clear(0);
			level.renderLight(lightScreen, xScroll, yScroll);
			screen.overlay(lightScreen, xScroll, yScroll);
		}
		renderGui();

		// If the game window isn't focused display a message!
		if (!this.hasFocus()) renderFocusNagger();

		// Drawing the screen pixels to the game
		for (int y = 0; y < screen.h; y++)
		{
			for (int x = 0; x < screen.w; x++)
			{
				pixels[x + y * WIDTH] = colors[screen.pixels[x + y * screen.w]];
			}
		}

		// Rendering the screen
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());

		int ww = WIDTH * SCALE;
		int hh = HEIGHT * SCALE;
		int xo = (getWidth() - ww) / 2;
		int yo = (getHeight() - hh) / 2;

		g.drawImage(image, xo, yo, ww, hh, null);
		g.dispose();
		bs.show();
	}

	private void renderGui()
	{
		// Black bar at the bottom of the screen
		for (int y = 0; y < 2; y++)
		{
			for (int x = 0; x < 20; x++)
			{
				screen.render(x * 8, screen.h - 16 + y * 8, 0 + 12 * 32, Color.get(000, 000, 000, 000), 0);
			}
		}

		// Player hearts
		for (int i = 0; i < 10; i++)
		{
			if (i < player.health) screen.render(i * 8, screen.h - 16, 0 + 12 * 32, Color.get(000, 200, 500, 533), 0);
			else screen.render(i * 8, screen.h - 16, 0 + 12 * 32, Color.get(000, 100, 000, 000), 0);

			if (player.staminaRechargeDelay > 0)
			{
				if (player.staminaRechargeDelay / 4 % 2 == 0) screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 555, 000, 000), 0);
				else screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 110, 000, 000), 0);
			}
			else
			{
				if (i < player.stamina) screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 220, 550, 553), 0);
				else screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 110, 000, 000), 0);
			}
		}

		// Player's currently equipped item
		if (player.activeItem != null)
		{
			player.activeItem.renderInventory(screen, 10 * 8, screen.h - 16);
		}

		// Any other menu (inventory, crafting, etc)
		if (menu != null)
		{
			menu.render(screen);
		}
	}

	private void renderFocusNagger()
	{
		String msg = "Click to focus!";
		int xx = (WIDTH - msg.length() * 8) / 2;
		int yy = (HEIGHT - 8) / 2;
		int w = msg.length();
		int h = 1;

		// Rendering the four corners of message box
		screen.render(xx - 8, yy - 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
		screen.render(xx + w * 8, yy - 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 1);
		screen.render(xx - 8, yy + 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 2);
		screen.render(xx + w * 8, yy + 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 3);

		// Rendering top and bottom of message box
		for (int x = 0; x < w; x++)
		{
			screen.render(xx + x * 8, yy - 8, 1 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
			screen.render(xx + x * 8, yy + 8, 1 + 13 * 32, Color.get(-1, 1, 5, 445), 2);
		}

		// Rendering left and right of message box
		for (int y = 0; y < h; y++)
		{
			screen.render(xx - 8, yy + y * 8, 2 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
			screen.render(xx + w * 8, yy + y * 8, 2 + 13 * 32, Color.get(-1, 1, 5, 445), 1);
		}

		// Makes text flash white / grey
		if ((tickCount / 20) % 2 == 0)
		{
			Font.draw(msg, screen, xx, yy, Color.get(5, 333, 333, 333));
		}
		else
		{
			Font.draw(msg, screen, xx, yy, Color.get(5, 555, 555, 555));
		}
	}

	public void scheduleLevelChange(int dir)
	{
		pendingLevelChange = dir;
	}

	public static void main(String[] args)
	{
		// Creating and starting the game
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				Game game = new Game();
				game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
				game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
				game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

				JFrame frame = new JFrame(Game.NAME);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				frame.add(game);
				frame.pack();
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

				game.start();
			}
		});
	}

	public void won()
	{
		wonTimer = 60*3;
		hasWon = true;
	}
}
