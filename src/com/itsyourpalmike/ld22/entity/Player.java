package com.itsyourpalmike.ld22.entity;

import java.util.List;

import com.itsyourpalmike.ld22.InputHandler;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.level.Level;

public class Player extends Mob
{
	private InputHandler input;
	private boolean wasAttacking;
	private int attackTime, attackDir;

	public Player(InputHandler input)
	{
		this.input = input;
		x = y = 24;
	}

	public void tick()
	{
		super.tick();

		// Moving the player
		int xa = 0;
		int ya = 0;
		if (input.up)
		{
			ya--;
		}
		if (input.down)
		{
			ya++;
		}
		if (input.left)
		{
			xa--;
		}
		if (input.right)
		{
			xa++;
		}

		move(xa, ya);

		// Attacking
		if (input.attack)
		{
			if (!wasAttacking)
			{
				attack();
			}
			wasAttacking = true;
		}
		else
		{
			wasAttacking = false;
		}

		if (attackTime > 0) attackTime--;
	}

	private void attack()
	{
		walkDist += 8;
		attackDir = dir;
		attackTime = 5;
		int yo = -2;

		// Hurts entities inside of tiles within the player's attack zone
		if (dir == 0)
		{
			hurt(x - 8, y + 4+yo, x + 8, y + 12+yo);
		}
		if (dir == 1)
		{
			hurt(x - 8, y - 12+yo, x + 8, y - 4+yo);
		}
		if (dir == 3)
		{
			hurt(x + 4, y - 8+yo, x + 12, y + 8+yo);
		}
		if (dir == 2)
		{
			hurt(x - 12, y - 8+yo, x - 4, y + 8+yo);
		}

		// Hurts the tile the player is facing
		int xt = x >> 4;
		int yt = (y+yo) >> 4;
		int r = 12;

		if (attackDir == 0) yt = (y + r+yo) >> 4;
		if (attackDir == 1) yt = (y - r+yo) >> 4;
		if (attackDir == 2) xt = (x - r) >> 4;
		if (attackDir == 3) xt = (x + r) >> 4;

		if (xt >= 0 && yt >= 0 && xt < level.w && yt < level.h)
		{
			level.getTile(xt, yt).hurt(level, xt, yt, this, random.nextInt(4) + 1, attackDir);
		}

	}

	// Hurting enemies
	private void hurt(int x0, int y0, int x1, int y1)
	{
		List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			if (e != this) entities.get(i).hurt(this, random.nextInt(4) + 1, attackDir);
		}
	}

	public void render(Screen screen)
	{
		// Drawing the player
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

			xt = 4 + ((walkDist >> 3) & 1) * 2;
		}

		int xo = x - 8;
		int yo = y - 11;
		if(inWater())
		{
			yo+= 4;
			int waterColor = Color.get(-1, -1, 115, 115);
			if(tickTime/8%2==0)
			{
				waterColor = Color.get(-1, 335, 5, 115);
			}
			screen.render(xo + 0, yo + 3, 5 + 13 * 32, waterColor, 0);
			screen.render(xo + 8, yo + 3, 5 + 13 * 32, waterColor, 1);
			
		}


		// Rendering attack thing-a-ma-bob
		if (attackTime > 0 && attackDir == 1)
		{
			screen.render(xo + 0, yo - 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555), 0);
			screen.render(xo + 8, yo - 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555), 1);
		}

		// Rendering the player
		int col = Color.get(-1, 111, 145, 543);
		if(hurtTime > 0)
		{
			col = Color.get(-1, 555, 555, 555);
		}
		screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1);
		screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32,col, flip1);
		
		if(!inWater())
		{
			screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2);
			screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2);
		}
		
		// Rendering attack thing-a-ma-bob
		if (attackTime > 0 && attackDir == 2)
		{
			screen.render(xo - 4, yo, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 1);
			screen.render(xo - 4, yo + 8, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 3);
		}

		// Rendering attack thing-a-ma-bob
		if (attackTime > 0 && attackDir == 3)
		{
			screen.render(xo + 8 + 4, yo, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 0);
			screen.render(xo + 8 + 4, yo + 8, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 2);
		}

		// Rendering attack thing-a-ma-bob
		if (attackTime > 0 && attackDir == 0)
		{
			screen.render(xo + 0, yo + 8 + 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555), 2);
			screen.render(xo + 8, yo + 8 + 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555), 3);
		}
	}
	
	public void touchItem(ItemEntity itemEntity)
	{
		itemEntity.take(this);
	}

	public boolean canSwim()
	{
		return true;
	}
}
