package sunrise.entity.space.polygon;

import sunrise.entity.space.SVector;
import sunrise.entity.space.SShape;

public abstract class SPolygon extends SShape {
    protected SPolygon(SVector position, double angle) {
        super(position, angle);
    }

    public abstract SVector[] getVertex();
}
