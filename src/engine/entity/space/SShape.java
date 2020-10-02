package engine.entity.space;

import engine.entity.SEntity;
import engine.environment.SEnvironment;

public abstract class SShape extends SEntity {
    private boolean relative = false;
    private SVector position;
    private double angle;

    protected SShape(SVector position, double angle) {
        this.position = new SVector(position);
        this.angle = angle;
    }

    public SVector getPosition() {
        SEnvironment se = getParentEnvironment();
        
        return se != null && relative ? SVector.add(position, se.getPosition()) : position;
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

    public boolean getRelative() {
        return relative;
    }

    public void setRelative(boolean relative) {
        this.relative = relative;
    }
}
