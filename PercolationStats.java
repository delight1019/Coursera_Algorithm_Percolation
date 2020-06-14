/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import static java.lang.Math.sqrt;

public class PercolationStats {
    private double[] fractionArray;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        fractionArray = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);

                percolation.open(row, col);
            }

            fractionArray[t] = percolation.numberOfOpenSites() / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractionArray);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / sqrt(fractionArray.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / sqrt(fractionArray.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, T);

        System.out.print(String.format("%-23s = %.16f\n", "mean", percolationStats.mean()));
        System.out.print(String.format("%-23s = %.16f\n", "stddev", percolationStats.stddev()));
        System.out.print(String.format("%-23s = [%.16f, %.16f]\n", "95% confidence interval", percolationStats.confidenceLo(), percolationStats.confidenceHi()));
    }
}