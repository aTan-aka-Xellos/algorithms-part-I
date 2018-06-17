import static edu.princeton.cs.algs4.StdRandom.uniform;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int totalSites;
    private final int dimension;

    private int numberOfOpenSites;
    private boolean[][] states;

    private WeightedQuickUnionUF quickUnion;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        validateDimension(n);

        dimension  = n;
        totalSites = n * n;
        quickUnion = new WeightedQuickUnionUF(totalSites);

        states = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n ; j++) {
                if (uniform(2) == 1) {
                    states[i][j] = true;
                    numberOfOpenSites++;

//                    unionToNeighborsIfOpen(i, j);
                } else {
                    states[i][j] = false;
                }
            }
        }
        System.out.println("");
    }


    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validatePrescribedInput(row, col);

        if (isOpen(row, col)) return;

        states [row - 1][col - 1] = true;
        numberOfOpenSites++;

        unionToNeighborsIfOpen(row - 1, col - 1);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validatePrescribedInput(row, col);

        StdOut.println("isOpen row: " + row + " col: " + col);
        if (row >= 10 || col >= 10) {
            StdOut.println("Fail isOpen row: " + row + " col: " + col);
        }
        return states[row - 1][col - 1];
    }

//    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validatePrescribedInput(row, col);

//        quickUnion.
        return false;
    }

//    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

//    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    public static void main(String[] args) {

        int dimension = 10;

        test_xyTo1D();
        test_neighbors();

        Percolation p = new Percolation(dimension);

        StdOut.print("Open sites: " + p.numberOfOpenSites());
        StdOut.print("QuickUnion count: " + p.quickUnion.count());
        PercolationVisualizer.draw(p, dimension);
    }


    private void unionToNeighborsIfOpen(int row, int col) {
        int left   = getLeftNeighborIndex  (row, col);
        int right  = getRightNeighborIndex (row, col);
        int bottom = getBottomNeighborIndex(row, col);
        int top    = getTopNeighborIndex   (row, col);

        int index = xyTo1D(row, col);
        unionNeighbor(left,   index);
        unionNeighbor(right,  index);
        unionNeighbor(bottom, index);
        unionNeighbor(top,    index);
    }

    private void unionNeighbor(int neighbor, int index) {
        if (index > -1 && neighbor > -1) quickUnion.union(neighbor, index);
    }

    // map from a 2-dimensional (row, column) pair to a 1-dimensional union find object index
    private static int xyTo1D(int row, int col) {
        return row * 10 + col;
    }

    private int getLeftNeighborIndex(int row, int col) {
        if (col > 0 && isOpen(row + 1, col - 1 + 1)) {
            return xyTo1D(row, col - 1);
        }
        else
            return -1;
    }

    private int getRightNeighborIndex(int row, int col) {
        if (col < dimension && isOpen(row + 1, col + 1 + 1)) {
            return xyTo1D(row, col + 1);

        }
        else
            return -1;
    }

    private int getBottomNeighborIndex(int row, int col) {
        if (row > 0 && isOpen(row - 1 + 1, col + 1)) {
            return xyTo1D(row - 1, col);
        }
        else
            return -1;
    }

    private int getTopNeighborIndex(int row, int col) {
        if (row < dimension && isOpen(row + 1 + 1, col + 1)) {
            return xyTo1D(row + 1, col);
        }
        else
            return -1;
    }

    private static void test_neighbors() {
        int dimension = 10;
        Percolation p = new Percolation(dimension);
        assert p.getLeftNeighborIndex(0, 0) == -1;
        assert p.getLeftNeighborIndex(1, 0) == -1;

        assert p.getRightNeighborIndex(dimension, dimension) == -1;
        assert p.getRightNeighborIndex(0, dimension) == -1;

        assert p.getBottomNeighborIndex(0, 0) == -1;
        assert p.getBottomNeighborIndex(0, dimension) == -1;

        assert p.getTopNeighborIndex(dimension, dimension) == -1;
        assert p.getTopNeighborIndex(dimension, 0) == -1;

        assert p.getLeftNeighborIndex(5, 5)   == 54;
        assert p.getRightNeighborIndex(5, 5)  == 56;
        assert p.getBottomNeighborIndex(5, 5) == 45;
        assert p.getTopNeighborIndex(5, 5)    == 65;




    }



    private static void test_xyTo1D() {
        assert xyTo1D(0, 0) == 0;
        assert xyTo1D(0, 5) == 5;
        assert xyTo1D(2, 8) == 28;
        assert xyTo1D(9, 0) == 90;
        assert xyTo1D(10, 10) == 110;
    }


    private static void validatePrescribedInput(int row, int col ) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateDimension(int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException();
        }
    }


    public static void print(Percolation p) {
        for (int i = 0; i < p.states.length; i++) {
            for (int j = 0; j < p.states.length; j++) {
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
