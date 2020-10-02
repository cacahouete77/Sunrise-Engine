package game.environments.menu;

import engine.entity.base.SUpdateable;
import engine.entity.graphics.AABBDrawable;
import engine.entity.space.SVector;
import engine.entity.space.polygon.SAABB;

import java.awt.image.BufferedImage;

public class SCursor extends SAABB implements AABBDrawable, SUpdateable {

    public SCursor(SVector position, SVector dimensions) {
        super(position, dimensions);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(long time) {
        SVector halfDimensions = new SVector(this.getDimensions());
        halfDimensions.multiply(0.5);

        ;
        this.setPosition(SVector.subtract(this.getParentEnvironment().getCursor(), halfDimensions));
    }

    @Override
    public void end() {

    }

    @Override
    public BufferedImage getTexture() {
        return null;
    }

    @Override
    public SAABB getDrawBox() {
        return this;
    }
}
