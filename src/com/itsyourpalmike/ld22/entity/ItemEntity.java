package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.entity.particles.TextParticle;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.level.Level;

public class ItemEntity extends Entity
{
	protected int walkDist = 0;
	protected int dir = 0;
	public int hurtTime = 0;
	protected int xKnockback, yKnockback;
	public int health = 10;

	public ItemEntity(Item item)
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
	
	public void hurt(Mob mob, int damage, int attackDir)
	{
		// Removes health, adds particles, and sets knockback
		level.add(new TextParticle("" +damage, x, y, Color.get(-1,  500,  500,  500)));
		health -= damage;
		
		if(attackDir==0) yKnockback = 6;
		if(attackDir==1) yKnockback = -6;
		if(attackDir==2) xKnockback = -6;
		if(attackDir==3) xKnockback = 6;
		hurtTime = 10;
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
