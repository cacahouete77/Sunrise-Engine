package sunrise.test;

import sunrise.entity.base.SUpdateable;
import sunrise.entity.space.SShape;
import sunrise.entity.space.SVector;
import sunrise.entity.space.polygon.SAABB;
import sunrise.entity.space.polygon.SSquare;
import sunrise.environment.SEnvironment;

public class TestEnvironment extends SEnvironment {

    public TestEnvironment(int width, int height, String graphicsPath) {
        super(width, height, graphicsPath);
    }

    public void start() {
        super.start();

        addEntity(new TestSquare(new SVector(10, 10), new SVector(10, 10)));
        addEntity(new TestSquare(new SVector(40, 40), new SVector(10, 10)));

        camera = new SAABB (new SVector(0, 0), new SVector(160, 90)) {};
    }

    public void update(long time) {
        super.update(time);

        SVector vector = ((SShape) drawables.get(0)).getPosition();
        vector = SVector.add(vector, new SVector(10, 0));

        ((SShape) drawables.get(0)).setPosition(vector);
    }
}
