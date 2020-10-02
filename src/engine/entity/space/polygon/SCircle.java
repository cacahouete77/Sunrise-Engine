package engine.entity.space.polygon;

import engine.entity.space.SShape;
import engine.entity.space.SVector;

public abstract class SCircle extends SShape {
    private double radius;

    protected SCircle(SVector position, double angle, double radius) {
        super(position, angle);

        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
