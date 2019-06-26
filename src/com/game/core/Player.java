package com.game.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.main.Game;
import com.game.main.GameObject;
import com.game.main.Handler;

public class Player extends GameObject {

	private Handler handler;
	private BufferedImage img;
	
	public Player(int x, int y, ID id, Handler handler) throws IOException {
		super(x, y, id);
		
		this.img = ImageIO.read(new File("res/pixel_ship_red.png"));
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
		double degrees = 0;
		
		if (velX > 0) {
			if (velY > 0) degrees = 135;
			else if (velY < 0) degrees = 45;
			else degrees = 90;
		} else if (velX < 0) {
			if (velY > 0) degrees = -135;
			else if (velY < 0) degrees = -45;
			else degrees = -90;
		} else if (velX == 0) {
			if (velY > 0) degrees = 180;
		}
		
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform old = g2d.getTransform();
		
		g2d.rotate(Math.toRadians(degrees), x + 16, y + 16);
		g2d.drawImage(img, x, y, 32, 32, null);
		
		g2d.setTransform(old);
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
