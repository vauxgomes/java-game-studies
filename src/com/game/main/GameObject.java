package com.game.main;

import java.awt.Graphics;

import com.game.core.ID;

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

	/* Gets & Sets */
	
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

}
