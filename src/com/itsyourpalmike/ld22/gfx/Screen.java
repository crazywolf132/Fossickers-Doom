package com.itsyourpalmike.ld22.gfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Screen
{
	// Important Screen variables
	//public List<Sprite> sprites = new ArrayList<Sprite>();

	/*public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

	private int[] tiles = new int[MAP_WIDTH * MAP_WIDTH];
	private int[] colors = new int[MAP_WIDTH * MAP_WIDTH];
	private int[] databits = new int[MAP_WIDTH * MAP_WIDTH];*/

	public int xOffset;
	public int yOffset;

	public static final int BIT_MIRROR_X = 0x01;
	public static final int BIT_MIRROR_Y = 0x02;

	public final int w, h;

	private SpriteSheet sheet;
	public int[] pixels;

	public Screen(int w, int h, SpriteSheet sheet)
	{
		this.w = w;
		this.h = h;
		this.sheet = sheet;

		pixels = new int[w * h];

		// Generating a test map
		//Random random = new Random();
		/*for (int i = 0; i < MAP_WIDTH * MAP_WIDTH; i++)
		{
			colors[i] = Color.get(00, 40, 50, 40);
			tiles[i] = 0;
			
			if(random.nextInt(40) == 0)
			{
				tiles[i] = 32;
				colors[i] = Color.get(111, 40, 222, 333);
				databits[i] = random.nextInt(2);
			}
			else if(random.nextInt(40) == 0)
			{
				tiles[i] = 33;
				colors[i] = Color.get(20, 40, 30, 550);
			}
			else
			{
				tiles[i] = random.nextInt(4);
				databits[i] = random.nextInt(4);
			}
		}*/

		//Font.setMap("texting the 02345136", this, 0, 0, Color.get(0, 555, 555, 555));
	}
	
	public void clear()
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = 255;
		}
	}

	// Renders all the tiles in the tile array + the sprites in the Sprite ArrayList
	/*public void renderBackground()
	{
		for (int yt = yScroll >> 3; yt <= (yScroll + h) >> 3; yt++)
		{
			int yp = yt * 8 - yScroll;

			for (int xt = xScroll >> 3; xt <= (xScroll + w) >> 3; xt++)
			{
				int xp = xt * 8 - xScroll;

				int ti = (xt & (MAP_WIDTH_MASK)) + (yt & (MAP_WIDTH_MASK)) * MAP_WIDTH;

				render(xp, yp, tiles[ti], colors[ti], databits[ti]);
			}
		}

		for (int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			render(s.x, s.y, s.img, s.col, s.bits);
		}
		sprites.clear(); // Clears the ArrayList ?
	}*/

	// Renders a single sprite / image
	public void render(int xp, int yp, int tile, int colors, int bits)
	{
		xp -= xOffset;
		yp -= yOffset;
		boolean mirrorX = (bits & BIT_MIRROR_X) > 0;
		boolean mirrorY = (bits & BIT_MIRROR_Y) > 0;

		int xTile = tile % 32;
		int yTile = tile / 32;
		int toffs = xTile * 8 + yTile * 8 * sheet.width;

		for (int y = 0; y < 8; y++)
		{
			int ys = y;
			if (mirrorY)
			{
				ys = 7 - y;
			}
			if (y + yp < 0 || y + yp >= h) continue;
			for (int x = 0; x < 8; x++)
			{
				if (x + xp < 0 || x + xp >= w) continue;

				int xs = x;
				if (mirrorX)
				{
					xs = 7 - x;
				}

				int col = (colors >> (sheet.pixels[xs + ys * sheet.width + toffs] * 8)) & 255;
				if (col < 255) pixels[(x + xp) + (y + yp) * w] = col;
			}
		}
	}

	public void setOffset(int xOffset, int yOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	// Changes the contents of our arrays to represent a desired tile
	/*public void setTile(int x, int y, int tile, int color, int bits)
	{
		int tp = (x & MAP_WIDTH_MASK) + (y & MAP_WIDTH_MASK) * MAP_WIDTH;
		tiles[tp] = tile;
		colors[tp] = color;
		databits[tp] = bits;
	}*/
}
