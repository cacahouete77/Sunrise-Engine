package sunrise.environment;

import sunrise.entity.SEntity;
import sunrise.entity.base.Updateable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SEnvironment implements Updateable {
    private BufferedImage bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
    private ArrayList<SEntity> entities;

    public SEnvironment(ArrayList<SEntity> entities) {
        this.entities = entities;
    }

    public void start() {

    }

    public void update(long time) {

    }

    public BufferedImage draw() {
        Graphics2D g2d = bufferedImage.createGraphics();

        

        return bufferedImage;
    }
}
