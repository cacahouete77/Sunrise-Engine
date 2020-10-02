package engine.entity.graphics;

import engine.entity.space.polygon.SAABB;

public interface AABBDrawable extends SDrawable {

    String getTexture();
    SAABB getDrawBox();
}
