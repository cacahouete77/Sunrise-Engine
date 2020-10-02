package engine.entity.space.polygon;

import engine.entity.space.SVector;
import engine.entity.space.SShape;

public abstract class SPolygon extends SShape {
    protected SPolygon(SVector position, double angle) {
        super(position, angle);
    }

    public abstract SVector[] getVertex();
}
