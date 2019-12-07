package sunrise.entity.graphics;

import sunrise.entity.space.polygon.SCircle;

public interface CircleDrawable extends SDrawable {

    String getTexture();
    SCircle getDrawBox();
}
