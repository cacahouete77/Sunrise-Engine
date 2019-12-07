package sunrise.entity.space.polygon;

import sunrise.entity.space.SShape;
import sunrise.entity.space.SVector;

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
