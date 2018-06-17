import static edu.princeton.cs.algs4.StdRandom.uniform;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int dimension;

    private int numberOfOpenSites;
    private boolean[][] states;

    private WeightedQuickUnionUF quickUnion;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        validateDimension(n);

        dimension  = n;

        states = new boolean[n + 1][n + 1];

        for (int i = 1; i < states.length; i++) {
            for (int j = 1; j < states.length ; j++) {
                if (uniform(2) == 1) {
                    states [i][j] = true;
                    numberOfOpenSites++;
                }
            }
        }
        quickUnion = new WeightedQuickUnionUF(n * n);

//        print(this);
        
        // 2 * N^2 - too slow
        for (int i = 1; i < states.length; i++) {
            for (int j = 1; j < states.length; j++) {
                if (isOpen(i, j)) {
                    unionNeighbors(i, j);
                }
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validatePrescribedInput(row, col);

        if (isOpen(row, col)) return;

        states [row][col] = true;
        unionNeighbors(row, col);
        numberOfOpenSites++;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validatePrescribedInput(row, col);

        return states[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validatePrescribedInput(row, col);

        if (!isOpen(row, col)) return false;

        for (int topCol = 1; topCol <= dimension; topCol++) {
            if (!isOpen(1, topCol)) continue;

            if (quickUnion.connected(xyTo1D(row, col), xyTo1D(1, topCol))) {
                return true;
            }
        }
        return false;
    }


    // does the system percolate?
    public boolean percolates() {

        for (int bottomCol = 1; bottomCol <= dimension; bottomCol++) {
            if (!isOpen(dimension, bottomCol)) {
                continue;
            }

            if (isFull(dimension, bottomCol)) return true;
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

//    public static void main(String[] args) {
//
//        int dimension = 10;
//
//        Percolation p = new Percolation(dimension);
//
//        StdOut.println("Initiated open sites: " + p.numberOfOpenSites());
//        StdOut.println("QuickUnion count: " + p.quickUnion.count());
//        PercolationVisualizer.draw(p, dimension);
//        StdOut.println("Percolates: " + p.percolates());
//    }

    private void unionNeighbors(int row, int col) {
        int[][] neighbors;

        neighbors = getNeighbors(row, col);
        for (int[] neighbor : neighbors) {
            if (neighbor.length > 0
                    && isOpen(neighbor[0], neighbor[1])
                    && !quickUnion.connected(xyTo1D(row, col),
                        xyTo1D(neighbor[0], neighbor[1]))) {

                quickUnion.union(xyTo1D(row, col),
                        xyTo1D(neighbor[0], neighbor[1]));
            }
        }
    }

    private int[][] getNeighbors(int row, int col) {
        validatePrescribedInput(row, col);
        int[][] neighbors = new int[4][2];

        neighbors[0] = getLeftNeighbor  (row, col);
        neighbors[1] = getRightNeighbor (row, col);
        neighbors[2] = getBottomNeighbor(row, col);
        neighbors[3] = getTopNeighbor   (row, col);

        return neighbors;
    }

    // map from a 2-dimensional (row, column) pair to a 1-dimensional union find object index
    private int xyTo1D(int row, int col) {
        validatePrescribedInput(row, col);
        return (row - 1) * dimension + col - 1;
    }

    private int[] getLeftNeighbor(int row, int col) {
        if (col > 1) return new int[]{row, col - 1};
        else         return new int[]{};
    }

    private int[] getRightNeighbor(int row, int col) {
        if (col < dimension) return new int[]{row, col + 1};
        else                 return new int[]{};
    }

    private int[] getBottomNeighbor(int row, int col) {
        if (row > 1) return new int[]{row - 1, col};
        else         return new int[]{};
    }

    private int[] getTopNeighbor(int row, int col) {
        if (row < dimension) return new int[]{row + 1, col};
        else                 return new int[]{};
    }

    private static void validatePrescribedInput(int row, int col ) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("row: " + row + " col: " + col);
        }
    }

    private static void validateDimension(int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private void print(Percolation p) {
        for (int i = 1; i < p.states.length; i++) {
            for (int j = 1; j < p.states.length; j++) {
                if (p.states[i][j]) {
                    StdOut.print(1);
                } else {
                    StdOut.print(0);
                }
                StdOut.print(" ");
            }
            StdOut.println();
        }
    }
}
