package com.itsyourpalmike.ld22;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.entity.TestMob;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.gfx.SpriteSheet;
import com.itsyourpalmike.ld22.level.Level;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;

	// Game constants
	public static final String NAME = "Minicraft- Mike's Version";
	public static final int HEIGHT = 120;
	public static final int WIDTH = 160;
	public static final int SCALE = 3;

	// Important game variables
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private boolean running = false;
	private InputHandler input = new InputHandler(this);
	private Screen screen;

	// SEPARATE COLORS FOR BACKGROUND AND FOREGROUND SPRITES/TILES
	/////////////////////////////////////
	private int[] colors1 = new int[256];
	private int[] colors2 = new int[256];
	/////////////////////////////////////

	private int tickCount = 0;
	private Level level;
	private Player player;

	public void start()
	{
		running = true;
		new Thread(this).start();
	}

	public void stop()
	{
		running = false;
	}

	private void init()
	{
		level = new Level(128, 128);
		player = new Player(input);
		player.findStartPos(level);
		level.add(player);
		for (int i = 0; i < 100; i++)
		{
			TestMob m = new TestMob();
			m.findStartPos(level);
			level.add(m);
		}

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

					int r1 = ((rr + mid) / 2) * 200 / 255 + 10;
					int g1 = ((gg + mid) / 2) * 200 / 255 + 10;
					int b1 = ((bb + mid) / 2) * 200 / 255 + 15;
					colors1[pp] = r1 << 16 | g1 << 8 | b1;

					int r2 = ((rr + mid) / 2) * 200 / 255 + 45;
					int g2 = ((gg + mid) / 2) * 200 / 255 + 45;
					int b2 = ((bb + mid) / 2) * 200 / 255 + 55;
					colors2[pp++] = r2 << 16 | g2 << 8 | b2;
				}
			}
		}

		// Creating the screen using a new SpriteSheet
		try
		{
			screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/icons.png"))));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		init();

		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
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
				ticks++;
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
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000)
			{
				lastTimer1 += 1000;
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void tick()
	{
		tickCount++;

		level.tick();

		if (!hasFocus()) // If we don't have focus release the keys, otherwise input can get stuck
		{
			input.releaseAll();
		}

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
		int yScroll = player.y - screen.h / 2;
		if(xScroll<0) xScroll = 0;
		if(yScroll<0) yScroll = 0;
		if(xScroll>level.w * 16 - screen.w) xScroll = level.w * 16 - screen.w;
		if(yScroll>level.h * 16 - screen.h) yScroll = level.h * 16 - screen.h;
		level.renderBackground(screen, xScroll, yScroll);

		// Drawing the screen pixels to the game's pixels using tile/background colors
		for (int y = 0; y < screen.h; y++)
		{
			for (int x = 0; x < screen.w; x++)
			{
				pixels[x + y * WIDTH] = colors1[screen.pixels[x + y * screen.w]];
			}
		}

		// rendering the sprites
		screen.clear();
		level.renderSprites(screen, xScroll, yScroll);

		// If the game window isn't focused display a message!
		if (!this.hasFocus())
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

		// Drawing the screen pixels to the game's pixels using
		// sprite/foreground colors
		for (int y = 0; y < screen.h; y++)
		{
			for (int x = 0; x < screen.w; x++)
			{
				int cc = screen.pixels[x + y * screen.w];
				if (cc < 255) pixels[x + y * WIDTH] = colors2[cc];
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
}
