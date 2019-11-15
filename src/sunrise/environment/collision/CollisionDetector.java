package sunrise.environment.collision;

import sunrise.entity.space.SShape;
import sunrise.entity.space.polygon.SSquare;

public class CollisionDetector {

    public boolean detectCollision(SShape s1, SShape s2) throws UnimplementedCollisionException {
        throw new UnimplementedCollisionException();
    }

    public boolean detectCollision(SSquare s1, SSquare s2) {
        return false; //TODO
    }
}
