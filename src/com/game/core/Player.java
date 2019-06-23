package com.game.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.main.Game;
import com.game.main.GameObject;
import com.game.main.Handler;

public class Player extends GameObject {

	private Handler handler;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
	@Override
	public void tick() {
		x += velX;
		y += velY;

		x = Game.clamp(x, 0, Game.WIDTH - 37);
		y = Game.clamp(y, 0, Game.HEIGHT - 60);
		
		collision();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, 32, 32);
	}
	
	private void collision() {
		for (GameObject o : handler.getObjects()) {
			if (o.getId() == ID.BasicEnemy) {
				if (this.getBounds().intersects(o.getBounds())) {
					HUD.HEALTH--;
				}
			}
		}
	}

}
