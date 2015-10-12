package com.itsyourpalmike.ld22.entity;

import java.util.List;

import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.tile.Tile;
import com.itsyourpalmike.ld22.plugin.UltimatePlugin;
import com.itsyourpalmike.ld22.sound.Sound;

// This is the entity version of actual items that are on the ground in the level
public class ArrowEntity extends Entity
{
	private int lifeTime;
	public double xa, ya;
	public double xx, yy;
	private int time;
	private Mob owner;
	private int dir;

	public ArrowEntity(Mob owner)
	{
		this.owner = owner;

		xx = this.x = owner.x;
		yy = this.y = owner.y;
		xr = 0;
		yr = 0;

		xa = ya = 0;
		this.dir = owner.dir;

		if (owner.dir == 2)
		{
			this.xa = -1;
		}
		else if (owner.dir == 3)
		{
			this.xa = 1;
		}
		else if (owner.dir == 1)
		{
			this.ya = -1;
		}
		else
		{
			this.ya = 1;
		}

		xa *= 2.5;
		ya *= 2.5;

		lifeTime = 60 * 10 + random.nextInt(30);
	}

	public void tick()
	{
		time++;

		if (time >= lifeTime)
		{
			remove();
			return;
		}

		xx += xa;
		yy += ya;

		x = (int)xx;
		y = (int)yy;

		List<Entity> toHit = level.getEntities(x, y, x, y);
		for (int i = 0; i < toHit.size(); i++)
		{
			Entity e = toHit.get(i);
			if (e instanceof Mob && !(e.getClass() == owner.getClass()))
			{
				e.hurt(owner, random.nextInt(5)+1, dir);
				this.remove();

				int xd = level.player.x - x;
				int yd = level.player.y - y;
				if (xd * xd + yd * yd < 80 * 80)
				{
					Sound.play("monsterHurt");
				}
			}
		}

		if (owner.level.getTile(x / 16, y / 16).id != Tile.get("water").id && owner.level.getTile(x / 16, y / 16).id != Tile.get("lava").id && !(owner.level.getTile(x / 16, y / 16).mayPass(level, x / 16, y / 16, this)))
		{
			owner.level.getTile(x / 16, y / 16).hurt(level, x / 16, y / 16, owner, 1, dir);
			remove();

			int xd = level.player.x - x;
			int yd = level.player.y - y;
			if (xd * xd + yd * yd < 80 * 80)
			{
				Sound.play("monsterHurt");
			}
		}
	}

	public boolean isBlockableBy(Mob mob)
	{
		return false;
	}

	public void render(Screen screen)
	{
		if (time >= lifeTime - 6 * 20)
		{
			if (time / 6 % 2 == 0) return;
		}

		int xt = 2;

		int flipper = 0;
		if (xa < 0) flipper = 1;
		if (ya != 0) xt++;

		if (ya > 0) flipper = 2;

		screen.render(x - 4, y - 4 - 2, xt, Color.get(-1, 555, 555, 321), flipper, UltimatePlugin.ultimateSheet);
	}

}