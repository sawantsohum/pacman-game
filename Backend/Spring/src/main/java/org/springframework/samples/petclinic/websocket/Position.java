package org.springframework.samples.petclinic.websocket;

public class Position {
	
	int x;
	int y;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * updates coordinates for the position
	 * @param x
	 * @param y
	 */
	public void UpdatePosition(int x ,int y) {
		
		this.x = x;
		this.y = y;
	}

}
