package puzzleSolver;

import java.io.*;

public class PuzzleBoard {
	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;

	public final static int SIZE = 4;

	int board[][];

	private void checkBoard() throws BadBoardException {
		int[] vals = new int[SIZE * SIZE];

		// check that the board contains all number 0...15
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j]<0 || board[i][j]>=SIZE*SIZE)
					throw new BadBoardException("found tile " + board[i][j]);
				vals[board[i][j]] += 1;
			}
		}

		for (int i = 0; i < vals.length; i++)
			if (vals[i] != 1)
				throw new BadBoardException("tile " + i +
											" appears " + vals[i] + "");

	}

	/**
	 * @param fileName
	 * @throws FileNotFoundException if file not found
	 * @throws BadBoardException     if the board is incorrectly formatted Reads a
	 *                               board from file and creates the board
	 */
	public PuzzleBoard(String fileName) throws IOException, BadBoardException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		board = new int[SIZE][SIZE];
		int c1, c2, s;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				c1 = br.read();
				c2 = br.read();
				s = br.read(); // skip the space
				if (s != ' ' && s != '\n') {
					br.close();
					throw new BadBoardException("error in line " + i);
				}
				if (c1 == ' ')
					c1 = '0';
				if (c2 == ' ')
					c2 = '0';
				board[i][j] = 10 * (c1 - '0') + (c2 - '0');
			}
		}
		checkBoard();

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

	/**
	 * Get the number of the tile, and moves it to the specified direction
	 * 
	 * @throws IllegalMoveException if the move is illegal
	 */
	public void makeMove(int tile, int direction) throws IllegalMoveException {
		Pair p = findCoord(tile);
		if (p == null)
			throw new IllegalMoveException("tile " + tile + " not found");
		int i = p.i;
		int j = p.j;

		// the tile is in position [i][j]
		switch (direction) {
		case UP: {
			if (i > 0 && board[i - 1][j] == 0) {
				board[i - 1][j] = tile;
				board[i][j] = 0;
				break;
			} else
				throw new IllegalMoveException("" + tile + "cannot move UP");
		}
		case DOWN: {
			if (i < SIZE - 1 && board[i + 1][j] == 0) {
				board[i + 1][j] = tile;
				board[i][j] = 0;
				break;
			} else
				throw new IllegalMoveException("" + tile + "cannot move DOWN");
		}
		case RIGHT: {
			if (j < SIZE - 1 && board[i][j + 1] == 0) {
				board[i][j + 1] = tile;
				board[i][j] = 0;
				break;
			} else
				throw new IllegalMoveException("" + tile + "cannot move LEFT");
		}
		case LEFT: {
			if (j > 0 && board[i][j - 1] == 0) {
				board[i][j - 1] = tile;
				board[i][j] = 0;
				break;
			} else
				throw new IllegalMoveException("" + tile + "cannot move LEFT");
		}
		default:
			throw new IllegalMoveException("Unexpected direction: " + direction);
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
