package game.environments;

import engine.entity.space.SVector;
import engine.environment.SEnvironment;
import game.environments.menu.SCursor;

public class InitialEnvironment extends SEnvironment {

    public InitialEnvironment(double width, double height, String graphicsPath) {
        super(width, height, graphicsPath);
    }

    public void start() {
        super.start();

        this.addEntity(new SCursor(new SVector(0, 0), new SVector(1, 1)));
    }
}
