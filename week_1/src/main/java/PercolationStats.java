import static edu.princeton.cs.algs4.StdIn.readAllInts;
import static edu.princeton.cs.algs4.StdOut.println;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private final double[] threshold;
    private final int trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int dimension, int trials) {
        validatePercolationStats(dimension, trials);


        this.trials = trials;
        this.threshold = new double[trials];

        long curr = System.currentTimeMillis();

        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(dimension);

            println();
//            println("openSites start: " + percolation.numberOfOpenSites());

            int counterOpen = 0;
            while(!percolation.percolates()) {
                int row = StdRandom.uniform(dimension);
                int col = StdRandom.uniform(dimension);

                // generated invalid values
                if (row == 0 || col == 0) continue;

                percolation.open(row, col);
                counterOpen++;
            }

            println("PERCOLATES!!!");
            println("takes open: " + counterOpen);


            threshold[i] = (double)percolation.numberOfOpenSites()
                    / (dimension * dimension);

//            println("openSites end:   " + percolation.numberOfOpenSites());
            println("trial:           " + i);
//            println("threshold:       " + threshold[i]);
        }

        StdOut.println(System.currentTimeMillis() - curr + " ms");

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()/Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()/Math.sqrt(trials));
    }

    public static void main(String[] args) {

        println("Enter dimension and numbers of trials:");

        int[] value = readAllInts();
        println(value[0] + " " + value[1]);

        PercolationStats pStats = new PercolationStats(value[0], value[1]);

//        println();
//        println("mean:      " + pStats.mean());1
//        println("stddev:    " + pStats.stddev());
//        println("interval: [" + pStats.confidenceLo()
//                + ", " + pStats.confidenceHi() + "]");
        println();
    }


    private static void validatePercolationStats(int n, int trials ) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n: " + n
                    + " trials: " + trials);
        }
    }

}
