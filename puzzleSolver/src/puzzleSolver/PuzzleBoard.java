package puzzleSolver;

import java.io.*;
import java.util.HashSet;
import java.util.PriorityQueue;

public class PuzzleBoard {
	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;


	private int SIZE;

	int board[][];


	/**
	 * @param fileName
	 * @throws FileNotFoundException if file not found
	     if the board is incorrectly formatted Reads a
	 *                               board from file and creates the board
	 */
	public PuzzleBoard(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		this.SIZE = Integer.parseInt(br.readLine());
		board = new int[SIZE][SIZE];
		int c1, c2, s;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				c1 = br.read();
				c2 = br.read();
				s = br.read(); // skip the space
				if (s != ' ' && s != '\n') {
					br.close();
				}
				if (c1 == ' ')
					c1 = '0';
				if (c2 == ' ')
					c2 = '0';
				board[i][j] = 10 * (c1 - '0') + (c2 - '0');
			}
		}
		br.close();
	}

	private class Pair {
		int i, j;

		Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

	private Pair findCoord(int tile) {
		int i = 0, j = 0;
		for (i = 0; i < SIZE; i++)
			for (j = 0; j < SIZE; j++)
				if (board[i][j] == tile)
					return new Pair(i, j);
		return null;
	}

	public void makeMove(int direction){
		int tile = 0;
		Pair p = findCoord(tile);

		int i = p.i;
		int j = p.j;

		// the tile is in position [i][j]
		switch (direction) {
		case UP: {
			if (i > 0) {
				board[i][j] = board[i - 1][j];
				board[i - 1][j] = tile;
				break;
			}
		}
		case DOWN: {
			if (i < SIZE - 1) {
				board[i][j] = board[i + 1][j];
				board[i + 1][j] = tile;
				break;
			}
		}
		case RIGHT: {
			if (j < SIZE - 1) {
				board[i][j] = board[i][j + 1];
				board[i][j + 1] = tile;
				break;
			}
		}
		case LEFT: {
			if (j > 0) {
				board[i][j] = board[i][j - 1];
				board[i][j - 1] = tile;
				break;
			}
		}
		}
	}

	/**
	 * @return true if and only if the board is solved, i.e., the board has all
	 *         tiles in their correct positions
	 */
	public boolean isSolved() {
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				if (board[i][j] != (4 * i + j + 1) % 16)
					return false;
		return true;
	}

	private String num2str(int i) {
		if (i == 0)
			return "  ";
		else if (i < 10)
			return " " + Integer.toString(i);
		else
			return Integer.toString(i);
	}

	public String toString() {
		String ans = "";
		for (int i = 0; i < SIZE; i++) {
			ans += num2str(board[i][0]);
			for (int j = 1; j < SIZE; j++)
				ans += " " + num2str(board[i][j]);
			ans += "\n";
		}
		return ans;
	}
}
