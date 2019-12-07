package sunrise.entity.graphics;

import sunrise.entity.space.polygon.SSquare;

public interface SquareDrawable extends SDrawable {

    String getTexture();
    SSquare getDrawBox();
}
