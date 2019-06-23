package com.game.main;

import java.awt.Graphics;

public abstract class GameObject {

	protected ID id;
	
	protected int x;
	protected int y;
	
	protected int velX;
	protected int velY;
	
	/**
	 * @param id
	 * @param x
	 * @param y
	 */
	public GameObject(int x, int y, ID id) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
}
