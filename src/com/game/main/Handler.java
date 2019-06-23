package com.game.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	protected LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	public void tick() {
		for (GameObject o : objects) {
			o.tick();
		}
	}
	
	public void render(Graphics g) {
		for (GameObject o : objects) {
			o.render(g);
		}
	}
	
	public void addObject(GameObject o) {
		this.objects.add(o);
	}
	
	public void removeObject(GameObject o) {
		this.objects.remove(o);
	}
	
	public LinkedList<GameObject> getObjects() {
		return objects;
	}
}
