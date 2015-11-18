package com.itsyourpalmike.ld22.entity;

import java.util.Random;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.plugin.UltimatePlugin;

public class Pumpkin extends Furniture
{
	int count = 0;
	int brightness = 5;
	private static final Random random = new Random();
	
	public Pumpkin()
	{
		super("Pumpkin");
		col = Color.get(-1, 210, 530, 550);
		sprite = 4;
		xr = 3;
		yr = 2;
		sheet = UltimatePlugin.icons2;
	}

	public int getLightRadius()
	{
		// FLickering light??
		count++;
		if(count >= 180)
		{
			int jackpot = random.nextInt(2);
			if(jackpot == 0) brightness = 6;
			else brightness = 5;
			count = 0;
		}
		
		return 0;
	}
	
}
