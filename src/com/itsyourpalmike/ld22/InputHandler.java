package com.itsyourpalmike.ld22;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener
{
	public class Key
	{
		public int presses, absorbs;
		public boolean down, clicked;

		public Key()
		{
			keys.add(this);
		}

		public void toggle(boolean pressed)
		{
			if (pressed != down)
			{
				down = pressed;
				if (pressed)
				{
					presses++;
				}
			}
		}
		
		public void tick()
		{
			if(absorbs < presses)
			{
				absorbs++;
				clicked = true;
			}
			else
			{
				clicked = false;
			}
		}
	}

	public List<Key> keys = new ArrayList<Key>();

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key attack = new Key();
	public Key menu = new Key();

	public void releaseAll()
	{
		for (int i = 0; i < keys.size(); i++)
		{
			keys.get(i).tick();
		}
	}

	public void tick()
	{
		for (int i = 0; i < keys.size(); i++)
		{
			keys.get(i).tick();
		}
	}

	public InputHandler(Game game)
	{
		game.addKeyListener(this);
	}

	public void keyPressed(KeyEvent k)
	{
		toggle(k, true);
	}

	public void keyReleased(KeyEvent k)
	{
		toggle(k, false);
	}

	public void keyTyped(KeyEvent k)
	{
	}

	private void toggle(KeyEvent k, boolean pressed)
	{
		if (k.getKeyCode() == KeyEvent.VK_NUMPAD8) up.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_NUMPAD2) down.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_NUMPAD4) left.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_NUMPAD6) right.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_UP) up.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_DOWN) down.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_LEFT) left.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_RIGHT) right.toggle(pressed);

		if (k.getKeyCode() == KeyEvent.VK_TAB) menu.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_ALT) attack.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_ALT_GRAPH) attack.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_SPACE) attack.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_CONTROL) attack.toggle(pressed);

		if (k.getKeyCode() == KeyEvent.VK_X) menu.toggle(pressed);
		if (k.getKeyCode() == KeyEvent.VK_C) attack.toggle(pressed);
	}
}
