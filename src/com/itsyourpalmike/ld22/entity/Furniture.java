package com.itsyourpalmike.ld22.entity;

import com.itsyourpalmike.ld22.gfx.Screen;

public class Furniture extends Entity
{
	private int pushTime = 0;
	private int pushDir = -1;
	public int col;
	public int sprite;

	public Furniture(int x, int y)
	{
		this.x = x;
		this.y = y;
		xr = 3;
		yr = 3;
	}

	public void tick()
	{
		if (pushDir == 0) move(0, 1);
		if (pushDir == 1) move(0, -1);
		if (pushDir == 2) move(-1, 0);
		if (pushDir == 3) move(1, 0);

		pushDir = -1;
		if (pushTime > 0)
		{
			pushTime--;
		}
	}

	public void render(Screen screen)
	{
		screen.render(x - 8, y - 8 - 4, sprite, col, 0);
		screen.render(x - 0, y - 8 - 4, sprite + 1, col, 0);
		screen.render(x - 8, y - 0 - 4, sprite + 32, col, 0);
		screen.render(x + 0, y - 0 - 4, sprite + 33, col, 0);
	}

	public boolean blocks(Entity e)
	{
		return true;
	}

	protected void touchedBy(Entity entity)
	{
		if (entity instanceof Player && pushTime == 0)
		{
			pushDir = ((Player)entity).dir;
			pushTime = 10;
		}
	}

	public boolean use(Player player, int attackDir)
	{

		return false;
	}
}
