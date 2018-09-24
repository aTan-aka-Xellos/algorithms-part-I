import java.util.Arrays;

public class FastCollinearPoints {

    private int numberOfSegments;
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validation(points);
        Arrays.sort(points);

        int size = points.length;
        LineSegment[] lineSegments = new LineSegment[size];

        for (int i = 0; i < size - 3; i++) {
            int slope_count = 0;

            Point[] subPoints = Arrays.copyOfRange(points, i + 1, size);
            Arrays.sort(subPoints, points[i].slopeOrder());
            double[] slopes = new double[subPoints.length];

            for (int j = 0; j < subPoints.length; j++) {
                slopes[slope_count++] = points[i].slopeTo(subPoints[j]);
            }

            int count = 1;
            for (int j = 0; j < slopes.length - 1; j++) {
                if (slopes[j] == slopes[j + 1]) {
                    count++;
                }

                if (slopes[j] != slopes[j + 1] && count < 3) {
                    count = 1;
                }

                if (count >= 3 && (slopes[j] != slopes[j + 1] || j == slopes.length - 2)) {

                    if (slopes[j] != slopes[j + 1]) {
                        lineSegments[numberOfSegments++] = new LineSegment(points[i], subPoints[j]);
                    } else if (j == slopes.length - 2) {
                        lineSegments[numberOfSegments++] = new LineSegment(points[i], subPoints[j + 1]);
                    }
                    count = 1;
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