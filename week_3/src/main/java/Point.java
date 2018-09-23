import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {

        double dy = that.y - this.y;
        double dx = that.x - this.x;

        if (dy == 0.0 && dx == 0.0) return NEGATIVE_INFINITY;
        if (dx == 0.0) return POSITIVE_INFINITY;
        if (dy == 0.0) return 0.0;

        return dy / dx;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y > that.y) return 1;
        if (this.y < that.y) return -1;

        return Integer.compare(this.x, that.x);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrderComparator();
    }

    private class SlopeOrderComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            double slope1 = slopeTo(o1);
            double slope2 = slopeTo(o2);

            return Double.compare(slope1, slope2);
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {

        testSlopeTo();
        testCompareTo();
        testSlopeOrder();
    }

    private static void testSlopeOrder() {
        Point p0 = new Point(10, 10);

        assert p0.slopeOrder().compare(new Point(20, 20), new Point(50, 50)) == 0;
        assert p0.slopeOrder().compare(new Point(50, 50), new Point(20, 20)) == 0;
        assert p0.slopeOrder().compare(new Point(20, 20), new Point(30, 15)) > 0;
        assert p0.slopeOrder().compare(new Point(20, 20), new Point(30, 15)) > 0;
        assert p0.slopeOrder().compare(new Point(20, 20), new Point(30, 50)) < 0;
        assert p0.slopeOrder().compare(new Point(20, 20), new Point(0, 10)) > 0;
    }

    private static void testCompareTo() {
        Point p0 = new Point(10, 10);
        Point p1 = new Point(10, 10);
        assert p0.compareTo(p1) == 0;

        p0 = new Point(10, 20);
        p1 = new Point(10, 10);
        assert p0.compareTo(p1) > 0;

        p0 = new Point(10, 10);
        p1 = new Point(10, 20);
        assert p0.compareTo(p1) < 0;

        p0 = new Point(20, 10);
        p1 = new Point(10, 10);
        assert p0.compareTo(p1) > 0;

        p0 = new Point(10, 10);
        p1 = new Point(20, 10);
        assert p0.compareTo(p1) < 0;
    }

    private static void testSlopeTo() {

        Point p0 = new Point(10, 10);
        Point p1 = new Point(20, 10);
        assert p0.slopeTo(p1) == +0.0;

        p0 = new Point(10, 10);
        p1 = new Point(10, 20);
        assert p0.slopeTo(p1) == POSITIVE_INFINITY;

        p0 = new Point(10, 10);
        p1 = new Point(10, 10);
        assert p0.slopeTo(p1) == NEGATIVE_INFINITY;

        p0 = new Point(10, 10);
        p1 = new Point(20, 20);
        assert p0.slopeTo(p1) == 1.0;

        p0 = new Point(10, 10);
        p1 = new Point(20, 110);
        assert p0.slopeTo(p1) == 10.0;

        p0 = new Point(20, 110);
        p1 = new Point(10, 10);
        assert p0.slopeTo(p1) == 10.0;

        p0 = new Point(10, 110);
        p1 = new Point(20, 10);
        assert p0.slopeTo(p1) == -10.0;

        p0 = new Point(110, 10);
        p1 = new Point(10, 20);
        assert p0.slopeTo(p1) == -0.1;

        p0 = new Point(10, 0);
        p1 = new Point(0, 32_767);
        assert p0.slopeTo(p1) == -3276.7;

        p0 = new Point(0, 0);
        p1 = new Point(10, 32_767);
        assert p0.slopeTo(p1) == 3276.7;

        p0 = new Point(0, 0);
        p1 = new Point(32_767, 10);
        assert p0.slopeTo(p1) == 3.051850947599719E-4;

        p0 = new Point(32_767, 0);
        p1 = new Point(0, 10);
        assert p0.slopeTo(p1) == -3.051850947599719E-4;
    }

}