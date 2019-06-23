package attempt2018;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] threshold;
    private final int trials;
    private static final double INTERVAL_COEFFICIENT = 1.96;

    private double mean;
    private double stddev;

    /**
     * Perform trials independent experiments on an n-by-n grid.
     * @param dimension dimension
     * @param trials trials
     */
    public PercolationStats(int dimension, int trials) {
        validatePercolationStats(dimension, trials);

        this.trials = trials;
        this.threshold = new double[trials];

        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(dimension);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(dimension + 1);
                int col = StdRandom.uniform(dimension + 1);

                // generated invalid values
                if (row == 0 || col == 0 || percolation.isOpen(row, col)) {
                    continue;
                }
                percolation.open(row, col);
            }

            threshold[i] = (double) percolation.numberOfOpenSites()
                    / (dimension * dimension);
        }
    }

    /**
     * Sample mean of percolation threshold.
     * @return mean
     */
    public double mean() {
        if (mean == 0.0) {
            mean = StdStats.mean(threshold);
        }
        return mean;
    }

    /**
     * Sample standard deviation of percolation threshold.
     * @return standard deviation
     */
    public double stddev() {
        if (stddev == 0.0) {
            stddev = StdStats.stddev(threshold);
        }
        return stddev;
    }

    /**
     * Low endpoint of 95% confidence interval.
     * @return confidenceLo
     */
    public double confidenceLo() {
        return mean() - (INTERVAL_COEFFICIENT * stddev() / Math.sqrt(trials));
    }

    /**
     * High endpoint of 95% confidence interval.
     * @return confidenceHi
     */
    public double confidenceHi() {
        return mean() + (INTERVAL_COEFFICIENT * stddev() / Math.sqrt(trials));
    }

    private static void validatePercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n: " + n
                    + " trials: " + trials);
        }
    }

    public static void main(String[] args) {
        int[] value = StdIn.readAllInts();
        StdOut.print(value);
    }
}