package engine.entity.space;

public class SVector {
    private double x, y;

    public SVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public SVector(SVector vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void add(SVector vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public void subtract(SVector vector) {
        this.x -= vector.x;
        this.y -= vector.y;
    }

    public void multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public double normal() {
        return Math.sqrt(x * x + y * y);
    }

    public double angle() {
        return Math.atan(y / x);
    }

    public static SVector add(SVector v1, SVector v2) {
        SVector v = new SVector(v1);
        v.add(v2);

        return v;
    }

    public static SVector subtract(SVector v1, SVector v2) {
        SVector v = new SVector(v1);
        v.subtract(v2);

        return v;
    }

    public static double dot(SVector v1, SVector v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public static SVector project(SVector v1, SVector v2) {
        SVector vector = new SVector(v2);
        vector.multiply(dot(v1, v2) / Math.pow(v2.normal(), 2));

        return vector;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
