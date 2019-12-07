package sunrise.test;

import sunrise.entity.graphics.AABBDrawable;
import sunrise.entity.space.SVector;
import sunrise.entity.space.polygon.SAABB;

public class TestSquare extends SAABB implements AABBDrawable {

    protected TestSquare(SVector position, SVector size) {
        super(position, size);
    }

    @Override
    public String getTexture() {
        return "TestSquare";
    }

    @Override
    public SAABB getDrawBox() {
        return this;
    }
}
