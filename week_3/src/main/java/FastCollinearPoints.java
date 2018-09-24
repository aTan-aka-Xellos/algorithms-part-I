import java.util.Arrays;

public class FastCollinearPoints {

    private int numberOfSegments;
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validation(points);
        Arrays.sort(points);

        int size = points.length;
        LineSegment[] lineSegments = new LineSegment[size * size];

        Point prevBasePoint = new Point(1, 100_000);

        for (int i = 0; i < size - 3; i++) {
            int slopeCount = 0;

            Point[] subPoints = Arrays.copyOfRange(points, i + 1, size);
            Arrays.sort(subPoints, points[i].slopeOrder());
            double[] slopes = new double[subPoints.length];

            for (Point subPoint : subPoints) {
                if (points[i].compareTo(subPoint) == 0) throw new IllegalArgumentException();
                slopes[slopeCount++] = points[i].slopeTo(subPoint);
            }

            int count = 1;
            for (int j = 0; j < slopes.length - 1; j++) {
                if (Double.compare(slopes[j], slopes[j + 1]) == 0) {
                    count++;
                } else if (count < 3) {
                    count = 1;
                } else {
                    if (prevBasePoint == points[i]
                        || Double.compare(prevBasePoint.slopeTo(subPoints[j]),
                        points[i].slopeTo(subPoints[j])) != 0) {

                        lineSegments[numberOfSegments++] = new LineSegment(points[i], subPoints[j]);
                        prevBasePoint = points[i];

                    }
                    count = 1;
                }
            }

            if (count >= 3) {
                if (prevBasePoint == points[i]
                    || Double.compare(prevBasePoint.slopeTo(subPoints[slopes.length - 1]),
                    points[i].slopeTo(subPoints[slopes.length - 1])) != 0) {

                    lineSegments[numberOfSegments++] = new LineSegment(points[i], subPoints[slopes.length - 1]);
                    prevBasePoint = points[i];

                }
            }
        }

        segments = Arrays.copyOf(lineSegments, numberOfSegments);

    }

    private void validation(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments);
    }
}