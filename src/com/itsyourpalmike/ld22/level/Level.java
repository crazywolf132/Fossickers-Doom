package com.itsyourpalmike.ld22.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.itsyourpalmike.ld22.entity.Entity;
import com.itsyourpalmike.ld22.entity.Mob;
import com.itsyourpalmike.ld22.entity.Player;
import com.itsyourpalmike.ld22.entity.Slime;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.levelgen.LevelGen;
import com.itsyourpalmike.ld22.level.tile.Tile;

public class Level
{
	private Random random = new Random();
	public int w, h;

	public byte[] tiles;
	public byte[] data;

	public int grassColor = 141;
	public int dirtColor = 322;
	public int sandColor = 550;

	public List<Entity> entities = new ArrayList<Entity>();
	public List<Entity>[] entitiesInTiles; // we keep track of what tiles entities are inside of for easy optimization / attacking
	private Comparator<Entity> spriteSorter = new Comparator<Entity>()
	{
		// Sorts entities so that the ones farther up get rendered behind the ones farther down
		public int compare(Entity e0, Entity e1)
		{
			if (e1.y < e0.y) return 1;
			if (e1.y > e0.y) return -1;
			return 0;
		}
	};

	@SuppressWarnings("unchecked")
	public Level(int w, int h, int level, Level parentLevel)
	{
		this.w = w;
		this.h = h;

		byte[][] maps;

		if (level == 1)
		{
			dirtColor = 444;
		}
		if (level == 0) maps = LevelGen.createTopMap(w, h);
		else if (level < 0) maps = LevelGen.createUndergroundMap(w, h, -level);
		else maps = LevelGen.createSkyMap(w, h); // Sky level

		tiles = maps[0];
		data = maps[1];

		if (parentLevel != null)
		{
			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
					if (parentLevel.getTile(x, y) == Tile.stairsDown)
					{
						setTile(x, y, Tile.stairsUp, 0);
						setTile(x - 1, y, Tile.dirt, 0);
						setTile(x + 1, y, Tile.dirt, 0);
						setTile(x, y - 1, Tile.dirt, 0);
						setTile(x, y + 1, Tile.dirt, 0);
						setTile(x - 1, y - 1, Tile.dirt, 0);
						setTile(x - 1, y + 1, Tile.dirt, 0);
						setTile(x + 1, y - 1, Tile.dirt, 0);
						setTile(x + 1, y + 1, Tile.dirt, 0);
					}
				}
			}
		}

		entitiesInTiles = new ArrayList[w * h];

		for (int i = 0; i < w * h; i++)
		{
			entitiesInTiles[i] = new ArrayList<Entity>();
		}
	}

	public void renderBackground(Screen screen, int xScroll, int yScroll)
	{
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = (screen.w + 15) >> 4;
		int h = (screen.h + 15) >> 4;

		screen.setOffset(xScroll, yScroll);

		for (int y = yo; y <= h + yo; y++)
		{
			for (int x = xo; x <= w + xo; x++)
			{
				getTile(x, y).render(screen, this, x, y);
			}
		}
		screen.setOffset(0, 0);
	}

	// Renders entities inside of tiles in the view area
	List<Entity> rowSprites = new ArrayList<Entity>();
	public Player player;

	public void renderSprites(Screen screen, int xScroll, int yScroll)
	{
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = (screen.w + 15) >> 4;
		int h = (screen.h + 15) >> 4;

		screen.setOffset(xScroll, yScroll);

		for (int y = yo - 1; y <= h + yo + 1; y++)
		{
			for (int x = xo - 1; x <= w + xo + 1; x++)
			{
				if (x < 0 || y < 0 || x >= this.w || y >= this.h) continue;
				this.rowSprites.addAll(entitiesInTiles[x + y * this.w]);
			}

			if (rowSprites.size() > 0)
			{
				sortAndRender(screen, rowSprites);
			}

			this.rowSprites.clear();
		}
		screen.setOffset(0, 0);
	}

	public void renderLight(Screen screen, int xScroll, int yScroll)
	{
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = (screen.w + 15) >> 4;
		int h = (screen.h + 15) >> 4;

		screen.setOffset(xScroll, yScroll);
		int r = 4;

		for (int y = yo - r; y <= h + yo + r; y++)
		{
			for (int x = xo - r; x <= w + xo + r; x++)
			{
				if (x < 0 || y < 0 || x >= this.w || y >= this.h) continue;
				List<Entity> entities = entitiesInTiles[x + y * this.w];
				for (int i = 0; i < entities.size(); i++)
				{
					Entity e = entities.get(i);
					int lr = e.getLightRadius();

					if (lr > 0)
					{
						screen.renderLight(e.x - 1, e.y - 4, lr * 8);
					}
				}
				int lr = getTile(x, y).getLightRadius(this, x, y);
				if (lr > 0) screen.renderLight(x * 16 + 8, y * 16 + 8, lr * 8);

			}
		}
		screen.setOffset(0, 0);
	}

	// private void renderLight(Screen screen, int x, int y, int r)
	// {
	// screen.renderLight(x,y,r);
	// }

	/// sorts entities before rendering them
	private void sortAndRender(Screen screen, List<Entity> list)
	{
		Collections.sort(list, spriteSorter);
		for (int i = 0; i < list.size(); i++)
		{
			list.get(i).render(screen);
		}
	}

	public Tile getTile(int x, int y)
	{
		if (x < 0 || y < 0 || x >= w || y >= h) return Tile.rock;
		return Tile.tiles[tiles[x + y * w]];
	}

	public void setTile(int x, int y, Tile t, int dataVal)
	{
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		tiles[x + y * w] = t.id;
		data[x + y * w] = (byte)dataVal;
	}

	public int getData(int x, int y)
	{
		if (x < 0 || y < 0 || x >= w || y >= h) return 0;
		return data[x + y * w] & 0xff;
	}

	public void setData(int x, int y, int val)
	{
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		data[x + y * w] = (byte)val;
	}

	// Adds entity to entities arraylist and inserts it into tile/entity spot
	public void add(Entity entity)
	{
		if (entity instanceof Player)
		{
			player = (Player)entity;
		}
		entity.removed = false;
		entities.add(entity);
		entity.init(this);

		insertEntity(entity.x >> 4, entity.y >> 4, entity);
	}

	public void remove(Entity e)
	{
		entities.remove(e);
		int xto = e.x >> 4;
		int yto = e.y >> 4;
		removeEntity(xto, yto, e);
	}

	// Adding / Removing entities from tiles
	///////////////////////////////////////////////////////////
	private void removeEntity(int x, int y, Entity e)
	{
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		entitiesInTiles[x + y * w].remove(e);
	}

	private void insertEntity(int x, int y, Entity e)
	{
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		entitiesInTiles[x + y * w].add(e);
	}
	///////////////////////////////////////////////////////////

	public void tick()
	{
		// Spawn in some mobs
		for (int i = 0; i < 10; i++)
		{
			Mob m = new Slime(random.nextInt(4) + 1);
			if (m.findStartPos(this))
			{
				this.add(m);
			}
		}

		// Ticks tiles with delays and randomization offset
		for (int i = 0; i < w * h / 50; i++)
		{
			int xt = random.nextInt(w);
			int yt = random.nextInt(w);
			getTile(xt, yt).tick(this, xt, yt);
		}

		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			int xto = e.x >> 4;
			int yto = e.y >> 4;

			e.tick();

			// Get rid of entities (dead mob, picked up loot, etc...)
			if (e.removed)
			{
				entities.remove(i--);
				removeEntity(xto, yto, e);
				continue;
			}
			else
			{
				int xt = e.x >> 4;
				int yt = e.y >> 4;

				// If an entity has moved to a new tile... KEEP TRACK OF IT!!!
				if (xto != xt || yto != yt)
				{
					removeEntity(xto, yto, e);
					insertEntity(xt, yt, e);
				}
			}
		}
	}

	// Gets entities within tiles of a certain area
	public List<Entity> getEntities(int x0, int y0, int x1, int y1)
	{
		List<Entity> result = new ArrayList<Entity>();

		int xt0 = (x0 >> 4) - 1;
		int yt0 = (y0 >> 4) - 1;
		int xt1 = (x1 >> 4) + 1;
		int yt1 = (y1 >> 4) + 1;

		for (int y = yt0; y <= yt1; y++)
		{
			for (int x = xt0; x <= xt1; x++)
			{
				if (x < 0 || y < 0 || x >= w || y >= h) continue;
				List<Entity> entities = entitiesInTiles[x + y * this.w];

				for (int i = 0; i < entities.size(); i++)
				{
					Entity e = entities.get(i);
					if (e.intersects(x0, y0, x1, y1)) result.add(e);
				}
			}
		}
		return result;
	}
}
