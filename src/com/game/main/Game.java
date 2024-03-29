package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.game.core.BasicEnemy;
import com.game.core.HUD;
import com.game.core.ID;
import com.game.core.Player;
import com.game.mechanics.KeyInput;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -6680875776510531796L;

	public static final int WIDTH;
	public static final int HEIGHT;

	private Thread thread;
	private boolean running = false;

	private Handler handler;
	private HUD hud;
	
	private BufferedImage bg;

	static {
		WIDTH = 640;
		HEIGHT = (WIDTH / 12) * 9;
	}

	public Game() throws IOException {
		handler = new Handler();
		handler.addObject(new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player_1, handler));

		Random rnd = new Random(2);
		for (int i = 0; i < 10; i++) {
			handler.addObject(new BasicEnemy(
					Game.clamp(rnd.nextInt(WIDTH) + 1, 0, WIDTH - 50), 
					Game.clamp(rnd.nextInt(HEIGHT) + 1, 0, HEIGHT - 50),
					ID.BasicEnemy));
		}

		hud = new HUD(); // Health bar
		
		this.bg = ImageIO.read(new File("res/background-black.png"));
		this.addKeyListener(new KeyInput(handler));
		
		new Window(WIDTH, HEIGHT, "First game", this);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.requestFocus();
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double timer = System.currentTimeMillis();
		// int frames = 0;

		// The game loop
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				tick();
				delta--;
			}

			if (running)
				render();

			// frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames);
				// frames = 0;
			}
		}

		stop();
	}

	private void tick() {
		handler.tick();
		hud.tick();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bg, 0, 0, WIDTH, HEIGHT, null);
		
		handler.render(g);
		hud.render(g);

		g.dispose();
		bs.show();
	}

	public static int clamp(int var, int min, int max) {
		if (var >= max) {
			var = max;
		} else if (var <= min) {
			var = min;
		}
		
		return var;
	}
	public static void main(String[] args) throws IOException {
		new Game();
	}

}
