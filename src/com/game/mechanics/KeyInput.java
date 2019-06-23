package com.game.mechanics;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.game.core.ID;
import com.game.main.GameObject;
import com.game.main.Handler;

public class KeyInput extends KeyAdapter {

	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		for (GameObject o : handler.getObjects()) {
			// Player 1
			if (o.getId() == ID.Player_1) {
				if (key == KeyEvent.VK_W) { // UP
					o.setVelY(-3);
				} else if (key == KeyEvent.VK_S) { // Down
					o.setVelY(3);
				} else if (key == KeyEvent.VK_A) { // 
					o.setVelX(-3);
				} else if (key == KeyEvent.VK_D) {
					o.setVelX(3);
				} else if (key == KeyEvent.VK_SPACE) {
					o.setVelX(0);
					o.setVelY(0);
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
	}
}
