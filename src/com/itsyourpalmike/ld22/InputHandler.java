package com.itsyourpalmike.ld22;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener
{
	public boolean up, down, left, right;
	public boolean attack;
	public boolean menu;
	
	public void releaseAll()
	{
		up = down = left = right = attack = menu = false;
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

	public void keyTyped(KeyEvent k){}

	private void toggle(KeyEvent k, boolean b)
	{
		if(k.getKeyCode() == KeyEvent.VK_UP) up = b;
		if(k.getKeyCode() == KeyEvent.VK_DOWN) down = b;
		if(k.getKeyCode() == KeyEvent.VK_LEFT) left = b;
		if(k.getKeyCode() == KeyEvent.VK_RIGHT) right = b;
		if(k.getKeyCode() == KeyEvent.VK_C) attack = b;
		if(k.getKeyCode() == KeyEvent.VK_X) menu = b;
	}
}
