package engine.environment;

import engine.entity.SEntity;
import engine.entity.base.SUpdateable;
import engine.entity.graphics.AABBDrawable;
import engine.entity.graphics.SDrawable;
import engine.entity.space.SVector;
import engine.entity.space.polygon.SAABB;
import engine.environment.collision.CollisionDetector;
import engine.environment.collision.UnimplementedCollisionException;
import engine.environment.graphics.GraphicsManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class SEnvironment implements SUpdateable {
    //Graphics
    private GraphicsManager gm;
    private Graphics2D g2d;
    private BufferedImage bufferedImage;
    private final Object graphicsLock = new Object();

    //Collisions
    private CollisionDetector cd = new CollisionDetector();

    //Entities
    protected SAABB camera;
    protected ArrayList<SDrawable> drawables;
    protected ArrayList<SUpdateable> updateables;

    public SEnvironment(double width, double height, String graphicsPath) {
        bufferedImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D) bufferedImage.getGraphics();
        gm = new GraphicsManager(graphicsPath);
    }

    //Updateable functions
    public void start() {
        drawables = new ArrayList<>();
        updateables = new ArrayList<SUpdateable>();
    }

    public void update(long time) {
        for(SUpdateable u : updateables)
            u.update(time);
    }

    public void end() {

    }
    //END Updateable

    //Graphics functions
    public BufferedImage draw() {

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        for (SDrawable d : drawables) {

            try {
                if (!cd.detectCollision(camera, d.getDrawBox())) {
                    continue;
                }
            } catch (UnimplementedCollisionException uce) {
                continue;
            }

            if (d instanceof AABBDrawable) {
                //Drawing AABB correctly
                SAABB drawBox = ((SAABB) d.getDrawBox());
                BufferedImage texture = gm.getTexture(d.getTexture());

                SVector position = new SVector(drawBox.getPosition());
                SVector dimensions = new SVector(drawBox.getDimensions());

                position.subtract(camera.getPosition());
                position.setX(position.getX() / camera.getDimensions().getX() * bufferedImage.getWidth());
                position.setY(position.getY() / camera.getDimensions().getY() * bufferedImage.getHeight());
                dimensions.setX(dimensions.getX() / camera.getDimensions().getX() * bufferedImage.getWidth());
                dimensions.setY(dimensions.getY() / camera.getDimensions().getY() * bufferedImage.getHeight());

                if (texture == null) {

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
                            (int) dimensions.getY());

                } else {

                    //If texture found, draw it
                    g2d.drawImage(texture,
                            (int) position.getX(),
                            (int) position.getY(),
                            (int) dimensions.getX(),
                            (int) dimensions.getY(),
                            null);
                }
            }
        }

        return bufferedImage;
    }

    public void setDimensions(double width, double height) {
        synchronized(graphicsLock) {
            double actualRatio = (0.0 + width) / height;
            double wantedRatio = (camera.getDimensions().getX()) / (camera.getDimensions().getY());

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
        if(entity instanceof SDrawable)
            drawables.add((SDrawable) entity);

        if(entity instanceof SUpdateable)
            updateables.add((SUpdateable) entity);
    }
    //END Entities
}
