package org.springframework.samples.petclinic.websocket;
/*
 * this class will make a pacman board with the boardes = 0 the board will have dimenseions 28*28 
 * 
 * */


public class PacManBoard {

	int[][] matrix ;
	static int boardX = 28;
	static int boardY = 28;
	
	
	public PacManBoard() {
	 matrix = new int[boardX][boardY];
	}
	/**
	 * 
	 */
	public void initBoard() {
		int i = 0;
		int j = 0;
		for(i = 0; i < matrix.length;i++) {
			for(j = 0; i < matrix[0].length; j++) {
				if(i == 0 || i  == boardX || j == 0 || j == boardY) {
					matrix [i][j] = 0;
				}
				
			}
		}
	}
	
}
