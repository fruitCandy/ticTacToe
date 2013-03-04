package app.game.tictactoe;

import java.util.Scanner;


public class Game {
	
	// 1 represent circle, 2 represents cross
	
	protected boolean gameEnd = false;
	protected boolean isTie = false;
	
	protected int[][] map = null;
	
	public void init() {
		map = new int[3][3];
	}
	
	public void start() {
		Scanner reader = new Scanner(System.in);
		//get user input for a
		int player = 1;
		int x = 0;
		int y = 0;
		while (!gameEnd) {
			// Get user request, retry when invalid moves
			System.out.println("Player" + player);
			showMap();
			System.out.println("Enter x, please pick a number between 1 and 3. (-1 to exit)");
			x = reader.nextInt();
			if (x == -1) {
				break;
			}
			System.out.println("Enter y, please pick a number between 1 and 3. (-1 to exit)");
			y = reader.nextInt();
			if (y == -1) {
				break;
			}
			if (!mark(player, x, y)) {
				System.out.println("The spot you picked has already been taken. Please pick another one.");
				continue;
			}
			if (player == 1) {
				player = 2;
			} else {
				player = 1;
			}
		}
		reader.close();
		if (x == -1 || y == -1) {
			System.out.println("Game was aborted...");
			return;
		}
		if (isTie) {
			System.out.println("Nobody is the winner, it's a tie.");
			return;
		}
		showMap();
		if (player == 1) {
			System.out.println("Player2 Won!");
		} else {
			System.out.println("Player1 Won!");
		}
	}
	
	public boolean mark(int player, int x, int y) {
		x = x-1;
		y = y-1;
		if (map[x][y] != 0) {
			return false;
		}
		setMap(player, x, y);
		if (checkTie()) {
			gameEnd = true;
		}
		if(checkGame(player)){
			gameEnd = true;
		}
		return true;
	}
	
	protected void setMap(int player, int x, int y) {
		map[x][y] = player;
	}
	
	// Could still work on more efficient way to checkGame
	public boolean checkGame(int player) {
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
	
	public boolean checkTie() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (map[i][j] == 0) {
					return false;
				}
			}
		}
		isTie = true;
		return true;
	}
	
	public void showMap(){
//		for (int i = 0; i < 3; i++) {
//			for (int j = 0; j < 3; j++) {
//				System.out.print(map[i][j]);
//				System.out.print("|");
//			}
//			System.out.println("---------");
//		}
		System.out.println(map[0][0] + "|" + map[1][0] + "|" + map[2][0]);
		System.out.println("-----");
		System.out.println(map[0][1] + "|" + map[1][1] + "|" + map[2][1]);
		System.out.println("-----");
		System.out.println(map[0][2] + "|" + map[1][2] + "|" + map[2][2]);
	}
	
	public int[][] getMap() {
		return map;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.init();
		game.start();
	}
}
