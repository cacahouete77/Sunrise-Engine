package engine.entity.space.polygon;

import engine.entity.space.SVector;

public abstract class SSquare extends SPolygon {
    private static final double QUARTER_ANGLE = Math.PI / 2;
    private SVector size;

    protected SSquare(SVector position, SVector size, double angle) {
        super(position, angle);

        this.size = new SVector(size);
    }

    public SVector getSize() {
        return size;
    }

    public void setSize(SVector size) {
        this.size = size;
    }

    public SVector[] getVertex() {
        SVector[] vertex = new SVector[4];

        double angle = size.angle();
        double length = Math.sqrt(size.getX() * size.getX() + size.getY() * size.getY());

        while (angle < 0)
            angle += QUARTER_ANGLE;

        while (angle >= QUARTER_ANGLE)
            angle -= QUARTER_ANGLE;

        double cangle = angle + 2 * (QUARTER_ANGLE - angle);

        angle += this.getAngle();
        cangle += this.getAngle();

        vertex[0] = new SVector(Math.cos(angle) * length, Math.sin(angle) * length);
        vertex[1] = new SVector(Math.cos(cangle) * length, Math.sin(cangle) * length);
        vertex[2] = new SVector(Math.cos(angle + Math.PI) * length, Math.sin(angle + Math.PI) * length);
        vertex[3] = new SVector(Math.cos(cangle + Math.PI) * length, Math.sin(cangle + Math.PI) * length);

        for(SVector vector : vertex)
            vector.add(this.getPosition());

        return vertex;
    }
}
