import java.util.Arrays;

public class BruteCollinearPoints {

    private int numberOfSegments;
    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validation(points);

        LineSegment[] lineSegments = new LineSegment[points.length];

        int length = points.length;

        for (int i = 0; i < length; i++) {

            for (int j = i + 1; j < length; j++) {
                double slopeToSecond = points[i].slopeTo(points[j]);
                if (slopeToSecond == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();

                for (int k = j + 1; k < length; k++) {
                    double slopeToThird = points[i].slopeTo(points[k]);

                    if (slopeToThird == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                    if (Double.compare(slopeToSecond, slopeToThird) != 0) continue;

                    for (int m = k + 1; m < length; m++) {
                        double slopeToFourth = points[i].slopeTo(points[m]);

                        if (slopeToFourth == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                        if (Double.compare(slopeToSecond, slopeToFourth) != 0) continue;

                        lineSegments[numberOfSegments] = findEdges(points, i, j, k, m);
                        numberOfSegments++;
                    }
                }
            }
        }
        segments = Arrays.copyOf(lineSegments, numberOfSegments);
    }

    private LineSegment findEdges(Point[] points, int i, int j, int k, int m) {

        Point[] p = {points[i], points[j], points[k], points[m]};
        Arrays.sort(p);

        return new LineSegment(p[0], p[3]);
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