import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class.
 */
public class Percolation {

    private final int dimension;

    private int numberOfOpenSites = 0;
    private final WeightedQuickUnionUF quickUnion;

    /**
     * create n-by-n grid, with all sites blocked.
     * @param n dimension
     */
    public Percolation(int n) {
        validateDimension(n);

        dimension = n;
        quickUnion = new WeightedQuickUnionUF(n * n);
    }

    /** Open site (row, col) if it is not open already.
     *
     * @param row row
     * @param col col
     */
    public void open(int row, int col) {
        validatePrescribedInput(row, col);

        throw new UnsupportedOperationException();
    }

    /**
     * Is site (row, col) open.
     * @param row row
     * @param col col
     * @return true if open
     */
    public boolean isOpen(int row, int col) {
        validatePrescribedInput(row, col);

        throw new UnsupportedOperationException();
    }

    /**
     * Is site (row, col) full?
     * @param row row
     * @param col col
     * @return true if full
     */
    public boolean isFull(int row, int col) {
        validatePrescribedInput(row, col);

        throw new UnsupportedOperationException();
    }



    /**
     * does the system percolate?
     * @return true if percolates
     */
    public boolean percolates() {

        throw new UnsupportedOperationException();
    }

    /**
     * Number of open sites.
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
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
