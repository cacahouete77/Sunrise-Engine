package sunrise.environment;

import sunrise.entity.SEntity;
import sunrise.entity.base.SUpdateable;
import sunrise.entity.graphics.AABBDrawable;
import sunrise.entity.graphics.CircleDrawable;
import sunrise.entity.graphics.SDrawable;
import sunrise.entity.graphics.SquareDrawable;
import sunrise.entity.space.SShape;
import sunrise.entity.space.SVector;
import sunrise.entity.space.polygon.SAABB;
import sunrise.entity.space.polygon.SSquare;
import sunrise.environment.collision.CollisionDetector;
import sunrise.environment.collision.UnimplementedCollisionException;
import sunrise.environment.graphics.GraphicsManager;

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

    public SEnvironment(int width, int height, String graphicsPath) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
        synchronized(graphicsLock) {
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

                if (d instanceof SquareDrawable) {
                    //TODO Drawing Squares incorrectly (no custom images)

                    SVector[] points = ((SSquare) d.getDrawBox()).getVertex();

                    for (int i = 0; i < points.length - 1; i++) {
                        if (i % 2 == 1)
                            g2d.setColor(Color.PINK);
                        else
                            g2d.setColor(Color.BLACK);

                        g2d.drawLine((int) points[i].getX(),
                                (int) points[i].getY(),
                                (int) points[i + 1].getX(),
                                (int) points[i + 1].getY());
                    }

                    g2d.setColor(Color.PINK);

                    g2d.drawLine((int) points[points.length - 1].getX(),
                            (int) points[points.length - 1].getY(),
                            (int) points[0].getX(),
                            (int) points[0].getY());

                } else if (d instanceof CircleDrawable) {
                    //TODO not drawing Circles

                } else if (d instanceof AABBDrawable) {
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
                                (int) dimensions.getX(),
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
        }

        return bufferedImage;
    }

    public void setDimensions(int width, int height) {
        synchronized(graphicsLock) {
            double actualRatio = (0.0 + width) / height;
            double wantedRatio = (camera.getDimensions().getX()) / (camera.getDimensions().getY());

            //System.out.println("---");
            //System.out.println(actualRatio);
            //System.out.println(wantedRatio);

            if(wantedRatio > actualRatio) {
                height = (int) (width / wantedRatio);
            } else if(wantedRatio < actualRatio) {
                width = (int) (wantedRatio * height);
            }


            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
