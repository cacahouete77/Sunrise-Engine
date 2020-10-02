package engine.environment;

import engine.SGame;
import engine.entity.SEntity;
import engine.entity.base.SUpdateable;
import engine.entity.graphics.AABBDrawable;
import engine.entity.graphics.SDrawable;
import engine.entity.space.SVector;
import engine.entity.space.polygon.SAABB;
import engine.environment.collision.CollisionDetector;
import engine.environment.collision.UnimplementedCollisionException;
import engine.environment.graphics.GraphicsManager;
import engine.environment.graphics.UnimplementedDrawingException;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class SEnvironment extends SAABB implements SUpdateable {
    //Upper class
    private SGame game;

    //Graphics
    private GraphicsManager gm;
    private Graphics2D g2d;
    private BufferedImage bufferedImage;
    private double windowWidth, windowHeight;
    private final Object graphicsLock = new Object();

    //Collisions
    private CollisionDetector cd = new CollisionDetector();

    //Entities
    private SVector cursor;
    private ArrayList<SEnvironment> environments;
    private ArrayList<SDrawable> drawables;
    private ArrayList<SUpdateable> updateables;

    public SEnvironment(double width, double height, String graphicsPath) {
        super(new SVector(0, 0), new SVector(16, 9));

        bufferedImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D) bufferedImage.getGraphics();
        gm = new GraphicsManager(graphicsPath);

        cursor = new SVector(0, 0);
    }

    //Updateable functions
    public void start() {
        environments = new ArrayList<>();
        drawables = new ArrayList<>();
        updateables = new ArrayList<>();
    }

    public void update(long time) {
        for(SUpdateable u : updateables)
            u.update(time);
    }

    public void end() {
        for(SUpdateable u : updateables)
            u.end();
    }
    //END Updateable

    //Upper class functions
    public void setGame(SGame game) {
        this.game = game;
    }

    public void requestEnvironment(SEnvironment se) {
        game.requestEnvironment(se);
    }
    //END Upper class

    //Graphics functions
    public BufferedImage draw() {

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        for (SDrawable d : drawables) {

            try {
                if (!cd.detectCollision(this, d.getDrawBox())) {
                    continue;
                }
            } catch (UnimplementedCollisionException uce) {
                continue;
            }

            if (d instanceof AABBDrawable) {
                //Drawing AABB correctly
                SAABB drawBox = ((SAABB) d.getDrawBox());
                BufferedImage texture = d.getTexture();

                SVector position = new SVector(drawBox.getPosition());
                SVector dimensions = new SVector(drawBox.getDimensions());

                position.subtract(this.getPosition());
                position.setX(position.getX() / this.getDimensions().getX() * bufferedImage.getWidth());
                position.setY(position.getY() / this.getDimensions().getY() * bufferedImage.getHeight());
                dimensions.setX(dimensions.getX() / this.getDimensions().getX() * bufferedImage.getWidth());
                dimensions.setY(dimensions.getY() / this.getDimensions().getY() * bufferedImage.getHeight());

                if (texture == null) {
                    //TODO Regler
                    //If texture not found, draw black and pink squares
                    g2d.setColor(Color.BLACK);

                    g2d.fillRect((int) position.getX(),
                            (int) position.getY(),
                            (int) dimensions.getX(),
                            (int) dimensions.getY());

                    g2d.setColor(Color.PINK);

                    dimensions.multiply(0.5);

                    g2d.fillRect((int) position.getX(),
                            (int) position.getY(),
                            (int) dimensions.getX(),
                            (int) dimensions.getY());

                    position.add(dimensions);

                    g2d.fillRect((int) position.getX(),
                            (int) position.getY(),
                            (int) Math.ceil(dimensions.getX()),
                            (int) Math.floor(dimensions.getY()));

                } else {

                    //If texture found, draw it
                    g2d.drawImage(texture,
                            (int) position.getX(),
                            (int) position.getY(),
                            (int) dimensions.getX(),
                            (int) dimensions.getY(),
                            null);
                }
            } else {
                try {
                    throw new UnimplementedDrawingException("This shape cannot be drawn");
                } catch(Exception ignored) {

                }
            }
        }

        return bufferedImage;
    }

    public void setDimensions(double width, double height) {
        synchronized(graphicsLock) {
            this.windowWidth = width;
            this.windowHeight = height;

            double actualRatio = (0.0 + width) / height;
            double wantedRatio = (this.getDimensions().getX()) / (this.getDimensions().getY());

            if(wantedRatio > actualRatio) {
                height = (int) (width / wantedRatio);
            } else if(wantedRatio < actualRatio) {
                width = (int) (wantedRatio * height);
            }

            bufferedImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
            g2d = (Graphics2D) bufferedImage.getGraphics();
        }
    }
    //END Graphics

    //Entities functions
    public void addEntity(SEntity entity) {

        if(entity instanceof SEnvironment)
            environments.add((SEnvironment) entity);

        if(entity instanceof SDrawable)
            drawables.add((SDrawable) entity);

        if(entity instanceof SUpdateable) {
            updateables.add((SUpdateable) entity);
            ((SUpdateable) entity).start();
        }

        entity.setParentEnvironment(this);
    }
    //END Entities

    //Controller functions
    public void mousePressed(MouseEvent me) {
        updateCursor(me);

        //TODO
    }

    public void mouseMoved(MouseEvent me) {
        updateCursor(me);

        //TODO
    }

    public SVector getCursor() {
        return cursor;
    }

    public void updateCursor(MouseEvent me) {
        double xOffset = (windowWidth - bufferedImage.getWidth()) / 2;
        double yOffset = (windowHeight - bufferedImage.getHeight()) / 2;

        double xCursor = me.getX() - xOffset;
        double yCursor = me.getY() - yOffset;

        xCursor = xCursor / bufferedImage.getWidth() * this.getDimensions().getX() + this.getPosition().getX();
        yCursor = yCursor / bufferedImage.getHeight() * this.getDimensions().getY() + this.getPosition().getY();

        this.cursor = new SVector(xCursor, yCursor);
    }
    //END Controller
}
