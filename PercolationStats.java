/* *****************************************************************************
 *  Name:    Hyehyeon Kim
 *  NetID:   kippem9088
 *  Precept: P01
 *
 *  Description: Given N and T, creates N x N grids and
 *               does T times trial of percolation.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CINFIDENCE_INTERVAL_CONST = 1.96;
    // Array to store fraction of opens sites
    private final double[] fractionArray;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        fractionArray = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

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
        return mean() - CINFIDENCE_INTERVAL_CONST * stddev() / Math.sqrt(fractionArray.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CINFIDENCE_INTERVAL_CONST * stddev() / Math.sqrt(fractionArray.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, t);

        System.out.print(String.format("%-23s = %.16f\n",
                                       "mean", percolationStats.mean()));
        System.out.print(String.format("%-23s = %.16f\n",
                                       "stddev", percolationStats.stddev()));
        System.out.print(String.format("%-23s = [%.16f, %.16f]\n",
                                       "95% confidence interval",
                                       percolationStats.confidenceLo(),
                                       percolationStats.confidenceHi()));
    }
}