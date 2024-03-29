package com.itsyourpalmike.ld22.gfx;

public class Screen
{
	public int xOffset;
	public int yOffset;

	public static final int BIT_MIRROR_X = 0x01;
	public static final int BIT_MIRROR_Y = 0x02;

	public int w, h;

	private SpriteSheet sheet;
	public int[] pixels;

	// Override the spritesheet - called by plugins
	public void changeSpritesheet(int w, int h, SpriteSheet sheet)
	{
		System.out.println("changed spritesheer");
		this.w = w;
		this.h = h;
		this.sheet = sheet;

		pixels = new int[w * h];
	}

	public Screen(int w, int h, SpriteSheet sheet)
	{
		this.w = w;
		this.h = h;
		this.sheet = sheet;

		pixels = new int[w * h];
	}

	public Screen(int w, int h)
	{
		this.w = w;
		this.h = h;
		pixels = new int[w * h];
	}

	public void clear(int color)
	{
		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = color;
		}
	}

	public void render(int xp, int yp, int tile, int colors, int bits)
	{
		render(xp, yp, tile, colors, bits, sheet);
	}

	// Renders a single sprite / image
	public void render(int xp, int yp, int tile, int colors, int bits, SpriteSheet altSheet)
	{
		if (altSheet == null) altSheet = sheet;
		xp -= xOffset;
		yp -= yOffset;
		boolean mirrorX = (bits & BIT_MIRROR_X) > 0;
		boolean mirrorY = (bits & BIT_MIRROR_Y) > 0;

		int xTile = tile % 32;
		int yTile = tile / 32;
		int toffs = xTile * 8 + yTile * 8 * altSheet.width;

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

				int col = (colors >> (altSheet.pixels[xs + ys * altSheet.width + toffs] * 8)) & 255;
				if (col < 255) pixels[(x + xp) + (y + yp) * w] = col;
			}
		}
	}

	public void setOffset(int xOffset, int yOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	private int[] dither = new int[] { 0, 8, 2, 10, 12, 4, 14, 6, 3, 11, 1, 9, 15, 7, 13, 5, };

	public void overlay(Screen screen2, int xa, int ya)
	{
		int[] oPixels = screen2.pixels;
		int i = 0;
		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				if (oPixels[i] / 10 <= dither[((x + xa) & 3) + ((y + ya) & 3) * 4]) pixels[i] = 0;
				i++;
			}

		}
	}

	public void renderLight(int x, int y, int r)
	{
		x -= xOffset;
		y -= yOffset;
		int x0 = x - r;
		int x1 = x + r;
		int y0 = y - r;
		int y1 = y + r;

		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 > w) x1 = w;
		if (y1 > h) y1 = h;
		
		for (int yy = y0; yy < y1; yy++)
		{
			int yd = yy - y;
			yd = yd * yd;
			for (int xx = x0; xx < x1; xx++)
			{
				int xd = xx - x;
				int dist = xd * xd + yd;
				
				if (dist <= r * r)
				{
					int br = 255 - dist * 255 / (r * r);
					if (pixels[xx + yy * w] < br) pixels[xx + yy * w] = br;
				}
			}
		}
	}
}
