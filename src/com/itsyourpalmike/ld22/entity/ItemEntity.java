package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.entity.particles.TextParticle;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.level.Level;

// This is the entity version of actual items that are on the ground in the level
public class ItemEntity extends Entity
{
	protected int walkDist = 0;
	protected int dir = 0;
	public int hurtTime = 0;
	protected int xKnockback, yKnockback;
	public double xa, ya, za;
	public double xx, yy, zz;
	private Item item;
	private int time = 0;

	public ItemEntity(Item item, int x, int y)
	{
		this.item = item;
		xx = this.x = x;
		yy = this.y = y;
		xr = 2;
		yr = 2;
		
		zz = 2;
		za = random.nextFloat() * 0.7 + 1;
		xa = random.nextGaussian()*0.3;
		ya = random.nextGaussian()*0.2;
	}
	
	public void tick()
	{
		time++;
		xx+=xa;
		yy+=ya;
		zz+=za;
		if(zz < 0)
		{
			zz = 0;
			za *= -0.5;
			xa *= 0.6;
			ya *= 0.6;
		}
		za -= 0.15;
		
		int nx=(int)xx;
		int ny=(int)yy;
		
		move(nx-x,ny-y);
		if(hurtTime > 0) hurtTime--;
	}
	
	public boolean isBlockableBy(Mob mob)
	{
		
		return false;
	}
	
	public void render(Screen screen)
	{
		
		int col = item.getColor();

		screen.render(x-4, y-4, item.getSprite(), Color.get(-1, 0, 0, 0), 0);
		screen.render(x-4, y-4-(int)(zz), item.getSprite(), col, 0);
	}
	
	protected void touchedBy(Entity entity)
	{
		if(time > 30)entity.touchItem(this);
	}

	public void take(Player player)
	{
		item.onTake(this);
		remove();
	}
	
}
