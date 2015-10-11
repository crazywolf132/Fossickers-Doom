package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.ResourceItem;
import com.itsyourpalmike.ld22.item.resource.Resource;

public class Zombie extends Mob
{
	private int xa, ya;
	private int lvl;
	private int randomWalkTime = 0; 

	public Zombie()
	{
		x = random.nextInt(64 * 16);
		y = random.nextInt(64 * 16);
		spawnChance = 3;
	}
	
	public void setLvl(int lvl)
	{
		this.lvl = lvl;
		health = maxHealth = lvl * lvl * 10;
	}
	

	public boolean canSpawn(int level)
	{
		return random.nextInt(5) <= 1;
	}

	public void tick()
	{
		super.tick();

		if (level.player != null && randomWalkTime == 0)
		{
			int xd = level.player.x - x;
			int yd = level.player.y - y;
			if (xd * xd + yd * yd < 50 * 50)
			{
				xa = 0;
				ya = 0;
				if (xd < 0) xa = -1;
				if (xd > 0) xa = 1;
				if (yd < 0) ya = -1;
				if (yd > 0) ya = 1;
			}
		}

		int speed = tickTime & 1;

		// movement changes if mob can't move in current direction or if random number is zero
		if (!move(xa * speed, ya * speed) || random.nextInt(200) == 0)
		{
			randomWalkTime = 60;
			xa = (random.nextInt(3) - 1) * random.nextInt(2);
			ya = (random.nextInt(3) - 1) * random.nextInt(2);
		}
		if(randomWalkTime > 0) randomWalkTime--;

	}

	public void render(Screen screen)
	{
		int xt = 0;
		int yt = 14;
		int flip1 = (walkDist >> 3) & 1;
		int flip2 = (walkDist >> 3) & 1;

		if (dir == 1)
		{
			xt += 2;
		}

		if (dir > 1)
		{
			flip1 = 0;
			flip2 = ((walkDist >> 4) & 1);

			if (dir == 2)
			{
				flip1 = 1;
			}

			xt += 4 + ((walkDist >> 3) & 1) * 2;
		}

		int xo = x - 8;
		int yo = y - 11;

		int col;

		col = Color.get(-1, 10, 252, 050);
		if (lvl == 2) col = Color.get(-1, 100, 522, 050);
		if (lvl == 3) col = Color.get(-1, 111, 444, 050);
		if (lvl == 4) col = Color.get(-1, 000, 111, 020);

		if (hurtTime > 0)
		{
			col = Color.get(-1, 555, 555, 555); // Render mob white (because it's hit)
		}

		// Rendering the mob
		screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1);
		screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1);
		screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2);
		screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2);
	}

	protected void touchedBy(Entity entity)
	{
		if (entity instanceof Player)
		{
			entity.hurt(this, lvl + 1, ((Player)entity).dir ^ 1);
		}
		
		if(level.player != null)
		{
			level.player.score+=50*lvl;
		}
	}
	
	protected void die()
	{
		super.die();

		int count = random.nextInt(2) + 1;

		for (int i = 0; i < count; i++)
			level.add(new ItemEntity(new ResourceItem(Resource.get("cloth")), x + random.nextInt(11) - 5, y + random.nextInt(11) - 5));
	}

}
