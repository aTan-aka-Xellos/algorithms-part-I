package attempt2018;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class.
 * The first attempt to solve the problem. Use recursion instead of tree structure.
 *
 * THIS IS PURE PIECE OF SHIT!!!
 *
 * Close it and never open ever again.
 */
public class Percolation {

    private final int dimension;

    private int numberOfOpenSites = 0;
    private final byte[][] states;
    private final WeightedQuickUnionUF quickUnion;

    /**
     * create n-by-n grid, with all sites blocked.
     * @param n dimension
     */
    public Percolation(int n) {
        validateDimension(n);

        dimension = n;
        states = new byte[n + 1][n + 1];
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

        states[row][col] = 1;

        if (row == 1) {
            setTagFull(row, col);
        }

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

        return states[row][col] > 0;
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

        return isTagFull(row, col);

    }

    private void setTagFull(int row, int col) {
        states[row][col] = 2;
    }

    private boolean isTagFull(int row, int col) {
        return states[row][col] == 2;
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
     * Number of open sites.
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    private void unionNeighbors(int row, int col) {
        int[][] neighbors;

        if (isTagFull(row, col)) {
            updateNeighbors(row, col);
        }

        neighbors = getNeighbors(row, col);
        for (int[] neighbor : neighbors) {
            if (neighbor.length > 0 && isOpen(neighbor[0], neighbor[1])) {

                if (!isTagFull(row, col) && isTagFull(neighbor[0], neighbor[1])) {
                    setTagFull(row, col);
                    updateNeighbors(row, col);
                }

                quickUnion.union(xyTo1D(row, col),
                        xyTo1D(neighbor[0], neighbor[1]));
            }
        }
    }

    private void updateNeighbors(int row, int col) {
        int[][] neighbors;
        neighbors = getNeighbors(row, col);

        for (int[] neighbor : neighbors) {
            if (neighbor.length > 0 && isOpen(neighbor[0], neighbor[1])
                    && !isTagFull(neighbor[0], neighbor[1])) {
                setTagFull(neighbor[0], neighbor[1]);
                updateNeighbors(neighbor[0], neighbor[1]);
            }
        }
    }

    private int[][] getNeighbors(int row, int col) {
        validatePrescribedInput(row, col);
        int numberOfNeighbors = 4;
        int[][] neighbors = new int[numberOfNeighbors][2];

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

    private void validatePrescribedInput(int row, int col) {
        if (row <= 0 || row > dimension || col <= 0 || col > dimension) {
            throw new IllegalArgumentException("row: " + row + " col: " + col);
        }
    }

    private void validateDimension(int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException();
        }
    }

}
