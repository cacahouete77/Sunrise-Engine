package sunrise.game;

import javafx.application.Application;
import sunrise.environment.SEnvironment;
import sunrise.test.STest;
import sunrise.test.TestEnvironment;

import java.awt.image.BufferedImage;

public class SGame implements Runnable {
    //Environment variables
    private SEnvironment environment;

    //Time variables
    private long lastTime = 0;
    private long unprocessedGraphics = 0;
    private long unprocessedComputations = 0;
    private long targetGraphics = (long) (1000.0 / 60);
    private long targetComputations = (long) (1000.0 / 60);

    //Graphics variables
    private SApplication application;

    public SGame(SApplication application, int width, int height) {
        this.application = application;

        this.environment = new TestEnvironment(width, height, "gameplay");
        this.environment.start();
    }

    @Override
    public void run() {
       while(true) {
           update();

           try {
               Thread.sleep(1);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    public void update() {
        if(lastTime == 0) {
            lastTime = System.currentTimeMillis();
        }

        long currentTime = System.currentTimeMillis();

        long difference = currentTime - lastTime;

        unprocessedGraphics += difference;
        unprocessedComputations += difference;

        if(unprocessedGraphics > targetGraphics) {
            application.draw(environment.draw());
            unprocessedGraphics = 0;
        }

        if(unprocessedComputations > targetComputations) {
            environment.update(unprocessedComputations);
            unprocessedComputations = 0;
        }

        lastTime = currentTime;
    }

    public void setDimensions(int width, int height) {
        environment.setDimensions(width, height);
    }
}
