package engine.entity.space.polygon;

import engine.entity.space.SVector;

public abstract class SAABB extends SPolygon {
    private SVector dimensions;

    protected SAABB(SVector position, SVector dimensions) {
        super(position, 0);

        this.dimensions = new SVector(dimensions);
    }

    @Override
    public SVector[] getVertex() {
        SVector[] vectors = new SVector[4];

        vectors[0] = new SVector(getPosition());
        vectors[1] = new SVector(getPosition());
        vectors[2] = new SVector(getPosition());
        vectors[3] = new SVector(getPosition());

        vectors[1].setX(vectors[1].getX() + dimensions.getX());

        vectors[2].add(dimensions);

        vectors[3].setY(vectors[3].getY() + dimensions.getY());

        return vectors;
    }

    public SVector getDimensions() {
        return dimensions;
    }

    public void setDimensions(SVector dimensions) {
        this.dimensions = dimensions;
    }

    public double getAngle() {
        return 0;
    }

    public void setAngle(double angle) {
        return;
    }

    public void rotate(double rotation) {
        return;
    }

}
