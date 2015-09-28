package com.itsyourpalmike.ld22.entity;

import java.util.List;

import com.itsyourpalmike.ld22.entity.particles.TextParticle;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.level.tile.Tile;

public class Mob extends Entity
{
	protected int walkDist = 0;
	protected int dir = 0;
	public int hurtTime = 0;
	protected int xKnockback, yKnockback;
	public int health = 10;

	public Mob()
	{
		x = y = 8;
		xr = 4;
		yr = 3;
	}
	
	public void tick()
	{
		if(health <= 0)
		{
			remove();
		}
		
		if(hurtTime > 0) hurtTime--;
	}

	public boolean move(int xa, int ya)
	{
		if(xKnockback < 0)
		{
			move2(-1, 0);
			xKnockback++;
		}
		if(xKnockback > 0)
		{
			move2(1, 0);
			xKnockback--;
		}
		if(yKnockback < 0)
		{
			move2(0, -1);
			yKnockback++;
		}
		if(yKnockback > 0)
		{
			move2(0, 1);
			yKnockback--;
		}
		if(hurtTime > 0) return true;
		
		if (xa != 0 || ya != 0)
		{
			walkDist++;
			if (xa < 0) dir = 2;
			if (xa > 0) dir = 3;
			if (ya < 0) dir = 1;
			if (ya > 0) dir = 0;
		}
		
		
		return super.move(xa, ya);
	}


	public boolean blocks(Entity e)
	{
		return e.isBlockableBy(this);
	}
	
	public void hurt(Mob mob, int dmg, int attackDir)
	{
		doHurt(dmg, attackDir);
	}
	
	protected void doHurt(int dmg, int attackDir)
	{
		// Removes health, adds particles, and sets knockback
		if(hurtTime > 0) return;
		
		level.add(new TextParticle("" +dmg, x, y, Color.get(-1,  500,  500,  500)));
		health -= dmg;
		
		if(attackDir==0) yKnockback = 6;
		if(attackDir==1) yKnockback = -6;
		if(attackDir==2) xKnockback = -6;
		if(attackDir==3) xKnockback = 6;
		hurtTime = 10;
	}
	
	public void hurt(Tile tile, int x, int y, int dmg)
	{
		int attackDir = dir^1;
		doHurt(dmg, attackDir);
	}
	
	// validates a starting position so mobs don't spawn inside of walls
	public void findStartPos(Level level)
	{
		while(true)
		{
			int x = random.nextInt(level.w);
			int y = random.nextInt(level.h);
			if(level.getTile(x, y).mayPass(level, x, y, this))
			{
				this.x = x * 16 + 8;
				this.y = y * 16 + 8;
				break;
			}
		}
	}
}
