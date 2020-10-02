package engine;

import engine.environment.SEnvironment;
import javafx.scene.input.MouseEvent;

public class SGame implements Runnable {
    //Environment variables
    private SEnvironment environment;

    //Time variables
    private long lastTime = 0;
    private long unprocessedGraphics = 0;
    private long unprocessedComputations = 0;
    public final long targetGraphics = (long) (1000.0 / 60);
    public final long targetComputations = (long) (1000.0 / 60);

    //Graphics variables
    private final SApplication application;

    public SGame(SApplication application, SEnvironment startEnvironment, double width, double height) {
        this.application = application;

        requestEnvironment(startEnvironment);
    }

    @Override
    public void run() {
        //Might be an easier way to avoid complaint from compiler
       while(Math.floor(0) == 0) {
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

    public void end() {
        environment.end();
    }

    public void setDimensions(double width, double height) {
        environment.setDimensions(width, height);
    }

    public void requestEnvironment(SEnvironment se) {
        this.environment = se;
        this.environment.start();
        this.environment.setGame(this);
    }

    //Controller functions
    public void mousePressed(MouseEvent me) {
        environment.mousePressed(me);
    }

    public void mouseMoved(MouseEvent me) {
        environment.mousePressed(me);
    }
}
