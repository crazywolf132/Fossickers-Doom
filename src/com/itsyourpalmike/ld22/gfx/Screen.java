package com.itsyourpalmike.ld22.gfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Screen
{
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
	}
	
	public void clear()
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = 0;
		}
	}

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
}
