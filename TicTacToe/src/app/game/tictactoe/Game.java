package app.game.tictactoe;


public class Game {
	
	// player 1 represents circle, player 2 represents cross
	protected int player = 1;
	protected boolean gameEnd = false;
	protected boolean isTie = false;
	protected boolean singlePlayer = false;
	
	protected int[][] map = null;
	protected int spaces = 9;
	
	public void init() {
		map = new int[3][3];
	}
	
	public boolean mark(int x, int y) {
		if (map[x][y] != 0) {
			return false;
		}
		map[x][y] = player;
		spaces--;
		gameEnd = checkGame();
		if (spaces == 0) {
			gameEnd = true;
			isTie = true;
			return true;
		}
		if (gameEnd) {
			return true;
		}
		
		if (player == 1) {
			player = 2;
		} else {
			player = 1;
		}
		return true;
	}
	
	// Could still work on more efficient way to checkGame
	public boolean checkGame() {
		for (int i = 0; i < 3; i++) {
			if (map[i][0] == player && map[i][1] == player && map[i][2] == player) {
				return true;
			}
		}
		for (int i = 0; i < 3; i++) {
			if (map[0][i] == player && map[1][i] == player && map[2][i] == player) {
				return true;
			}
		}
		if (map[0][0] == player && map[1][1] == player && map[2][2] == player) {
			return true;
		}
		if (map[0][2] == player && map[1][1] == player && map[2][0] == player) {
			return true;
		}
		return false;
	}
	
	public void computerMove() {
		if (!singlePlayer) {
			return;
		}
	}
	
	public int[][] getMap() {
		return map;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public void vsComputer() {
		singlePlayer = true;
	}
	
	public boolean getSinglePlayer() {
		return singlePlayer;
	}
	
	public int getGameStatus() {
		if (gameEnd && !isTie) {
			return 1; //current player won
		} else if (gameEnd && isTie){
			return 0; //tie
		} else {
			return -1;
		}
	}
}
