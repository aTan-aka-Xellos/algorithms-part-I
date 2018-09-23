import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

public class PointTest  {

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {

        testSlopeTo();
        testCompareTo();
        testSlopeOrder();
    }

    public static void testSlopeOrder() {
        Point p0 = new Point(10, 10);

        assert p0.slopeOrder().compare(new Point(20, 20), new Point(50, 50)) == 0;
        assert p0.slopeOrder().compare(new Point(50, 50), new Point(20, 20)) == 0;
        assert p0.slopeOrder().compare(new Point(20, 20), new Point(30, 15)) > 0;
        assert p0.slopeOrder().compare(new Point(20, 20), new Point(30, 15)) > 0;
        assert p0.slopeOrder().compare(new Point(20, 20), new Point(30, 50)) < 0;
        assert p0.slopeOrder().compare(new Point(20, 20), new Point(0, 10)) > 0;
    }

    public static void testCompareTo() {
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

    public static void testSlopeTo() {

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