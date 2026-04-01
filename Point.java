import java.util.Objects;

public class Point implements Comparable<Point> {
    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double produsVectorial(Point o, Point a, Point b) {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
    }

    public static boolean esteInStanga(Point a, Point b, Point p) {
        // Logica conform modelului furnizat
        return produsVectorial(a, b, p) >= 0;
    }

    @Override
    public int compareTo(Point o) {
        if (Math.abs(this.x - o.x) > 1e-9) return Double.compare(this.x, o.x);
        return Double.compare(this.y, o.y);
    }

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)", x, y);
    }
}