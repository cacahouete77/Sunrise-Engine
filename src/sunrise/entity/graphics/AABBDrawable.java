package sunrise.entity.graphics;

import sunrise.entity.space.polygon.SAABB;

public interface AABBDrawable extends SDrawable {

    String getTexture();
    SAABB getDrawBox();
}
