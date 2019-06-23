package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

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

	static {
		WIDTH = 640;
		HEIGHT = (WIDTH / 12) * 9;
	}

	public Game() {
		handler = new Handler();		
		handler.addObject(new Player(100,  100,  ID.Player_1));
		
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
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double timer = System.currentTimeMillis();
		int frames = 0;

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

			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames);
				frames = 0;
			}
		}

		stop();
	}

	private void tick() {
		handler.tick();
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

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Game();
	}

}
