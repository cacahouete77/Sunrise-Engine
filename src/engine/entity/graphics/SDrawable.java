package engine.entity.graphics;

import engine.entity.space.SShape;

import java.awt.image.BufferedImage;

public interface SDrawable {

    BufferedImage getTexture();
    SShape getDrawBox();
}
