package engine.entity.graphics;

import engine.entity.space.polygon.SAABB;

import java.awt.image.BufferedImage;

public interface AABBDrawable extends SDrawable {

    BufferedImage getTexture();
    SAABB getDrawBox();
}
