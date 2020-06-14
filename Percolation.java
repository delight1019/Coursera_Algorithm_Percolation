/* *****************************************************************************
 *  Name:    Hyehyeon Kim
 *  NetID:   kippem9088
 *  Precept: P01
 *
 *  Description:  Generates N x N grid and check that it percolates.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] board; // Game Board
    private boolean[] lowerLine; // represents lowest line of the board
    private final WeightedQuickUnionUF weightedQuickUnionUF; // Union Find
    private int numOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 1);
        board = new boolean[n + 1][n + 1];
        lowerLine = new boolean[n + 1];
        numOfOpenSites = 0;

        for (int i = 0; i <= n; i++) {
            lowerLine[i] = false;

            for (int j = 0; j <= n; j++) {
                board[i][j] = false;
            }
        }
    }

    // returns length of the board
    private int getN() {
        return board.length - 1;
    }

    // converts row, col to index of the Union Find
    private int getUFIndex(int row, int col) {
        return (row-1) * getN() + col;
    }

    // returns whether the given row is the uppermost row
    private boolean isUpperRow(int row) {
        return (row == 1);
    }

    // returns whether the given row is the lowest row
    private boolean isLowerRow(int row) {
        return (row == getN());
    }

    // returns wheter the given col is the leftmost col
    private boolean isLeftCol(int col) {
        return (col == 1);
    }

    // returns whether the given col is the rightmost col
    private boolean isRightCol(int col) {
        return (col == getN());
    }

    // returns wheter the given row, col is on the corner site
    private boolean checkCornerCases(int row, int col) {
        return ((row <= 0) || (row > getN()) ||
                (col <= 0) || (col > getN()));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (checkCornerCases(row, col)) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            board[row][col] = true;
            numOfOpenSites++;

            if (isUpperRow(row)) {
                weightedQuickUnionUF.union(getUFIndex(row, col), 0);
            }

            if (isLowerRow(row)) {
                lowerLine[col] = true;
            }

            if (!isUpperRow(row)) {
                int nextRow = row - 1;
                int nextCol = col;

                if (isOpen(nextRow, nextCol) &&
                        (weightedQuickUnionUF.find(getUFIndex(row, col)) != weightedQuickUnionUF.find(getUFIndex(nextRow, nextCol)))) {
                    weightedQuickUnionUF.union(getUFIndex(row, col), getUFIndex(nextRow, nextCol));
                }
            }

            if (!isLowerRow(row)) {
                int nextRow = row + 1;
                int nextCol = col;

                if (isOpen(nextRow, nextCol) &&
                    (weightedQuickUnionUF.find(getUFIndex(row, col)) != weightedQuickUnionUF.find(getUFIndex(nextRow, nextCol)))) {
                    weightedQuickUnionUF.union(getUFIndex(row, col), getUFIndex(nextRow, nextCol));
                }
            }

            if (!isLeftCol(col)) {
                int nextRow = row;
                int nextCol = col - 1;

                if (isOpen(nextRow, nextCol) &&
                    (weightedQuickUnionUF.find(getUFIndex(row, col)) != weightedQuickUnionUF.find(getUFIndex(nextRow, nextCol)))) {
                    weightedQuickUnionUF.union(getUFIndex(row, col), getUFIndex(nextRow, nextCol));
                }
            }

            if (!isRightCol(col)) {
                int nextRow = row;
                int nextCol = col + 1;

                if (isOpen(nextRow, nextCol) &&
                        (weightedQuickUnionUF.find(getUFIndex(row, col)) != weightedQuickUnionUF.find(getUFIndex(nextRow, nextCol)))) {
                    weightedQuickUnionUF.union(getUFIndex(row, col), getUFIndex(nextRow, nextCol));
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (checkCornerCases(row, col)) {
            throw new IllegalArgumentException();
        }

        return board[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (checkCornerCases(row, col)) {
            throw new IllegalArgumentException();
        }

        return weightedQuickUnionUF.find(0) == weightedQuickUnionUF.find(getUFIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= getN(); i++) {
            if (lowerLine[i] && (weightedQuickUnionUF.find(0) == weightedQuickUnionUF.find(getUFIndex(getN(), i)))) {
                return true;
            }
        }

        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        // Not Implement
    }
}
