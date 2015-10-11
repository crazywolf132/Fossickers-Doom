package com.itsyourpalmike.ld22;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.itsyourpalmike.ld22.crafting.Crafting;
import com.itsyourpalmike.ld22.crafting.FurnitureRecipe;
import com.itsyourpalmike.ld22.crafting.ResourceRecipe;
import com.itsyourpalmike.ld22.crafting.ToolRecipe;
import com.itsyourpalmike.ld22.entity.AirWizard;
import com.itsyourpalmike.ld22.entity.Anvil;
import com.itsyourpalmike.ld22.entity.Chest;
import com.itsyourpalmike.ld22.entity.Furnace;
import com.itsyourpalmike.ld22.entity.Lantern;
import com.itsyourpalmike.ld22.entity.Oven;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.entity.Slime;
import com.itsyourpalmike.ld22.entity.Workbench;
import com.itsyourpalmike.ld22.entity.Zombie;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.gfx.SpriteSheet;
import com.itsyourpalmike.ld22.item.ToolType;
import com.itsyourpalmike.ld22.item.resource.FoodResource;
import com.itsyourpalmike.ld22.item.resource.PlantableResource;
import com.itsyourpalmike.ld22.item.resource.Resource;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.level.tile.CactusTile;
import com.itsyourpalmike.ld22.level.tile.CloudCactusTile;
import com.itsyourpalmike.ld22.level.tile.CloudTile;
import com.itsyourpalmike.ld22.level.tile.DirtTile;
import com.itsyourpalmike.ld22.level.tile.FarmTile;
import com.itsyourpalmike.ld22.level.tile.FlowerTile;
import com.itsyourpalmike.ld22.level.tile.GrassTile;
import com.itsyourpalmike.ld22.level.tile.HardRockTile;
import com.itsyourpalmike.ld22.level.tile.HoleTile;
import com.itsyourpalmike.ld22.level.tile.InfiniteFallTile;
import com.itsyourpalmike.ld22.level.tile.LavaTile;
import com.itsyourpalmike.ld22.level.tile.OreTile;
import com.itsyourpalmike.ld22.level.tile.RockTile;
import com.itsyourpalmike.ld22.level.tile.SandTile;
import com.itsyourpalmike.ld22.level.tile.SaplingTile;
import com.itsyourpalmike.ld22.level.tile.StairsTile;
import com.itsyourpalmike.ld22.level.tile.Tile;
import com.itsyourpalmike.ld22.level.tile.TreeTile;
import com.itsyourpalmike.ld22.level.tile.WaterTile;
import com.itsyourpalmike.ld22.level.tile.WheatTile;
import com.itsyourpalmike.ld22.screen.DeadMenu;
import com.itsyourpalmike.ld22.screen.LevelTransitionMenu;
import com.itsyourpalmike.ld22.screen.Menu;
import com.itsyourpalmike.ld22.screen.TitleMenu;
import com.itsyourpalmike.ld22.screen.WonMenu;
import com.itsyourpalmike.ld22.sound.Sound;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;

	// Game constants
	public final static int WIDTH = 160;
	public final static int HEIGHT = 120;
	public final static int SCALE = 3;
	public final static Dimension DIMENSIONS = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	public final static String NAME = "Minicraft: Ultimate Edition";
	public static Collection<MinicraftPlugin> plugins;
	public JFrame frame;

	// Important game variables
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private int[] colors = new int[256];
	private boolean running = false;

	// Important game objects
	private InputHandler input = new InputHandler(this);

	public Screen screen;
	public Screen lightScreen;

	public Level level;
	private Level[] levels = new Level[5];
	private int currentLevel = 3;

	public Player player;

	public Menu menu;

	private int playerDeadTime;
	private int pendingLevelChange;
	private int wonTimer = 0;
	public boolean hasWon = false;
	public int tickCount = 0;
	public int gameTime = 0;

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

		for (int i = 0; i < 5; i++)
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

		lightScreen = new Screen(WIDTH, HEIGHT);
		try
		{
			screen = new Screen(Game.WIDTH, Game.HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResource("/icons.png"))));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		loadVanilla();

		PluginManager pm = PluginManagerFactory.createPluginManager();
		pm.addPluginsFrom(new File("C:/Users/Mike/Desktop/plugins/").toURI());
		plugins = new PluginManagerUtil(pm).getPlugins(MinicraftPlugin.class);
		for (MinicraftPlugin plugin : plugins)
		{
			plugin.onLoad(this);
			System.out.println("Loaded:\"" + plugin.getName() + "\"");
		}

		resetGame();

		// Displays the Main Menu Screen
		setMenu(new TitleMenu());
	}

	private void loadVanilla()
	{
		Sound.load("playerHurt", this.getClass().getResource("/playerhurt.wav"));
		Sound.load("playerDeath", this.getClass().getResource("/death.wav"));
		Sound.load("monsterHurt", this.getClass().getResource("/monsterhurt.wav"));
		Sound.load("test", this.getClass().getResource("/test.wav"));
		Sound.load("pickup", this.getClass().getResource("/pickup.wav"));
		Sound.load("bossdeath", this.getClass().getResource("/bossdeath.wav"));
		Sound.load("craft", this.getClass().getResource("/craft.wav"));

		Tile.load("grass", new GrassTile());
		Tile.load("rock", new RockTile());
		Tile.load("water", new WaterTile());
		Tile.load("flower", new FlowerTile());
		Tile.load("tree", new TreeTile());
		Tile.load("dirt", new DirtTile());
		Tile.load("sand", new SandTile());
		Tile.load("cactus", new CactusTile());
		Tile.load("hole", new HoleTile());
		Tile.load("treeSapling", new SaplingTile("grass", "tree"));
		Tile.load("cactusSapling", new SaplingTile("sand", "cactus"));
		Tile.load("farmland", new FarmTile());
		Tile.load("wheat", new WheatTile());
		Tile.load("lava", new LavaTile());
		Tile.load("stairsDown", new StairsTile(false));
		Tile.load("stairsUp", new StairsTile(true));
		Tile.load("infiniteFall", new InfiniteFallTile());
		Tile.load("cloud", new CloudTile());
		Tile.load("hardRock", new HardRockTile());
		Tile.load("ironOre", new OreTile("I.ORE"));
		Tile.load("goldOre", new OreTile("G.ORE"));
		Tile.load("gemOre", new OreTile("gem"));
		Tile.load("cloudCactus", new CloudCactusTile());

		Resource.load(new Resource("Wood", 1 + 4 * 32, Color.get(-1, 200, 531, 430)));
		Resource.load(new Resource("Stone", 2 + 4 * 32, Color.get(-1, 111, 333, 555)));
		Resource.load(new PlantableResource("Flower", 0 + 4 * 32, Color.get(-1, 10, 444, 330), "flower", "grass"));
		Resource.load(new PlantableResource("Acorn", 3 + 4 * 32, Color.get(-1, 100, 531, 320), "treeSapling", "grass"));
		Resource.load(new PlantableResource("Dirt", 2 + 4 * 32, Color.get(-1, 100, 322, 432), "dirt", "hole", "water", "lava"));
		Resource.load(new PlantableResource("Sand", 2 + 4 * 32, Color.get(-1, 110, 440, 550), "sand", "grass", "dirt"));
		Resource.load(new PlantableResource("Cactus", 4 + 4 * 32, Color.get(-1, 10, 40, 50), "cactusSapling", "sand"));
		Resource.load(new PlantableResource("Seeds", 5 + 4 * 32, Color.get(-1, 10, 40, 50), "wheat", "farmland"));
		Resource.load(new Resource("Wheat", 6 + 4 * 32, Color.get(-1, 110, 330, 550)));
		Resource.load(new FoodResource("Bread", 8 + 4 * 32, Color.get(-1, 110, 330, 550), 2, 5));
		Resource.load(new FoodResource("Apple", 9 + 4 * 32, Color.get(-1, 100, 300, 500), 1, 5));

		Resource.load(new Resource("COAL", 10 + 4 * 32, Color.get(-1, 000, 111, 111)));
		Resource.load(new Resource("I.ORE", 10 + 4 * 32, Color.get(-1, 100, 322, 544)));
		Resource.load(new Resource("G.ORE", 10 + 4 * 32, Color.get(-1, 110, 440, 553)));
		Resource.load(new Resource("IRON", 11 + 4 * 32, Color.get(-1, 100, 322, 544)));
		Resource.load(new Resource("GOLD", 11 + 4 * 32, Color.get(-1, 110, 330, 553)));

		Resource.load(new Resource("SLIME", 10 + 4 * 32, Color.get(-1, 10, 30, 50)));
		Resource.load(new Resource("GLASS", 12 + 4 * 32, Color.get(-1, 555, 555, 555)));
		Resource.load(new Resource("cloth", 1 + 4 * 32, Color.get(-1, 25, 252, 141)));
		Resource.load(new PlantableResource("cloud", 2 + 4 * 32, Color.get(-1, 222, 555, 444), "cloud", "infiniteFall"));
		Resource.load(new Resource("gem", 13 + 4 * 32, Color.get(-1, 101, 404, 545)));

		try
		{
			// WORKBENCH RECIPES
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			Crafting.workbenchRecipes.add(new FurnitureRecipe(Lantern.class).addCost("Wood", 5).addCost("SLIME", 10).addCost("GLASS", 4));

			Crafting.workbenchRecipes.add(new FurnitureRecipe(Oven.class).addCost("Stone", 15));
			Crafting.workbenchRecipes.add(new FurnitureRecipe(Furnace.class).addCost("Stone", 20));
			Crafting.workbenchRecipes.add(new FurnitureRecipe(Workbench.class).addCost("Wood", 20));
			Crafting.workbenchRecipes.add(new FurnitureRecipe(Chest.class).addCost("Wood", 20));
			Crafting.workbenchRecipes.add(new FurnitureRecipe(Anvil.class).addCost("IRON", 5));

			Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.sword, 0).addCost("Wood", 5));
			Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.axe, 0).addCost("Wood", 5));
			Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.hoe, 0).addCost("Wood", 5));
			Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.pickaxe, 0).addCost("Wood", 5));
			Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.shovel, 0).addCost("Wood", 5));
			Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.sword, 1).addCost("Wood", 5).addCost("Stone", 5));
			Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.axe, 1).addCost("Wood", 5).addCost("Stone", 5));
			Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.hoe, 1).addCost("Wood", 5).addCost("Stone", 5));
			Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.pickaxe, 1).addCost("Wood", 5).addCost("Stone", 5));
			Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.shovel, 1).addCost("Wood", 5).addCost("Stone", 5));
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			// ANVIL RECIPES
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.sword, 2).addCost("Wood", 5).addCost("IRON", 5));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.axe, 2).addCost("Wood", 5).addCost("IRON", 5));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.hoe, 2).addCost("Wood", 5).addCost("IRON", 5));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.pickaxe, 2).addCost("Wood", 5).addCost("IRON", 5));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.shovel, 2).addCost("Wood", 5).addCost("IRON", 5));

			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.sword, 3).addCost("Wood", 5).addCost("GOLD", 5));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.axe, 3).addCost("Wood", 5).addCost("GOLD", 5));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.hoe, 3).addCost("Wood", 5).addCost("GOLD", 5));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.pickaxe, 3).addCost("Wood", 5).addCost("GOLD", 5));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.shovel, 3).addCost("Wood", 5).addCost("GOLD", 5));

			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.sword, 4).addCost("Wood", 5).addCost("gem", 50));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.axe, 4).addCost("Wood", 5).addCost("gem", 50));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.hoe, 4).addCost("Wood", 5).addCost("gem", 50));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.pickaxe, 4).addCost("Wood", 5).addCost("gem", 50));
			Crafting.anvilRecipes.add(new ToolRecipe(ToolType.shovel, 4).addCost("Wood", 5).addCost("gem", 50));
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			// FURNACE RECIPES
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			Crafting.furnaceRecipes.add(new ResourceRecipe("IRON").addCost("I.ORE", 4).addCost("COAL", 1));
			Crafting.furnaceRecipes.add(new ResourceRecipe("GOLD").addCost("G.ORE", 4).addCost("COAL", 1));
			Crafting.furnaceRecipes.add(new ResourceRecipe("Glass").addCost("Sand", 4).addCost("COAL", 1));
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			// OVEN RECIPES
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			Crafting.ovenRecipes.add(new ResourceRecipe("Bread").addCost("Wheat", 4));
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Level.addMobToLevelSpawner(Slime.class);
		Level.addMobToLevelSpawner(Zombie.class);
		Level.addMobToLevelSpawner(AirWizard.class);

		System.out.println("Loaded:\"Vanilla Minicraft\"");
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
				if (wonTimer > 0)
				{
					if (--wonTimer == 0)
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

	public void won()
	{
		wonTimer = 60 * 3;
		hasWon = true;
	}
}
