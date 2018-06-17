import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class.
 */
public class Percolation {

    private final int dimension;

    private int numberOfOpenSites = 0;
    private boolean[][] states;

    private WeightedQuickUnionUF quickUnion;

    /**
     * create n-by-n grid, with all sites blocked.
     * @param n dimension
     */
    public Percolation(int n) {
        validateDimension(n);

        dimension = n;
        states = new boolean[n + 1][n + 1];
        quickUnion = new WeightedQuickUnionUF(n * n);
    }

    /** Open site (row, col) if it is not open already.
     *
     * @param row row
     * @param col col
     */
    public void open(int row, int col) {
        validatePrescribedInput(row, col);

        if (isOpen(row, col)) {
            return;
        }

        states [row][col] = true;
        unionNeighbors(row, col);
        numberOfOpenSites++;
    }

    /**
     * Is site (row, col) open.
     * @param row row
     * @param col col
     * @return true if open
     */
    public boolean isOpen(int row, int col) {
        validatePrescribedInput(row, col);

        return states[row][col];
    }

    /**
     * Is site (row, col) full?
     * @param row row
     * @param col col
     * @return true if full
     */
    public boolean isFull(int row, int col) {
        validatePrescribedInput(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        for (int topCol = 1; topCol <= dimension; topCol++) {
            if (!isOpen(1, topCol)) {
                continue;
            }

            if (quickUnion.connected(xyTo1D(row, col), xyTo1D(1, topCol))) {
                return true;
            }
        }
        return false;
    }

    /**
     * does the system percolate?
     * @return true if percolates
     */
    public boolean percolates() {

        for (int bottomCol = 1; bottomCol <= dimension; bottomCol++) {
            if (!isOpen(dimension, bottomCol)) {
                continue;
            }

            if (isFull(dimension, bottomCol)) {
                return true;
            }
        }
        return false;
    }

    /**
     * number of open sites.
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }


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

        neighbors[0] = getLeftNeighbor(row, col);
        neighbors[1] = getRightNeighbor(row, col);
        neighbors[2] = getBottomNeighbor(row, col);
        neighbors[3] = getTopNeighbor(row, col);

        return neighbors;
    }

    private int xyTo1D(int row, int col) {
        validatePrescribedInput(row, col);
        return (row - 1) * dimension + col - 1;
    }

    private int[] getLeftNeighbor(int row, int col) {
        if (col > 1) {
            return new int[]{row, col - 1};
        } else {
            return new int[]{};
        }
    }

    private int[] getRightNeighbor(int row, int col) {
        if (col < dimension) {
            return new int[]{row, col + 1};
        } else {
            return new int[]{};
        }
    }

    private int[] getBottomNeighbor(int row, int col) {
        if (row > 1) {
            return new int[]{row - 1, col};
        } else {
            return new int[]{};
        }
    }

    private int[] getTopNeighbor(int row, int col) {
        if (row < dimension) {
            return new int[]{row + 1, col};
        } else {
            return new int[]{};
        }
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

}
