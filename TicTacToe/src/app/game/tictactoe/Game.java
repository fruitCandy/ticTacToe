package app.game.tictactoe;


public class Game {
	
	// player 1 represents circle, player 2 represents cross
	protected int player = 1;
	protected boolean gameEnd = false;
	protected boolean isTie = false;
	
	protected int[][] map = null;
	protected int spaces = 9;
	
	public void init() {
		map = new int[3][3];
	}
	
	public void start() {
		//get user input for a
		int player = 1;
		int x = 0;
		int y = 0;
		while (!gameEnd) {
			// Get user request, retry when invalid moves
			System.out.println("Player" + player);
			showMap();
			System.out.println("Enter x, please pick a number between 1 and 3. (-1 to exit)");
			System.out.println("Enter y, please pick a number between 1 and 3. (-1 to exit)");
			if (!mark(x, y)) {
				System.out.println("The spot you picked has already been taken. Please pick another one.");
				continue;
			}
		}
		if (isTie) {
			System.out.println("Nobody is the winner, it's a tie.");
			return;
		}
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
	
	public void showMap(){
		System.out.println(map[0][0] + "|" + map[1][0] + "|" + map[2][0]);
		System.out.println("-----");
		System.out.println(map[0][1] + "|" + map[1][1] + "|" + map[2][1]);
		System.out.println("-----");
		System.out.println(map[0][2] + "|" + map[1][2] + "|" + map[2][2]);
	}
	
	public int[][] getMap() {
		return map;
	}
	
	public int getPlayer() {
		return player;
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
