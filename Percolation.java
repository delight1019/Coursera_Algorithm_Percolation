/* *****************************************************************************
 *  Name:    Hyehyeon Kim
 *  NetID:   kippem9088
 *  Precept: P01
 *
 *  Description:  Generates N x N grid and check that it percolates.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;

public class Percolation {
    private static final int BLOCKED = 0;
    private static final int OPENED = 1;

    // Array to get directions of (0, 1), (1, 0), (0, -1), (-1, 0)
    private final int[] fourDirectionsX = {0, 1, 0, -1};
    // Array to get directions of (0, 1), (1, 0), (0, -1), (-1, 0)
    private final int[] fourDirectionsY = {1, 0, -1, 0};
    private int[][] board; // Game Board
    private int[] lowerLine; // represents lowest line of the board
    private WeightedQuickUnionUF uf; // Union Find

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        uf = new WeightedQuickUnionUF(n * n + 1);
        board = new int[n][n];
        lowerLine = new int[n];

        for (int i = 0; i < n; i++) {
            lowerLine[i] = 0;

            for (int j = 0; j < n; j++) {
                board[i][j] = BLOCKED;
            }
        }
    }

    // returns length of the board
    private int getN() {
        return board.length;
    }

    // converts row, col to index of the Union Find
    private int getUFIndex(int row, int col) {
        return row * board.length + col + 1;
    }

    // returns whether the given row is the uppermost row
    private boolean isUpperRow(int row) {
        return (row == 0);
    }

    // returns whether the given row is the lowest row
    private boolean isLowerRow(int row) {
        return (row == getN() - 1);
    }

    // returns wheter the given col is the leftmost col
    private boolean isLeftCol(int col) {
        return (col == 0);
    }

    // returns whether the given col is the rightmost col
    private boolean isRightCol(int col) {
        return (col == getN() - 1);
    }

    // returns wheter the given row, col is on the corner site
    private boolean checkCornerCases(int row, int col) {
        return ((row < 0) || (row >= getN()) ||
                (col < 0) || (col >= getN()));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (checkCornerCases(row, col)) throw new IllegalArgumentException();

        if (!isOpen(row, col)) {
            board[row][col] = OPENED;

            List<Integer> skipIndex = new ArrayList<Integer>();

            if (isUpperRow(row)) {
                uf.union(getUFIndex(row, col), 0);
                skipIndex.add(3);
            }

            if (isLowerRow(row)) {
                lowerLine[col] = 1;
                skipIndex.add(1);
            }

            if (isLeftCol(col)) {
                skipIndex.add(2);
            }

            if (isRightCol(col)) {
                skipIndex.add(0);
            }

            for (int i = 0; i < 4; i++) {
                if (skipIndex.contains(i)) {
                    continue;
                }

                int nextRow = row + fourDirectionsX[i];
                int nextCol = col + fourDirectionsY[i];

                if (isOpen(nextRow, nextCol)) {
                    uf.union(getUFIndex(row, col), getUFIndex(nextRow, nextCol));
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (checkCornerCases(row, col)) throw new IllegalArgumentException();

        return board[row][col] == OPENED;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (checkCornerCases(row, col)) throw new IllegalArgumentException();

        return uf.connected(0, getUFIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;

        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                if (board[i][j] == OPENED) {
                    count++;
                }
            }
        }

        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < getN(); i++) {
            if ((lowerLine[i] == 1) && (uf.connected(0, getUFIndex(getN() - 1, i)))) {
                return true;
            }
        }

        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
