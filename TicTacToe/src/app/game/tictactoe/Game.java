package app.game.tictactoe;

import java.util.ArrayList;


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
		player = 1;
		gameEnd = false;
		isTie = false;
		spaces = 9;
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
		/*
		// For furture use to reduce code.
		for (int i = 0; i < 3; i++) {
			for (int j = 0; i < 3; j++) {
				
			}
		}
		*/
		if (map[0][0] == player && map[1][1] == player && map[2][2] == player) {
			return true;
		}
		if (map[0][2] == player && map[1][1] == player && map[2][0] == player) {
			return true;
		}
		return false;
	}
	
	public int[] computerMove() {
		int[] xy = {-1, -1};
		if (!singlePlayer) {
			return null;
		}
		
		// Find the winning move to either to win or to stop the player to win if any
		xy = getWinningMove();
		
		// Check what's the best options left
		if (xy[0] == -1 && xy[1] == -1) {
			xy = getBestMove();
		}
		
		mark(xy[0], xy[1]);
		return xy;
	}
	
	// return the move to win
	// return -1, -1 if no winning move
	protected int[] getWinningMove() {
		int[] xy = {-1, -1};
		for (int i = 0; i < 3; i++) {
			int[] temp = {map[i][0], map[i][1], map[i][2]};
			int pos = checkWinningLine(temp);
			if (pos != -1) {
				xy[0] = i;
				xy[1] = pos;
				return xy;
			}
		}
		for (int i = 0; i < 3; i++) {
			int[] temp = {map[0][i], map[1][i], map[2][i]};
			int pos = checkWinningLine(temp);
			if (pos != -1) {
				xy[0] = pos;
				xy[1] = i;
				return xy;
			}
		}
		{
			int[] temp = {map[0][0], map[1][1], map[2][2]};
			int pos = checkWinningLine(temp);
			if (pos != -1) {
				xy[0] = pos;
				xy[1] = pos;
				return xy;
			}
		}
		{
			int[] temp = {map[0][2], map[1][1], map[2][0]};
			int pos = checkWinningLine(temp);
			if (pos != -1) {
				xy[0] = pos;
				xy[1] = 2 - pos;
				return xy;
			}
		}
		return xy;
	}
	
	// if a winning move in the line, return the position
	protected int checkWinningLine(int[] line) {
		int zero = 0;
		if (line[0] == 0) zero++;
		if (line[1] == 0) zero++;
		if (line[2] == 0) zero++;
		if (zero != 1) {
			return -1;
		}
		if (line[0] == line[1]) {
			return 2;
		}
		if (line[0] == line[2]) {
			return 1;
		}
		if (line[1] == line[2]) {
			return 0;
		}
		return -1;
	}
	
	protected int[] getBestMove() {
		int[] xy = {-1, -1};
		ArrayList<int[]> tempList = null;
		//Get all possible moves by priorities and shuffle them to pick a random best moves, return the best moves 
		//1: 11
		if (map[1][1] == 0) {
			xy[0] = 1;
			xy[1] = 1;
			return xy;
		}
		//2: 00 02 20 22
		int x = 0;
		int y = 0;
		tempList = new ArrayList<int[]>();
		for (int i = 0; i < 2; i++) {
			x = i * 2;
			y = i * 2;
			// 00 22
			if (map[x][y] == 0) {
				xy[0] = x;
				xy[1] = y;
				tempList.add(xy);
			}
			x = i * 2;
			y = 2 - i * 2;
			// 02 20
			if (map[x][y] == 0) {
				xy[0] = x;
				xy[1] = y;
				tempList.add(xy);
			}
		}
		if (tempList.size() > 0) {
			return getRandomMove(tempList);
		}
		//3: 01 10 12 21
		for (int i = 0; i < 2; i++) {
			x = i;
			y = i + 1;
			// 01 12
			if (map[x][y] == 0) {
				xy[0] = x;
				xy[1] = y;
				tempList.add(xy);
			}
			x = i + 1;
			y = i;
			// 10 21
			if (map[x][y] == 0) {
				xy[0] = x;
				xy[1] = y;
				tempList.add(xy);
			}
		}
		if (tempList.size() > 0) {
			return getRandomMove(tempList);
		}
		// Should not get here, if any reason means there is a bug
		return null;
	}
	
	protected int[] getRandomMove(ArrayList<int[]> moves) {
		int index = (int) (Math.random() % moves.size());
		return moves.get(index);
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
