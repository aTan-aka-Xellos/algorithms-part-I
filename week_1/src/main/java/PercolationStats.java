import static edu.princeton.cs.algs4.StdIn.readAllInts;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] threshold;
    private final int trials;

    /**
     * perform trials independent experiments on an n-by-n grid
     * @param dimension dimension
     * @param trials trials
     */
    public PercolationStats(int dimension, int trials) {
        validatePercolationStats(dimension, trials);

        this.trials = trials;
        this.threshold = new double[trials];

        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(dimension);

            while(!percolation.percolates()) {
                int row = StdRandom.uniform(dimension + 1);
                int col = StdRandom.uniform(dimension + 1);

                // generated invalid values
                if (row == 0 || col == 0) continue;
                percolation.open(row, col);
            }

            threshold[i] = (double)percolation.numberOfOpenSites()
                    / (dimension * dimension);
        }
    }

    /**
     * sample mean of percolation threshold.
     * @return mean
     */
    public double mean() {
        return StdStats.mean(threshold);
    }

    /**
     * sample standard deviation of percolation threshold.
     * @return stddev
     */
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    /**
     * low endpoint of 95% confidence interval.
     * @return confidenceLo
     */
    public double confidenceLo() {
        return mean() - (1.96 * stddev()/Math.sqrt(trials));
    }

    /**
     * high endpoint of 95% confidence interval.
     * @return confidenceHi
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev()/Math.sqrt(trials));
    }

    public static void main(String[] args) {

        int[] value = readAllInts();
        PercolationStats pStats = new PercolationStats(value[0], value[1]);
    }

    private static void validatePercolationStats(int n, int trials ) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n: " + n
                    + " trials: " + trials);
        }
    }
}