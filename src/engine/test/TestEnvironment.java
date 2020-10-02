package engine.test;

import engine.entity.space.SShape;
import engine.entity.space.SVector;
import engine.entity.space.polygon.SAABB;
import engine.environment.SEnvironment;

public class TestEnvironment extends SEnvironment {

    public TestEnvironment(double width, double height, String graphicsPath) {
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
