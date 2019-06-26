package com.game.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.game.main.Game;
import com.game.main.GameObject;

public class BasicEnemy extends GameObject {

	private BufferedImage img;
	private double degrees = 0;
	private final double rotation = 0.5;
	
	public BasicEnemy(int x, int y, ID id) throws IOException {
		super(x, y, id);
		
		this.velX = 4;
		this.velY = 5;
		
		this.degrees = new Random().nextInt(180);		
		this.img = ImageIO.read(new File("res/asteroid_grey_tiny.png"));
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
//		g.setColor(Color.red);
//		g.fillRect(x, y, 16, 16);

		Graphics2D g2d = (Graphics2D)g;
		AffineTransform old = g2d.getTransform();
		
		g2d.rotate(Math.toRadians(degrees), x + 10, y + 10);
		g2d.drawImage(img, x, y, 20, 20, null);
		
		g2d.setTransform(old);
		
		degrees += rotation;
	}

}
