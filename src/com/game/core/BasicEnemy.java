package com.game.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.main.Game;
import com.game.main.GameObject;

public class BasicEnemy extends GameObject {

	public BasicEnemy(int x, int y, ID id) {
		super(x, y, id);
		velX = 5;
		velY = 4;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 16, 16);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		if (y <= 0 || y >= Game.HEIGHT - 32) { velY *= -1; }
		if (x <= 0 || x >= Game.WIDTH - 16) { velX *= -1; }
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x, y, 16, 16);
	}

}
