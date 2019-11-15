package sunrise.entity.space;

import sunrise.entity.SEntity;
import sunrise.entity.base.SVector;

public abstract class SShape extends SEntity {
    private SVector position;
    private double angle;

    protected SShape(SVector position, double angle) {
        this.position = new SVector(position);
        this.angle = angle;
    }

    public SVector getPosition() {
        return position;
    }

    public void setPosition(SVector position) {
        this.position = new SVector(position);
    }

    public void move(SVector movement) {
        this.position.add(movement);
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void rotate(double rotation) {
        this.angle += rotation;
    }
}
