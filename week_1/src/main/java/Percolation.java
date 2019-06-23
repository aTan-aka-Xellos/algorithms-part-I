import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class.
 */
public class Percolation {

    private final int dimension;

    // used to have constant check if system is percolated, see end of video [Union−Find Applications]
    private final int virtualTopSiteCoordinate;
    private final int virtualBottomSiteCoordinate;
    private final WeightedQuickUnionUF quickUnion;

    private int numberOfOpenSites = 0;
    private byte[] state;

    /**
     * Creates n-by-n grid, with all sites blocked.
     *
     * @param n dimension
     */
    public Percolation(int n) {
        validateDimension(n);

        dimension = n;
        virtualTopSiteCoordinate = n * n; // n * n the size of the grid, real sites use coordinates {0,n-1}
        virtualBottomSiteCoordinate = n * n + 1;

        quickUnion = new WeightedQuickUnionUF(n * n + 2);
        state = new byte[n * n];

    }

    /** Open the site (row, col), if it's closed.
     *
     * @param row row
     * @param col col
     */
    public void open(int row, int col) {
        validateCoordinates(row, col);

        if (isOpen(row, col)) return;

        numberOfOpenSites++;
        int currentSite = xyTo1D(row, col);
        state[currentSite] = 1;

        union(currentSite, row + 1, col);
        union(currentSite, row - 1, col);
        union(currentSite, row, col + 1);
        union(currentSite, row, col - 1);

        if (row == 1) {
            quickUnion.union(currentSite, virtualTopSiteCoordinate);
        }
        if (row == dimension) {
            quickUnion.union(currentSite, virtualBottomSiteCoordinate);
        }
    }

    /**
     * Union tested site with neighbor, if it exists and open.
     *
     * @param oneDimensionSite 1-dimension coordinates of site under examination.
     * @param row coordinate of the neighbor to union
     * @param col coordinate of the neighbor to union
     */
    private void union(int oneDimensionSite, int row, int col) {
        if (!isValidCoordinates(row, col)) return;
        if (!isOpen(row, col)) return;

        quickUnion.union(oneDimensionSite, xyTo1D(row, col));
    }

    /**
     * Determine if the site (row, col) is open.
     *
     * @param row row
     * @param col col
     * @return true if open
     */
    public boolean isOpen(int row, int col) {
        validateCoordinates(row, col);

        return state[xyTo1D(row, col)] == 1;
    }

    /**
     * Determine if the site (row, col) is Full,
     * which means it has connection to at least one top site.
     *
     * @param row row
     * @param col col
     * @return true if full
     */
    public boolean isFull(int row, int col) {
        validateCoordinates(row, col);

        return isOpen(row, col) && quickUnion.connected(xyTo1D(row, col), virtualTopSiteCoordinate);
    }


    /**
     * Determine if the system percolates,
     * which means at least one bottom site has connection to at least one top site.
     *
     * @return true if percolates
     */
    public boolean percolates() {
        return quickUnion.connected(virtualTopSiteCoordinate, virtualBottomSiteCoordinate);
    }


    /**
     * Number of open sites.
     *
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }


    /**
     * Convert 2-dimension coordinates into a single 1-dimension coordinate.
     * It based on the order of the elements starting from 0:
     *      {1,1} coordinate - 0
     *      {1,2} coordinate - 1
     *      {9,9} coordinate - 88
     *
     * @param row row
     * @param col col
     * @return 1-dimension coordinate
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * dimension + (col - 1);
    }


    private boolean isValidCoordinates(int row, int col) {
        return row > 0 && col > 0 && row <= dimension && col <= dimension;
    }

    private void validateCoordinates(int row, int col) {
        if (isValidCoordinates(row, col)) return;

        throw new IllegalArgumentException("row: " + row + " col: " + col);
    }

    private void validateDimension(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Dimension should be in range: 0-" + n);
        }
    }

}
