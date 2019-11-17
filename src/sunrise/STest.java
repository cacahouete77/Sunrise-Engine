package sunrise;

import sunrise.entity.base.SVector;
import sunrise.entity.space.polygon.SSquare;
import sunrise.environment.collision.CollisionDetector;

public class STest {
    public static void main(String[] args) {
        SSquare square1 = new SSquare(new SVector(0, 0), new SVector(1, 1), 0) {
            @Override
            public void start() {

            }

            @Override
            public void update(long time) {

            }
        };

        SSquare square2 = new SSquare(new SVector(2, 2), new SVector(1, 1), 0.3) {
            @Override
            public void start() {

            }

            @Override
            public void update(long time) {

            }
        };

        SVector[] vertex = square1.getVertex();
        for(SVector vector : vertex)
            System.out.println(vector);

        vertex = square2.getVertex();
        for(SVector vector : vertex)
            System.out.println(vector);

        CollisionDetector cd = new CollisionDetector();

        System.out.println(cd.detectCollision(square1, square2));
    }
}
