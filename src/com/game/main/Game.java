package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

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

	static {
		WIDTH = 640;
		HEIGHT = (WIDTH / 12) * 9;
	}

	public Game() {
		handler = new Handler();
		handler.addObject(new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player_1));

		hud = new HUD();
		
		Random rnd = new Random();
		for (int i = 0; i < 10; i++) {
			handler.addObject(new BasicEnemy(rnd.nextInt(WIDTH) + 1, rnd.nextInt(HEIGHT) + 1, ID.BasicEnemy));
		}

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
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

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
	public static void main(String[] args) {
		new Game();
	}

}
