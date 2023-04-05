package fifteenpuzzle;

import java.io.*;
import java.util.*;

import static fifteenpuzzle.Solver.closedList;
import static fifteenpuzzle.Solver.openList;

public class PuzzleBoard {
	String path;
	int height;
	double cost;

	private int emptyTileRow;
	private int emptyTileCol;
	public int SIZE;

	byte board[][];

	public static byte goalState[][];

	private void findEmptyTilePosition() {
		Pair p = findCoord(0);
		emptyTileRow = p.i;
		emptyTileCol = p.j;
	}

	public PuzzleBoard(PuzzleBoard old){
		cost = 0;
		emptyTileCol=old.emptyTileCol;
		emptyTileRow=old.emptyTileRow;
		SIZE =old.SIZE;
		board = new byte[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			System.arraycopy(old.board[i],0,board[i],0,SIZE);
		}
		height = old.height+1;
		path = old.path;
	}

	public int euclideanDistance() {
		int distance = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int value = board[i][j];
				if (value != 0) {
					int goalRow = (value - 1) / SIZE;
					int goalCol = (value - 1) % SIZE;
					distance += Math.sqrt(Math.pow(i - goalRow, 2) + Math.pow(j - goalCol, 2));
				}
			}
		}
		return distance;
	}
	public int getManhattanDistance() {
		int distance = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] != 0) {
					int goalRow = (board[i][j] - 1) / SIZE;
					int goalCol = (board[i][j] - 1) % SIZE;
					distance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
				}
			}
		}
		return distance;
	}

	public void getTotalCost() {
		 this.cost = (euclideanDistance()*0.4 + getManhattanDistance()*0.85) + hammingHeuristic();
	}

	public int manhattanDistanceFirstRowColumn() {
		int distance = 0;

		// Calculate distances for first row
		for (int col = 0; col < SIZE - 1; col++) {
			if (board[0][col] != col + 1) {
				distance += Math.abs(col + 1 - board[0][col]);
			}
		}

		// Calculate distances for first column
		for (int row = 1; row < SIZE - 1; row++) {
			if (board[row][0] != row * SIZE + 1) {
				distance += Math.abs(row * SIZE + 1 - board[row][0]);
			}
		}

		return distance;
	}

	public int hammingHeuristic() {
		int h = 0;
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				int tile = board[row][col];
				if (tile != 0 && tile != goalState[row][col]) {
					h++; // increment heuristic value for each misplaced tile
				}
			}
		}
		return h;
	}

	public int getLinearConflict() {
		int conflict = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] != 0 && isTileInRowOrColumn(i, j)) {
					int goalRow = (board[i][j] - 1) / board.length;
					int goalCol = (board[i][j] - 1) % board[0].length;
					if (goalRow == i) {
						for (int k = j + 1; k < board[0].length; k++) {
							if (board[i][k] != 0 && isTileInRowOrColumn(i, k) && (board[i][j] > board[i][k])) {
								conflict += 2;
							}
						}
					} else if (goalCol == j) {
						for (int k = i + 1; k < board.length; k++) {
							if (board[k][j] != 0 && isTileInRowOrColumn(k, j) && (board[i][j] > board[k][j])) {
								conflict += 2;
							}
						}
					}
				}
			}
		}
		return conflict;
	}
	private boolean isTileInRowOrColumn(int row, int col) {
		for (int i = 0; i < board.length; i++) {
			if (board[row][i] != 0 && i != col) {
				return true;
			}
			if (board[i][col] != 0 && i != row) {
				return true;
			}
		}
		return false;
	}
	public PuzzleBoard(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		this.SIZE = Integer.parseInt(br.readLine());
		board = new byte[SIZE][SIZE];
		goalState = new byte[SIZE][SIZE];
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
				board[i][j] = (byte)(10 * (c1 - '0') + (c2 - '0'));
			}
		}
		br.close();
		int counter =1;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++){
				goalState[i][j] = (byte)counter++;
			}
		}
		findEmptyTilePosition();
		goalState[SIZE-1][SIZE-1] = 0;
		getTotalCost();
		height =0;
		path = "";
	}

	private class Pair {
		int i, j;

		Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

	private Pair findCoord(int tile) {
		int i, j;
		for (i = 0; i < SIZE; i++)
			for (j = 0; j < SIZE; j++)
				if (board[i][j] == tile)
					return new Pair(i, j);
		return null;
	}


	public void makeMove(){
		int[][] deltas = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
		String[] directions = { "D", "U", "R", "L" };

		for (int i = 0; i < 4; i++) {
			int newRow = emptyTileRow + deltas[i][0];
			int newCol = emptyTileCol + deltas[i][1];

			if (newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE) {
				PuzzleBoard newBoard = new PuzzleBoard(this);
				newBoard.board[emptyTileRow][emptyTileCol] = newBoard.board[newRow][newCol];
				newBoard.board[newRow][newCol] = 0;
				newBoard.emptyTileRow = newRow;
				newBoard.emptyTileCol = newCol;
				newBoard.getTotalCost();
				newBoard.path += board[newBoard.emptyTileRow][newBoard.emptyTileCol] + " " + directions[i] + "\n";
				if (!closedList.contains(newBoard)) {
					openList.add(newBoard);
				}
			}
		}

	}
	public boolean isGoalState() {
		return Arrays.deepEquals(board, goalState);
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
	@Override
	public int hashCode() {
		int result = 17;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				result = 31 * result + board[i][j];
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof PuzzleBoard)) {
			return false;
		}
		PuzzleBoard other = (PuzzleBoard) obj;
		if (other.SIZE != this.SIZE) {
			return false;
		}

		if (this.hashCode() != other.hashCode()) {
			return false;
		}

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (other.board[i][j] != this.board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}



