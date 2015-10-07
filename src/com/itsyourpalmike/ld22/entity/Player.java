package com.itsyourpalmike.ld22.entity;

import java.util.List;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.InputHandler;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.item.FurnitureItem;
import com.itsyourpalmike.ld22.item.Item;
import com.itsyourpalmike.ld22.level.Level;
import com.itsyourpalmike.ld22.level.tile.Tile;
import com.itsyourpalmike.ld22.screen.InventoryMenu;

public class Player extends Mob
{
	private InputHandler input;
	private int attackTime, attackDir;

	public Inventory inventory = new Inventory();
	public Game game;
	public Item activeItem;
	public Item attackItem;
	public int stamina;
	public int staminaRecharge;
	public int staminaRechargeDelay;
	public int score;
	public int maxStamina = 10;

	public Player(Game game, InputHandler input)
	{
		this.input = input;
		x = y = 24;
		this.game = game;
		stamina = maxStamina;
		
		inventory.add(new FurnitureItem(new Workbench()));
	}

	public void tick()
	{
		super.tick();

		if (stamina <= 0 && staminaRechargeDelay == 0 && staminaRecharge == 0)
		{
			staminaRechargeDelay = 40;
		}

		if (staminaRechargeDelay > 0)
		{
			staminaRechargeDelay--;
		}

		if (staminaRechargeDelay == 0)
		{
			staminaRecharge++;
			if (inWater())
			{
				staminaRecharge = 0;
			}
			while (staminaRecharge > maxStamina)
			{
				staminaRecharge -= 10;
				if (stamina < maxStamina) stamina++;
			}
		}

		// Moving the player
		int xa = 0;
		int ya = 0;

		if (input.up.down)
		{
			ya--;
		}
		if (input.down.down)
		{
			ya++;
		}
		if (input.left.down)
		{
			xa--;
		}
		if (input.right.down)
		{
			xa++;
		}
		
		if(inWater() && tickTime % 60 == 0)
		{
			if(stamina > 0) stamina--;
			else
			{
				hurt(this, 1, dir^1);
			}
		}

		if (staminaRechargeDelay % 2 == 0)
		{
			move(xa, ya);
		}

		// Pressing the attack button
		if (input.attack.clicked)
		{
			if (stamina == 0)
			{

			}
			else
			{
				stamina--;
				staminaRecharge = 0;
				attack();
			}
		}

		// Pressing the menu button
		if (input.menu.clicked)
		{
			if (!use())
			{
				game.setMenu(new InventoryMenu(this));
			}
		}

		if (attackTime > 0) attackTime--;
	}

	private void attack()
	{
		walkDist += 8;
		attackDir = dir;

		attackItem = activeItem;
		if (activeItem == null || activeItem.canAttack()) // If we have a bare hand
		{
			attackTime = 5;
			int yo = -2;

			// Hurts entities inside of tiles within the player's attack zone
			if (dir == 0)
			{
				hurt(x - 8, y + 4 + yo, x + 8, y + 12 + yo);
			}
			if (dir == 1)
			{
				hurt(x - 8, y - 12 + yo, x + 8, y - 4 + yo);
			}
			if (dir == 3)
			{
				hurt(x + 4, y - 8 + yo, x + 12, y + 8 + yo);
			}
			if (dir == 2)
			{
				hurt(x - 12, y - 8 + yo, x - 4, y + 8 + yo);
			}

			// Hurts the tile the player is facing
			int xt = x >> 4;
			int yt = (y + yo) >> 4;
			int r = 12;

			if (attackDir == 0) yt = (y + r + yo) >> 4;
			if (attackDir == 1) yt = (y - r + yo) >> 4;
			if (attackDir == 2) xt = (x - r) >> 4;
			if (attackDir == 3) xt = (x + r) >> 4;

			if (xt >= 0 && yt >= 0 && xt < level.w && yt < level.h)
			{
				level.getTile(xt, yt).hurt(level, xt, yt, this, random.nextInt(3) + 1, attackDir);
			}
		}
		if (activeItem != null)
		{
			attackTime = 10;
			int yo = -2;

			// Interacting w/ entities inside of tiles within the player's attack zone
			if (dir == 0)
			{
				interact(x - 8, y + 4 + yo, x + 8, y + 12 + yo);
			}
			if (dir == 1)
			{
				interact(x - 8, y - 12 + yo, x + 8, y - 4 + yo);
			}
			if (dir == 3)
			{
				interact(x + 4, y - 8 + yo, x + 12, y + 8 + yo);
			}
			if (dir == 2)
			{
				interact(x - 12, y - 8 + yo, x - 4, y + 8 + yo);
			}

			// Interacts with the tile the player is facing
			int xt = x >> 4;
			int yt = (y + yo) >> 4;
			int r = 12;

			if (attackDir == 0) yt = (y + r + yo) >> 4;
			if (attackDir == 1) yt = (y - r + yo) >> 4;
			if (attackDir == 2) xt = (x - r) >> 4;
			if (attackDir == 3) xt = (x + r) >> 4;

			if (xt >= 0 && yt >= 0 && xt < level.w && yt < level.h)
			{
				if (activeItem.interactOn(level.getTile(xt, yt), level, xt, yt, this, attackDir))
				{

				}
				else
				{
					level.getTile(xt, yt).interact(level, xt, yt, this, activeItem, attackDir);
				}

				if (activeItem.isDepleted())
				{
					activeItem = null;
				}
			}

		}
	}

	// Using menu button
	private boolean use()
	{
		int yo = -2;

		if (dir == 0 && use(x - 8, y + 4 + yo, x + 8, y + 12 + yo)) return true;
		if (dir == 1 && use(x - 8, y - 12 + yo, x + 8, y - 4 + yo)) return true;
		if (dir == 3 && use(x + 4, y - 8 + yo, x + 12, y + 8 + yo)) return true;
		if (dir == 2 && use(x - 12, y - 8 + yo, x - 4, y + 8 + yo)) return true;

		int xt = x >> 4;
		int yt = (y + yo) >> 4;
		int r = 12;

		if (attackDir == 0) yt = (y + r + yo) >> 4;
		if (attackDir == 1) yt = (y - r + yo) >> 4;
		if (attackDir == 2) xt = (x - r) >> 4;
		if (attackDir == 3) xt = (x + r) >> 4;

		if (xt >= 0 && yt >= 0 && xt < level.w && yt < level.h)
		{
			if (level.getTile(xt, yt).use(level, xt, yt, this, attackDir))
			{
				return true;
			}
		}

		return false;
	}

	// Usong menu button on entities
	private boolean use(int x0, int y0, int x1, int y1)
	{
		List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			if (e != this) if (e.use(this, attackDir)) return true;
		}

		return false;
	}

	// Using the active item to interact with entities
	private void interact(int x0, int y0, int x1, int y1)
	{
		List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			if (e != this) if (e.interact(this, activeItem, attackDir)) return;
		}
	}

	// Hurting enemies with bare hand (random number damage)
	private void hurt(int x0, int y0, int x1, int y1)
	{
		List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			if (e != this) entities.get(i).hurt(this, getAttackDamage(e), attackDir);
		}
	}

	private int getAttackDamage(Entity e)
	{
		int dmg = random.nextInt(3) + 1;
		if (attackItem != null)
		{
			dmg += attackItem.getAttackDamageBonus(e);
		}

		return dmg;

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
		if (inWater())
		{
			yo += 4;
			int waterColor = Color.get(-1, -1, 115, 115);
			if (tickTime / 8 % 2 == 0)
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

			if (attackItem != null)
			{
				attackItem.renderIcon(screen, xo + 4, yo - 4); // Rendering item icon
			}
		}

		// Rendering the player
		int col = Color.get(-1, 100, 220, 532);
		if (hurtTime > 0)
		{
			col = Color.get(-1, 555, 555, 555);
		}

		if (activeItem instanceof FurnitureItem)
		{
			yt += 2;
		}

		screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1);
		screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1);

		if (!inWater()) // If we're in water, don't render lower half of the player
		{
			screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2);
			screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2);
		}

		// Rendering attack thing-a-ma-bob
		if (attackTime > 0 && attackDir == 2)
		{
			screen.render(xo - 4, yo, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 1);
			screen.render(xo - 4, yo + 8, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 3);

			if (attackItem != null)
			{
				attackItem.renderIcon(screen, xo - 4, yo + 4); // Rendering item icon
			}
		}

		// Rendering attack thing-a-ma-bob
		if (attackTime > 0 && attackDir == 3)
		{
			screen.render(xo + 8 + 4, yo, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 0);
			screen.render(xo + 8 + 4, yo + 8, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 2);

			if (attackItem != null)
			{
				attackItem.renderIcon(screen, xo + 8 + 4, yo + 4); // Rendering item icon
			}
		}

		// Rendering attack thing-a-ma-bob
		if (attackTime > 0 && attackDir == 0)
		{
			screen.render(xo + 0, yo + 8 + 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555), 2);
			screen.render(xo + 8, yo + 8 + 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555), 3);

			if (attackItem != null)
			{
				attackItem.renderIcon(screen, xo + 4, yo + 8 + 4); // Rendering item icon
			}
		}

		if (activeItem instanceof FurnitureItem)
		{
			Furniture furniture = ((FurnitureItem)activeItem).furniture;
			furniture.x = x;
			furniture.y = yo;
			furniture.render(screen);
		}
	}

	// Colliding with an ItemEntity AKA picking up loot
	public void touchItem(ItemEntity itemEntity)
	{
		inventory.add(itemEntity.item);
		itemEntity.take(this);
	}

	public boolean canSwim()
	{
		return true;
	}

	// validates a starting position so player doesn't spawn inside of walls
	public void findStartPos(Level level)
	{
		while (true)
		{
			int x = random.nextInt(level.w);
			int y = random.nextInt(level.h);
			if (level.getTile(x, y) == Tile.grass)
			{
				this.x = x * 16 + 8;
				this.y = y * 16 + 8;
				break;
			}
		}
	}

	public boolean payStamina(int cost)
	{
		if(cost > stamina) return false;
		stamina-=cost;
		return true;
	}
}
